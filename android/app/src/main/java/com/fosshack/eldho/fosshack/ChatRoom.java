package com.fosshack.eldho.fosshack;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.Serializable;


public class ChatRoom implements Serializable {
    String models,pk,title,content,lon,lan,img,upvotes,downvotes,originality;

    Bitmap bit;


    public ChatRoom() {
    }





    public String getmodels() {
        return models;
    }

    public void setmodels(String models) {
        this.models = models;
    }

    public String getpk() {
        return pk;
    }

    public void setpk(String pk) {
        this.pk = pk;
    }


    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }
   public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getlon() {
        return lon;
    }

    public void setlon(String lon) {
        this.lon = lon;
    }
 public String getlan() {
        return lan;
    }

    public void setlan(String lan) {
        this.lan = lan;
    }
public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }


public String getupvotes() {
        return upvotes;
    }

    public void setupvotes(String upvotes) {
        this.upvotes = upvotes;
    }

public String getdownvotes() {
        return downvotes;
    }

    public void setdownvotes(String downvotes) {
        this.downvotes = downvotes;
    }
public String getoriginality() {
        return originality;
    }

    public void setoriginality(String originality) {
        this.originality = originality;
    }



}
