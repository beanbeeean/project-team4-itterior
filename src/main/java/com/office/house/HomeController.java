package com.office.house;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class HomeController {
	
	@GetMapping(value = {"", "/"})
	public String home() {
		log.info("[HomeController] home()");
		
		return "home";
	}
}
