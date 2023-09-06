package com.office.house.user;

import java.security.SecureRandom;
import java.util.*;

import com.office.house.board.BoardDto;
import com.office.house.board.IBoardDaoMapper;
import com.office.house.comment.ICommentDaoMapper;
import com.office.house.like.LikeDto;
import com.office.house.product.IProductDaoMapper;
import com.office.house.product.ProductDto;
import com.office.house.util.page.Criteria;
import com.office.house.util.page.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService implements IUserService {


	@Autowired
    IUserDaoMapper iUserDaoMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

    @Autowired
   JavaMailSender mailSender;

    @Autowired
    IBoardDaoMapper iBoardDaoMapper;

    @Autowired
    ICommentDaoMapper iCommentDaoMapper;

    @Autowired
    IProductDaoMapper iProductDaoMapper;

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
        if(idVerifiedUserDto!=null && idVerifiedUserDto.getU_state()==1 && passwordEncoder.matches(msgMap.get("u_pw"), idVerifiedUserDto.getU_pw())){
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
        int result_board = iBoardDaoMapper.updateUserImage(userDto);
        int result_comment = iCommentDaoMapper.updateUserImage(userDto);
        log.info(result_board);

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

    @Override
    public Map<String, Object> findPasswordConfirm(Map<String, String> msgMap) {
        Map<String, Object> map = new HashMap<>();
        int result = -1;
        result = iUserDaoMapper.selectUserForFindPassword(msgMap);
        if(result > 0){
            String newPassword = createNewPassword();
            result = iUserDaoMapper.updatePassword(msgMap.get("u_id"), passwordEncoder.encode(newPassword));
            if(result > 0){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("ADMIN");
                message.setTo(msgMap.get("u_mail"));
                message.setSubject("새 비밀번호를 발송해드립니다.");
                message.setText(msgMap.get("u_id") + "님의 새 비밀번호\n " + newPassword);
                mailSender.send(message);
            }
        }
        map.put("result", result);
        return map;
    }

    // 새 비밀번호 만들기
    private String createNewPassword() {
        System.out.println("[UserMemberService] createNewPassword()");
        char[] chars = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer stringBuffer = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());

        int index = 0;
        int length = chars.length;
        for(int i = 0; i < 8; i++) {
            index = secureRandom.nextInt(length);

            if(index % 2 == 0) {
                stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
            }else {
                stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
            }

        }

        return stringBuffer.toString();
    }

    @Override
    public int userWriteConfirm(BoardDto boardDto) {
        log.info("[UserMemberService] userWriteConfirm()");

        int result = iBoardDaoMapper.insertNewBoard(boardDto);

        return result;
    }

    @Override
    public List<BoardDto> getBoardList(String u_id) {
        log.info("[UserMemberService] getBoardList()");

        List<BoardDto> boardDtos = iUserDaoMapper.selectBoardList(u_id);

        log.info(boardDtos.size());

        return boardDtos;
    }

    @Override
    public Map<String, Object> getUserLikeBoardList(int sort, int pageNum, int amount, String keyword, String u_id) {
        log.info("getUserLikeBoardList()");

        Map<String, Object> map = new HashMap<>();
        Criteria criteria = new Criteria(pageNum, amount);
        map.put("skip",criteria.getSkip());
        map.put("amount",criteria.getAmount());
        map.put("keyword",keyword);
        map.put("sort",sort);
        map.put("u_id",u_id);
        List<BoardDto> boardDtos = iBoardDaoMapper.selectLikeBoardList(map);
        int totalCnt = boardDtos.size();
        System.out.println("totalCnt :::: " + totalCnt);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("totalCnt", totalCnt);
        map.put("boardDtos", boardDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    public Map<String, Object> getUserLikeProducts(int pageNum, int amount, String u_id) {
        log.info("[UserMemberService] getUserLikeProducts()");
        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        map.put("skip", criteria.getSkip());
        map.put("amount", criteria.getAmount());
        map.put("u_id", u_id);
        List<ProductDto> dtos = iProductDaoMapper.selectLikeProducts(map);
        log.info(dtos);

        int totalCnt = iProductDaoMapper.selectLikeProductsCnt(criteria.getSkip(), criteria.getAmount(),u_id);
        log.info("totalCnt" + totalCnt);

        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        List<Integer> likeList = new ArrayList<>();
        for(ProductDto dto : dtos){
            if(dto.getP_like() > 0){
                likeList.add(dto.getP_no());
            }
        }

        List<LikeDto> isLikedDtos = new ArrayList<>();
        if(likeList.size() > 0){
            isLikedDtos = iProductDaoMapper.selectLikedProduct(likeList);
        }
        log.info(isLikedDtos.size());
        log.info(isLikedDtos);

        map.put("isLikedDtos", isLikedDtos);
        map.put("productDtos", dtos);
        map.put("pageMakerDto", pageMakerDto);
        return map;
    }

}
