package com.office.house.admin;

import com.office.house.board.BoardDto;
import com.office.house.user.UserDto;
import com.office.house.util.Criteria;
import com.office.house.youtube.YoutubeDto;
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

    AdminDto adminListDetail(int a_no);

    int adminListModifyConfirm(AdminDto adminDto);

    List<UserDto> userList(String keyWord, Criteria criteria);

    Object userListDetail(int u_no);

    int userListModifyConfirm(UserDto userDto);

    List<ChannelDto> youtubeChannelList(String keyWord, Criteria criteria);

    boolean isChannel(ChannelDto channelDto);

    int insertNewChannel(ChannelDto channelDto);

    Object youtubeChannelListDetail(int yc_no);

    int youtubeChannelListModifyConfirm(ChannelDto channelDto);

    List<YoutubeDto> youtubeList(String keyWord, Criteria criteria);

    Object youtubeListDetail(int y_no);

    int youtubeListModifyConfirm(YoutubeDto youtubeDto);

    List<BoardDto> boardList(String keyWord, Criteria criteria);

    Object boardListDetail(int b_no);

    int boardListModifyConfirm(BoardDto boardDto);
}
