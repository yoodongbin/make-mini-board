package com.example.controller;

import com.example.dao.BoardMapper;
import com.example.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardMapper mapper;

    //전체 게시글 출력, 게시글 main 화면
    @RequestMapping(value = "/board-list")
    public String getBoard(Model model) {
        System.out.println("컨트롤러-getBoard");
        model.addAttribute("board", mapper.getBoard());
        return "board-list";
    }

    //insert form
    @GetMapping("/board-insert")
    public String insertBoard() {
        return "board-insert";
    }

    //게시글 insert
    @RequestMapping(value = "/board-post", method = RequestMethod.POST)
    public String setBoard(BoardDTO boardDTO, Model model) {
        System.out.println("컨트롤러-setBoard");
        mapper.setBoard(boardDTO);
        return "board-post";
    }

    //게시글 상세보기
    @RequestMapping(value = "/board-detail")
    public String detailBoard(@RequestParam("board_seq") int board_seq, Model model) {
        System.out.println("컨트롤러-detailBoard");
        BoardDTO boardDTO = mapper.findBoardBySeq(board_seq);
        model.addAttribute("detail", boardDTO);
        return "board-detail";
    }
    @RequestMapping(value = "/delete-board")
    public String deleteBoard(@RequestParam("board_seq") int board_seq) {
        System.out.println("삭제하러 옴 !");
        mapper.deleteBoardBySeq(board_seq);
        mapper.getBoard();
        /*
        		return "redirect:/qnaDetail.do?qNum="+qNum;
         */
        return "redirect:/board-list";
    }

}
