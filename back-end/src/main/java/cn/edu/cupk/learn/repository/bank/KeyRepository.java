package cn.edu.cupk.learn.repository.bank;

import cn.edu.cupk.learn.common.entity.KeyPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 参考答案DAO
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Mapper
@Repository
public interface KeyRepository extends BaseMapper<KeyPO> {
}
