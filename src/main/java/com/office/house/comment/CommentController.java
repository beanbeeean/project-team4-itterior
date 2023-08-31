package com.office.house.comment;

import com.office.house.board.BoardDto;
import com.office.house.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Log4j2
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/regist_reply_confirm")
    @ResponseBody
    public Object registReplyConfirm(CommentDto commentDto, HttpSession session){
        log.info("[BoardController] boardWriteConfirm()");

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        commentDto.setU_id(loginedMemberDto.getU_id());

        int result = commentService.registReplyConfirm(commentDto);

        return result;

    }

}
