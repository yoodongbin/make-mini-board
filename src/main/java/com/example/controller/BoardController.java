package com.example.controller;

import com.example.dao.BoardMapper;
import com.example.dto.BoardDTO;
import com.example.dto.MemberDTO;
import com.example.dto.Message;
import com.example.dto.Pagination;
import com.example.service.BoardService;
import com.example.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

@Controller
public class BoardController {
    //로그
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BoardMapper mapper;

    private BoardService boardService;

    public BoardController(BoardMapper mapper, BoardService boardService) {
        this.mapper = mapper;
        this.boardService = boardService;
    }

    //전체 게시글 출력, 게시글 main 화면
    @GetMapping(value = "/board-list")
    public ModelAndView getBoard(HttpSession session, ModelAndView model, @RequestParam(defaultValue = "1") int curPage) {
        int countContents = boardService.forPaging();
//        한 페이지에 몇개씩 보여야 하는지
        Pagination.PAGE_SCALE = 10;
//        몇개의 블록씩 페이지에 표시할 건지 !
        Pagination.BLOCK_SCALE = 3;
        Pagination pagination = new Pagination(countContents, curPage);
        int start = pagination.getPageBegin()-1;
        int end = Pagination.PAGE_SCALE;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        if(memberDTO == null) {
        }else {
            model.addObject("login_info", memberDTO);
        }
        model.addObject("board", boardService.getPagingBoard(start, end));
        model.addObject("paging", pagination);
        model.setViewName("board-list");
        return model;
    }

    //insert form
    @GetMapping("/board-input")
    public String insertBoard(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null) {
            return "member/try-login";
        }else {
            return "board-input";
        }
    }
    //게시글 insert
    @PostMapping(value = "/board-post")
    public ModelAndView setBoardV2(HttpSession session, BoardDTO boardDTO, ModelAndView model) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        boardService.saveBoard(memberDTO.getMember_seq(), boardDTO);
        BoardDTO boardDTO1 = boardService.forGroupNum();
        boardService.setGroupNum(boardDTO1.getBoard_seq());
        Message message = new Message("게시글이 등록됐습니다.", "board-list");
        model.addObject("data", message);
        model.setViewName("message");

        return model;
    }
    //게시글 상세보기 and 댓글기능 !
    @GetMapping(value = "/board-detail")
    public ModelAndView detailBoard(HttpServletRequest httpServletRequest, @RequestParam("board_seq") int board_seq, ModelAndView model) {
        HttpSession session = httpServletRequest.getSession();

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        if (memberDTO == null) {
            Message message = new Message("로그인을 하셔야 볼 수 있습니다.", "try-login");
            model.addObject("data", message);
            model.setViewName("message");
        }else {
            model.addObject("member_info", memberDTO);
            boardService.viewCount(board_seq);
            BoardDTO boardDTO = boardService.findByBoardSeq(board_seq);
            model.addObject("detail", boardDTO);
            //세션추가
            session.setAttribute("b_detail",boardDTO);
            //댓글 부분
            model.addObject("comments", boardService.joinComment(board_seq));
            model.setViewName("board-detail");
        }
        return model;
    }

    //게시글 삭제하기
    @GetMapping(value = "/delete-board")
    public ModelAndView deleteBoard(@RequestParam("board_seq") int board_seq, ModelAndView model) {
        int countComments = mapper.countOfComments(board_seq);
        int countReply = boardService.findReplyBoardBySeq(board_seq);
        if(countComments == 0 && countReply == 0) {
            mapper.deleteBoardBySeq(board_seq);
            mapper.getBoard();
            Message message = new Message("삭제했습니다.", "board-list");
            model.addObject("data", message);
            model.setViewName("message");
        } else {
            Message message = new Message("댓글과 답글이 있는 게시글은 삭제할 수 없습니다.", "board-list");
            model.addObject("data", message);
            model.setViewName("message");
        }
        return model;
    }

    //게시글 수정하기
    @GetMapping(value = "/update-board")
    public String updateBoard(@RequestParam("board_seq") int board_seq, Model model) {
        BoardDTO boardDTO = boardService.findByBoardSeq(board_seq);
        model.addAttribute("detail", boardDTO);
        return "board-update";
    }

    //게시글 update
    @PostMapping(value = "/modify-board")
    public ModelAndView modifyBoard(BoardDTO boardDTO, ModelAndView model) {
        mapper.updateBoardBySeq(boardDTO);
        model.addObject("detail", boardDTO);
        model.addObject("board",mapper.getBoard());
        Message message = new Message("게시글이 수정됐습니다.", "board-list");
        model.addObject("data", message);
        model.setViewName("message");
        return model;
    }

    //답글 !!!!!
    //insert form
    @GetMapping("/board-reply-input")
    public String insertReplyBoard(@RequestParam("board_seq") int board_seq, Model model) {
        BoardDTO boardDTO = boardService.findByBoardSeq(board_seq);
        model.addAttribute("reply_info", boardDTO);
        return "board-reply-input";
    }
    //답글 등록
    @PostMapping(value = "/board-reply-post")
    public ModelAndView setReplyBoard(HttpSession session, BoardDTO boardDTO, ModelAndView model) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        BoardDTO detail_bo = (BoardDTO) session.getAttribute("b_detail");

        boardService.findReplyBoardBySeq(detail_bo.getBoard_seq());
        String titles = "->"+detail_bo.getTitle()+"("+boardService.findReplyBoardBySeq(detail_bo.getBoard_seq())+")";
        boardDTO.setTitle(titles);

        boardDTO.setGroup_num(detail_bo.getGroup_num());
        boardDTO.setBoard_level((detail_bo.getBoard_level())+1);
        boardService.setReplyBoard(memberDTO.getMember_seq(), detail_bo.getBoard_seq(), boardDTO);
        Message message = new Message("답글이 등록됐습니다.", "board-list");
        model.addObject("data", message);
        model.setViewName("message");
        return model;
    }

    //검색
    @RequestMapping("/search-post")
    public ModelAndView searchBoards(@RequestParam("keyword") String keyword, ModelAndView model) {
        model.addObject("searchList", boardService.searchForKeyword(keyword));
        model.setViewName("search-post");
        return model;
    }
}
