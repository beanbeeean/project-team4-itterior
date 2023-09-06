package com.office.house.admin;

import com.office.house.board.BoardDto;
import com.office.house.user.UserDto;
import com.office.house.product.ProductDto;
import com.office.house.util.page.Criteria;
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

    UserDto userListDetail(int u_no);

    int userListModifyConfirm(UserDto userDto);

    List<ChannelDto> youtubeChannelList(String keyWord, Criteria criteria);

    boolean isChannel(ChannelDto channelDto);

    int insertNewChannel(ChannelDto channelDto);

    ChannelDto youtubeChannelListDetail(int yc_no);

    int youtubeChannelListModifyConfirm(ChannelDto channelDto);

    List<YoutubeDto> youtubeList(String keyWord, Criteria criteria);

    YoutubeDto youtubeListDetail(int y_no);

    int youtubeListModifyConfirm(int y_state,int y_no);

    List<BoardDto> boardList(String keyWord, Criteria criteria);

    BoardDto boardListDetail(int b_no);

    int boardListModifyConfirm(int b_state,int b_no);

    List<ProductDto> productList(String keyWord, Criteria criteria);

    ProductDto productListDetail(int p_no);

    int productListModifyConfirm(int p_state,int p_no);

}
