package com.office.house.config;

import com.office.house.admin.AdminDto;
import com.office.house.admin.IAdminDaoMapper;
import com.office.house.user.IUserDaoMapper;
import com.office.house.admin.AdminDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyAdminDetailsService implements UserDetailsService {

    @Autowired
    IAdminDaoMapper iAdminDaoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");

        log.info("username : " + username);

        AdminDto adminDto = new AdminDto();
        adminDto.setA_id(username);

        AdminDto selectedAdminDto = iAdminDaoMapper.selectAdminForLogin(adminDto);

        return User.builder()
                .username(selectedAdminDto.getA_id())
                .password(selectedAdminDto.getA_pw())
                .roles("ADMIN")
                .build();
    }
}
