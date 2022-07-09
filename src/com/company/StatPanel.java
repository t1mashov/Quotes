package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatPanel extends JPanel {
    public  StatPanel(Main main) {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        //ArrayList<Quote> allQuotes = main.database.getQuotes();
        ArrayList<Quote> allQuotes = TableObjectsBox.quotes;
        int count = 0;
        for (Quote q : allQuotes) {
            if (q.user_id == main.currentUser.id) count += 1;
        }

        JLabel myQuotesText = new JLabel("Количество моих записей: "+count);
        myQuotesText.setBounds(50, 50, 200, 30);

        JButton back = new JButton("<-");
        back.addActionListener(e -> main.previousPage());
        back.setBounds(50, main.getHeight()-100, 50, 30);

        this.add(myQuotesText);
        this.add(back);

    }
}
