package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegPagePanel extends JPanel {

    JButton reg;
    JTextField login, password;
    JLabel error;
    ArrayList<Group> groups;
    JComboBox<String> groupsBox;

    public RegPagePanel(Main main) {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        JButton back = new JButton("<-");
        back.addActionListener(e -> main.previousPage());
        back.setBounds(50, main.getHeight()-100, 50, 30);

        JLabel loginText = new JLabel("login:");
        loginText.setFont(new Font("Arial", Font.PLAIN, 20));
        loginText.setBounds(main.getWidth()/2-100, main.getHeight()/2-100, 50, 30);

        login = new JTextField();
        login.setBounds(main.getWidth()/2-40, main.getHeight()/2-100, 200, 30);

        JLabel passwordText = new JLabel("пароль: ");
        passwordText.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordText.setBounds(main.getWidth()/2-120, main.getHeight()/2-100+40, 100,30);

        password = new JTextField();
        password.setBounds(main.getWidth()/2-40, main.getHeight()/2-100+40, 200, 30);

        JLabel groupText = new JLabel("группа: ");
        groupText.setFont(new Font("Arial", Font.PLAIN, 20));
        groupText.setBounds(main.getWidth()/2-120, main.getHeight()/2-100+40*2, 100,30);

        groups = main.database.getGroups();

        String[] groupNames = groups.stream()
                .map(g -> g.num)
                .toArray(String[]::new);
        groupsBox = new JComboBox(groupNames);
        groupsBox.setBounds(main.getWidth()/2-40, main.getHeight()/2-100+40*2, 100, 30);

        List<String> usersLogins = Arrays.stream(main.database.getUsers()
                .stream()
                .map(u -> u.login)
                .toArray(String[]::new))
                .toList();


        error = new JLabel("");
        error.setFont(new Font("Arial", Font.PLAIN, 20));
        error.setBounds(main.getWidth()/2-150, 50, 300, 30);

        reg = new JButton("Зарегистрироваться");
        reg.setBounds(main.getWidth()/2-80, main.getHeight()/2-100+40*3, 160, 30);
        reg.addActionListener(e -> {
            String userLogin = login.getText().strip();
            String userPassword = password.getText();
            if (usersLogins.contains(userLogin)) {
                error.setText("<html><span style=\"color:red\">Данный логин уже существует!</span></html>");
            } else if (userLogin.equals("")) {
                error.setText("<html><span style=\"color:red\">Укажите логин!</span></html>");
            } else {
                error.setText("");

                // Процесс регистрации
                int userIdGroup = groups.stream()
                        .filter(g -> g.num.equals(groupsBox.getSelectedItem()))
                        .toArray(Group[]::new)[0].id;

                main.database.registerUser(userLogin, userPassword, userIdGroup);

                main.changePage(new EnterPagePanel(main));
            }
        });

        this.add(back);
        this.add(loginText);
        this.add(login);
        this.add(passwordText);
        this.add(password);
        this.add(groupText);
        this.add(groupsBox);
        this.add(reg);
        this.add(error);
    }
}
