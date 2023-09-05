package com.office.house.like;

import com.office.house.board.BoardDto;
import com.office.house.board.IBoardDaoMapper;
import com.office.house.user.product.IProductDaoMapper;
import com.office.house.user.product.IProductService;
import com.office.house.user.product.ProductDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class LikeService {

    @Autowired
    ILikeDaoMapper iLikeDaoMapper;

    @Autowired
    IProductDaoMapper iProductDaoMapper;

    @Autowired
    IBoardDaoMapper iBoardDaoMapper;

    public Map<String, Object> likeConfirm(Map<String, String> msgMap) {
        log.info("likeConfirm");
        Map<String, Object> map = new HashMap<>();
        int result = -1;

        if(msgMap.get("method").equals("like")){
            result = iLikeDaoMapper.insertLikeCount(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));
        }else{
            result = iLikeDaoMapper.deleteLikeCount(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));
        }

        if(result > 0){
            int likeCnt = iLikeDaoMapper.selectLikeCount(msgMap.get("type"), msgMap.get("no"));
            int isLike = iLikeDaoMapper.selectCurrentUserLikeCount(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));
            System.out.println("isLike ::: " + isLike);
            switch (msgMap.get("type")){
                case "1":
                    result = iProductDaoMapper.updateLikeCountForProduct(msgMap.get("no"),likeCnt);
                    ProductDto productDto = iProductDaoMapper.selectProductByNo(msgMap.get("no"));
                    map.put("productDto", productDto);
                    break;
                case "2":
                    result = iBoardDaoMapper.updateLikeCountForBoard(msgMap.get("no"),likeCnt);
                    BoardDto boardDto = iBoardDaoMapper.selectBoardByNo(msgMap.get("no"));
                    map.put("boardDto", boardDto);
                    break;
                case "3":
                    break;
            }


            map.put("isLike", isLike);

        }

        map.put("result", result);
        return map;
    }
}