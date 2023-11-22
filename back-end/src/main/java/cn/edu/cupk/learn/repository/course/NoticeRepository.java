package cn.edu.cupk.learn.repository.course;

import cn.edu.cupk.learn.common.entity.course.NoticePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NoticeRepository extends BaseMapper<NoticePO> {

}
