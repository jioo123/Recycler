package com.study.recycler_view;

public class Album {
    private String title = "";
    private String artist = "";
    private int image;  // 이런식으로 이미지 Id 를 데이터클래스에 저장하는건 좋지못함

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setImage(int image) {
        this.image = image;
    }




}
