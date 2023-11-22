package cn.edu.cupk.learn.api.controller;

import cn.edu.cupk.learn.common.domain.bank.BankPractice;
import cn.edu.cupk.learn.common.domain.bank.BankQuestion;
import cn.edu.cupk.learn.common.domain.course.Practice;
import cn.edu.cupk.learn.common.dto.bank.BankPracticeDTO;
import cn.edu.cupk.learn.common.dto.bank.BankQuestionDTO;
import cn.edu.cupk.learn.common.dto.course.*;
import cn.edu.cupk.learn.common.dto.practice.PracticeInviteDTO;
import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.enums.QuestionType;
import cn.edu.cupk.learn.common.exception.ApiException;
import cn.edu.cupk.learn.common.vo.PageVO;
import cn.edu.cupk.learn.common.vo.bank.BankPracticeVO;
import cn.edu.cupk.learn.common.vo.practice.PracticeVO;
import cn.edu.cupk.learn.core.manager.KeyManager;
import cn.edu.cupk.learn.core.service.AuthService;
import cn.edu.cupk.learn.core.service.BankService;
import cn.edu.cupk.learn.core.service.CourseService;
import cn.edu.cupk.learn.core.service.PracticeService;
import cn.edu.cupk.learn.util.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.stream.Collectors;

/**
 * 教师模块api
 *
 * @author 箬玉
 * @author Icy
 * @version 1.0.0
 * @apiNote 教师端API
 * @since 1.0.0
 */
@Slf4j
@Api(tags = "教师模块")
@Validated
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/teacher")
@PreAuthorize("hasAuthority('role:teacher')")
public class TeacherController extends BaseController {

    private final AuthService authService;

    private final CourseService courseService;

    private final PracticeService practiceService;

    private final BankService bankService;

    private final KeyManager keyManager;

    private final TimeUtil timeUtil;

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
     * 获取教师课程列表
     *
     * @param page 页码
     * @return 课程列表
     */
    @ApiOperation("获取教师课程列表")
    @GetMapping("/course/list")
    public Object listCourse(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return courseService.listTeacherCourse(page, getUserId());
    }

    /**
     * 获取课程简介
     *
     * @param courseId 课程ID
     * @return 课程信息
     */
    @ApiOperation("获取课程简介")
    @GetMapping("/course/get")
    public Object getCourseIntro(@RequestParam("courseId") Long courseId) {
        checkTeacher(courseId);
        return courseService.getTeacherCourse(courseId);
    }

    /**
     * 删除课程
     *
     * @param courseId 课程ID
     */
    @ApiOperation("删除课程")
    @GetMapping("/course/delete")
    public void deleteCourse(@RequestParam("courseId") long courseId) {
        checkTeacher(courseId);
        courseService.deleteCourse(courseId, getUserId());
    }

    /**
     * 获取课程通知列表
     *
     * @param page     页码
     * @param courseId 课程ID
     */
    @ApiOperation("获取课程通知列表")
    @GetMapping("/course/notice/list")
    public Object listNotice(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
            @RequestParam("courseId") Long courseId) {
        checkTeacher(courseId);
        return courseService.listNotice(page, courseId);
    }

    /**
     * 某课程发布新通知
     *
     * @param form 通知内容表单
     */
    @ApiOperation("发布新通知")
    @PostMapping("/course/notice/add")
    public void addCourseNotice(@NotNull @RequestBody NoticeDTO form) {
        checkTeacher(form.getCourseId());
        courseService.addNotice(form.getTitle(), form.getContent(), form.getCourseId());
    }

    /**
     * 查询某课程的练习列表
     *
     * @param page     页码
     * @param courseId 课程ID
     * @return 练习简略信息列表
     */
    @ApiOperation("获取课程练习列表")
    @PostMapping("/course/practice/list")
    public Object listCoursePractice(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
            @RequestParam("courseId") Long courseId) {
        checkTeacher(courseId);
        PageVO<Practice> vo = courseService.listPractice(page, courseId);
        return new PageVO<>(vo.getRecords().stream()
                .map(PracticeVO::new).collect(Collectors.toList()), vo.getPages());
    }

    /**
     * 查询练习详细信息
     *
     * @param practiceId 练习ID
     * @return 练习详细信息
     */
    @ApiOperation("查询练习详情")
    @GetMapping("/course/practice/get")
    public Object getPractice(@RequestParam("practiceId") Long practiceId) {
        return new PracticeVO(courseService.getPractice(practiceId));
    }

    /**
     * 查询选择某课程的学生列表
     *
     * @param page     页码
     * @param keyWord  关键字
     * @param courseId 课程ID
     * @return 选课学生列表
     */
    @ApiOperation("查询选课学生列表")
    @GetMapping("/course/student/list")
    public Object listCourseStudent(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
            @RequestParam(value = "keyWord", defaultValue = "") String keyWord,
            @RequestParam("courseId") Long courseId) {
        checkTeacher(courseId);
        return courseService.listCourseStudent(page, keyWord, courseId);
    }

