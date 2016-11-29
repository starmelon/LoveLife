package com.starmelon.lovelife.data.ifeng.newsdetail;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

    @SerializedName("documentId")
    @Expose
    private String documentId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumbnail_doc")
    @Expose
    private ThumbnailDoc thumbnailDoc;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("editorcode")
    @Expose
    private String editorcode;
    @SerializedName("editTime")
    @Expose
    private String editTime;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("wapurl")
    @Expose
    private String wapurl;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("wwwurl")
    @Expose
    private String wwwurl;
    @SerializedName("shareurl")
    @Expose
    private String shareurl;
    @SerializedName("commentsUrl")
    @Expose
    private String commentsUrl;
    @SerializedName("commentCount")
    @Expose
    private String commentCount;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("img")
    @Expose
    private List<Img> img = new ArrayList<Img>();
    @SerializedName("likeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("commentType")
    @Expose
    private String commentType;
    @SerializedName("praise")
    @Expose
    private String praise;

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
     * The thumbnailDoc
     */
    public ThumbnailDoc getThumbnailDoc() {
        return thumbnailDoc;
    }

    /**
     *
     * @param thumbnailDoc
     * The thumbnail_doc
     */
    public void setThumbnailDoc(ThumbnailDoc thumbnailDoc) {
        this.thumbnailDoc = thumbnailDoc;
    }

    /**
     *
     * @return
     * The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The editorcode
     */
    public String getEditorcode() {
        return editorcode;
    }

    /**
     *
     * @param editorcode
     * The editorcode
     */
    public void setEditorcode(String editorcode) {
        this.editorcode = editorcode;
    }

    /**
     *
     * @return
     * The editTime
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     *
     * @param editTime
     * The editTime
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    /**
     *
     * @return
     * The updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * @param updateTime
     * The updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     * @return
     * The wapurl
     */
    public String getWapurl() {
        return wapurl;
    }

    /**
     *
     * @param wapurl
     * The wapurl
     */
    public void setWapurl(String wapurl) {
        this.wapurl = wapurl;
    }

    /**
     *
     * @return
     * The introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     *
     * @param introduction
     * The introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     *
     * @return
     * The wwwurl
     */
    public String getWwwurl() {
        return wwwurl;
    }

    /**
     *
     * @param wwwurl
     * The wwwurl
     */
    public void setWwwurl(String wwwurl) {
        this.wwwurl = wwwurl;
    }

    /**
     *
     * @return
     * The shareurl
     */
    public String getShareurl() {
        return shareurl;
    }

    /**
     *
     * @param shareurl
     * The shareurl
     */
    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
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
     * The commentCount
     */
    public String getCommentCount() {
        return commentCount;
    }

    /**
     *
     * @param commentCount
     * The commentCount
     */
    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The img
     */
    public List<Img> getImg() {
        return img;
    }

    /**
     *
     * @param img
     * The img
     */
    public void setImg(List<Img> img) {
        this.img = img;
    }

    /**
     *
     * @return
     * The likeCount
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     *
     * @param likeCount
     * The likeCount
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }


    /**
     *
     * @return
     * The commentType
     */
    public String getCommentType() {
        return commentType;
    }

    /**
     *
     * @param commentType
     * The commentType
     */
    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }


    /**
     *
     * @return
     * The praise
     */
    public String getPraise() {
        return praise;
    }

    /**
     *
     * @param praise
     * The praise
     */
    public void setPraise(String praise) {
        this.praise = praise;
    }

}