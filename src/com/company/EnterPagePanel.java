package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EnterPagePanel extends JPanel {
    public EnterPagePanel(Main main) {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        JLabel error = new JLabel("");
        error.setFont(new Font("Arial", Font.PLAIN, 20));
        error.setBounds(main.getWidth()/2-150, 50, 300, 30);

        JButton back = new JButton("<-");
        back.addActionListener(e -> main.previousPage());
        back.setBounds(50, main.getHeight()-100, 50, 30);

        JLabel loginText = new JLabel("login:");
        loginText.setFont(new Font("Arial", Font.PLAIN, 20));
        loginText.setBounds(main.getWidth()/2-100, main.getHeight()/2-100, 50, 30);

        JTextField login = new JTextField();
        login.setBounds(main.getWidth()/2-40, main.getHeight()/2-100, 200, 30);

        JLabel passwordText = new JLabel("пароль: ");
        passwordText.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordText.setBounds(main.getWidth()/2-120, main.getHeight()/2-100+40, 100,30);

        JTextField password = new JTextField();
        password.setBounds(main.getWidth()/2-40, main.getHeight()/2-100+40, 200, 30);

        JButton enter = new JButton("Войти");
        enter.setBounds(main.getWidth()/2-50, main.getHeight()/2, 100, 30);


        ArrayList<User> users = main.database.getUsers();
        enter.addActionListener(e -> {
            String userLogin = login.getText();
            String userPassword = password.getText();
            boolean correct = false;
            for (User u : users) {
                if (userLogin.equals(u.login) && userPassword.equals(u.password)) {
                    correct = true;
                    main.currentUser = u;
                }
            }
            if (correct) {
                error.setText("");



            } else {
                error.setText("<html><span style=\"color:red\">Неверный логин или пароль!</span></html>");
            }
        });

        this.add(back);
        this.add(loginText);
        this.add(login);
        this.add(passwordText);
        this.add(password);
        this.add(enter);
        this.add(error);

    }
}
