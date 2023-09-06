package com.office.house.youtube;

import com.office.house.admin.ChannelDto;
import com.office.house.board.BoardDto;
import com.office.house.like.LikeDto;
import com.office.house.product.ProductDto;
import com.office.house.util.page.Criteria;
import com.office.house.util.page.PageMakerDto;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
@Log4j2
@Service
public class YoutubeService implements IYoutubeService{

    @Autowired
    IYoutubeDaoMapper iYoutubeDaoMapper;

    @Override
    public void youtube() {

        log.info("[YoutubeService] youtube()");

        List<ChannelDto> ChannelDtos = iYoutubeDaoMapper.searchChannel();

        for(int i = 0;i < ChannelDtos.size();i++){
            bringYoutube(ChannelDtos.get(i));
        }


    }
    @Override
    public void bringYoutube(ChannelDto channelDto) {

        log.info("[YoutubeService] youtube()");

        StringBuilder result = new StringBuilder();

        String key = "AIzaSyC7o2xkuXM_LxnN-jw-QsXOn-1eS-rwfvw";        //고등학교 아스키코드

        try {

            String apiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&regionCode=kr&type=video,channel&order=date" +
                    "&q=" + channelDto.getYc_channel().replaceAll(" ", "%20") +
                    "&maxResults=" + channelDto.getYc_bring_cnt() +
                    "&key=" + key;

            System.out.println(apiUrl);

            //https://www.googleapis.com/youtube/v3/search?part=snippet&q=아울%20디자인&maxResults=5&regionCode=kr&type=video,channel&order=date&&key=AIzaSyC7o2xkuXM_LxnN-jw-QsXOn-1eS-rwfvw

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine);
            }

            JSONParser jsonParser = new JSONParser();
            System.out.println(result);
            JSONObject jsonObj = (JSONObject) jsonParser.parse(String.valueOf(result));

            JSONArray array = (JSONArray)jsonObj.get("items");
            for (int i = 0; i < array.size(); i++) {
                JSONObject jObj = (JSONObject) array.get(i);

                JSONObject jObj2 = (JSONObject) jObj.get("id");

                JSONObject jObj3 = (JSONObject) jObj.get("snippet");
                JSONObject jObj4 = (JSONObject) jObj3.get("thumbnails");
                JSONObject jObj5 = (JSONObject) jObj4.get("default");

                YoutubeDto youtubeDto = new YoutubeDto();

                youtubeDto.setY_id((String) jObj2.get("videoId"));
                youtubeDto.setY_channel(channelDto.getYc_channel());
                youtubeDto.setY_title((String)jObj3.get("title"));
                youtubeDto.setY_content((String)jObj3.get("description"));
                youtubeDto.setY_img((String)jObj5.get("url"));
                youtubeDto.setY_date((String)jObj3.get("publishTime"));

                iYoutubeDaoMapper.insertNewYoutube(youtubeDto);

                //https://www.youtube.com/embed/

            }

            urlConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getYoutubes(String keyWord, int pageNum, int amount, String u_id) {

        log.info("[YoutubeService] getYoutubes()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<YoutubeDto> YoutubeDtos = iYoutubeDaoMapper.getYoutubes(keyWord, criteria, u_id);

        int totalCnt = iYoutubeDaoMapper.getTotalCnt(keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("YoutubeDtos", YoutubeDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public Map<String, Object> getLikeYoutubes(String keyWord, int pageNum, int amount, String u_id) {

        log.info("[YoutubeService] getLikeYoutubes()");

        Map<String, Object> map = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        List<YoutubeDto> YoutubeDtos = iYoutubeDaoMapper.getLikeYoutubes(keyWord, criteria, u_id);

        int totalCnt = iYoutubeDaoMapper.getTotalLikeCnt(keyWord, u_id);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        map.put("YoutubeDtos", YoutubeDtos);
        map.put("pageMakerDto", pageMakerDto);

        return map;
    }

    @Override
    public int youtubeLikeUpdate(Map<String, String> msgMap) {

        log.info("[YoutubeService] youtubeLikeUpdate()");

        iYoutubeDaoMapper.increaseLike(msgMap.get("no"));
        iYoutubeDaoMapper.insertYoutubeLike(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));

        return iYoutubeDaoMapper.searchLike(msgMap.get("no"));
    }

    @Override
    public int youtubeLikeDelete(Map<String, String> msgMap) {

        log.info("[YoutubeService] youtubeLikeDelete()");

        iYoutubeDaoMapper.decreaseLike(msgMap.get("no"));
        iYoutubeDaoMapper.deleteYoutubeLike(msgMap.get("type"), msgMap.get("no"), msgMap.get("u_id"));

        return iYoutubeDaoMapper.searchLike(msgMap.get("no"));
    }

    public Map<String, Object> getMainYoutubeList() {
        log.info("[YoutubeService] getMainYoutubeList");
        Map<String, Object> map = new HashMap<>();

        List<YoutubeDto> dtos = iYoutubeDaoMapper.selectMainYoutube();

        List<Integer> likeList = new ArrayList<>();
        for(YoutubeDto dto : dtos){
            if(dto.getY_like() > 0){
                likeList.add(dto.getY_no());
            }
        }

        log.info("likeList: " + likeList);


        List<LikeDto> isLikedDtos = new ArrayList<>();
        if(likeList.size() > 0){
            isLikedDtos = iYoutubeDaoMapper.selectLikedYoutube(likeList);
        }

        log.info(isLikedDtos);
        if(isLikedDtos.get(0) == null){
            isLikedDtos.clear();
        }
        map.put("isLikedDtos", isLikedDtos);
        map.put("youtubeDtos", dtos);
        return map;
    }
}
