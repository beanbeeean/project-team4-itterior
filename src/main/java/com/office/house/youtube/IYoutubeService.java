package com.office.house.youtube;

import com.office.house.admin.ChannelDto;

import java.util.Map;

public interface IYoutubeService {
    public void youtube();
    void bringYoutube(ChannelDto channelDto);

    Map<String, Object> getYoutubes(String keyWord, String sort, int pageNum, int amount, String u_id);

    int youtubeLikeUpdate(Map<String, String> msgMap);

    int youtubeLikeDelete(Map<String, String> msgMap);

    Map<String, Object> getLikeYoutubes(String keyWord, String sort, int pageNum, int amount, String uId);
}
