package com.example.controller;

import com.example.dao.BoardMapper;
import com.example.dto.BoardDTO;
import com.example.dto.CommentDTO;
import com.example.dto.MemberDTO;
import com.example.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class BoardController {
    //로그
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BoardMapper mapper;

    public BoardController(BoardMapper mapper) {
        this.mapper = mapper;
    }

    //전체 게시글 출력, 게시글 main 화면
    @RequestMapping(value = "/board-list")
    public String getBoard(Model model) {

        logger.info("컨트롤러-getBoard");
        model.addAttribute("board", mapper.getBoard());
        return "board-list";
    }

    //insert form
    @GetMapping("/board-input")
    public String insertBoard(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        logger.info(id);
        logger.info("여기 로그인 관련 처리.");

        return "board-input";
    }

    //게시글 insert
    @RequestMapping(value = "/board-post", method = {RequestMethod.POST, RequestMethod.GET})
    public String setBoard(HttpServletRequest httpServletRequest, BoardDTO boardDTO, Model model) {
        HttpSession session = httpServletRequest.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        logger.info("출력 가즈아 ~~~~~~~~~~~~~"+String.valueOf(memberDTO));
        int m_seq = memberDTO.getMember_seq();
        logger.info(String.valueOf(m_seq));
        boardDTO.setMember_seq(m_seq);
        logger.info(String.valueOf(boardDTO));
        mapper.setBoard(boardDTO);
        return "board-post";
    }

    //게시글 상세보기 and 댓글기능 !
    @RequestMapping(value = "/board-detail")
    public String detailBoard(@RequestParam("board_seq") int board_seq, Model model) {
        System.out.println("컨트롤러-detailBoard");
        mapper.viewCount(board_seq);
        BoardDTO boardDTO = mapper.findBoardBySeq(board_seq);
        logger.info(String.valueOf(board_seq));
        model.addAttribute("detail", boardDTO);
        //댓글 부분
        logger.info("댓글 부분 출력해보면"+String.valueOf(mapper.joinComment(board_seq)));
        model.addAttribute("comments", mapper.joinComment(board_seq));
        return "board-detail";
    }

    //게시글 삭제하기
    @RequestMapping(value = "/delete-board")
    public String deleteBoard(@RequestParam("board_seq") int board_seq) {
        logger.info("삭제하러 옴 !");
        mapper.deleteBoardBySeq(board_seq);
        mapper.getBoard();
        return "redirect:/board-list";
    }

    //게시글 수정하기
    @RequestMapping(value = "/update-board")
    public String updateBoard(@RequestParam("board_seq") int board_seq, Model model) {
        logger.info("게시글 수정할 폼을 불러오겠어용"+board_seq);
        BoardDTO boardDTO = mapper.findBoardBySeq(board_seq);
        logger.info(String.valueOf(boardDTO));
        model.addAttribute("detail", boardDTO);
        return "board-update";
    }

    //게시글 update
    @RequestMapping(value = "/modify-board")
    public String modifyBoard(BoardDTO boardDTO, Model model) {
        mapper.updateBoardBySeq(boardDTO);
        model.addAttribute("detail", boardDTO);
        model.addAttribute("board",mapper.getBoard());
        return "board-detail";
    }
}
