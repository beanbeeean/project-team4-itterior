package com.office.house.admin;

import com.office.house.user.UserDto;
import com.office.house.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminDaoMapper {
    AdminDto selectAdminForLogin(AdminDto adminDto);

    boolean isAdmin(AdminDto adminDto);

    int insertNewAccount(AdminDto adminDto);

    int adminModifyConfirm(AdminDto adminDto);

    int adminDeleteConfirm(AdminDto adminDto);

    List<AdminDto> adminList(String keyWord, Criteria criteria);

    int getTotalCnt(String table, String column, String keyWord);

    AdminDto adminListDetail(int aNo);

    int adminListModifyConfirm(AdminDto adminDto);

    List<UserDto> userList(String keyWord, Criteria criteria);

    Object userListDetail(int u_no);

    int userListModifyConfirm(UserDto userDto);

    List<ChannelDto> youtubChannelList(String keyWord, Criteria criteria);

    Object youtubChannelListDetail(int ycNo);

    int youtubChannelListModifyConfirm(ChannelDto channelDto);
}
