package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReaderPagePanel extends JPanel {
    public ReaderPagePanel(Main main) {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        JButton back = new JButton("<-");
        back.addActionListener(e -> main.previousPage());
        back.setBounds(50, main.getHeight()-100, 50, 30);

        JPanel table = new JPanel();
        table.setBackground(new Color(220,220,220));
        ArrayList<Quote> allQuotes = TableObjectsBox.quotes;

        table.setLayout(new GridLayout(allQuotes.size(), 1));
        for (Quote q : allQuotes) {
            table.add(new QuoteFragment(
                    false,
                    q,
                    null
            ));
        }
        table.setPreferredSize(new Dimension(main.getWidth()-20, allQuotes.size()*130));

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBounds(10, 10, main.getWidth()-40, main.getHeight()-150);
        scrollTable.getVerticalScrollBar().setUnitIncrement(5);

        this.add(scrollTable);
        this.add(back);

    }
}
