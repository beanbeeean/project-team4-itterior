package com.office.house.user;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService implements IUserService {
	
	@Autowired
    IUserDaoMapper iUserDaoMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
    public int createAccountConfirm(UserDto userDto) {
        log.info("[UserService] createAccountConfirm()");

        boolean isUser = iUserDaoMapper.isMember(userDto.getU_id());
        if(!isUser){
            userDto.setU_pw(passwordEncoder.encode(userDto.getU_pw()));

            int result = -1;
            try {
                result = iUserDaoMapper.insertNewAccount(userDto);

            } catch(Exception e) {
                e.printStackTrace();
            }

            switch (result) {
                case -1:
                    log.info("[UserService] DATABASE_COMMUNICATION_TROUBLE");
                    break;

                case 0:
                	log.info("[UserService] INSERT_FAIL_DATABASE");
                    break;

                case 1:
                	log.info("[UserService] INSERT_SUCCESS_DATABASE");
                    break;
            }

            return result;
        } else {
            return 0;
        }
    }

	public UserDto userLoginConfirm(UserDto userDto) {
		log.info("[UserService] userLoginConfirm()");

        UserDto idVerifiedUserDto = iUserDaoMapper.selectUserForLogin(userDto);
        if(idVerifiedUserDto!=null && idVerifiedUserDto.getU_use()==1 && passwordEncoder.matches(userDto.getU_pw(), idVerifiedUserDto.getU_pw())){
            return idVerifiedUserDto;
        } else {
            return null;
        }
	}

	public UserDto userModifyConfirm(UserDto userDto) {
		log.info("[UserService] userModifyConfirm()");

        int result = iUserDaoMapper.updateAccount(userDto);
        if(result>0)
            return iUserDaoMapper.getLatestAccountInfo(userDto);
        else
            return null;
	}

	public Map<String, Object> userDeleteConfirm(String u_no) {
		log.info("[UserService] userDeleteConfirm()");

        Map<String, Object> map = new HashMap<>();

        int result = iUserDaoMapper.deleteAccount(Integer.parseInt(u_no));
        switch (result){
            case -1:
            	log.info("database communication error");
                break;
            case 0:
            	log.info("database delete fail");
                break;
            case 1:
            	log.info("database delete success");
                break;
        }

        map.put("result", result);

        return map;
	}

}
