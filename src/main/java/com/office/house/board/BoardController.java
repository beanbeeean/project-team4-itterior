package com.office.house.board;

import com.office.house.user.UserDto;
import com.office.house.util.upload.UploadFileService;
import com.office.house.util.page.PageDefine;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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


    @GetMapping("/get_board_list")
    @ResponseBody
    public Map<String, Object> getBoardList(@RequestParam(value = "sort", required = false) int sort,
                                            @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                            @RequestParam(value="amount", required = false, defaultValue = PageDefine.DEFAULT_BOARD_AMOUNT) int amount,
                                            @RequestParam(required = false, value = "keyword") String keyword,
                                            HttpSession session){
        log.info("[BoardController] getBoardList()");

        Map<String, Object> resultMap = boardService.getBoardList(sort, pageNum, amount, keyword);

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        if(loginedMemberDto != null){
            resultMap.put("u_id", loginedMemberDto.getU_id());
        }else{
            resultMap.put("u_id", "please_login");
        }
        return resultMap;
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

    // 게시물 수정
    @GetMapping("/board_modify_form")
    public Object boardModifyForm(@RequestParam("b_no") int b_no, Model model, HttpSession session) {
        log.info("[BoardController] boardModifyForm");

        String nextPage = "board/board_modify_form";

        BoardDto boardDto = boardService.boardModifyForm(b_no);
        model.addAttribute("boardDto",boardDto);

        String boardAuthor = boardDto.getU_id();
        log.info("작성자: " + boardAuthor);
        model.addAttribute("boardAuthor",boardAuthor);

        return nextPage;
    }

    @PostMapping("/board_modify_confirm")
    @ResponseBody
    public Object boardModifyConfirm(BoardDto boardDto, HttpSession session, Model model, @RequestParam(value="file", required = false) MultipartFile file, @RequestParam("b_no") int b_no){
        log.info("[BoardController] boardModifyConfirm()");

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        boardDto.setU_id(loginedMemberDto.getU_id());
        boardDto.setU_img(loginedMemberDto.getU_img());

        String savedFileName = uploadFileService.uploadBoardThumbnail(loginedMemberDto.getU_id(), file);
        boardDto.setB_thumbnail(savedFileName);

        int result = boardService.boardmodifyConfirm(boardDto, b_no);

        return result;
    }

    @PostMapping("/delete_board_confirm")
    @ResponseBody
    public Object deleteBoardConfirm(@RequestBody Map<String, Object> boardmap){
        log.info("[BoardController] deleteBoardConfirm()");

        Map<String, Object> resultMap = boardService.deleteBoard(boardmap);
        return resultMap;

    }

    // 메인 화면에 뿌려지는 게시물
    @GetMapping("/get_main_board_list")
    @ResponseBody
    public Map<String, Object> getMainBoardList(HttpSession session) {
        log.info("[UserController] getMainBoardList()");

        Map<String, Object> resultMap = boardService.getMainBoardList();

        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        if (loginedMemberDto != null) {
            resultMap.put("u_id", loginedMemberDto.getU_id());
        } else {
            resultMap.put("u_id", "please_login");
        }
        return resultMap;
    }

}