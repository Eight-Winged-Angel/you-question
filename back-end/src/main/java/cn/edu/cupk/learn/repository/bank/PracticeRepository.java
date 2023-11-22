package cn.edu.cupk.learn.repository.bank;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.domain.course.StudentQuestion;
import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeRepository extends BaseMapper<PracticePO> {

    /**
     * 查找练习所有题目以及该学生曾经提交的答案
     * @param practiceId 练习ID
     * @param studentId 学生ID
     * @return 题目与答案列表
     */
    List<StudentQuestion> selectQuestionStudent(
            @Param("practiceId") Long practiceId, @Param("studentId") Long studentId);

    /**
     * 判别练习邀请码是否已被使用
     * @param inviteCode 邀请码
     * @return 是否已被使用
     */
    Boolean existsInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 根据邀请码选取练习
     * @param inviteCode 邀请码
     * @return 练习
     */
    PracticePO selectByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 分页查询课程练习列表
     * @param page 页码
     * @param courseId 课程ID
     * @return 联系列表
     */
    IPage<BankPractice> pageByCourse(IPage<BankPractice> page, @Param("courseId") Long courseId);

    /**
     * 分页关键字查询题库练习列表
     * @param page 页码
     * @param keyWord 关键字
     * @return 题库练习列表
     */
    IPage<BankPractice> selectAllWithIntro(IPage<BankPractice> page, @Param("keyWord") String keyWord);

    /**
     * 邀请码查询题库练习
     * @param inviteCode 邀请码
     * @return 题库练习
     */
    BankPractice selectByInviteWithIntro(@Param("inviteCode") String inviteCode);
}