    /**
     * 发布新课程
     *
     * @param form 新课程表单
     */
    @ApiOperation("发布新课程")
    @PostMapping("/course/add")
    public void addCourse(@NotNull @RequestBody CourseDTO form) {
        courseService.addCourse(form.getTitle(), form.getContent(), getUserId());
    }

    /**
     * 更改课程信息
     *
     * @param form 课程信息表单
     */
    @ApiOperation("更改课程信息")
    @PostMapping("/course/update")
    public void updateCourse(@NotNull @RequestBody CourseDTO form) {
        checkTeacher(form.getCourseId());
        courseService.updateCourse(form.getCourseId(), form.getTitle(), form.getContent(), getUserId());
    }

    /**
     * 删除课程某通知
     *
     * @param courseId 课程ID
     * @param noticeId 通知ID
     */
    @ApiOperation("删除通知")
    @GetMapping("/course/notice/delete")
    public void deleteNotice(@RequestParam("courseId") Long courseId, @RequestParam("noticeId") Long noticeId) {
        checkTeacher(courseId);
        courseService.deleteNotice(getUserId(), courseId, noticeId);
    }

    /**
     * 邀请码搜索练习
     *
     * @param inviteCode 邀请码
     * @return 练习信息
     */
    @ApiOperation("根据邀请码搜索练习")
    @GetMapping("/course/practice/invite")
    public Object inviteCoursePractice(@RequestParam("inviteCode") @Pattern(regexp = "^[\\w_\\-]{12}$") String inviteCode) {
        return new BankPracticeVO(bankService.inviteBankPractice(inviteCode));
    }

    /**
     * 根据练习邀请码为课程添加练习
     *
     * @param form 邀请码与基本信息表单
     */
    @ApiOperation("根据练习邀请码为课程添加练习")
    @PostMapping("/course/practice/add")
    public void addCoursePractice(@NotNull @RequestBody PracticeInviteDTO form) {
        Short seconds = timeUtil.toSeconds(form.getTimeLimit());
        practiceService.inviteCoursePractice(form.getInviteCode(), form.getTitle(), form.getStartTime(),
                form.getEndTime(), seconds, form.getContent(), getUserId(), form.getCourseId());
    }

    /**
     * 题库新增题目
     *
     * @param form 新题目表单
     */
    @ApiOperation("题库新增题目")
    @PostMapping("/bank/question/add")
    public void addBankQuestion(@NotNull @RequestBody BankQuestionDTO form) {
        QuestionType type = QuestionType.typeOf(form.getType());
        if (type == QuestionType.UNKNOWN) {
            throw new ApiException(ApiCode.DATA_NOT_SUPPORT);
        }
        bankService.addBankQuestion(type, form.getContent(), form.getAnswer(), getUserId());
    }

    /**
     * 拉取题库简略题目列表
     *
     * @param keyWord 关键字
     * @param type    题型
     * @param global  是否全站搜索
     * @param page    页码
     * @return 题目列表
     */
    @ApiOperation("拉取题库简略题目列表")
    @GetMapping("/bank/question/list")
    public Object listBankQuestion(
            @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
            @RequestParam(value = "type", required = false, defaultValue = "无限制")
            @Pattern(regexp = "^((([单多]选)|(判断)|(填空)|(应用))题)|([无不]限制)|(所有)(题型?)?$") String type,
            @RequestParam(value = "global", required = false, defaultValue = "false") Boolean global,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) Integer page) {
        Long teacherId = global ? null : getUserId();
        PageVO<BankQuestion> vo = bankService.listBankQuestion(keyWord, QuestionType.typeOf(type), teacherId, page);
        return new PageVO<>(vo.getRecords().stream()
                .map(keyManager::parseBankQuestionVO).collect(Collectors.toList()), vo.getPages());
    }

    /**
     * 题库新增练习
     *
     * @param form 新练习表单
     * @return 练习邀请码
     */
    @ApiOperation("题库新增练习")
    @PostMapping("/bank/practice/add")
    public String addBankPractice(@NotNull @RequestBody BankPracticeDTO form) {
        Short seconds = timeUtil.toSeconds(form.getTimeLimit());
        return bankService.addBankPractice(form.getTitle(), form.getStartTime(), form.getEndTime(), seconds,
                form.getContent(), getUserId(), form.getQuestion());
    }

    /**
     * 拉取题库简略练习列表
     *
     * @param keyWord 关键字
     * @param page    页码
     * @return 练习列表
     */
    @ApiOperation("拉取题库简略练习列表")
    @GetMapping("/bank/practice/list")
    public Object listBankPractice(
            @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Min(1) Integer page) {
        PageVO<BankPractice> vo = bankService.listBankPractice(keyWord, page);
        return new PageVO<>(vo.getRecords().stream()
                .map(BankPracticeVO::new).collect(Collectors.toList()), vo.getPages());
    }

}
