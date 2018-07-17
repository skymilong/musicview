package com.musicview.musicview.singer.api;

import com.musicview.musicview.singer.Service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/singerinfo")
public class SingerAPI {

    @Autowired
    private SingerService singerService;

    @RequestMapping(name="/add",method = RequestMethod.GET)
    public Map<String,Object> getSingerAdd(){
        try {
            return singerService.getSingerAdd();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
