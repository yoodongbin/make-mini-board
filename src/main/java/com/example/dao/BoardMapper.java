package com.example.dao;

import com.example.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    public List<BoardDTO> getBoard();

    public List<BoardDTO> getPagingBoard(int start, int end);

    public void setBoard(BoardDTO boardDTO);

    public BoardDTO findBoardBySeq(int board_seq);

    public void deleteBoardBySeq(int board_seq);

    public void updateBoardBySeq(BoardDTO boardDTO);

    public void viewCount(int board_seq);

    public List joinComment(int board_seq);

    public int forPaging();

    public int countOfComments(int board_seq);

    public void setReplyBoard(BoardDTO boardDTO);

    public int findReplyBoardBySeq(int board_seq);

    public List<BoardDTO> searchForKeyword(String keyword);
}
