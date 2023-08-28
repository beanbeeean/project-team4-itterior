package com.office.house.member;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MemberService implements IMemberService{
	
	@Autowired
	IMemberDaoMapper iMemberDaoMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
    public int createAccountConfirm(MemberDto memberDto) {
        log.info("[MemberService] createAccountConfirm()");

        boolean isMember = iMemberDaoMapper.isMember(memberDto.getM_id());
        if(!isMember){
            memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));

            int result = -1;
            try {
                result = iMemberDaoMapper.insertNewAccount(memberDto);

            } catch(Exception e) {
                e.printStackTrace();
            }

            switch (result) {
                case -1:
                    log.info("[MemberService] DATABASE_COMMUNICATION_TROUBLE");
                    break;

                case 0:
                	log.info("[MemberService] INSERT_FAIL_DATABASE");
                    break;

                case 1:
                	log.info("[MemberService] INSERT_SUCCESS_DATABASE");
                    break;
            }

            return result;
        } else {
            return 0;
        }
    }

	public MemberDto memberLoginConfirm(MemberDto memberDto) {
		log.info("[MemberService] memberLoginConfirm()");

        MemberDto idVerifiedMemberDto = iMemberDaoMapper.selectMemberForLogin(memberDto);
        if(idVerifiedMemberDto!=null && idVerifiedMemberDto.getM_use()==1 && passwordEncoder.matches(memberDto.getM_pw(), idVerifiedMemberDto.getM_pw())){
            return idVerifiedMemberDto;
        } else {
            return null;
        }
	}

	public MemberDto memberModifyConfirm(MemberDto memberDto) {
		log.info("[MemberService] memberModifyConfirm()");

        int result = iMemberDaoMapper.updateAccount(memberDto);
        if(result>0)
            return iMemberDaoMapper.getLatestAccountInfo(memberDto);
        else
            return null;
	}

	public Map<String, Object> memberDeleteConfirm(String m_no) {
		log.info("[MemberService] memberDeleteConfirm()");

        Map<String, Object> map = new HashMap<>();

        int result = iMemberDaoMapper.deleteAccount(Integer.parseInt(m_no));
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
