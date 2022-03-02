package com.example.service;

import com.example.dao.BoardMapper;
import com.example.dao.MemberMapper;
import com.example.dto.BoardDTO;
import com.example.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private BoardMapper boardMapper;
    private MemberMapper memberMapper;

    public BoardService(BoardMapper boardMapper, MemberMapper memberMapper) {
        this.boardMapper = boardMapper;
        this.memberMapper = memberMapper;
    }

    public BoardDTO saveBoard(Integer memberSeq, BoardDTO boardDTO){
        boardDTO.setMember_seq(memberSeq);
        BoardMapper boardMapper = this.boardMapper;
        boardMapper.setBoard(boardDTO);

        return boardDTO;

    }
}
