package com.office.house.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.office.house.board.BoardDto;
import com.office.house.util.page.PageMakerDto;
import com.office.house.util.upload.UploadFileService;
import com.office.house.util.page.PageDefine;
import com.office.house.youtube.YoutubeDto;
import com.office.house.youtube.YoutubeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    YoutubeService youtubeService;

    @Autowired
    UploadFileService uploadFileService;

	// create account confirm
    @ResponseBody
    @PostMapping("/create_account_confirm")
    public Map<String, Object> createAccountConfirm(@RequestBody Map<String, String> msgMap){
        log.info("[UserController] createAccountConfirm()");

        Map<String, Object> resultMap = userService.createAccountConfirm(msgMap);

        return resultMap;
    }

    @GetMapping("/user_login_form")
    public String userLoginForm(){
        log.info("userLoginForm()");
        return "user/user_login_form";
    }

    // login confirm
//    @ResponseBody
//    @PostMapping("/user_login_confirm")
//    public Map<String, Object> userLoginConfirm(@RequestBody Map<String, String> msgMap, HttpSession session, Model model){
//        log.info("[UserController] userLoginConfirm()");
//
//        Map<String, Object> resultMap = userService.userLoginConfirm(msgMap);
//
//        UserDto loginedMemberDto =  (UserDto)resultMap.get("loginedMemberDto");
//
//        if(loginedMemberDto != null){
//            session.setAttribute("loginedMemberDto",loginedMemberDto);
//            session.setMaxInactiveInterval(60*30);
//        }
//        model.addAttribute("loginedMemberDto", loginedMemberDto);
//        return resultMap;
//    }

    // logout confirm
   /* @GetMapping("/user_logout_confirm")
    public String userLogoutConfirm(HttpSession session){
        log.info("[UserController] userLogoutConfirm()");

        String nextPage = "redirect:/";
        session.removeAttribute("loginedMemberDto");

        return nextPage;
    }*/

    // modify form
    @GetMapping("/user_modify_form")
    public String userModifyForm(){
        log.info("[UserController] userModifyForm()");

        String nextPage = "user/user_modify_form";

        return nextPage;
    }

    // modify confirm
    @PostMapping("/user_modify_confirm")
    public String userModifyConfirm(UserDto userDto, HttpSession session, @RequestParam("file") MultipartFile file){
        log.info("[UserController] userModifyConfirm()");

        String nextPage = "user/user_modify_success";
        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");

//        String savedFileName = uploadFileService.upload(loginedMemberDto.getU_id(), file);

        String savedFileName = null;

        log.info(file.getSize());
        log.info(session.getAttribute("u_img"));
        log.info(loginedMemberDto.getU_img());

        if(file.getSize()>0) {
            savedFileName = uploadFileService.upload(loginedMemberDto.getU_id(), file);
        } else {
            userDto.setU_img(loginedMemberDto.getU_img());
        }

        if(savedFileName!=null){
            userDto.setU_id(loginedMemberDto.getU_id());
            userDto.setU_img(savedFileName);
        }

        loginedMemberDto = userService.userModifyConfirm(userDto);

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

    // My Page
    @GetMapping("/user_myPage")
    public String usermyPage(){
        log.info("[UserController] usermyPage()");

        String nextPage = "user/user_myPage";

        return nextPage;
    }

    @GetMapping("/user_board_list")
    public String userBoardList(@RequestParam("u_id") String u_id, Model model){
        log.info("[UserController] userBoardList()");

        String nextPage = "user/user_board_list";
        List<BoardDto> boardDtos = userService.getBoardList(u_id);
        model.addAttribute("boardDtos", boardDtos);
        return nextPage;
    }

    @PostMapping("/get_logged_user_info")
    public ResponseEntity<Map<String, Object>> getLoggedInUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");

        Map<String, Object> response = new HashMap<>();
        if (loginedMemberDto != null) {
            response.put("u_no", loginedMemberDto.getU_no());
        } else {
            response.put("u_no", null);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user_like_board_list")
    public String userLikeBoardList(){
        log.info("[UserController] userLikeList()");

        String nextPage = "user/user_like_board_list";

        return nextPage;
    }

    @GetMapping("/get_user_like_board_list")
    @ResponseBody
    public Map<String, Object> getUserLikeBoardList(@RequestParam(value = "sort", required = false) int sort,
                                                    @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                                    @RequestParam(value="amount", required = false, defaultValue = PageDefine.DEFAULT_BOARD_AMOUNT) int amount,
                                                    @RequestParam(required = false, value = "keyword") String keyword,
                                                    HttpSession session){
        log.info("getUserLikeBoardList");

        Map<String, Object> resultMap = new HashMap<>();
        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        if(loginedMemberDto != null){
            resultMap.put("u_id", loginedMemberDto.getU_id());
            resultMap = userService.getUserLikeBoardList(sort, pageNum, amount, keyword, loginedMemberDto.getU_id());
        }else{
            resultMap.put("u_id", "please_login");
        }

        return resultMap;
    }

    @GetMapping("/user_write_form")
    public String userWriteForm(){
        log.info("[UserController] userWriteForm()");

        String nextPage = "user/user_write_form";

        return nextPage;
    }

    // FIND PASSWORD
    @ResponseBody
    @PostMapping("/find_password_confirm")
    public Map<String, Object> findPasswordConfirm(@RequestBody Map<String, String> msgMap){
        log.info("[UserController] userDeleteConfirm()");

        Map<String, Object> resultMap = userService.findPasswordConfirm(msgMap);

        return resultMap;
    }

    // 좋아요한 상품
    @GetMapping("/user_like_product_list")
    public String showUserLikeProductList(){
        log.info("[UserController] showUserLikeProductList()");

        String nextPage = "user/user_like_product_list";

        return nextPage;
    }

    @GetMapping("get_user_like_products")
    @ResponseBody
    public Map<String, Object>  getUserLikeProducts(
            @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
            @RequestParam(value="amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount,
            HttpSession session){
        log.info("getProducts");
        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> resultMap = userService.getUserLikeProducts(pageNum, amount, loginedMemberDto.getU_id());
        if(loginedMemberDto != null){
            resultMap.put("u_id", loginedMemberDto.getU_id());
        }else{
            resultMap.put("u_id", "please_login");
        }
        return resultMap;
    }

    @GetMapping("user_like_youtube_list")
    public String showUserLikeYoutubeList(Model model, HttpSession session,
                                 @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                 @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_YOUTUBE_AMOUNT) int amount){

        log.info("[UserController] showUserLikeYoutubeList()");

        String nextPage = "user/user_like_youtube_list";

        UserDto userDto = (UserDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map = youtubeService.getLikeYoutubes(keyWord, pageNum, amount, userDto.getU_id());

        List<YoutubeDto> YoutubeDtos = (List<YoutubeDto>) map.get("YoutubeDtos");
        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        model.addAttribute("YoutubeDtos", YoutubeDtos);
        model.addAttribute("pageMakerDto", pageMakerDto);
        model.addAttribute("keyWord", keyWord);

        return nextPage;
    }
}
