package com.office.house.admin;

import com.office.house.user.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AdminService implements IAdminService{

    @Autowired
    IAdminDaoMapper iAdminDaoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public AdminDto adminLoginConfirm(AdminDto adminDto) {
        log.info("[AdminController] adminLoginConfirm()");

        AdminDto idVerifiedAdminDto = iAdminDaoMapper.selectAdminForLogin(adminDto);
        if(idVerifiedAdminDto!=null && idVerifiedAdminDto.getA_state()==1 && passwordEncoder.matches(adminDto.getA_pw(), idVerifiedAdminDto.getA_pw())){

            return iAdminDaoMapper.selectAdminForLogin(adminDto);

        }

        return null;
    }
}
