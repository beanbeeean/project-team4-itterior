package com.office.house.user;

import java.util.Map;

public interface IUserService {
	
	public Map<String, Object> createAccountConfirm(Map<String, String> msgMap);

	public Map<String, Object> userLoginConfirm(Map<String, String> msgMap);

	public UserDto userModifyConfirm(UserDto userDto);

	public Map<String, Object> userDeleteConfirm(String u_no);

    public Map<String, Object> findPasswordConfirm(Map<String, String> msgMap);
}
