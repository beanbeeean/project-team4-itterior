package com.office.house.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDaoMapper {

	boolean isMember(String m_id);

	int insertNewAccount(MemberDto memberDto);

	MemberDto selectMemberForLogin(MemberDto memberDto);

	int updateAccount(MemberDto memberDto);

	MemberDto getLatestAccountInfo(MemberDto memberDto);

	int deleteAccount(int parseInt);


}
