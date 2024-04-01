package com.example.medical_application;

public class musics {
    private String audio_title,aid;



    public musics(String audio_title,  String aid) {
        this.audio_title = audio_title;
        this.aid = aid;
    }

    public String getName() {
        return audio_title;
    }
    public String getid() {
        return aid;
    }
}
