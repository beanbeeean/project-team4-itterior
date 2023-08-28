package com.office.house.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
	@RequestMapping(value={"/",""})
	public String home() {
		System.out.println("[AdminHomeController] home()");
		
		String nextPage = "admin/home";
		return nextPage;
	}

}
