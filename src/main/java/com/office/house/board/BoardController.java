package com.office.house.board;

import com.office.house.user.UserDto;
import com.office.house.user.util.UploadFileService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    UploadFileService uploadFileService;

    @GetMapping({"/", ""})
    public String home() {
        log.info("[BoardController] home()");

        String nextPage = "board/home";

        return nextPage;
    }
    @GetMapping("/board_write_form")
    public String boardWriteForm(){
        log.info("[BoardController] boardWriteForm()");

        String nextPage = "board/board_write_form";

        return nextPage;
    }

    @PostMapping("/board_write_confirm")
    @ResponseBody
    public Object boardWriteConfirm(BoardDto boardDto, HttpSession session , @RequestParam(value="file", required = false) MultipartFile file){
        log.info("[BoardController] boardWriteConfirm()");

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        boardDto.setU_id(loginedMemberDto.getU_id());
        boardDto.setU_img(loginedMemberDto.getU_img());

        String savedFileName = uploadFileService.uploadBoardThumbnail(loginedMemberDto.getU_id(), file);
        boardDto.setB_thumbnail(savedFileName);

        int result = boardService.boardWriteConfirm(boardDto);

        return result;

    }

    // 게시물 업로드 할 때 이미지 업로드
    @PostMapping("/upload")
    public ModelAndView image(MultipartHttpServletRequest request, HttpSession session) throws Exception {

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");

        // ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
        // modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
        // jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단
        // WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로 등록해야 함
        ModelAndView mav = new ModelAndView("jsonView");

        // ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서 uploadFile에 저장함
        MultipartFile uploadFile = request.getFile("upload");

        String savedFileName = uploadFileService.uploadBoard(loginedMemberDto.getU_id(), uploadFile);

        // uploaded, url 값을 modelandview를 통해 보냄
        mav.addObject("uploaded", true); // 업로드 완료
        mav.addObject("url", "/userBoardUploadImg/"+loginedMemberDto.getU_id()+"/"+savedFileName); // 업로드 파일의 경로

        return mav;
    }

    // board의 전체 게시물 보여주기
    @GetMapping("/get_board_list")
    @ResponseBody
    public Map<String, Object> getBoardList(){
        log.info("[BoardController] getBoardList()");

        Map<String, Object> map = boardService.getBoardList();

        return map;
    }

    // 게시물 디테일
    @GetMapping("/get_board")
    public Object getBoard(@RequestParam("b_no") int b_no, Model model, HttpSession session) {
        log.info("[BoardController] getBoard");

        String nextPage = "board/get_board";

        BoardDto boardDto = boardService.getBoard(b_no);
        model.addAttribute("boardDto",boardDto);

        String boardAuthor = boardDto.getU_id();
        log.info("작성자: " + boardAuthor);
        model.addAttribute("boardAuthor",boardAuthor);

        return nextPage;
    }

}
