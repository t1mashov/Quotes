package com.company;

import java.util.Date;

class Group {
    public int id;
    public String num;

    public Group(int id, String num) {
        this.id = id;
        this.num = num;
    }
}

class User {
    public int id;
    public String login;
    public String password;
    public int id_group;

    public User(int id, String login, String password, int id_group) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.id_group = id_group;
    }
}

class Quote {
    public int id;
    public String subject;
    public String teacher;
    public String quote;
    public Date date;

    public Quote(int id, String subject, String teacher, String quote, Date date) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.quote = quote;
        this.date = date;
    }
}

class UserQuote {
    public int id_user;
    public int id_quote;
}