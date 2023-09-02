package com.office.house.user.product.crawling;

import com.office.house.user.product.IProductDaoMapper;
import com.office.house.user.product.ProductDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IkeaCrawlingService implements ICrawlingService{

    @Autowired
    IProductDaoMapper iProductDaoMapper;

    @Override
    public void crawlingProducts(String url, int type) {
        System.setProperty("webdriver.chrome.driver", "C:\\btc\\chromedriver-win64\\chromedriver.exe"); // 윈도우

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        List<ProductDto> productDtos = new ArrayList<>();
        driver.get(url);

        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }

        List<WebElement> elements = driver.findElements(By.cssSelector("div.plp-fragment-wrapper"));

        for (WebElement noticeElement : elements) {
            String brand = "IKEA";

            WebElement nameElement = noticeElement.findElement(By.cssSelector("div.pip-header-section__container-text span.pip-header-section__title--small"));
            String name = nameElement.getText();

            WebElement salesPriceElement = noticeElement.findElement(By.cssSelector("span.pip-price span.pip-price__integer"));
            String salesPrice = salesPriceElement.getText().replace(",","").replace("원","");; // "td" 태그의 텍스트(날짜)를 가져옵니다.

            WebElement imageElement = noticeElement.findElement(By.cssSelector("span.pip-product-compact__main-box > img.pip-image"));
            String image = imageElement.getAttribute("src");

            WebElement linkElement = noticeElement.findElement(By.cssSelector("div.pip-product-compact > a"));
            String link = linkElement.getAttribute("href");

            ProductDto dto = ProductDto.builder()
                    .p_brand(brand)
                    .p_name(name)
                    .p_sales_price(salesPrice)
                    .p_img(image)
                    .p_link(link)
                    .p_category(type)
                    .build();

            productDtos.add(dto);
        }

        // WebDriver 종료
        driver.quit();
        for(ProductDto dto : productDtos) {
            int result = iProductDaoMapper.isExistProduct(dto.getP_link());
            if(result > 0){
                iProductDaoMapper.updateProducts(dto);
            }else{
                iProductDaoMapper.insertProducts(dto);
            }
        }

    }
}
