package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    /*
    하나만 생성하고 공용으로 사용하면 됨 -> Spring container에 등록하면 하나만 등록됨!
    private final MemberService memberService = new MemberService();
    * */
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
