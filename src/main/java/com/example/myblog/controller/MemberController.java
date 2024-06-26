package com.example.myblog.controller;

import com.example.myblog.dto.Member;
import com.example.myblog.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/login") //localhost/login
    public String login(){
        return "member/login";
    }

    @PostMapping(value = "/login")
    public String loginMember(Member member, HttpSession session){ //로그인 처리
        //1. 사용자가 입력한 로그인 데이터와 DB에 저장된 데이터가 맞는지 비교
        try{
            Member loginMember = memberService.loginMember(member);

            if (loginMember != null) {
                //로그인 성공시 로그인한 사람의 name과 memberId를 세션에 저장
                //.setAttribute(키, 값) -> 세션에 값을 저장
                session.setAttribute("name", loginMember.getName());
                session.setAttribute("member_id", loginMember.getMemberId());
                return "redirect:/"; // 로그인 성공 시 홈페이지로 redirect
            }
        } catch(Exception e) {
            // 예외 처리 로직 구현 필요
            throw new RuntimeException(e); // 일단은 RuntimeException으로 던지기
        }
        return "member/login"; //3. 로그인 실패시 login페이지로 이동
    }
    @GetMapping(value = "/logout") //localhost/logout
    public String logoutMember(HttpSession session) {
        //세션에 저장된 name과 member_id 삭제
        session.removeAttribute("name");
        session.removeAttribute("member_id");
        return "redirect:/"; //로그아웃 성공시 index페이지로 redirect
    }
}
