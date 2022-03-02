package com.example.dao;

import com.example.dto.BoardDTO;
import com.example.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    public List<MemberDTO> getMembers();

    //회원가입
    public void setMember(MemberDTO memberDTO);

    //email 중복체크
    public int checkDuplication(MemberDTO memberDTO);

    // 로그인
    public MemberDTO loginMember(MemberDTO memberDTO);

    // 사용자 정보조회
    public MemberDTO getMember(Integer memberSeq);
}
