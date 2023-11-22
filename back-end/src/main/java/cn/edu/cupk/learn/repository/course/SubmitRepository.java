package cn.edu.cupk.learn.repository.course;

import cn.edu.cupk.learn.common.entity.course.SubmitPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 学生提交答案DAO
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Mapper
@Repository
public interface SubmitRepository extends BaseMapper<SubmitPO> {

    /**
     * 学生提交答案前校验参数合法性
     * @param practiceId 练习ID
     * @param questionId 问题ID
     * @param studentId 学生ID
     * @return 是否合法
     */
    Boolean exists(@Param("practiceId") Long practiceId, @Param("questionId") Long questionId,
                   @Param("studentId") Long studentId);

    void insertOrUpdate(SubmitPO submit);

}
