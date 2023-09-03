package com.office.house.user.product;

import com.office.house.util.Criteria;
import com.office.house.util.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

        System.out.println("size : " + dtos.size());
        map.put("productDtos", dtos);
        map.put("pageMakerDto", pageMakerDto);
        return map;
    }

    @Override
    public Map<String, Object> productLikeConfirm(Map<String, String> msgMap) {
        log.info("productLikeConfirm");
        Map<String, Object> map = new HashMap<>();
        int result = iProductDaoMapper.insertLikeCount(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));
        if(result > 0){
            result = iProductDaoMapper.updateLikeCountForProduct(msgMap.get("no"));
        }
        map.put("result", result);
        return map;
    }
}