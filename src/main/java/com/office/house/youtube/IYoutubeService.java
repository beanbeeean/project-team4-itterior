package com.office.house.youtube;

import com.office.house.admin.ChannelDto;

import java.util.Map;

public interface IYoutubeService {
    public void youtube();
    void bringYoutube(ChannelDto channelDto);

    Map<String, Object> getYoutubes(String keyWord, int pageNum, int amount);
}
