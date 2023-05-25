package com.example.onlinebrokerage;

public class model {

    private String ImageUrl;

    public model()
    {

    }

    public model(String ImageUrl)
    {
        this.ImageUrl=ImageUrl;
    }

    public String getImageUrl() {

        return ImageUrl;
    }

    public void setImageUrl(String imageurl) {
        this.ImageUrl = imageurl;
    }
}
