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

        log.info("[AdminService] adminLoginConfirm()");

        AdminDto idVerifiedAdminDto = iAdminDaoMapper.selectAdminForLogin(adminDto);
        if(idVerifiedAdminDto!=null && idVerifiedAdminDto.getA_state()==1 && passwordEncoder.matches(adminDto.getA_pw(), idVerifiedAdminDto.getA_pw())){

            return iAdminDaoMapper.selectAdminForLogin(adminDto);

        }

        return null;
    }

    @Override
    public int createAccountConfirm(AdminDto adminDto) {

        log.info("[AdminService] createAccountConfirm()");

        boolean isUser = iAdminDaoMapper.isAdmin(adminDto);

        if(!isUser) {
            adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));
            return iAdminDaoMapper.insertNewAccount(adminDto);
        }

        return -1;
    }

    @Override
    public int adminModifyConfirm(AdminDto adminDto) {

        log.info("[AdminService] adminModifyConfirm()");

        adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));
        return iAdminDaoMapper.adminModifyConfirm(adminDto);
    }

    @Override
    public int adminDeleteConfirm(AdminDto adminDto) {

        log.info("[AdminService] adminDeleteConfirm()");

        return iAdminDaoMapper.adminDeleteConfirm(adminDto);
    }
}
