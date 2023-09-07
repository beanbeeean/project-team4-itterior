package com.office.house.search;

import com.office.house.board.BoardDto;
import com.office.house.product.ProductDto;
import com.office.house.user.UserDto;
import com.office.house.youtube.YoutubeDto;
import jakarta.servlet.http.HttpSession;
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
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/search")
    public String mainSearch(@RequestParam("keyword") String keyword, Model model, HttpSession session){
        log.info("mainSearch");
        String u_id = "";
        UserDto userDto = (UserDto) session.getAttribute("loginedMemberDto");
        if(userDto != null){
            u_id = userDto.getU_id();
        }



        Map<String, Object> resultMap = searchService.mainSearch(keyword, u_id);

        List<ProductDto> productDtos = (List<ProductDto>) resultMap.get("productDtos");
        List<BoardDto> boardDtos = (List<BoardDto>) resultMap.get("boardDtos");
        List<YoutubeDto> youtubeDtos = (List<YoutubeDto>) resultMap.get("youtubeDtos");

        model.addAttribute("productDtos", productDtos);
        model.addAttribute("boardDtos", boardDtos);
        model.addAttribute("youtubeDtos", youtubeDtos);
        model.addAttribute("keyword", keyword);

        return "search/search";
    }

}
