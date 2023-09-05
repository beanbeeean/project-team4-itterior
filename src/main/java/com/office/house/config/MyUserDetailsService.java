package com.office.house.config;

import com.office.house.user.IUserDaoMapper;
import com.office.house.user.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    IUserDaoMapper iUserDaoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");

        log.info("username : " + username);

        UserDto userDto = new UserDto();
        userDto.setU_id(username);

        UserDto selectedUserDto = iUserDaoMapper.selectUserForLogin(userDto.getU_id());

        return User.builder()
                .username(selectedUserDto.getU_id())
                .password(selectedUserDto.getU_pw())
                .roles("USER")
                .build();
    }
}
