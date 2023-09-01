package com.office.house.board;

import com.office.house.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardDaoMapper {
    int insertNewBoard(BoardDto boardDto);

    List<BoardDto> selectBoardList();

    BoardDto getBoard(int bNo);

    int updateUserImage(UserDto userDto);
}
