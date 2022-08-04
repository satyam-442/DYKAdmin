package com.example.dyk_admin.modal;

public class Users {
    String About, Email, Name, Password, Phone, token, uid;

    public Users() {
    }

    public Users(String about, String email, String name, String password, String phone, String token, String uid) {
        About = about;
        Email = email;
        Name = name;
        Password = password;
        Phone = phone;
        this.token = token;
        this.uid = uid;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
