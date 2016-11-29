package com.starmelon.lovelife.data.ifeng.newsdetail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Meta {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("o")
    @Expose
    private String o;
    @SerializedName("documentId")
    @Expose
    private String documentId;

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
     * The o
     */
    public String getO() {
        return o;
    }

    /**
     *
     * @param o
     * The o
     */
    public void setO(String o) {
        this.o = o;
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

}