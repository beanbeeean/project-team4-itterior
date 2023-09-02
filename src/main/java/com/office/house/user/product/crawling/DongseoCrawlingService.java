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
public class DongseoCrawlingService implements ICrawlingService{

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

        List<WebElement> elements = new ArrayList<>();
        switch (type){
            case 1:
                elements = driver.findElements(By.cssSelector("div.cate_031003 > div.goods_list_cont > div.item_gallery_type > ul > li"));
                break;
            case 2:
                elements = driver.findElements(By.cssSelector("div.cate_033002 > div.goods_list_cont > div.item_gallery_type > ul > li"));
                break;
            case 3:
                elements = driver.findElements(By.cssSelector("div.cate_031002 > div.goods_list_cont > div.item_gallery_type > ul > li"));
                break;
            case 4:
                elements = driver.findElements(By.cssSelector("div.cate_030007002 > div.goods_list_cont > div.item_gallery_type > ul > li"));
                break;
            case 5:
                elements = driver.findElements(By.cssSelector("div.cate_028001 > div.goods_list_cont > div.item_gallery_type > ul > li"));
                break;
        }

        System.out.println("----------" + elements.size());

        for (WebElement noticeElement : elements) {
            String brand = "동서가구";

            WebElement nameElement = noticeElement.findElement(By.cssSelector("div.item_info_cont > div.item_tit_box a > strong.item_name"));
            String name = nameElement.getText();

            WebElement salesPriceElement = noticeElement.findElement(By.cssSelector("div.item_money_box > strong.item_price > span"));
            String salesPrice = salesPriceElement.getText().replace(",","").replace("원","");

            WebElement imageElement = noticeElement.findElement(By.cssSelector("div.item_photo_box"));
            String image = imageElement.getAttribute("data-image-main");

            WebElement linkElement = noticeElement.findElement(By.cssSelector("div.item_photo_box > a"));
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
