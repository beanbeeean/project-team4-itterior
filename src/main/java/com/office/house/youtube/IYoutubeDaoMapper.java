package com.office.house.youtube;

import com.office.house.admin.ChannelDto;
import com.office.house.board.BoardDto;
import com.office.house.like.LikeDto;
import com.office.house.util.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IYoutubeDaoMapper {
    List<ChannelDto> searchChannel();

    void insertNewYoutube(YoutubeDto youtubeDto);

    List<YoutubeDto> getYoutubes(String keyWord, String sort, Criteria criteria, String u_id);

    List<YoutubeDto> getLikeYoutubes(String keyWord, Criteria criteria, String u_id);
    int getTotalCnt(String keyWord);
    void insertYoutubeLike(String type, String no, String u_id);

    void deleteYoutubeLike(String type, String no, String u_id);

    void increaseLike(String no);

    void decreaseLike(String no);

    int searchLike(String no);

    int getTotalLikeCnt(String keyWord, String u_id);

    List<YoutubeDto> selectMainYoutube();

    List<LikeDto> selectLikedYoutube(List<Integer> likeList);

    int updateLikeCountForYoutube(String no, int likeCnt);

    YoutubeDto selectYoutubeByNo(String no);
    List<YoutubeDto> selectYoutubeMainSearch(String keyword, String uId);

    int isYoutube(String y_id);
}
