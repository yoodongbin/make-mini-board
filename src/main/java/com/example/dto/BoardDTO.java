package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private int board_seq;
    private int member_seq;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String board_contents;
    private String upload_image;

    @Override
    public String toString() {
        return "BoardDTO{" +
                "board_seq=" + board_seq +
                ", member_seq=" + member_seq +
                ", title='" + title + '\'' +
                ", board_contents='" + board_contents + '\'' +
                '}';
    }
}
