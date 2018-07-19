package com.musicview.musicview.singer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WordsController {

    @RequestMapping("/wordscloud")
    public String toSongWords(){
        return "songwords";
    }
}
