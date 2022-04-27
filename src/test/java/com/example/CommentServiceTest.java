package com.example;

import com.example.dto.BoardDTO;
import com.example.dto.CommentDTO;
import com.example.service.BoardService;
import com.example.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("댓글 목록을 가지고 온다.")
    public void testGetCommentList_ok(){
        // 댓글 목록은 보통 관련 boardseq가 있어야 가지고 올 수 있는데 테스트용 getcomment를 만들어야 할까요 ??
    }

}
