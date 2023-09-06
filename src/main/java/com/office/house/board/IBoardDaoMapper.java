package com.office.house.board;

import com.office.house.like.LikeDto;
import com.office.house.user.UserDto;
import com.office.house.youtube.YoutubeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IBoardDaoMapper {
    int insertNewBoard(BoardDto boardDto);

    List<BoardDto> selectBoardList(int sort, String keyword, int skip, int amount);

    void updateHit(int bNo);

    BoardDto getBoard(int bNo);

    int updateUserImage(UserDto userDto);

    BoardDto boardModifyForm(int bNo);

    int updateBoard(Map<String, Object> map);

    int deleteBoard(Map<String, Object> boardmap);

    int selectBoardListCnt(int sort, String keyword, int skip, int amount);

    List<LikeDto> selectLikedBoard(List<Integer> likeList);

    int updateLikeCountForBoard(String no, int likeCnt);

    BoardDto selectBoardByNo(String no);

    List<BoardDto> selectLikeBoardList (Map<String, Object> map);

    List<BoardDto> selectMainBoardList();
    List<BoardDto> selectBoardMainSearch(String keyword, String uId);

    void increaseLike(String no);

    void insertBoardLike(String type, String no, String u_id);

    int searchLike(String no);

    void decreaseLike(String no);

    void deleteBoardLike(String type, String no, String u_id);
}
