package com.example.controller;

import com.example.dao.BoardMapper;
import com.example.dto.BoardDTO;
import com.example.dto.MemberDTO;
import com.example.dto.Pagination;
import com.example.service.BoardService;
import com.example.util.SessionUtil;
import com.sun.tools.jconsole.JConsoleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String getBoard(HttpSession session, Model model, @RequestParam(defaultValue = "1") int curPage) {
        int countContents = boardService.forPaging();
//        한 페이지에 몇개씩 보여야 하는지
        Pagination.PAGE_SCALE = 10;
//        몇개의 블록씩 페이지에 표시할 건지 !
        Pagination.BLOCK_SCALE = 3;
        Pagination pagination = new Pagination(countContents, curPage);
        int start = pagination.getPageBegin();
        int end = Pagination.PAGE_SCALE;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        if(memberDTO == null) {
            logger.info("컨트롤러-getBoard");
        }else {
            model.addAttribute("login_info", memberDTO);
        }
        model.addAttribute("board", boardService.getPagingBoard(start, end));
        model.addAttribute("paging", pagination);
        return "board-list";
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
    public String setBoardV2(HttpSession session, BoardDTO boardDTO, Model model) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        BoardDTO boardDTO1 = boardService.saveBoard(memberDTO.getMember_seq(), boardDTO);

        return "board-post";
    }

    //게시글 상세보기 and 댓글기능 !
    @GetMapping(value = "/board-detail")
    public String detailBoard(HttpServletRequest httpServletRequest, @RequestParam("board_seq") int board_seq, Model model) {
        HttpSession session = httpServletRequest.getSession();

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        model.addAttribute("member_info", memberDTO);

        mapper.viewCount(board_seq);
        BoardDTO boardDTO = mapper.findBoardBySeq(board_seq);
        model.addAttribute("detail", boardDTO);
        //세션추가
        session.setAttribute("b_detail",boardDTO);
        //댓글 부분
        model.addAttribute("comments", mapper.joinComment(board_seq));
        return "board-detail";
    }

    //게시글 삭제하기
    @GetMapping(value = "/delete-board")
    public String deleteBoard(@RequestParam("board_seq") int board_seq) {
        int countComments = mapper.countOfComments(board_seq);
        int countReply = boardService.findReplyBoardBySeq(board_seq);
        if(countComments == 0 && countReply == 0) {
            mapper.deleteBoardBySeq(board_seq);
            mapper.getBoard();
        } else {
            logger.info("댓글과 답글이 있는 게시글은 삭제할 수 없습니다.");
        }
        return "redirect:/board-list";
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
    public String modifyBoard(BoardDTO boardDTO, Model model) {
        mapper.updateBoardBySeq(boardDTO);
        model.addAttribute("detail", boardDTO);
        model.addAttribute("board",mapper.getBoard());
        return "board-post";
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
    public String setReplyBoard(HttpSession session, BoardDTO boardDTO, Model model) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        BoardDTO detail_bo = (BoardDTO) session.getAttribute("b_detail");
        boardService.findReplyBoardBySeq(detail_bo.getBoard_seq());
        String titles = detail_bo.getTitle()+"("+boardService.findReplyBoardBySeq(detail_bo.getBoard_seq())+")";
        boardDTO.setTitle(titles);
        boardService.setReplyBoard(memberDTO.getMember_seq(), detail_bo.getBoard_seq(), boardDTO);
        return "board-post";
    }

    //검색
    @RequestMapping("/search-post")
    public String searchBoards(@RequestParam("keyword") String keyword, Model model) {
        logger.info(keyword);
        model.addAttribute("searchList", boardService.searchForKeyword(keyword));
        return "search-post";
    }
}
