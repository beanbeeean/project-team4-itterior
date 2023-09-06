package com.office.house.search;

import com.office.house.board.BoardDto;
import com.office.house.board.IBoardDaoMapper;
import com.office.house.product.IProductDaoMapper;
import com.office.house.product.ProductDto;
import com.office.house.youtube.IYoutubeDaoMapper;
import com.office.house.youtube.YoutubeDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service

public class SearchService {

    @Autowired
    IBoardDaoMapper iBoardDaoMapper;

    @Autowired
    IProductDaoMapper iProductDaoMapper;

    @Autowired
    IYoutubeDaoMapper iYoutubeDaoMapper;

    public Map<String, Object> mainSearch(String keyword, String uId) {
        log.info("mainSearch");
        Map<String, Object> map = new HashMap<>();

        List<ProductDto> productDtos = iProductDaoMapper.selectProductMainSearch(keyword,uId);

        List<BoardDto> boardDtos = iBoardDaoMapper.selectBoardMainSearch(keyword,uId);

        List<YoutubeDto> youtubeDtos = iYoutubeDaoMapper.selectYoutubeMainSearch(keyword,uId);

        map.put("productDtos", productDtos);

        map.put("boardDtos", boardDtos);

        map.put("youtubeDtos", youtubeDtos);


        return map;

    }
}
