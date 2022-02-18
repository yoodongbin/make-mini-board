package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private int board_seq;
    private int member_seq;
    private String title;
    private String board_contents;

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
