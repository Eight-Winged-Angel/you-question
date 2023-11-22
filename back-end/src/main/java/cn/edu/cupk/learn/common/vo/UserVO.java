package cn.edu.cupk.learn.common.vo;

import lombok.*;

import java.util.Date;

/**
 * 用户数据JSON
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private Long userId;

    private Date createTime;

    private Date updateTime;

    private String name;

    private String phone;

    private String schoolName;

    private String QQ;

    private String email;
}
