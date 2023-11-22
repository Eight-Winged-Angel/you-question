package cn.edu.cupk.learn.common.vo.notice;


import lombok.*;

import java.util.Date;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListVO {

    private long noticeId;

    private String title;

    private String content;

    private Date updateTime;
}
