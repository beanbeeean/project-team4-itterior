package com.office.house.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserDaoMapper {

	boolean isUser(String u_id);

	int insertNewAccount(UserDto userDto);

	UserDto selectUserForLogin(UserDto userDto);

	int updateAccount(UserDto userDto);

	UserDto getLatestAccountInfo(UserDto userDto);

	int deleteAccount(int parseInt);


}
