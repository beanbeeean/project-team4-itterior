package com.office.house.user;

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
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    UserService userService;
	
	// create account form
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("[UserController] createAccountForm()");
		
		String nextPage = "user/create_account_form";
		
		return nextPage;
	}
	
	// create account confirm
	@PostMapping("/create_account_confirm")
    public String createAccountConfirm(UserDto userDto){
        log.info("[UserController] createAccountConfirm()");

        String nextPage = "user/create_account_success";

        int result = userService.createAccountConfirm(userDto);
        if(result <= 0)
            nextPage = "user/create_account_fail";

        return nextPage;
    }
	
	// login form
    @GetMapping("/user_login_form")
    public String userLoginForm(){
        log.info("[UserController] userLoginForm()");

        String nextPage = "user/user_login_form";

        return nextPage;
    }
    
    // login confirm
    @PostMapping("/user_login_confirm")
    public String userLoginConfirm(UserDto userDto, HttpSession session){
    	log.info("[UserController] userLoginConfirm()");

        String nextPage = "user/user_login_success";

        UserDto loginedMemberDto =  userService.userLoginConfirm(userDto);

        if(loginedMemberDto != null){
            session.setAttribute("loginedMemberDto",loginedMemberDto);
            session.setMaxInactiveInterval(60*30);
        } else {
            nextPage = "user/user_login_fail";
        }

        return nextPage;
    }
    
    // logout confirm
    @GetMapping("/user_logout_confirm")
    public String userLogoutConfirm(HttpSession session){
    	log.info("[UserController] userLogoutConfirm()");

        String nextPage = "redirect:/";
        session.removeAttribute("loginedMemberDto");

        return nextPage;
    }
    
    // modify form
    @GetMapping("/user_modify_form")
    public String userModifyForm(){
    	log.info("[UserController] userModifyForm()");

        String nextPage = "user/user_modify_form";

        return nextPage;
    }
    
    // modify confirm
    @PostMapping("/user_modify_confirm")
    public String userModifyConfirm(UserDto userDto, HttpSession session){
    	log.info("[UserController] userModifyConfirm()");

        String nextPage = "user/user_modify_success";

        UserDto loginedMemberDto = userService.userModifyConfirm(userDto);
        if(loginedMemberDto!=null){
            session.setAttribute("loginedMemberDto",loginedMemberDto);
            session.setMaxInactiveInterval(60*30);

        } else{
            nextPage = "user/user_modify_fail";
        }

        return nextPage;
    }
    
    // delete confirm
    @PostMapping("/user_delete_confirm")
    @ResponseBody
    public Object userDeleteConfirm(@RequestBody Map<String, String> msgMap, HttpSession session){
    	log.info("[UserController] userDeleteConfirm()");

        Map<String, Object> map = userService.userDeleteConfirm(msgMap.get("u_no"));
        if(((int)map.get("result"))>0)
            session.removeAttribute("loginedMemberDto");

        return map;
    }

}
