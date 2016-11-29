package com.starmelon.lovelife.data.ifeng.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Link {

    public String type;
    public String url;
    public String weburl;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
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

    /**
     *
     * @return
     * The weburl
     */
    public String getWeburl() {
        return weburl;
    }

    /**
     *
     * @param weburl
     * The weburl
     */
    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

}