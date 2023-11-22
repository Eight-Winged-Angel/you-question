package cn.edu.cupk.learn.repository.bank;

import cn.edu.cupk.learn.common.domain.bank.BankQuestion;
import cn.edu.cupk.learn.common.entity.bank.QuestionPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 题库DAO
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
@Repository
public interface QuestionRepository extends BaseMapper<QuestionPO> {

    IPage<BankQuestion> pageWithKey(
            IPage<BankQuestion> page,
            @Param("keyWord") String keyWord,
            @Param("type") Short type,
            @Param("teacherId") Long teacherId);

}
