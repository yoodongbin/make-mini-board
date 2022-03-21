package com.example.service;

import com.example.dao.BoardMapper;
import com.example.dao.CommentMapper;
import com.example.dao.MemberMapper;
import com.example.dto.BoardDTO;
import com.example.dto.CommentDTO;
import com.example.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private BoardMapper boardMapper;
    private MemberMapper memberMapper;
    private CommentMapper commentMapper;

    public BoardService(BoardMapper boardMapper, MemberMapper memberMapper, CommentMapper commentMapper) {
        this.boardMapper = boardMapper;
        this.memberMapper = memberMapper;
        this.commentMapper = commentMapper;
    }

    public List<BoardDTO> getBoardList() {
        List<BoardDTO> boardDTOList = boardMapper.getBoard();
        return boardDTOList;
    }

    public List<BoardDTO> getPagingBoard (int start, int end) {
        List<BoardDTO> pagingBoardList = boardMapper.getPagingBoard(start, end);
//        작성자이름 부릉부릉하다가 끝
        pagingBoardList.forEach(s-> System.out.println(memberMapper.getMember(s.getMember_seq())));
        return pagingBoardList;
    }

    public List<BoardDTO> searchForKeyword(String keyword){
        List<BoardDTO> searchList = boardMapper.searchForKeyword(keyword);
        System.out.println(searchList);
        return searchList;
    }

    public BoardDTO saveBoard(Integer memberSeq, BoardDTO boardDTO) {
        boardDTO.setMember_seq(memberSeq);
        BoardMapper boardMapper = this.boardMapper;
        boardMapper.setBoard(boardDTO);
        return boardDTO;
    }

    public BoardDTO forGroupNum() {
        BoardDTO groupNum = boardMapper.forGroupNum();
        return groupNum;
    }

    public void setGroupNum(Integer boardSeq) {
        boardMapper.setGroupNum(boardSeq);
    }

    public BoardDTO findByBoardSeq(Integer boardSeq) {
        BoardDTO boardDTO = boardMapper.findBoardBySeq(boardSeq);
        return  boardDTO;
    }

    public int forPaging() {
        int boardCount = boardMapper.forPaging();
        return boardCount;
    }

    public BoardDTO setReplyBoard(Integer memberSeq, Integer parentSeq, BoardDTO boardDTO) {
        boardDTO.setMember_seq(memberSeq);
        boardDTO.setParent_seq(parentSeq);
        BoardMapper boardMapper = this.boardMapper;
        boardMapper.setReplyBoard(boardDTO);
        return boardDTO;
    }

    public int findReplyBoardBySeq(Integer boardSeq) {
        int replyCount = boardMapper.findReplyBoardBySeq(boardSeq);
        return replyCount;
    }

    public void viewCount(int board_seq) {
        boardMapper.viewCount(board_seq);
    }

    public List<CommentDTO> joinComment(int board_seq) {
        List<CommentDTO> joinComment = boardMapper.joinComment(board_seq);
        return joinComment;
    }


}
