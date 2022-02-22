package com.example.dao;

import com.example.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<CommentDTO> getComment();
}
