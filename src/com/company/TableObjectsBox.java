package com.company;

import java.util.ArrayList;

public class TableObjectsBox {
    static ArrayList<Group> groups;
    static ArrayList<Quote> quotes;
    static ArrayList<Role> roles;
    static ArrayList<User> users;
    static ArrayList<UserRole> userRoles;

    Main main;

    public TableObjectsBox(Main main) {
        this.main = main;
    }

    public void update(Main main) {
        groups = main.database.getGroups();
        quotes = main.database.getQuotes();
        roles = main.database.getRoles();
        users = main.database.getUsers();
        userRoles = main.database.getUsersRoles();
    }

    public void updateQuotes() {
        quotes = main.database.getQuotes();
    }

    public void updateUsers() {
        users = main.database.getUsers();
    }

    public void updateUserRoles() {
        userRoles = main.database.getUsersRoles();
    }

}
