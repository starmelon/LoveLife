package com.starmelon.lovelife.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.sql.Timestamp;

/**
 * Created by starmelon on 2016/11/13 0013.
 */

@Entity
public class Collection {

    @Id
    private Long id;
    private int  newsid;
    private String title;//
    private long time;
    //private String time;//收藏的时间
    @Generated(hash = 656158102)
    public Collection(Long id, int newsid, String title, long time) {
        this.id = id;
        this.newsid = newsid;
        this.title = title;
        this.time = time;
    }
    @Generated(hash = 1149123052)
    public Collection() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getNewsid() {
        return this.newsid;
    }
    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    




}
