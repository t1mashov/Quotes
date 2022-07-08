package com.company;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class UpdateRegDataPanel extends RegPagePanel {
    public UpdateRegDataPanel(Main main) {
        super(main);

        List<String> usersLogins = Arrays.stream(main.database.getUsers()
                        .stream()
                        .map(u -> u.login)
                        .toArray(String[]::new))
                .toList();

        this.remove(reg);
        this.remove(this.login);
        this.remove(this.password);

        this.login.setText(main.currentUser.login);
        this.password.setText(main.currentUser.password);

        this.add(this.login);
        this.add(this.password);

        reg = new JButton("Изменить");
        reg.setBounds(main.getWidth()/2-80, main.getHeight()/2-100+40*3, 160, 30);
        reg.addActionListener(e -> {
            String userLogin = login.getText().strip();
            String userPassword = password.getText();
            if (usersLogins.contains(userLogin) && !userLogin.equals(main.currentUser.login)) {
                error.setText("<html><span style=\"color:red\">Данный логин уже существует!</span></html>");
            } else if (userLogin.equals("")) {
                error.setText("<html><span style=\"color:red\">Укажите логин!</span></html>");
            } else {
                error.setText("");

                // Изменение данных регистрации
                int userIdGroup = groups.stream()
                        .filter(g -> g.num.equals(groupsBox.getSelectedItem()))
                        .toArray(Group[]::new)[0].id;

                main.database.updateUserData(main.currentUser.id, userLogin, userPassword, userIdGroup);

                main.previousPage();
            }
        });

        this.add(reg);

    }
}
