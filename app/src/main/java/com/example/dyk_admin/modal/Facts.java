package com.example.dyk_admin.modal;

public class Facts {
    String Category, Content, Date, FactID, Time;

    public Facts() {
    }

    public Facts(String category, String content, String date, String factID, String time) {
        Category = category;
        Content = content;
        Date = date;
        FactID = factID;
        Time = time;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFactID() {
        return FactID;
    }

    public void setFactID(String factID) {
        FactID = factID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}