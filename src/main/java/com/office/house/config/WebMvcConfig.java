package com.office.house.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/userUploadImg/**")
                .addResourceLocations("file:///c://semipjt/upload/");

        registry.addResourceHandler("/userBoardUploadImg/**")
                .addResourceLocations("file:///c://semipjt/upload/board/");

        registry.addResourceHandler("/userBoardThumbnailImg/**")
                .addResourceLocations("file:///c://semipjt/upload/thumbnail/");

    }



}
