package com.office.house;

import com.office.house.user.IUserDaoMapper;
import com.office.house.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class HomeController {

	@Autowired
	IUserDaoMapper iUserDaoMapper;
	
	@GetMapping(value = {"", "/"})
	public String home(@AuthenticationPrincipal User user, HttpSession session) {
		log.info("[HomeController] home()");

		if(user != null){
			UserDto userDto = new UserDto();
			userDto.setU_id(user.getUsername());

			UserDto loginedMemberDto = iUserDaoMapper.selectUserForLogin(userDto.getU_id());
			session.setAttribute("loginedMemberDto", loginedMemberDto);
			session.setMaxInactiveInterval(60 * 30);
		}
		session.removeAttribute("loginedAdminDto");

		return "home";
	}
}
