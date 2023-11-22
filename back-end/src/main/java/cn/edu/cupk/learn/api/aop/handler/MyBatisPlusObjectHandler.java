package cn.edu.cupk.learn.api.aop.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 接口说明
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Component
public class MyBatisPlusObjectHandler implements MetaObjectHandler {

    // FIXME: 这里存的是UTC标准时，谁会用LocalDateTime搞东八区搞一下？-
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        setFieldValByName("createTime", date, metaObject);
        setFieldValByName("updateTime", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        setFieldValByName("updateTime", date, metaObject);
    }

}
