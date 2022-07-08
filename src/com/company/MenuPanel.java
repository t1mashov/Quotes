package com.company;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel(Main main) {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        JButton back = new JButton("<-");
        back.addActionListener(e -> main.previousPage());
        back.setBounds(50, main.getHeight()-100, 50, 30);

        JButton quotes = new JButton("Цитаты");
        quotes.setBounds(main.getWidth()/2-50, main.getHeight()/2-80, 100, 30);
        quotes.addActionListener(e -> {
            main.changePage(new RegQuotePanel(main));
        });

        JButton changeRegData = new JButton("Изменить регистрационные данные");
        changeRegData.setBounds(main.getWidth()/2-125, main.getHeight()/2-15, 250, 30);
        changeRegData.addActionListener(e -> {
            main.changePage(new UpdateRegDataPanel(main));
        });

        JButton stat = new JButton("Статистика");
        stat.addActionListener(e -> {
            main.changePage(new StatPanel(main));
        });
        stat.setBounds(main.getWidth()/2-50, main.getHeight()/2+50, 100, 30);

        this.add(quotes);
        this.add(changeRegData);
        this.add(stat);
        this.add(back);

    }
}
