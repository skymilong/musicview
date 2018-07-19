package com.musicview.musicview.singer.api;

import com.musicview.musicview.singer.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class WordsAPI {
    @Autowired
    SongService songService;

    @RequestMapping("/wordcloud")
    public Map<String,Object> getWordsCloud(){
        try {
           return songService.getWordsCloud();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
