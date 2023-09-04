package com.office.house.admin;

import com.office.house.board.BoardDto;
import com.office.house.user.UserDto;
import com.office.house.util.Criteria;
import com.office.house.util.PageMakerDto;
import com.office.house.youtube.YoutubeDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class AdminService implements IAdminService{

    @Autowired
    IAdminDaoMapper iAdminDaoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    //  ADMIN START
    @Override
    public AdminDto adminLoginConfirm(AdminDto adminDto) {

        log.info("[AdminService] adminLoginConfirm()");

        AdminDto idVerifiedAdminDto = iAdminDaoMapper.selectAdminForLogin(adminDto);
        if(idVerifiedAdminDto!=null && idVerifiedAdminDto.getA_state()==1 && passwordEncoder.matches(adminDto.getA_pw(), idVerifiedAdminDto.getA_pw())){

            return iAdminDaoMapper.selectAdminForLogin(adminDto);

        }

        return null;
    }

    @Override
    public int createAccountConfirm(AdminDto adminDto) {

        log.info("[AdminService] createAccountConfirm()");

        boolean isUser = iAdminDaoMapper.isAdmin(adminDto);

        if(!isUser) {
            adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));
            return iAdminDaoMapper.insertNewAccount(adminDto);
        }

        return -1;
    }

    @Override
    public int adminModifyConfirm(AdminDto adminDto) {

        log.info("[AdminService] adminModifyConfirm()");

        adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));
        return iAdminDaoMapper.adminModifyConfirm(adminDto);
    }

    @Override
    public int adminDeleteConfirm(AdminDto adminDto) {

        log.info("[AdminService] adminDeleteConfirm()");

        return iAdminDaoMapper.adminDeleteConfirm(adminDto);
    }

    //  ADMIN_END END

    //  ADMIN_LIST START

    @Override
    public Map<String, Object> adminList(String keyWord, int pageNum, int amount) {

        log.info("[AdminService] adminList()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<AdminDto> AdminDtos = iAdminDaoMapper.adminList(keyWord, criteria);
        String table = "tbl_admin";
        String column = "a_id";

        int totalCnt = iAdminDaoMapper.getTotalCnt(table, column, keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("AdminDtos", AdminDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public AdminDto adminListDetail(int a_no) {

        log.info("[AdminService] adminModifyConfirm()");

        return iAdminDaoMapper.adminListDetail(a_no);
    }

    @Override
    public int adminListModifyConfirm(AdminDto adminDto) {

        log.info("[AdminService] adminModifyConfirm()");

        adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));

        return iAdminDaoMapper.adminListModifyConfirm(adminDto);
    }
    //  ADMIN_LIST END

    //  USER_LIST START

    @Override
    public Map<String, Object> userList(String keyWord, int pageNum, int amount) {

        log.info("[AdminService] userList()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<UserDto> UserDtos = iAdminDaoMapper.userList(keyWord, criteria);
        String table = "tbl_user";
        String column = "u_id";

        int totalCnt = iAdminDaoMapper.getTotalCnt(table, column, keyWord);
        
        System.out.println(totalCnt + " " + keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("UserDtos", UserDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public Object userListDetail(int u_no) {

        log.info("[AdminService] userListDetail()");

        return iAdminDaoMapper.userListDetail(u_no);
    }

    @Override
    public int userListModifyConfirm(UserDto userDto) {

        log.info("[AdminService] userListModifyConfirm()");

        userDto.setU_pw(passwordEncoder.encode(userDto.getU_pw()));

        return iAdminDaoMapper.userListModifyConfirm(userDto);
    }

    //  USER_LIST END

    //  YOUTUBE_CHANNEL_LIST START

    @Override
    public Map<String, Object> youtubChannelList(String keyWord, int pageNum, int amount) {

        log.info("[AdminService] youtubChannelList()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<ChannelDto> ChannelDtos = iAdminDaoMapper.youtubeChannelList(keyWord, criteria);
        String table = "tbl_youtube_channel";
        String column = "yc_channel";

        int totalCnt = iAdminDaoMapper.getTotalCnt(table, column, keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("ChannelDtos", ChannelDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public int createYoutubeChannelConfirm(ChannelDto channelDto) {

        log.info("[AdminService] createYoutubeChannelConfirm()");

        boolean isChannel = iAdminDaoMapper.isChannel(channelDto);

        if(!isChannel) {
            return iAdminDaoMapper.insertNewChannel(channelDto);
        }

        return -1;
    }

    @Override
    public Object youtubeChannelListDetail(int yc_no) {

        log.info("[AdminService] youtubeChannelListDetail()");

        return iAdminDaoMapper.youtubeChannelListDetail(yc_no);
    }

    @Override
    public int youtubeChannelListModifyConfirm(ChannelDto channelDto) {

        log.info("[AdminService] youtubeChannelListModifyConfirm()");

        return iAdminDaoMapper.youtubeChannelListModifyConfirm(channelDto);
    }

    //  YOUTUBE_CHANNEL_LIST END

    //  YOUTUBE_LIST START
    @Override
    public Map<String, Object> youtubeList(String keyWord, int pageNum, int amount) {
        log.info("[AdminService] youtubeList()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<YoutubeDto> YoutubeDtos = iAdminDaoMapper.youtubeList(keyWord, criteria);
        String table = "tbl_youtube";
        String column = "y_channel";

        int totalCnt = iAdminDaoMapper.getTotalCnt(table, column, keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        System.out.println(totalCnt);

        map.put("YoutubeDtos", YoutubeDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public Object youtubeListDetail(int y_no) {

        log.info("[AdminService] youtubeListDetail()");

        return iAdminDaoMapper.youtubeListDetail(y_no);
    }

    @Override
    public int youtubeListModifyConfirm(YoutubeDto youtubeDto) {

        log.info("[AdminService] youtubeListModifyConfirm()");

        return iAdminDaoMapper.youtubeListModifyConfirm(youtubeDto);

    }

    //  YOUTUBE_LIST END

    //  YOUTUBE_LIST START
    @Override
    public Map<String, Object> boardList(String keyWord, int pageNum, int amount) {
        log.info("[AdminService] boardList()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<BoardDto> BoardDtos = iAdminDaoMapper.boardList(keyWord, criteria);
        String table = "tbl_board";
        String column = "u_id";

        int totalCnt = iAdminDaoMapper.getTotalCnt(table, column, keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        System.out.println(totalCnt);

        map.put("BoardDtos", BoardDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public Object boardListDetail(int b_no) {

        log.info("[AdminService] boardListDetail()");

        return iAdminDaoMapper.boardListDetail(b_no);
    }

    @Override
    public int boardListModifyConfirm(BoardDto boardDto) {

        log.info("[AdminService] boardListModifyConfirm()");

        return iAdminDaoMapper.boardListModifyConfirm(boardDto);

    }

    //  YOUTUBE_LIST END


}
