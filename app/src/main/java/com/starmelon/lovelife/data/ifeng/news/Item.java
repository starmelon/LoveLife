package com.starmelon.lovelife.data.ifeng.news;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Item {

    public String thumbnail;
    public String online;
    public String title;
    @Nullable
    public String showType;
    @Nullable
    public String source;
    @Nullable
    public String updateTime;
    public String id;
    public String documentId;
    public String type;
    @Nullable
    public boolean hasSlide;
    public String commentsUrl;
    public String comments;
    public String commentsall;
    public String styleType;
    public Link link;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isHasSlide() {
        return hasSlide;
    }

    public void setHasSlide(boolean hasSlide) {
        this.hasSlide = hasSlide;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    /**
     * @return
     */
    public String getSource() {
        return source;
    }


    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     *
     * @param thumbnail
     * The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     *
     * @return
     * The online
     */
    public String getOnline() {
        return online;
    }

    /**
     *
     * @param online
     * The online
     */
    public void setOnline(String online) {
        this.online = online;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The showType
     */
    public String getShowType() {
        return showType;
    }

    /**
     *
     * @param showType
     * The showType
     */
    public void setShowType(String showType) {
        this.showType = showType;
    }

    /**
     *
     * @return
     * The commentsUrl
     */
    public String getCommentsUrl() {
        return commentsUrl;
    }

    /**
     *
     * @param commentsUrl
     * The commentsUrl
     */
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     *
     * @param documentId
     * The documentId
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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
     * The link
     */
    public Link getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(Link link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The comments
     */
    public String getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     *
     * @return
     * The commentsall
     */
    public String getCommentsall() {
        return commentsall;
    }

    /**
     *
     * @param commentsall
     * The commentsall
     */
    public void setCommentsall(String commentsall) {
        this.commentsall = commentsall;
    }

}