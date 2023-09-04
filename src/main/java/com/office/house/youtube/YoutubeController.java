package com.office.house.youtube;

import com.office.house.admin.AdminDto;
import com.office.house.admin.AdminService;
import com.office.house.util.PageDefine;
import com.office.house.util.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String adminLoginForm(Model model,
                                 @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                 @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_YOUTUBE_AMOUNT) int amount){

        log.info("[YoutubeController] adminLoginForm()");

        String nextPage = "youtube/youtube_home";

        Map<String, Object> map = youtubeService.getYoutubes(keyWord, pageNum, amount);

        List<YoutubeDto> YoutubeDtos = (List<YoutubeDto>) map.get("YoutubeDtos");

        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        model.addAttribute("YoutubeDtos", YoutubeDtos);
        model.addAttribute("pageMakerDto", pageMakerDto);
        model.addAttribute("keyWord", keyWord);

        return nextPage;
    }

    //  YOUTUBE END

}