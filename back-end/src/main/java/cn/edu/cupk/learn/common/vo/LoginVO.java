package cn.edu.cupk.learn.common.vo;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String token;

    private List<String> role;
}
