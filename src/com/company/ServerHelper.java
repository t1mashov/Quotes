package com.company;

import java.sql.*;
import java.util.ArrayList;

public class ServerHelper {
    private Statement statement;

    public ServerHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2022_quotes",
                    "std_2022_quotes", "11111111");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("ERROR: Connection failed");
        }
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        String query = "SELECT * FROM `group`";
        try {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                groups.add(new Group(
                        result.getInt("id"),
                        result.getString("num")
                ));
            }
        } catch (Exception e) {}
        return groups;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user`";
        try {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("login"),
                        result.getString("password"),
                        result.getInt("id_group")
                ));
            }
        } catch (Exception e) {}
        return users;
    }

    public void registerUser(String login, String password, int id_group) {
        try {
            statement.executeUpdate(
                    "INSERT INTO `user` (`login`, `password`, `id_group`) VALUES (\""+login+"\", \""+password+"\", "+id_group+")"
            );

            int userId = -1;
            ResultSet res = statement.executeQuery(
                    "SELECT id FROM `user` WHERE `login`=\""+login+"\""
            );
            if (res.next()) userId = res.getInt("id");

            int defaultUserId = -1;
            res = statement.executeQuery(
                    "SELECT id FROM `role` WHERE `name`=\"default_user\""
            );
            if (res.next()) defaultUserId = res.getInt("id");

            statement.executeUpdate(
                    "INSERT INTO user_role (`id_user`, `id_role`) VALUES ("+userId+", "+defaultUserId+")"
            );

        } catch (Exception e) {
            System.out.println("SOME ERROR");
        }

    }



//    public ArrayList<Integer> getEditableQuoteId(User user) {
//        ArrayList<Integer> res = new ArrayList<>();
//
//    }

}
