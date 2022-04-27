package com.example;

import com.example.dto.BoardDTO;
import com.example.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("기본 게시판의 목록을 가지고 온다.")
    public void testGetBoardList_ok(){
        List<BoardDTO> boardList = boardService.getPagingBoard(1, 10);

        assertThat(boardList.size(), is(greaterThan(0)));

        List<BoardDTO> pList = boardList.stream().filter(p->p.getBoard_level() == 0).collect(Collectors.toList());
        List<BoardDTO> cList = boardList.stream().filter(p->p.getBoard_level() == 1).collect(Collectors.toList());
        List<BoardDTO> ccList = boardList.stream().filter(p->p.getBoard_level() == 2).collect(Collectors.toList());
    }



}
