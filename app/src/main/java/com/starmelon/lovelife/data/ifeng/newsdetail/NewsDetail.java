package com.starmelon.lovelife.data.ifeng.newsdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class NewsDetail {


    private Meta meta;

    private Body body;

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     * The body
     */
    public Body getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(Body body) {
        this.body = body;
    }

}
