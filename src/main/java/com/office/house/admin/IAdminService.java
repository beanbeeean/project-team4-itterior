package com.office.house.admin;

import com.office.house.user.UserDto;
import com.office.house.youtube.YoutubeDto;

import java.util.Map;

public interface IAdminService {


    AdminDto adminLoginConfirm(AdminDto adminDto);

    int createAccountConfirm(AdminDto adminDto);

    int adminModifyConfirm(AdminDto adminDto);

    int adminDeleteConfirm(AdminDto adminDto);

    Map<String, Object> adminList(String keyWord, int pageNum, int amount);

    AdminDto adminListDetail(int a_no);

    int adminListModifyConfirm(AdminDto adminDto);

    Map<String, Object> userList(String keyWord, int pageNum, int amount);

    Object userListDetail(int u_no);

    int userListModifyConfirm(UserDto userDto);

    Map<String, Object> youtubChannelList(String keyWord, int pageNum, int amount);

    int createYoutubeChannelConfirm(ChannelDto channelDto);

    Object youtubChannelListDetail(int yc_no);

    int youtubChannelListModifyConfirm(ChannelDto channelDto);

    Map<String, Object> youtubList(String keyWord, int pageNum, int amount);

    Object youtubListDetail(int y_no);


}
