package com.example.controller;

import com.example.dao.MemberMapper;
import com.example.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class MemberController {
    @Autowired
    private MemberMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //전체 게시글 출력, 게시글 main 화면
    @RequestMapping(value = "/member-list")
    public String getMember(Model model) {
        logger.info("컨트롤러-getMember");
        model.addAttribute("member", mapper.getMember());
        return "member/member-list";
    }

    //insert form
    @GetMapping("/member-input")
    public String insertMember() {
        return "member/member-input";
    }

    //회원 insert
    @RequestMapping(value = "/member-post", method = RequestMethod.POST)
    public String setMember(MemberDTO memberDTO, Model model) {
        logger.info("컨트롤러-setBoard");
        logger.info(memberDTO.getEmail());
        if(memberDTO.getEmail().isEmpty()) {
            logger.info("정보를 입력하세요.");
            return "member/member-input";
        } else {
            int emailDuplication = mapper.checkDuplication(memberDTO);
            if (emailDuplication != 0) {
                logger.info("이미 가입된 회원입니다.");
                return "member/try-login";
            } else {
                logger.info("회원가입 성공");
                mapper.setMember(memberDTO);
                return "member/member-post";
            }
        }
    }
    // 로그인페이지 이동
    @GetMapping("/try-login")
    public String login() {
        return "member/try-login";
    }
    //로그인
    @RequestMapping(value = "/member-login",  method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPost(HttpServletRequest req, @ModelAttribute("member") MemberDTO memberDTO, RedirectAttributes rttr, Error error) {
        logger.info(error.getMessage());
        HttpSession session = req.getSession();
            MemberDTO login = mapper.loginMember(memberDTO);
            session.setAttribute("member", login);
            logger.info("뭐가 찍히냐 ? " + login);
            if (login == null) {
                System.out.println("로그인 실패");
                rttr.addFlashAttribute("msg", false);
                return "redirect:/try-login";
            } else {
                System.out.println("로그인 성공");
                return "redirect:/board-list";
            }

    }
}
