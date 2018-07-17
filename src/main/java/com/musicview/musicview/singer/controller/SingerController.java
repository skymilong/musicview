package com.musicview.musicview.singer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingerController {

    @RequestMapping("/singerpage")
    public String getSingerPage(){
        return "singer";
    }
}
