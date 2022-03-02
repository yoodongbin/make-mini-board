package com.example.service;

import com.example.dao.BoardMapper;
import com.example.dao.MemberMapper;
import com.example.dto.BoardDTO;
import com.example.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private BoardMapper boardMapper;
    private MemberMapper memberMapper;

    public BoardService(BoardMapper boardMapper, MemberMapper memberMapper) {
        this.boardMapper = boardMapper;
        this.memberMapper = memberMapper;
    }

    public List<BoardDTO> getBoardList() {
        List<BoardDTO> boardDTOList = boardMapper.getBoard();

        return boardDTOList;
    }

    public BoardDTO saveBoard(Integer memberSeq, BoardDTO boardDTO) {
        boardDTO.setMember_seq(memberSeq);
        BoardMapper boardMapper = this.boardMapper;
        boardMapper.setBoard(boardDTO);
        return boardDTO;
    }

    /*
        public CommentDTO getCommentBySeq(Integer commentSeq) {
       CommentDTO commentDTO = commentMapper.findCommentBySeq(commentSeq);
        return commentDTO;
    }
     */

    public BoardDTO findByBoardSeq(Integer boardSeq) {
        BoardDTO boardDTO = boardMapper.findBoardBySeq(boardSeq);
        return  boardDTO;
    }

}
