package com.office.house.user;

import com.office.house.board.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserDaoMapper {

	boolean isUser(String u_id);

	int insertNewAccount(Map<String, String> msgMap);

	UserDto selectUserForLogin(String u_id);

	int updateAccount(UserDto userDto);

	UserDto getLatestAccountInfo(UserDto userDto);

	int deleteAccount(int parseInt);

    int selectUserForFindPassword(Map<String, String> msgMap);

	int updatePassword(String u_id, String newPassword);

	List<BoardDto> selectBoardList(String u_id);
}
