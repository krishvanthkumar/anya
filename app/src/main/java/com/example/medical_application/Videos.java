package com.example.medical_application;

public class Videos {
    private String video_title,vid;



    public Videos(String video_title,  String vid) {
        this.video_title = video_title;
        this.vid = vid;
    }

    public String getName() {
        return video_title;
    }
    public String getid() {
        return vid;
    }
}
