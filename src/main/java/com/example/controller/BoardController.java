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
        Pagination.BLOCK_SCALE = 5;
        Pagination.PAGE_SCALE = 3;
        Pagination pagination = new Pagination(countContents, curPage);
        logger.info(countContents + "카운트 콘텐츠 알려줘");
//여기서 end는 끝값이 아니라 얼만큼 출력할지를 나타낼 것 !
        int start = pagination.getPageBegin();
        int end = Pagination.PAGE_SCALE;
//        int end = pagination.getPageEnd();
        logger.info(start + "스타트값" + end +"end값");
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        if(memberDTO == null) {
            logger.info("컨트롤러-getBoard");
        }else {
            model.addAttribute("login_info", memberDTO);
        }
//        model.addAttribute("board", boardService.getBoardList());
        logger.info(start + "스타트값" + end +"end값");
        model.addAttribute("board", boardService.getPagingBoard(start, end));
        logger.info("맞게 조회되나 보자" +
                ""+boardService.getPagingBoard(start, end));
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
//-----------------------------------서비스 이용 본보기-----------------------------------------------
//    //게시글 insert
//    @RequestMapping(value = "/board-post", method = {RequestMethod.POST, RequestMethod.GET})
//    public String setBoard(HttpServletRequest httpServletRequest, BoardDTO boardDTO, Model model) {
//        HttpSession session = httpServletRequest.getSession();
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
//        int m_seq = memberDTO.getMember_seq();
//        boardDTO.setMember_seq(m_seq);
//        mapper.setBoard(boardDTO);
//        return "board-post";
//    }

    //게시글 insert
    @PostMapping(value = "/board-post")
    public String setBoardV2(HttpSession session, BoardDTO boardDTO, Model model) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("login");
        BoardDTO boardDTO1 = boardService.saveBoard(memberDTO.getMember_seq(), boardDTO);

        return "board-post";
    }
//-----------------------------------서비스 이용 본보기-----------------------------------------------

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
