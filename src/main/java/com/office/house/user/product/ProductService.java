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

    /*public void crawlProducts() {
        // 크롬 드라이버 설정
        System.setProperty("webdriver.chrome.driver", "C:\\btc\\chromedriver-win64\\chromedriver.exe"); // 윈도우

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        // Upbit 공지사항 페이지 접속
//        driver.get("http://shop.danawa.com/main/?controller=goods&methods=index&productRegisterAreaGroupSeq=78&serviceSectionSeq=594&cate=1#1");


        List<ProductDto> productDtos = new ArrayList<>();
        //WebElement webElement = driver.findElement(By.cssSelector("a[rel='next']"));
        int cnt = 32;
        for(int i = 0; i < cnt; i+= 32) {
            driver.get("https://www.ggumim.co.kr/furniture/more/278_290/?sort_by=f.bookmark_count&sort_direction=desc&per_page="+i);
            //driver.get("https://www.ggumim.co.kr/furniture/more/278_296?sort_by=f.bookmark_count&sort_direction=desc&per_page="+i);
            //webElement.click();

            // 공지사항 목록을 가져옵니다.
            List<WebElement> elements = driver.findElements(By.cssSelector("div.preact-furniture-list > div.item"));
            System.out.println("----------" + elements.size());
            // 반환할 NoticeDto 리스트 생성

            // 각 공지사항에 대하여
            for (WebElement noticeElement : elements) {
                // 공지사항의 제목과 링크를 가져옵니다.
                // "td.lAlign > a"는 "td" 태그 중 클래스가 "lAlign"인 태그의 자식 "a" 태그를 선택합니다.
                WebElement brandElement = noticeElement
                        .findElement(By.cssSelector("div.item div.brand"));
                String brand = brandElement.getText(); // "a" 태그의 텍스트(제목)를 가져옵니다.

                // "td:nth-child(2)"는 "td" 태그 중 두 번째 자식에 해당하는 태그를 선택합니다.
                WebElement nameElement = noticeElement.findElement(By.cssSelector("div.item div.name"));
                String name = nameElement.getText(); // "td" 태그의 텍스트(날짜)를 가져옵니다.


                WebElement dcPriceElement = noticeElement.findElement(By.cssSelector("div.item span.price"));
                String dcPrice = dcPriceElement.getText().replace(",","").replace("원",""); // "td" 태그의 텍스트(날짜)를 가져옵니다.

//                WebElement oriPriceElement = noticeElement.findElement(By.cssSelector("div.item div.price-original > span"));
//                String oriPrice = oriPriceElement.getText(); // "td" 태그의 텍스트(날짜)를 가져옵니다.

                WebElement imageElement = noticeElement.findElement(By.cssSelector("div.item div.item-image"));
                String image = imageElement.getAttribute("style").replace("background-image: url(\"", "").replace("\");", "");; // "td" 태그의 텍스트(날짜)를 가져옵니다.

                WebElement linkElement = noticeElement.findElement(By.cssSelector("div.item > a"));
                String link = linkElement.getAttribute("href"); // "td" 태그의 텍스트(날짜)를 가져옵니다.

                // Notice 객체 생성 및 저장
                ProductDto productDto = ProductDto.builder()
                        .p_brand(brand)
                        .p_name(name)
                        .p_sales_price(dcPrice)
                        .p_img(image)
                        .p_link(link)
                        .p_category(1)
                        .build();

                // 저장된 Notice 객체를 productDto로 변환하여 리스트에 추가합니다.
                productDtos.add(productDto);
            }

        }

        // WebDriver 종료
        driver.quit();
        for(ProductDto dto : productDtos) {
            iProductDaoMapper.insertProducts(dto);
        }
    }*/

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
}