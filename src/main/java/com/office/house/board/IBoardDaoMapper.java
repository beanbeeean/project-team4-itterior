package com.office.house.board;

import com.office.house.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IBoardDaoMapper {
    int insertNewBoard(BoardDto boardDto);

    List<BoardDto> selectBoardList();

    void updateHit(int bNo);

    BoardDto getBoard(int bNo);

    int updateUserImage(UserDto userDto);

    BoardDto boardModifyForm(int bNo);

    int updateBoard(Map<String, Object> map);

    int deleteBoard(Map<String, Object> boardmap);

}
