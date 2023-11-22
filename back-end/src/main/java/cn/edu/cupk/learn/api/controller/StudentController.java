package cn.edu.cupk.learn.api.controller;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.dto.practice.SubmitDTO;
import cn.edu.cupk.learn.common.vo.PageVO;
import cn.edu.cupk.learn.common.vo.student.StudentPracticeVO;
import cn.edu.cupk.learn.core.manager.PracticeManager;
import cn.edu.cupk.learn.core.service.AuthService;
import cn.edu.cupk.learn.core.service.CourseService;
import cn.edu.cupk.learn.core.service.PracticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.stream.Collectors;

/**
 * @author Genghe
 * @author Icy
 * @version 1.0.0
 * @apiNote 学生端API
 * @since 1.0.0
 */
@Api(tags = "学生模块")
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/student")
@PreAuthorize("hasAuthority('role:student')")
public class StudentController extends BaseController {

    private final AuthService authService;

    private final PracticeService practiceService;

    private final CourseService courseService;

    private final PracticeManager practiceManager;

    /**
     * 获取个人中心数据
     *
     * @return 个人中心数据
     */
    @ApiOperation("获取个人中心数据")
    @GetMapping("/account")
    public Object myAccount() {
        return authService.getAccount(getUserId());
    }

    /**
     * 获取学生课程列表
     *
     * @param page 页码
     * @return 学生课程列表
     */
    @ApiOperation("获取学生课程列表")
    @GetMapping("/course/list")
    public Object listCourse(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return courseService.listStudentCourse(page, getUserId());
    }

    /**
     * 学生加入课程
     *
     * @param courseId 课程ID
     */
    @ApiOperation("学生添加课程")
    @GetMapping("/course/add")
    public void addCourse(@ApiParam("课程id") @RequestParam("courseId") Long courseId) {
        courseService.addStudentCourse(getUserId(), courseId);
    }

    /**
     * 邀请码检索课程信息
     *
     * @param inviteCode 邀请码
     * @return 课程信息
     */
    @ApiOperation("学生获取课程")
    @GetMapping("/course/invite")
    public Object getCourseByInviteCode(@RequestParam("inviteCode") String inviteCode) {
        return courseService.getCourseByInviteCode(inviteCode);
    }

    /**
     * 检验学生是否是课程成员
     *
     * @param courseId 课程ID
     * @return 是否为课程成员
     */
    @ApiOperation("检验学生是否是课程成员")
    @GetMapping("/course/verify")
    public Object verifyStudent(@RequestParam("courseId") Long courseId) {
        return courseService.verifyStudent(getUserId(), courseId);
    }

    /**
     * 学生获取某课程详细信息
     *
     * @param courseId 课程id
     * @return 详细信息
     */
    @ApiOperation("学生获取某课程详细信息")
    @GetMapping("/course/get")
    public Object getCourse(@ApiParam(value = "课程id") @RequestParam("courseId") Long courseId) {
        return courseService.getStudentCourse(courseId);
    }

    /**
     * 学生退课
     *
     * @param courseId 课程ID
     */
    @ApiOperation("学生退课")
    @GetMapping("/course/delete")
    public void deleteCourse(@RequestParam("courseId") Long courseId) {
        courseService.deleteStudent(getUserId(), courseId);
    }

    /**
     * 学生获取通知列表
     *
     * @param page 页码
     * @param courseId 课程ID
     * @return 通知列表
     */
    @ApiOperation("学生获得通知列表")
    @GetMapping("/course/notice/list")
    public Object listNotice(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
            @RequestParam("courseId") Long courseId) {
        return courseService.listNotice(page, courseId);
    }

    /**
     * 学生做题读题
     *
     * @param practiceId 练习ID
     * @return 题目数据
     */
    @ApiOperation("学生做题读题")
    @GetMapping("/course/practice/read")
    public Object readPractice(@RequestParam("practiceId") Long practiceId) {
        return practiceService.studentReadPractice(practiceId, getUserId()).stream()
                .map(practiceManager::parseStudentQuestionVO).collect(Collectors.toList());
    }

    /**
     * 学生做题提交答案
     *
     * @param form 答案表单
     */
    @ApiOperation("学生做题提交答案")
    @PostMapping("/course/practice/submit")
    public void studentSubmit(@NotNull @RequestBody SubmitDTO form) {
        practiceService.studentSubmit(form.getPracticeId(), form.getQuestionId(), getUserId(), form.getAnswer());
    }

    /**
     * 学生获取课程练习列表
     *
     * @param courseId 课程ID
     * @return 课程练习列表
     */
    @ApiOperation("学生获取课程练习列表")
    @GetMapping("/course/practice/list")
    public Object listCoursePractice(
            @RequestParam("courseId") Long courseId,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) Integer page) {
        PageVO<BankPractice> vo = practiceService.listCoursePractice(courseId, page);
        return new PageVO<>(vo.getRecords().stream()
                .map(StudentPracticeVO::new).collect(Collectors.toList()), vo.getPages());
    }

}
