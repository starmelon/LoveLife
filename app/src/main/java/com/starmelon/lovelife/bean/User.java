package com.starmelon.lovelife.bean;

/**
 * Created by starmelon on 2016/11/17 0017.
 */

public class User {

    private String nickname;
    private String pwb;
    private String headPic;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwb() {
        return pwb;
    }

    public void setPwb(String pwb) {
        this.pwb = pwb;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public User(String nickname, String pwb, String headPic) {
        this.nickname = nickname;
        this.pwb = pwb;
        this.headPic = headPic;
    }
}
