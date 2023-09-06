package com.office.house.util.scheduler;

import com.office.house.util.crawling.DongseoCrawlingService;
import com.office.house.util.crawling.GgumiCrawlingService;
import com.office.house.util.crawling.IkeaCrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class CrawlingScheduler {
	
	@Autowired
	GgumiCrawlingService ggumiCrawlingService;

	@Autowired
	DongseoCrawlingService dongseoCrawlingService;

	@Autowired
	IkeaCrawlingService ikeaCrawlingService;
	
	@Scheduled(cron = "0 10 0 * * *")
	public void gguimiSchduler() {
		System.out.println("집꾸미기 스케줄링 실행");

		// 테이블 1
		ggumiCrawlingService.crawlingProducts("https://www.ggumim.co.kr/furniture/more/278_290/?sort_by=f.bookmark_count&sort_direction=desc&per_page=", 1);

		// 의자 2
		ggumiCrawlingService.crawlingProducts("https://www.ggumim.co.kr/furniture/more/278_296/?sort_by=f.bookmark_count&sort_direction=desc&per_page=",2);

		// 거실장 3
		ggumiCrawlingService.crawlingProducts("https://www.ggumim.co.kr/furniture/more/278_293/?sort_by=f.bookmark_count&sort_direction=desc&per_page=", 3);

		// 수납정리 4
		ggumiCrawlingService.crawlingProducts("https://www.ggumim.co.kr/furniture/more/282_319/?sort_by=f.bookmark_count&sort_direction=desc&per_page=", 4);

		// 침대 5
		ggumiCrawlingService.crawlingProducts("https://www.ggumim.co.kr/furniture/more/278_288/?sort_by=f.bookmark_count&sort_direction=desc&per_page=", 5);

		// 조명 6
		ggumiCrawlingService.crawlingProducts("https://www.ggumim.co.kr/furniture/more/280/?sort_by=f.bookmark_count&sort_direction=desc&per_page=",6);
	}

	@Scheduled(cron = "0 10 1 * * *")
	public void dongseoSchduler() {
		System.out.println("동서 가구 스케줄링 실행");

		// 테이블 1
		dongseoCrawlingService.crawlingProducts("https://www.dongsuhfurniture.co.kr/goods/goods_list.php?cateCd=031003", 1);

		// 의자 2
		dongseoCrawlingService.crawlingProducts("https://www.dongsuhfurniture.co.kr/goods/goods_list.php?cateCd=033002", 2);

		// 거실장 3
		dongseoCrawlingService.crawlingProducts("https://www.dongsuhfurniture.co.kr/goods/goods_list.php?cateCd=031002", 3);

		// 수납정리 4
		dongseoCrawlingService.crawlingProducts("https://www.dongsuhfurniture.co.kr/goods/goods_list.php?cateCd=030007002", 4);

		// 침대 5
		dongseoCrawlingService.crawlingProducts("https://dongsuhfurniture.co.kr/goods/goods_list.php?cateCd=028001", 5);

	}


	@Scheduled(cron = "0 5 1 * * *")
	public void ikeaSchduler() {
		System.out.println("이케아 스케줄링 실행");

		// 테이블 1
		ikeaCrawlingService.crawlingProducts("https://www.ikea.com/kr/ko/cat/tables-desks-fu004/?page=3", 1);

		// 의자 2
		ikeaCrawlingService.crawlingProducts("https://www.ikea.com/kr/ko/cat/chairs-fu002/?page=3", 2);

		// 거실장 3
		ikeaCrawlingService.crawlingProducts("https://www.ikea.com/kr/ko/cat/sideboards-buffets-console-tables-30454/?page=3", 3);

		// 수납정리 4
		ikeaCrawlingService.crawlingProducts("https://www.ikea.com/kr/ko/cat/cabinets-cupboards-st003/?page=3", 4);

		// 침대 5
		ikeaCrawlingService.crawlingProducts("https://www.ikea.com/kr/ko/cat/beds-bm003/?page=3", 5);

		// 조명 6
		ikeaCrawlingService.crawlingProducts("https://www.ikea.com/kr/ko/cat/lamps-li002/?page=3", 6);
	}

}

