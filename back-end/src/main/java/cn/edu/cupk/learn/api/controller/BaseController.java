package cn.edu.cupk.learn.api.controller;

import cn.edu.cupk.learn.common.domain.User;
import cn.edu.cupk.learn.core.manager.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 提供获取UID方法
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
public abstract class BaseController {

    @Autowired
    protected AuthManager authManager;

    protected Long getUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId();
    }

    protected void checkTeacher(long courseId) {
        checkTeacher(courseId, getUserId());
    }

    protected void checkTeacher(long courseId, long teacherId) {
        authManager.checkTeacher(courseId, teacherId);
    }

}
