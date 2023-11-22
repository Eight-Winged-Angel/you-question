package cn.edu.cupk.learn.common.vo.student;

import lombok.*;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class StudentListVO {

    private Long userId;

    private String name;

    private String school;

    private String studentId;
}
