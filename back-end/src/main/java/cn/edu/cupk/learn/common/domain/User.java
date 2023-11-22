package cn.edu.cupk.learn.common.domain;

import cn.edu.cupk.learn.common.entity.UserPO;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 身份认证业务用户实体类
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class User implements UserDetails {

    private static final long serialVersionUID = -6634318190888926617L;

    private UserPO user;

    private List<String> roleList;

    // 只需序列化roleList即可，否则存入redis出现异常
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public User(UserPO user, List<String> roleList) {
        this.user = user;
        this.roleList = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        // 将roleList中String类型权限信息封装成SimpleGrantedAuthority对象
        authorities = roleList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 使用Spring-Security框架的“用户名”实际上是手机号
    @Override
    public String getUsername() {
        return user.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
