package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDTO {
    private int comment_seq;
    private int board_seq;
    private int member_seq;
    @NotBlank(message = "내용을 입력하세요.")
    private String comment_contents;
    private Date comment_created;
}
