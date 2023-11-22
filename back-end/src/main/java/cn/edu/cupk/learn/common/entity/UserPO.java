package cn.edu.cupk.learn.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * t_user表
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class UserPO implements Serializable {

    private static final long serialVersionUID = 3042112031982669233L;

    @TableId(type = IdType.AUTO)
    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("user_name")
    private String name;

    @TableField("user_phone")
    private String phone;

    @TableField
    private Long schoolId;

    @TableField("user_password")
    private String password;

    @TableField("user_qq")
    private String QQ;

    @TableField("user_email")
    private String email;

}
