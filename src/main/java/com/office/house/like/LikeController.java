package com.office.house.like;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping("/like_confirm")
    @ResponseBody
    public Map<String, Object> likeConfirm(@RequestBody Map<String, String> msgMap){
        log.info("productLikeConfirm");
        Map<String, Object> resultMap = likeService.likeConfirm(msgMap);

        return resultMap;
    }

}
