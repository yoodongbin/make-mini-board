package com.example.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {
    private int board_seq;
    private int member_seq;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String board_contents;
    private String image;
    private Date create_date;
    private int view;
    private int parent_seq;
    private int group_num;
    private int board_level;
    private CommentDTO commentDTO;
    private MemberDTO memberDTO;
}
