package com.example.controller;

import com.example.dao.CommentMapper;
import com.example.dto.BoardDTO;
import com.example.dto.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    //로그
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentMapper mapper;

    //댓글 insert
    @GetMapping(value = "/comment-post")
    public String setComment(HttpSession session, CommentDTO commentDTO, Model model) {
        logger.info(String.valueOf(session));
        logger.info((String) session.getAttribute("member_seq"));
        logger.info("컨트롤러-setBoard");
        model.addAttribute("comments",mapper.setComment(commentDTO));
        return "redirect:/";
    }
}
