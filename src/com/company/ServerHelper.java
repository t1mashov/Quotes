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
                    "INSERT INTO user_role (`id_user`, `id_role`, `verify_group_id`) VALUES ("+userId+", "+defaultUserId+", 1)"
            );

        } catch (Exception e) {
            System.out.println("ERROR IN registerUser()");
        }

    }



    public ArrayList<Quote> getQuotes() {
        ArrayList<Quote> quotes = new ArrayList<>();

        try {
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM `quote`"
            );
            while (result.next()) {
                quotes.add(new Quote(
                        result.getInt("id"),
                        result.getString("subject"),
                        result.getString("teacher"),
                        result.getString("quote"),
                        result.getDate("date"),
                        result.getInt("user_id")
                ));
            }
        } catch (Exception e) {}
        return quotes;
    }



    public ArrayList<UserRole> getUsersRoles() {
        ArrayList<UserRole> usersRoles = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM `user_role`"
            );
            while (result.next()) {
                usersRoles.add(new UserRole(
                        result.getInt("id_user"),
                        result.getInt("id_role"),
                        result.getInt("verify_group_id")
                ));
            }
        } catch (Exception e) {}
        return usersRoles;
    }



    public ArrayList<Role> getRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM `role`"
            );
            while (result.next()) {
                roles.add(new Role(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }
        } catch (Exception e) {}
        return roles;
    }



    public void addQuote(int userId, String quote, String subject, String teacher, String date) {
        try {
            String query = date.equals("")
                    ? "INSERT INTO `quote` (`subject`, `teacher`, `quote`, `user_id`)" +
                            "VALUES (\""+subject+"\", \""+teacher+"\", \""+quote+"\", "+userId+")"
                    : "INSERT INTO `quote` (`subject`, `teacher`, `quote`, `date`, `user_id`)" +
                            "VALUES (\""+subject+"\", \""+teacher+"\", \""+quote+"\", Date(\""+date+"\"), "+userId+")";
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("ERROR IN addQuote()");
        }
    }



    public void updateUserData(int userId, String userLogin, String userPassword, int userIdGroup) {
        try {
            System.out.println(userId+", "+userLogin+", "+userPassword+", "+userIdGroup);
            statement.executeUpdate(
                    "UPDATE `user` "+
                        "SET `login` = \""+userLogin+"\", `password` = \""+userPassword+"\", `id_group` = "+userIdGroup+" "+
                        "WHERE `id` = "+userId
            );
        } catch (Exception e) {
            System.out.println("ERROR IN updateUserData()");
        }
    }



    public void updateQuote(int quoteId, String subject, String teacher, String quote, String date) {
        try {
            String query = "UPDATE `quote` "+
                    "SET `subject`=\""+subject+"\", "+
                    "`teacher`=\""+teacher+"\", "+
                    "`quote`=\""+quote+"\", " +
                    "`date`=Date(\""+date+"\") " +
                    "WHERE `id` = "+quoteId;
            System.out.println(query);
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("ERROR IN updateQuote()");
        }
    }



    public void deleteQuote(int id) {
        try {
            statement.executeUpdate(
                    "DELETE FROM `quote` WHERE `id` = "+id
            );
        } catch (Exception e) {
            System.out.println("ERROR IN deleteQuote()");
        }
    }


}
