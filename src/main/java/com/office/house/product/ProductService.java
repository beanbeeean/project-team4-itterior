package com.office.house.product;

import com.office.house.like.LikeDto;
import com.office.house.util.page.Criteria;
import com.office.house.util.page.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ProductService implements IProductService{

    @Autowired
    IProductDaoMapper iProductDaoMapper;

    @Override
    public Map<String, Object> getProducts(String[] category, String sort, String filter,
                                           String keyword, int pageNum, int amount) {
        log.info("getProducts");
        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<ProductDto> dtos = iProductDaoMapper.selectProducts(category, sort, filter, keyword, criteria.getSkip(), criteria.getAmount());
        int totalCnt = iProductDaoMapper.selectProductsCnt(category, sort, filter, keyword, criteria.getSkip(), criteria.getAmount());
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        List<Integer> likeList = new ArrayList<>();
        for(ProductDto dto : dtos){
            if(dto.getP_like() > 0){
                likeList.add(dto.getP_no());
            }
        }
        List<LikeDto> isLikedDtos = new ArrayList<>();
        if(likeList.size() > 0){
            isLikedDtos = iProductDaoMapper.selectLikedProduct(likeList);
        }
        map.put("isLikedDtos", isLikedDtos);
        map.put("productDtos", dtos);
        map.put("pageMakerDto", pageMakerDto);
        return map;
    }


    @Override
    public Map<String, Object> updateProductHit(String no) {
        log.info("updateProductHit");

        Map<String, Object> map = new HashMap<>();
        int result = iProductDaoMapper.updateHitCount(no);
        if(result > 0){
            ProductDto productDto = iProductDaoMapper.selectProductByNo(no);
            map.put("productDto", productDto);
        }
        return map;
    }
}