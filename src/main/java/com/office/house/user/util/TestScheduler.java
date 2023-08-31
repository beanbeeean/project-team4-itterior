package com.office.house.user.util;

import com.office.house.user.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class TestScheduler {
	
	@Autowired
	ProductService productService;
	
	@Scheduled(cron = "0 54 20 * * *")
	public void testMethod() {
		System.out.println("스케줄링 실행");
		productService.crawlProducts();
	}

}

