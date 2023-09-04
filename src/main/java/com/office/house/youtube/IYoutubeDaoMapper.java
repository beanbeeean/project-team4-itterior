package com.office.house.youtube;

import com.office.house.admin.ChannelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IYoutubeDaoMapper {
    List<ChannelDto> searchChannel();

    void insertNewYoutube(YoutubeDto youtubeDto);
}
