package com.example.controller;

import com.example.dao.CommentMapper;
import com.example.dto.BoardDTO;
import com.example.dto.CommentDTO;
import com.example.dto.MemberDTO;
import com.example.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    //로그
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentMapper mapper;

    private CommentService commentService;

    public CommentController(CommentMapper mapper, CommentService commentService) {
        this.mapper = mapper;
        this.commentService = commentService;
    }

    //댓글 insert
    @PostMapping(value = "/comment-post")
    public String setComment(HttpSession session,CommentDTO commentDTO, Model model) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        BoardDTO detail_bo = (BoardDTO) session.getAttribute("b_detail");
        CommentDTO commentDTO1 = commentService.saveComment(memberDTO.getMember_seq(), detail_bo.getBoard_seq(), commentDTO);
        return "redirect:/board-detail?board_seq="+detail_bo.getBoard_seq();
    }

    //댓글 삭제하기
    @GetMapping(value = "/delete-comment")
    public String deleteComment(HttpSession session, @RequestParam("comment_seq") int comment_seq) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        CommentDTO commentDTO = commentService.getCommentBySeq(comment_seq);
        //현재 로그인한 사용자와, 댓글을 쓴 글쓴이의 멤버 id가 같은지를 확인함
        if (memberDTO.getMember_seq() == commentDTO.getMember_seq()) {
            commentService.removeComment(comment_seq);
        } else {
            logger.info("삭제권한이 없습니다.");
        }
        return "redirect:/board-detail?board_seq="+commentDTO.getBoard_seq();
    }
}
