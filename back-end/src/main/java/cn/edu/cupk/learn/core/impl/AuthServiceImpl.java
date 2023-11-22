package cn.edu.cupk.learn.core.impl;

import cn.edu.cupk.learn.common.domain.SmsVerifyCodeAuthenticationToken;
import cn.edu.cupk.learn.common.domain.User;
import cn.edu.cupk.learn.common.entity.UserPO;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.enums.ServiceCode;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.exception.ServiceException;
import cn.edu.cupk.learn.common.vo.LoginVO;
import cn.edu.cupk.learn.common.vo.UserVO;
import cn.edu.cupk.learn.config.JwtConfig;
import cn.edu.cupk.learn.core.manager.SmsManager;
import cn.edu.cupk.learn.core.service.AuthService;
import cn.edu.cupk.learn.repository.UserRepository;
import cn.edu.cupk.learn.util.RedisUtil;
import cn.hutool.jwt.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录模块
 * @apiNote 登录模块
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SmsManager smsManager;

    @Autowired
    private JwtConfig jwtConfig;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 实现spring-security登录所用接口，Username实际是Phone
     * @param s 用户手机号
     * @return 用户实体
     * @throws DbException 用户手机号未找到
     */
    @Override
    public UserDetails loadUserByUsername(@Validated @NotEmpty String s) {
        // 查询用户信息
        UserPO userPO = userRepository.selectByPhone(s);
        if (userPO == null) {
            throw new DbException(DbCode.USER_NOT_FOUND);
        }

        List<String> roleList = userRepository.listRoleById(userPO.getUserId());
        // 把数据封装成UserDetails返回
        return new User(userPO, roleList);
    }

    @Override
    public void codeSend(String phone) throws ServiceException {
       smsManager.codeSend(phone);
    }

    @Override
    public String codeVerify(String phone, String code) throws ServiceException {
        String value = (String)redisUtil.get("verify:" + phone);
        if (value == null) {
            throw new ServiceException(ServiceCode.VERIFY_TIMEOUT);
        }

        // 检查错误次数
        smsManager.checkTimes(phone);

        // redis存储错误次数
        String verifyTimes = "verifyTimes:" + phone;
        // 输入正确生成token存redis，返回临时token
        if (value.equals(code)) {
            redisUtil.delete("verify:" + phone);
            redisUtil.delete(verifyTimes);
            Map<String, Object> map = redisUtil.createMap("verified", phone);
            String token = JWTUtil.createToken(map, jwtConfig.getPrivateKey().getBytes());
            redisUtil.set(token, phone, 10, TimeUnit.MINUTES);
            return token;
        }

        // 输入错误
        Integer times = (Integer) redisUtil.get(verifyTimes);
        // 第一次输错，还没存储错误次数
        if (times == null) {
            redisUtil.set(verifyTimes, 2, 60, TimeUnit.MINUTES);
            throw new ServiceException(ServiceCode.VERIFY_MISMATCH, 2);
        }

        // 第二次输错，存2写1，还能输一次；存1自减变0不满足该分支
        if (--times > 0) {
            redisUtil.set(verifyTimes, times, 60, TimeUnit.MINUTES);
            throw new ServiceException(ServiceCode.VERIFY_MISMATCH, times);
        }

        // 第三次输错，锁住
        redisUtil.set(verifyTimes, 0, 60, TimeUnit.MINUTES);
        throw new ServiceException(ServiceCode.VERIFY_MISMATCH_TOO_MANY, 15);

    }

    @Override
    public LoginVO register(String token, String name, String password) throws ServiceException, DbException {
        String phone = verifyToken(token);
        String code = passwordEncoder.encode(password);
        UserPO user = UserPO.builder().name(name).phone(phone).password(code).build();
        System.out.println("impl bg");
        System.out.println(user.toString());
        if (userRepository.existsByPhone(phone)) {
            System.out.println("here phone exist");
            throw new DbException(DbCode.PHONE_ALREADY_EXISTS);
        }

        try {
            System.out.println("insert user");
            userRepository.insert(user);
        } catch (Exception e) {
            System.out.println("insert user fail");
            System.out.println(e.toString());
            throw new DbException(DbCode.SQL_ERROR);
        }

        redisUtil.delete(token);
        // 注册完自动登录，返回token
        return loginByPassword(phone, password);
    }

    @Override
    public LoginVO loginByPassword(String phone, String password) throws DbException {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(phone, password);
            return loginRedisToken(authenticationToken);

        } catch (AuthenticationException e) {
            // 判断用户是否存在
            throw new DbException(
                    userRepository.existsByPhone(phone)
                        ? DbCode.WRONG_PASSWORD
                        : DbCode.USER_NOT_FOUND
            );
        }
    }

    @Override
    public LoginVO loginByVerifyCode(String phone, String verifyCode) throws ServiceException {
        try {
            SmsVerifyCodeAuthenticationToken authenticationToken =
                    new SmsVerifyCodeAuthenticationToken(phone, verifyCode);
            return loginRedisToken(authenticationToken);
        } catch (AuthenticationException e) {
            throw new DbException(
                    userRepository.existsByPhone(phone)
                        ? DbCode.WRONG_PASSWORD
                        : DbCode.USER_NOT_FOUND
            );
        }
    }

    @Override
    public void logout() {
        // 获取用户id
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        // 如果无值会被Filter拦截，无需ifn判断
        Long uid = user.getUser().getUserId();
        redisUtil.delete("login:" + uid);
    }

    @Override
    public void reset(String token, String password) throws ServiceException, DbException {
        String phone = verifyToken(token);
        String code = passwordEncoder.encode(password);
        UserPO userPO = userRepository.selectByPhone(phone);
        if (passwordEncoder.matches(password, userPO.getPassword())) {
            throw new DbException(DbCode.SAME_PASSWORD);
        }

        userRepository.updatePassword(phone, code);
        redisUtil.delete(token);
        // 强制重新登录
        try {
            logout();
        } catch (Exception ignored) {}
    }

    // 封装以便于多种登录方法共用
    private LoginVO loginRedisToken(Authentication authenticationToken) {
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        User user = (User)authenticate.getPrincipal();
        String uid = user.getUser().getUserId().toString();
        Map<String, Object> map = redisUtil.createMap("uid", uid);
        redisUtil.set("login:" + uid, user);
        return LoginVO.builder()
                .token(JWTUtil.createToken(map, jwtConfig.getPrivateKey().getBytes()))
                .role(user.getRoleList()).build();
    }

    @NotNull
    private String verifyToken(String token) throws ServiceException {
        String phone = (String)redisUtil.get(token);
        if (phone == null) {
            throw new ServiceException(ServiceCode.TOKEN_TIMEOUT);
        }

        return phone;
    }

    @Override
    public UserVO getAccount(long userId) {
        return userRepository.selectVO(userId);
    }
}
