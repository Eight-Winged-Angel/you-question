package cn.edu.cupk.learn.core.impl;

import cn.edu.cupk.learn.common.domain.course.Course;
import cn.edu.cupk.learn.common.domain.course.Practice;
import cn.edu.cupk.learn.common.entity.bank.PracticePO;
import cn.edu.cupk.learn.common.vo.PageVO;
import cn.edu.cupk.learn.common.vo.course.CourseListVO;
import cn.edu.cupk.learn.common.entity.course.CoursePO;
import cn.edu.cupk.learn.common.entity.course.NoticePO;
import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.exception.ApiException;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.vo.course.*;
import cn.edu.cupk.learn.common.vo.student.StudentListVO;
import cn.edu.cupk.learn.common.vo.notice.NoticeListVO;
import cn.edu.cupk.learn.core.service.CourseService;
import cn.edu.cupk.learn.repository.bank.PracticeRepository;
import cn.edu.cupk.learn.repository.course.NoticeRepository;
import cn.edu.cupk.learn.repository.course.CourseRepository;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import cn.edu.cupk.learn.common.enums.DbCode;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xihongshi
 * @version 1.0.0
 * @apiNote 做题模块
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final NoticeRepository noticeRepository;

    private final PracticeRepository practiceRepository;

    private final static Pattern PATTERN_SNO = Pattern.compile("^[\\d\\-]$");

    @Override
    public void addCourse(String title, String content, long teacherId) {
        LocalDateTime now = LocalDateTime.now();
        Date endTime = Date.from(now.plusDays(7).toInstant(ZoneOffset.of("+8")));
        CoursePO coursePO = CoursePO.builder().title(title)
                .content(content)
                .teacherId(teacherId)
                .inviteCode(IdUtil.nanoId(12))
                .endTime(endTime)
                .build();
        courseRepository.insert(coursePO);
    }

    @Override
    @Transactional
    public void deleteCourse(long courseId, long teacherId) throws DbException {
        courseRepository.deleteAllStudent(courseId);
        courseRepository.deleteById(courseId);
    }

    @Override
    public void updateCourse(long courseId, String title, String content, long teacherId) throws DbException {
        courseRepository.updateCourse(courseId, title, content);
    }

    @Override
    public PageVO<CourseListVO> listStudentCourse(int page, long studentId) throws DbException {
        IPage<CourseListVO> res = courseRepository.pageListByStudent(new Page<>(page, 10), studentId);
        if (page > res.getPages()) {
            res = courseRepository.pageListByTeacher(new Page<>(res.getPages(), 10), studentId);
        }
        return new PageVO<>(res);
    }

    @Override
    public PageVO<CourseListVO> listTeacherCourse(int page, long teacherId) throws DbException {
        IPage<CourseListVO> res = courseRepository.pageListByTeacher(new Page<>(page, 10), teacherId);
        if (page > res.getPages()) {
            res = courseRepository.pageListByTeacher(new Page<>(res.getPages(), 10), teacherId);
        }
        return new PageVO<>(res);
    }


    @Override
    public CourseVO getTeacherCourse(long courseId) throws DbException {
        return new CourseVO(courseRepository.selectDomainById(courseId));
    }

    @Override
    public CourseVO getStudentCourse(long courseId) throws DbException {
        Course course = courseRepository.selectDomainById(courseId);
        if (course == null) {
            throw new DbException(DbCode.COURSE_NOT_EXIST);
        }

        course.setInviteCode(null);
        return new CourseVO(course);
    }

    @Override
    public void addNotice(String title, String content, long courseId) throws DbException {
        NoticePO noticePO = NoticePO.builder()
                .noticeTitle(title)
                .noticeContent(content)
                .courseId(courseId).build();

        try {
            noticeRepository.insert(noticePO);
        } catch (Exception e) {
            throw new DbException(DbCode.SQL_ERROR);
        }
    }

    @Override
    public PageVO<NoticeListVO> listNotice(int page, long courseId) throws DbException {
        IPage<NoticeListVO> res = courseRepository.selectAllNoticeList(new Page<>(page, 10), courseId);
        if (res.getPages() < page) {
            res = courseRepository.selectAllNoticeList(new Page<>(res.getPages(), 10), courseId);
        }
        return new PageVO<>(res);
    }

    @Override
    public void addStudentCourse(long studentId, long courseId) throws ApiException {
        Boolean b = courseRepository.existsStudent(studentId, courseId);
        if (b != null && b) {
            throw new ApiException(ApiCode.ALREADY_ADD);
        }

        courseRepository.insertStudent(studentId, courseId);
    }

    @Override
    public void deleteStudent(long studentId, long courseId) throws DbException {
        courseRepository.deleteStudent(studentId, courseId);
    }

    @Override
    public PageVO<StudentListVO> listCourseStudent(int page, String keyWord, long courseId) throws DbException {
        if (StrUtil.isEmpty(keyWord)) {
            keyWord = "";
        }
        Matcher matcher = PATTERN_SNO.matcher(keyWord);
        IPage<StudentListVO> res = courseRepository.pageStudent(matcher.matches(), new Page<>(page, 10), keyWord, courseId);
        if (res.getPages() < page) {
            res = courseRepository.pageStudent(matcher.matches(), new Page<>(res.getPages(), 10), keyWord, courseId);
        }
        return new PageVO<>(res);
    }

    @Override
    public CourseVO getCourseByInviteCode(String inviteCode) throws DbException {
        Course course = courseRepository.selectDomainByInviteCode(inviteCode);
        if (course == null) {
            throw new DbException(DbCode.INVITE_CODE_NOT_FOUND);
        }

        return new CourseVO(course);
    }

    @Override
    public Practice getPractice(long practiceId) throws DbException {
        PracticePO practice = practiceRepository.selectById(practiceId);
        if (practice == null) {
            throw new DbException(DbCode.PRACTICE_NOT_FOUND);
        }

        return new Practice(practice);
    }

    @Override
    public PageVO<Practice> listPractice(int page, long courseId) throws DbException {
        IPage<Practice> res = courseRepository.pagePracticeList(new Page<>(page, 10), courseId);
        if (res.getPages() < page) {
            res = courseRepository.pagePracticeList(new Page<>(res.getPages(), 10), courseId);
        }

        return new PageVO<>(res);
    }

    public void deleteNotice(long teacherId, long courseId, long noticeId) throws DbException {
        courseRepository.deleteNotice(courseId, noticeId);
    }

    @Override
    public boolean verifyStudent(long studentId, long courseId) throws DbException {
        Boolean b = courseRepository.existsStudent(studentId, courseId);
        return b != null && b;
    }
}
