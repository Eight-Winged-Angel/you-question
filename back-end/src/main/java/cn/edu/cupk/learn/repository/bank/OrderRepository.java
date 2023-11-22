package cn.edu.cupk.learn.repository.bank;

import cn.edu.cupk.learn.common.entity.bank.OrderPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 题号DAO
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
@Repository
public interface OrderRepository extends BaseMapper<OrderPO> {

    void copy(@Param("new") Long newPractice, @Param("old") Long oldPractice);

}
