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

    public BoardDTO findByBoardSeq(Integer boardSeq) {
        BoardDTO boardDTO = boardMapper.findBoardBySeq(boardSeq);
        return  boardDTO;
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

}
