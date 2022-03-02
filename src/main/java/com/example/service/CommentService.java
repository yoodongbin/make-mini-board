package com.example.service;

import com.example.dao.BoardMapper;
import com.example.dao.CommentMapper;
import com.example.dao.MemberMapper;
import com.example.dto.CommentDTO;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;

@Service
public class CommentService {
    private BoardMapper boardMapper;
    private MemberMapper memberMapper;
    private CommentMapper commentMapper;

    public CommentService(BoardMapper boardMapper, MemberMapper memberMapper, CommentMapper commentMapper) {
        this.boardMapper = boardMapper;
        this.memberMapper = memberMapper;
        this.commentMapper = commentMapper;
    }

    public CommentDTO saveComment(Integer memberSeq, Integer boardSeq, CommentDTO commentDTO) {
        commentDTO.setMember_seq(memberSeq);
        commentDTO.setBoard_seq(boardSeq);
        CommentMapper commentMapper = this.commentMapper;
        commentMapper.setComment(commentDTO);
        return commentDTO;
    }

    public CommentDTO getCommentBySeq(Integer commentSeq) {
       CommentDTO commentDTO = commentMapper.findCommentBySeq(commentSeq);
        return commentDTO;
    }

    public void removeComment(Integer commentSeq) {
        commentMapper.deleteCommentBySeq(commentSeq);
    }
}
