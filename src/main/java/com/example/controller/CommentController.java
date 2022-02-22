package com.example.controller;

import com.example.dao.CommentMapper;
import com.example.dto.BoardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommentController {
    //로그
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentMapper mapper;

    //게시글 insert
    @RequestMapping(value = "/comment-post", method = RequestMethod.POST)
    public String setComment(BoardDTO boardDTO, Model model) {
        logger.info("컨트롤러-setBoard");
        model.addAttribute(mapper.getComment());
        return "board-post";
    }
}
