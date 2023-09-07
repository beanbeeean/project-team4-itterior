package com.office.house.config;

import com.office.house.admin.AdminDto;
import com.office.house.admin.IAdminDaoMapper;
import com.office.house.user.IUserDaoMapper;
import com.office.house.user.UserDto;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
    IAdminDaoMapper iAdminDaoMapper;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    MyAdminDetailsService myAdminDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
        log.info("filterChain");

        http.csrf().disable()   //CSRF 보호 기능 비활성화
                .cors().disable()   //CORS 설정 비활성화
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()      //HTTP 요청 인증 설정
                        .requestMatchers("/css/**", "/error/**", "/imgs/**", "/js/**", "", "/", "/product/**", "**",
                                "/admin/create_account_form").permitAll()
                        .anyRequest().authenticated()   //위에 있는 경로 외 요청은 전부 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/admin/admin_login_form")
                        .loginProcessingUrl("/admin/admin_login_confirm")
                        .usernameParameter("a_id")
                        .passwordParameter("a_pw")
                        .successHandler((request, response, authentication) -> {

                            AdminDto adminDto = new AdminDto();
                            adminDto.setA_id(authentication.getName());
                            AdminDto loginedAdminDto = iAdminDaoMapper.selectAdminForLogin(adminDto);

                            HttpSession session = request.getSession();
                            session.setAttribute("loginedAdminDto", loginedAdminDto);
                            session.setMaxInactiveInterval(60 * 30);

                            response.sendRedirect("/admin/admin_myPage");
                        })
                        .failureHandler((request, response, exception) -> {
                            log.info("failureHandler!!");
                            response.sendRedirect("/admin/admin_login_form");

                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/admin/admin_logout_confirm")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            log.info("logoutSuccessHandler!!");

                            HttpSession session = request.getSession();
                            session.removeAttribute("loginedAdminDto");   //세션 데이터 삭제

                            response.sendRedirect("/admin");

                        })
                )
                .userDetailsService(myAdminDetailsService)   //사용자 정보 들고옴
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainUser(HttpSecurity http) throws Exception {
        log.info("filterChain");

        http.csrf().disable()   //CSRF 보호 기능 비활성화
                .cors().disable()   //CORS 설정 비활성화
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()      //HTTP 요청 인증 설정
                        .requestMatchers("/css/**", "/error/**", "/imgs/**", "/js/**", "", "/",
                                "/board", "/board/", "/board/get_board_list", "/board/get_main_board_list", "/board/get_board",
                                "/youtube", "/youtube/", "/youtube/**",
                                "/product", "/product/","/product/get_products", "/product/get_main_product_list",
                                "/userUploadImg/**", "/user/create_account_form", "/userBoardUploadImg/**", "/userBoardThumbnailImg/**"
                        ).permitAll()
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

                        })
                        .failureHandler((request, response, exception) -> {
                            log.info("failureHandler!!");
                            response.sendError(500);

                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/user/user_logout_confirm")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            log.info("logoutSuccessHandler!!");

                            HttpSession session = request.getSession();
                            session.removeAttribute("loginedMemberDto");   //세션 데이터 삭제

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
