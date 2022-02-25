package com.example.controller;

import com.example.dao.CommentMapper;
import com.example.dto.BoardDTO;
import com.example.dto.CommentDTO;
import com.example.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    //로그
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentMapper mapper;

    //댓글 insert
    @RequestMapping(value = "/comment-post")
    public String setComment(HttpServletRequest httpServletRequest,@RequestParam("board_seq")int board_seq ,CommentDTO commentDTO, Model model) {
        HttpSession session = httpServletRequest.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        logger.info("commentdto 기존 기존"+commentDTO);
        logger.info("commentDTO 출력"+commentDTO);
        int m_seq = memberDTO.getMember_seq();
        logger.info("잘 넘겨주나 ? m seq" + m_seq);
        commentDTO.setMember_seq(m_seq);
//        commentDTO.setBoard_seq(board_seq);
        logger.info("commetDTO 출력"+ commentDTO);
        mapper.setComment(commentDTO);
        logger.info("mapper 다녀왔느뇨");
        return "comment/comment-input";
    }
}
