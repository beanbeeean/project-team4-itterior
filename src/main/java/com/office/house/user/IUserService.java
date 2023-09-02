package com.office.house.user;

import com.office.house.board.BoardDto;

import java.util.List;
import java.util.Map;

public interface IUserService {
	
	public Map<String, Object> createAccountConfirm(Map<String, String> msgMap);

	public Map<String, Object> userLoginConfirm(Map<String, String> msgMap);

	public UserDto userModifyConfirm(UserDto userDto);

	public Map<String, Object> userDeleteConfirm(String u_no);

    public Map<String, Object> findPasswordConfirm(Map<String, String> msgMap);

	public int userWriteConfirm(BoardDto boardDto);

	public List<BoardDto> getBoardList(String u_id);
}
