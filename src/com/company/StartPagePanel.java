package com.company;

import javax.swing.*;
import java.awt.*;

public class StartPagePanel extends JPanel {
    public StartPagePanel(Main main) {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        int bw = 100, bh = 30;

        JButton enter = new JButton("Войти");
        enter.addActionListener(e -> {
            main.database = new ServerHelper();
            main.table.update();
            main.changePage(new EnterPagePanel(main));
        });
        enter.setBounds(
                main.getWidth()/2-bw/2, main.getHeight()/3-bh/2,
                bw, bh
        );

        bw = 150;
        JButton reg = new JButton("Регистрация");
        reg.addActionListener(e -> {
            main.database = new ServerHelper();
            main.table.update();
            main.changePage(new RegPagePanel(main));
        });
        reg.setBounds(
                main.getWidth()/2-bw/2, main.getHeight()/3-bh/2+bh+10,
                bw, bh
        );

        bw = 200;
        JButton enterGuest = new JButton("Вход без регистрации");
        enterGuest.addActionListener(e -> {
            main.database = new ServerHelper();
            main.table.update();
            main.changePage(new ReaderPagePanel(main));
        });
        enterGuest.setBounds(
                main.getWidth()/2-bw/2, main.getHeight()/3-bh/2+(bh+10)*2,
                bw, bh
        );


        this.add(enter);
        this.add(reg);
        this.add(enterGuest);

    }
}
