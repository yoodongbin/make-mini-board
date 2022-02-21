package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO implements Serializable {
    private int member_seq;
    private String name;
    @Email(message = "이메일 형식으로 입력하세요")
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String pwd;

}
