package com.office.house.youtube;

import com.office.house.admin.AdminDto;
import com.office.house.admin.AdminService;
import com.office.house.user.UserDto;
import com.office.house.util.page.PageDefine;
import com.office.house.util.page.PageMakerDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/youtube")
public class YoutubeController {

    @Autowired
    YoutubeService youtubeService;

    //  YOUTUBE START
    @GetMapping({"/youtube_home","","/"})
    public String home(Model model, HttpSession session,
                                 @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
                                 @RequestParam(value = "sort", required = false, defaultValue = "0") String sort,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                 @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_YOUTUBE_AMOUNT) int amount){

        log.info("[YoutubeController] home()");

        String nextPage = "youtube/youtube_home";

        UserDto userDto = (UserDto) session.getAttribute("loginedMemberDto");
        Map<String, Object> map;
        if(userDto != null){
            map = youtubeService.getYoutubes(keyWord, sort, pageNum, amount, userDto.getU_id());
        } else {
            map = youtubeService.getYoutubes(keyWord, sort, pageNum, amount, "");
        }

        List<YoutubeDto> YoutubeDtos = (List<YoutubeDto>) map.get("YoutubeDtos");

        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");
        System.out.println("totalCnt : " + map.get("totalCnt"));

        model.addAttribute("YoutubeDtos", YoutubeDtos);
        model.addAttribute("pageMakerDto", pageMakerDto);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("totalCnt", map.get("totalCnt"));
        model.addAttribute("sort", sort);

        return nextPage;
    }

    //  YOUTUBE END

    @PostMapping("/youtube_like_update")
    @ResponseBody
    public int youtubeLikeUpdate(@RequestBody Map<String, String> msgMap) {
        log.info("[YoutubeController] youtubeLikeConfirm()");

        return youtubeService.youtubeLikeUpdate(msgMap);
    }

    @PostMapping("/youtube_like_delete")
    @ResponseBody
    public int youtubeLikeDelete(@RequestBody Map<String, String> msgMap) {
        log.info("[YoutubeController] youtubeLikeConfirm()");

        return youtubeService.youtubeLikeDelete(msgMap);
    }

    @GetMapping("/get_main_youtube_list")
    @ResponseBody
    public Map<String, Object>  getMainYoutubeList(HttpSession session){
        log.info("[YoutubeController] getMainYoutubeList()");
        Map<String, Object> resultMap = youtubeService.getMainYoutubeList();
        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        if(loginedMemberDto != null){
            resultMap.put("u_id", loginedMemberDto.getU_id());
        }else{
            resultMap.put("u_id", "please_login");
        }
        return resultMap;
    }

}