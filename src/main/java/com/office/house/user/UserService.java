package com.office.house.user;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
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
    public Map<String, Object> createAccountConfirm(Map<String, String> msgMap){
        log.info("[UserService] createAccountConfirm()");

        Map<String, Object> map = new HashMap<>();
        boolean isUser = iUserDaoMapper.isUser(msgMap.get("u_id"));
        int result = -1;
        map.put("result", result);
        if(!isUser){
            msgMap.put("u_pw",passwordEncoder.encode(msgMap.get("u_pw")));

            try {
                result = iUserDaoMapper.insertNewAccount(msgMap);
                map.put("result", result);

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
        }
        return map;
    }

    @Override
	public Map<String, Object> userLoginConfirm(Map<String, String> msgMap) {
		log.info("[UserService] userLoginConfirm()");
        Map<String, Object> map = new HashMap<>();
        UserDto idVerifiedUserDto = iUserDaoMapper.selectUserForLogin(msgMap.get("u_id"));
        if(idVerifiedUserDto!=null && idVerifiedUserDto.getU_use()==1 && passwordEncoder.matches(msgMap.get("u_pw"), idVerifiedUserDto.getU_pw())){
            map.put("loginedMemberDto", idVerifiedUserDto);
            return map;
        } else {
            return null;
        }
	}

    @Override
	public UserDto userModifyConfirm(UserDto userDto) {
		log.info("[UserService] userModifyConfirm()");

        int result = iUserDaoMapper.updateAccount(userDto);

        if(result>0)
            return iUserDaoMapper.getLatestAccountInfo(userDto);

        else
            return null;
	}

    @Override
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
