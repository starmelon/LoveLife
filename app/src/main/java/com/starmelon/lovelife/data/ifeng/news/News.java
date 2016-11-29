package com.starmelon.lovelife.data.ifeng.news;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {


    public String listId;
    public String type;
    public Integer expiredTime;
    public Integer currentPage;
    public Integer totalPage;
    public List<Item> item = new ArrayList<Item>();

    /**
     *
     * @return
     * The listId
     */
    public String getListId() {
        return listId;
    }

    /**
     *
     * @param listId
     * The listId
     */
    public void setListId(String listId) {
        this.listId = listId;
    }

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
     * The expiredTime
     */
    public Integer getExpiredTime() {
        return expiredTime;
    }

    /**
     *
     * @param expiredTime
     * The expiredTime
     */
    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     *
     * @return
     * The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     * The currentPage
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     * The totalPage
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     *
     * @param totalPage
     * The totalPage
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    /**
     *
     * @return
     * The item
     */
    public List<Item> getItem() {
        return item;
    }

    /**
     *
     * @param item
     * The item
     */
    public void setItem(List<Item> item) {
        this.item = item;
    }


}