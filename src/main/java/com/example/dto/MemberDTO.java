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
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO implements Serializable {
    private int member_seq;
    @NotBlank(message = "이름을 입력하세요")
    private String name;
    @Email(message = "이메일 형식으로 입력하세요")
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    /*
        영문(소,대문자) + 숫자 + 특수문자 + 8~12자 + (영문소, 영문대, 숫자, 특수문자 최소 1글자 이상), 공백 허용 안 함 !
     */
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,12}", message = "비밀번호는 특수문자를 제외한 8~12자리여야 합니다.")
    private String password;
    private Date birthdate;
    private Date create_date;
}
