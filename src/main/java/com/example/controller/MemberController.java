package com.example.controller;

import com.example.dao.MemberMapper;
import com.example.dto.MemberDTO;
import com.example.dto.Message;
import com.example.service.MemberService;
import com.example.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    private MemberMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MemberService memberService;

    public MemberController(MemberMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
    }

    //전체 게시글 출력, 게시글 main 화면
    @GetMapping(value = "/member-list")
    public ModelAndView getMember(ModelAndView model) {
        model.addObject("member",memberService.getMembers());
        model.setViewName("member/member-list");
        return model;
    }

    //insert form
    @GetMapping("/member-input")
    public String insertMember() {
        return "member/member-input";
    }

    //회원 insert
    @PostMapping(value = "/member-post")
    public ModelAndView setMember(MemberDTO memberDTO, ModelAndView model) {
        if(memberDTO.getEmail().isEmpty()) {
            model.setViewName("member/member-input");
        } else {
            int emailDuplication = memberService.checkDuplication(memberDTO);
            if (emailDuplication > 0) {
                Message message = new Message("이메일이 중복됩니다.","member-input");
                model.addObject("data", message);
                model.setViewName("message");
            } else {
                mapper.setMember(memberDTO);
                Message message = new Message("회원가입이 완료됐습니다..","try-login");
                model.addObject("data", message);
                model.setViewName("message");
            }
        }
        return model;
    }

    // 로그인페이지 이동
    @GetMapping("/try-login")
    public String login(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (session.getId() == null) {
        } else {
            session.removeAttribute(session.getId());
            session.invalidate(); //근데 이거 좀 위험함 ! 모든 세션 다 죽임 ㄷ ㄷ
        }
        return "member/try-login";
    }
    //로그인
    @RequestMapping(value = "/member-login",  method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginPost(HttpServletRequest httpServletRequest, @ModelAttribute("member") MemberDTO memberDTO, ModelAndView model) {
        //세션
        HttpSession session = httpServletRequest.getSession();
        MemberDTO login = mapper.loginMember(memberDTO);
            if (login == null) {
                Message message = new Message("이메일 또는 패스워드가 틀립니다.", "try-login");
                model.addObject("data", message);
                model.setViewName("message");
            } else {
                session.setAttribute("login", login);
                SessionUtil.setLoginMemberId(session, memberDTO.getEmail());
                model.setViewName("redirect:/board-list");
            }
            return model;
    }
}
