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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getMember(Model model) {
        logger.info("컨트롤러-getMember");

        model.addAttribute("member", mapper.getMembers());
        return "member/member-list";
    }

    //insert form
    @GetMapping("/member-input")
    public String insertMember() {
        return "member/member-input";
    }

//    //회원 insert
//    @PostMapping(value = "/member-post")
//    public String setMember(MemberDTO memberDTO, Model model) {
//        if(memberDTO.getEmail().isEmpty()) {
//            return "member/member-input";
//        } else {
//            int emailDuplication = mapper.checkDuplication(memberDTO);
//            logger.info(emailDuplication + "이메일 중복값 얼마게 ?");
//
//            if (emailDuplication > 0) {
//                Message message = new Message("이메일이 중복됩니다.","message");
//                model.addAttribute("data", message);
//
//                return "member/try-login";
//            } else {
//                mapper.setMember(memberDTO);
//                return "member/member-post";
//            }
//        }
//    }
//회원 insert
@PostMapping(value = "/member-post")
public ModelAndView setMember(MemberDTO memberDTO, ModelAndView model) {
    if(memberDTO.getEmail().isEmpty()) {
        model.setViewName("member/member-input");
    } else {
        int emailDuplication = mapper.checkDuplication(memberDTO);
        if (emailDuplication > 0) {
            Message message = new Message("이메일이 중복됩니다.","member-input");
            model.addObject("data", message);
            model.setViewName("message");
        } else {
            mapper.setMember(memberDTO);
            model.setViewName("member/member-post");
        }

    }
    return model;
}

    // 로그인페이지 이동
    @GetMapping("/try-login")
    public String login(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (session.getId() == null) {
            logger.info("로그인 세션 살아있어 ? 아니 로그인세션 없어");
        } else {
            session.removeAttribute(session.getId());
            session.invalidate(); //근데 이거 좀 위험함 ! 모든 세션 다 죽임 ㄷ ㄷ
        }
        return "member/try-login";
    }
    //로그인
    @RequestMapping(value = "/member-login",  method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPost(HttpServletRequest httpServletRequest, @ModelAttribute("member") MemberDTO memberDTO, RedirectAttributes rttr, Error error) {
        //세션
        HttpSession session = httpServletRequest.getSession();
        MemberDTO login = mapper.loginMember(memberDTO);

            if (login == null) {
                rttr.addFlashAttribute("msg", false);

                return "redirect:/try-login";
            } else {
                String returnURL = httpServletRequest.getParameter("returnURL");
                session.setAttribute("login", login);
                SessionUtil.setLoginMemberId(session, memberDTO.getEmail());
                return "redirect:/board-list";
            }

    }
}
