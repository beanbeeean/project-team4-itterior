package com.office.house.board;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping({"/", ""})
    public String home() {
        log.info("[BoardController] home()");

        String nextPage = "board/home";

        return nextPage;
    }

}
