package cn.edu.cupk.learn.core.manager;

import cn.edu.cupk.learn.common.entity.course.SubmitPO;
import cn.edu.cupk.learn.repository.course.SubmitRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * 接口说明
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Component
public class SubmitManager extends ServiceImpl<SubmitRepository, SubmitPO> {

    public boolean access2Submit(long practiceId, long questionId, long studentId) {
        Boolean b = baseMapper.exists(practiceId, questionId, studentId);
        return b != null && b;
    }

    public void submit(SubmitPO submit) {
        baseMapper.insertOrUpdate(submit);
    }


}
