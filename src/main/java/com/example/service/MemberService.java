package com.example.service;

import com.example.dao.MemberMapper;
import com.example.dto.BoardDTO;
import com.example.dto.MemberDTO;
import jdk.jfr.MemoryAddress;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public List<MemberDTO> getMembers() {
        List<MemberDTO> getMembers = memberMapper.getMembers();
        return getMembers;
    }

    public int checkDuplication(MemberDTO memberDTO) {
        int checkDuplication = memberMapper.checkDuplication(memberDTO);
        return checkDuplication;
    }

}
