package cn.edu.cupk.learn.common.vo.practice;

import lombok.*;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class PracticeListVO {

    private long practiceId;

    private String title;

    private String state = "";
}
