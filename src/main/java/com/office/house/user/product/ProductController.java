package com.office.house.user.product;

import com.office.house.user.UserDto;
import com.office.house.util.page.PageDefine;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object>  getProducts(@RequestParam(required = false, value = "category") String[] category,
                                            @RequestParam(required = false, value = "sort") String sort,
                                            @RequestParam(required = false, value = "filter") String filter,
                                            @RequestParam(required = false, value = "keyword") String keyword,
                                            @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                            @RequestParam(value="amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount,
                                            HttpSession session){
        log.info("getProducts");
        Map<String, Object> resultMap = productService.getProducts(category, sort, filter, keyword, pageNum, amount);
        UserDto loginedMemberDto = (UserDto) session.getAttribute("loginedMemberDto");
        if(loginedMemberDto != null){
            resultMap.put("u_id", loginedMemberDto.getU_id());
        }else{
            resultMap.put("u_id", "please_login");
        }
        return resultMap;
    }

    @PostMapping("/product_like_confirm")
    @ResponseBody
    public Map<String, Object> productLikeConfirm(@RequestBody Map<String, String> msgMap){
        log.info("productLikeConfirm");
        Map<String, Object> resultMap = productService.productLikeConfirm(msgMap);

        return resultMap;
    }

    @PostMapping("/update_product_hit")
    @ResponseBody
    public Map<String, Object> updateProductHit(@RequestBody Map<String, String> msgMap){
        log.info("updateProductHit");
        Map<String, Object> resultMap = productService.updateProductHit(msgMap.get("no"));
        return resultMap;
    }
}