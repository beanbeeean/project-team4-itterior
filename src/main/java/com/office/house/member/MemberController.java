package com.office.house.member;

import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	// create account form
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("[MemberController] createAccountForm()");
		
		String nextPage = "member/create_account_form";
		
		return nextPage;
	}
	
	// create account confirm
	@PostMapping("/create_account_confirm")
    public String createAccountConfirm(MemberDto memberDto){
        log.info("[MemberController] createAccountConfirm()");

        String nextPage = "member/create_account_success";

        int result = memberService.createAccountConfirm(memberDto);
        if(result <= 0)
            nextPage = "member/create_account_fail";

        return nextPage;
    }
	
	// login form
    @GetMapping("/member_login_form")
    public String memberLoginForm(){
        log.info("[MemberController] memberLoginForm()");

        String nextPage = "member/member_login_form";

        return nextPage;
    }
    
    // login confirm
    @PostMapping("/member_login_confirm")
    public String memberLoginConfirm(MemberDto memberDto, HttpSession session){
    	log.info("[MemberController] memberLoginConfirm()");

        String nextPage = "member/member_login_success";

        MemberDto loginedMemberDto =  memberService.memberLoginConfirm(memberDto);

        if(loginedMemberDto != null){
            session.setAttribute("loginedMemberDto",loginedMemberDto);
            session.setMaxInactiveInterval(60*30);
        } else {
            nextPage = "member/member_login_fail";
        }

        return nextPage;
    }
    
    // logout confirm
    @GetMapping("/member_logout_confirm")
    public String memberLogoutConfirm(HttpSession session){
    	log.info("[MemberController] memberLogoutConfirm()");

        String nextPage = "redirect:/";
        session.removeAttribute("loginedMemberDto");

        return nextPage;
    }
    
    // modify form
    @GetMapping("/member_modify_form")
    public String memberModifyForm(){
    	log.info("[MemberController] memberModifyForm()");

        String nextPage = "member/member_modify_form";

        return nextPage;
    }
    
    // modify confirm
    @PostMapping("/member_modify_confirm")
    public String memberModifyConfirm(MemberDto memberDto, HttpSession session){
    	log.info("[MemberController] memberModifyConfirm()");

        String nextPage = "member/member_modify_success";

        MemberDto loginedMemberDto = memberService.memberModifyConfirm(memberDto);
        if(loginedMemberDto!=null){
            session.setAttribute("loginedMemberDto",loginedMemberDto);
            session.setMaxInactiveInterval(60*30);

        } else{
            nextPage = "member/member_modify_fail";
        }

        return nextPage;
    }
    
    // delete confirm
    @PostMapping("/member_delete_confirm")
    @ResponseBody
    public Object memberDeleteConfirm(@RequestBody Map<String, String> msgMap, HttpSession session){
    	log.info("[MemberController] memberDeleteConfirm()");

        Map<String, Object> map = memberService.memberDeleteConfirm(msgMap.get("m_no"));
        if(((int)map.get("result"))>0)
            session.removeAttribute("loginedMemberDto");

        return map;
    }

}
