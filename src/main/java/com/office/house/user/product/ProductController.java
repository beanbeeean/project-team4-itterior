package com.office.house.user.product;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping({"","/"})
    public String home(){
        log.info("home()");
        //productService.crawlProducts();
        return "user/product/product_home";
    }

    @GetMapping("get_products")
    @ResponseBody
    public Map<String, Object>  getProducts(@RequestParam(required = false, value = "category") String[] category, @RequestParam(required = false, value = "sort") String sort,
                                            @RequestParam(required = false, value = "filter") String filter){
        log.info("getProducts");
//        Map<String, Object> resultMap = productService.getProducts(type);
//        for (String txt : category){
//            System.out.println("category : " + txt);
//        }
//        System.out.println("sort : " + sort);
//
//        for (String txt : filter){
//            System.out.println("filter : " + txt);
//        }
        Map<String, Object> resultMap = productService.getProducts(category, sort, filter);
        return resultMap;

    }
}
