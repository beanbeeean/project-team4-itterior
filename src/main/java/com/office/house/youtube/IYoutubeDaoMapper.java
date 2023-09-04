package com.office.house.youtube;

import com.office.house.admin.ChannelDto;
import com.office.house.board.BoardDto;
import com.office.house.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IYoutubeDaoMapper {
    List<ChannelDto> searchChannel();

    void insertNewYoutube(YoutubeDto youtubeDto);

    List<YoutubeDto> getYoutubes(String keyWord, Criteria criteria);

    int getTotalCnt(String keyWord);
}
