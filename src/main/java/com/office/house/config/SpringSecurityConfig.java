package com.office.house.config;

import com.office.house.user.IUserDaoMapper;
import com.office.house.user.UserDto;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@Log4j2
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    @Autowired
    IUserDaoMapper iUserDaoMapper;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("filterChain");

        http.csrf().disable()   //CSRF 보호 기능 비활성화
                .cors().disable()   //CORS 설정 비활성화
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()      //HTTP 요청 인증 설정
                        .requestMatchers("/css/**", "/error/**", "/imgs/**", "/js/**", "", "/", "/product/**").permitAll()
                        .anyRequest().authenticated()   //위에 있는 경로 외 요청은 전부 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/user/user_login_form")
                        .loginProcessingUrl("/user/user_login_confirm")
                        .usernameParameter("u_id")
                        .passwordParameter("u_pw")
                        .successHandler((request, response, authentication) -> {

                            UserDto userDto = new UserDto();
                            userDto.setU_id(authentication.getName());
                            UserDto loginedMemberDto = iUserDaoMapper.selectUserForLogin(userDto.getU_id());

                            HttpSession session = request.getSession();
                            session.setAttribute("loginedMemberDto", loginedMemberDto);
                            session.setMaxInactiveInterval(60 * 30);
                            response.sendRedirect("/");
                            request.setAttribute("login", "yes");
                        })
                        .failureHandler((request, response, exception) -> {
                            log.info("failureHandler!!");
                            response.sendRedirect("/user/user_login_form");

                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/user/user_logout_confirm")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            log.info("logoutSuccessHandler!!");

                            HttpSession session = request.getSession();
                            session.invalidate();   //세션 데이터 삭제

                            response.sendRedirect("/");

                        })
                )
                .userDetailsService(myUserDetailsService)   //사용자 정보 들고옴
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);

        return http.build();
    }

}
