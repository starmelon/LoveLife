package com.starmelon.lovelife.data.ifeng.news;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Style {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("images")
    @Expose
    private List<String> images = new ArrayList<String>();
    @SerializedName("slideCount")
    @Expose
    private Integer slideCount;

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
     * The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The slideCount
     */
    public Integer getSlideCount() {
        return slideCount;
    }

    /**
     *
     * @param slideCount
     * The slideCount
     */
    public void setSlideCount(Integer slideCount) {
        this.slideCount = slideCount;
    }

}