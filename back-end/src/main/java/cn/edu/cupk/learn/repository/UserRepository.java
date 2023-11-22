package cn.edu.cupk.learn.repository;

import cn.edu.cupk.learn.common.entity.UserPO;
import cn.edu.cupk.learn.common.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户DAO
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
@Repository
public interface UserRepository extends BaseMapper<UserPO> {

    /**
     * 根据手机号查询用户是否存在
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 根据手机号查询用户PO
     * @param phone 手机号
     * @return 用户PO
     */
    UserPO selectByPhone(String phone);

    /**
     * 读取用户权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> listRoleById(long userId);

    /**
     * 重置密码
     * @param phone 手机号
     * @param password 新密码
     */
    void updatePassword(@Param("phone") String phone, @Param("password") String password);

    /**
     * 查询个人中心信息
     * @param userId 用户ID
     * @return 个人中心信息
     */
    UserVO selectVO(@Param("userId") Long userId);
}
