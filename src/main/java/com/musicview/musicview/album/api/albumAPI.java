package com.musicview.musicview.album.api;

import com.musicview.musicview.album.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/albuminfo")
public class albumAPI {
    @Autowired
    private AlbumService albumService;
    @RequestMapping(name="/add",method = RequestMethod.GET)
    public Map<String,Object> getAlbumAdd(){
        try {
            albumService.getAlbumAdd();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
