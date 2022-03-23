package com.example.dao;

import com.example.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<CommentDTO> getComment();

    //댓글 삭제 시 해당 코멘트 작성자와 현재 로그인 세션의 멤버 아이디와 비교하기 위함
    public CommentDTO findCommentBySeq(int comment_seq);

    public List<CommentDTO> myComment(int member_seq);

    public void setComment(CommentDTO commentDTO);

    public void deleteCommentBySeq(int comment_seq);
}
