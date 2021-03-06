package com.starmelon.lovelife.data.ifeng.newsdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailDoc {

    @SerializedName("size")
    @Expose
    private Size size;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     *
     * @return
     * The size
     */
    public Size getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}