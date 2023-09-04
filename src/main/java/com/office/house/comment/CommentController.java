package com.office.house.comment;

import com.office.house.board.BoardDto;
import com.office.house.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    // 전체 댓글 보여주기
    @GetMapping("/get_comment_list")
    @ResponseBody
    public Map<String, Object> getCommentList(@RequestParam("b_no") int b_no){
        log.info("[CommentController] getCommentList()");

        Map<String, Object> map = commentService.getCommentList(b_no);

        log.info(map.size());

        return map;
    }

    @PostMapping("/regist_reply_confirm")
    @ResponseBody
    public Object registReplyConfirm(CommentDto commentDto, HttpSession session){
        log.info("[CommentController] registReplyConfirm()");

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        commentDto.setU_id(loginedMemberDto.getU_id());
        commentDto.setU_img(loginedMemberDto.getU_img());

        int result = commentService.registReplyConfirm(commentDto);

        return result;
    }

    // 대댓글 작성
    @PostMapping("/regist_re_reply_confirm")
    @ResponseBody
    public Object registRereplyConfirm(CommentDto commentDto, HttpSession session){
        log.info("[CommentController] registRereplyConfirm()");

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        commentDto.setU_id(loginedMemberDto.getU_id());

        int result = commentService.registRereplyConfirm(commentDto);

        return result;
    }

    // 댓글 삭제
    @PostMapping("/delete_comment_confirm")
    @ResponseBody
    public Object deleteCommentConfirm(@RequestBody Map<String, Object> commentmap){
        log.info("[CommentController] deleteCommentConfirm()");

        Map<String, Object> resultMap = commentService.deleteComment(commentmap);
        return resultMap;
    }

    // 댓글 수정
    @PostMapping("/modify_comment_confirm")
    @ResponseBody
    public Object modifyCommentConfirm(CommentDto commentDto, @RequestParam("c_no") int c_no){
        log.info("[CommentController] modifyCommentConfirm()");

        int result = commentService.modifyCommentConfirm(commentDto, c_no);

        return result;
    }

}
