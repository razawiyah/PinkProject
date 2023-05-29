package com.example.pinkproject.appmodel;

public class UserModel {

    String name,email,password,account,id;

    public UserModel() {
    }

    public UserModel(String name, String email, String password, String account, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.account = account;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}