package com.office.house.youtube;

import com.office.house.admin.ChannelDto;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

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

}