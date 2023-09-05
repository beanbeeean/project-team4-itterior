package com.office.house.youtube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class YoutubeScheduler {
	
	@Autowired
	YoutubeService youtubeService;
	
	@Scheduled(cron = "0 5 2 * * *")
	public void youtubeMethod() {
		System.out.println("유튜브 스케줄링 실행");
		youtubeService.youtube();
	}

}

