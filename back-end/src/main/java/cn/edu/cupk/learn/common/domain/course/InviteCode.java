package cn.edu.cupk.learn.common.domain.course;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
public class InviteCode {



    @TableField
    private Long courseId;

    @TableField
    private String inviteCode;

    @TableField
    private Date endTime;
}
