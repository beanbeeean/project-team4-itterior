package com.office.house.util.crawling;

import com.office.house.product.ProductDto;
import com.office.house.product.IProductDaoMapper;
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
public class GgumiCrawlingService implements ICrawlingService{

    @Autowired
    IProductDaoMapper iProductDaoMapper;

    @Override
    public void crawlingProducts(String url, int type) {
            // 크롬 드라이버 설정
            System.setProperty("webdriver.chrome.driver", "C:\\btc\\chromedriver-win64\\chromedriver.exe"); // 윈도우

            // 크롬 옵션 설정
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            WebDriver driver = new ChromeDriver(options);

            List<ProductDto> productDtos = new ArrayList<>();

            int cnt = 32;
            for(int i = 0; i < cnt; i+= 32) {
                driver.get(url+i);

                List<WebElement> elements = driver.findElements(By.cssSelector("div.preact-furniture-list > div.item"));


                for (WebElement noticeElement : elements) {

                    WebElement brandElement = noticeElement.findElement(By.cssSelector("div.item div.brand"));
                    String brand = brandElement.getText();

                    WebElement nameElement = noticeElement.findElement(By.cssSelector("div.item div.name"));
                    String name = nameElement.getText();

                    WebElement dcPriceElement = noticeElement.findElement(By.cssSelector("div.item span.price"));
                    String salesPrice = dcPriceElement.getText().replace(",","").replace("원","");

                    WebElement imageElement = noticeElement.findElement(By.cssSelector("div.item div.item-image"));
                    String image = imageElement.getAttribute("style").replace("background-image: url(\"", "").replace("\");", "");

                    WebElement linkElement = noticeElement.findElement(By.cssSelector("div.item > a"));
                    String link = linkElement.getAttribute("href");

                    ProductDto dto = new ProductDto();
                    dto.setP_brand(brand);
                    dto.setP_name(name);
                    dto.setP_sales_price(salesPrice);
                    dto.setP_img(image);
                    dto.setP_link(link);
                    dto.setP_category(type);

                    productDtos.add(dto);
                }

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
