package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegQuotePanel extends JPanel {
    Main main;
    QuoteFillFragment nq;
    JButton send, newQuote, update, delete, undo;
    JPanel table;
    JScrollPane scrollTable;

    Quote selectedQuote;


    // создание/обновление окна с цитатами
    public void updateContentPanel() {
        if (table!=null) this.remove(table);
        if (scrollTable!=null) this.remove(scrollTable);

        // панель с цитатами
        table = new JPanel();
        table.setBackground(new Color(220,220,220));
        main.table.updateQuotes();
        ArrayList<Quote> allQuotes = TableObjectsBox.quotes;

        table.setLayout(new GridLayout(allQuotes.size(), 1));
        for (Quote q : allQuotes) {
            table.add(new QuoteFragment(
                    main.currentUser.controlledUserId.contains(q.user_id),
                    q,
                    this
            ));
        }
        table.setPreferredSize(new Dimension(main.getWidth()-20, allQuotes.size()*130));

        // прокрутка для панели с цитатами
        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(10, 10, main.getWidth()-40, main.getHeight()/2-10);
        scrollTable.getVerticalScrollBar().setUnitIncrement(5);

        this.add(scrollTable);
        this.repaint();
    }


    public RegQuotePanel(Main main) {
        this.main = main;
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        //создание/обновление окна с цитатами
        updateContentPanel();

        JButton back = new JButton("<-");
        back.addActionListener(e -> main.previousPage());
        back.setBounds(50, main.getHeight()-100, 50, 30);

        // панель для создания/редактирования записи
        nq = new QuoteFillFragment();
        nq.setBounds(50, main.getHeight()/2+20, main.getWidth()-100, 150);

        update = new JButton("Изменить");
        update.setBounds(main.getWidth()/2-150, main.getHeight()-100, 130, 30);
        update.addActionListener(e -> {
            this.remove(update);
            this.remove(delete);
            this.remove(undo);
            this.remove(nq);

            // сохранение позиции scroll
            int currentScroll = scrollTable.getVerticalScrollBar().getValue();

            main.database.updateQuote(
                    selectedQuote.id,
                    nq.subject.getText(),
                    nq.teacher.getText(),
                    nq.quoteArea.getText(),
                    nq.date.getText()
            );
            main.table.updateQuotes();

            updateContentPanel();
            this.add(newQuote);
            main.revalidate();

            scrollTable.getVerticalScrollBar().setValue(currentScroll);
        });

        delete = new JButton("Удалить");
        delete.setBounds(main.getWidth()/2, main.getHeight()-100, 130, 30);
        delete.addActionListener(e -> {
            this.remove(update);
            this.remove(delete);
            this.remove(undo);
            this.remove(nq);

            // сохранение позиции scroll
            int currentScroll = scrollTable.getVerticalScrollBar().getValue();

            main.database.deleteQuote(selectedQuote.id);
            main.table.updateQuotes();

            updateContentPanel();
            this.add(newQuote);
            main.revalidate();

            scrollTable.getVerticalScrollBar().setValue(currentScroll);
        });

        undo = new JButton("Отмена");
        undo.setBounds(main.getWidth()/2+150, main.getHeight()-100, 130, 30);
        undo.addActionListener(e -> {
            this.remove(update);
            this.remove(delete);
            this.remove(undo);
            this.remove(send);
            this.remove(nq);
            this.add(newQuote);
            this.repaint();
        });

        send = new JButton("Отправить");
        send.setBounds(main.getWidth()/2, main.getHeight()-100, 130, 30);

        newQuote = new JButton("новая цитата");
        newQuote.setBounds(main.getWidth()/2, main.getHeight()-100, 130, 30);
        newQuote.addActionListener(e -> {
            this.remove(newQuote);
            this.add(undo);
            this.add(send);
            this.add(nq);
            this.repaint();
            nq.repaintAll();
        });

        send.addActionListener(e -> {
            this.add(newQuote);
            this.remove(send);
            this.remove(undo);
            this.remove(nq);
            this.repaint();

            // сохранение позиции scroll
            int currentScroll = scrollTable.getVerticalScrollBar().getValue();

            main.database.addQuote(
                    main.currentUser.id,
                    nq.quoteArea.getText(),
                    nq.subject.getText(),
                    nq.teacher.getText(),
                    nq.date.getText()
            );
            main.table.updateQuotes();

            updateContentPanel();
            main.revalidate();
            scrollTable.getVerticalScrollBar().setValue(currentScroll);
        });


        this.add(newQuote);
        this.add(back);
        this.add(scrollTable);
    }

    // функция включения режима редактирования
    public void edit(QuoteFragment fragment) {
        selectedQuote = fragment.q;
        if (nq!=null) this.remove(nq);

        nq = new QuoteFillFragment();
        nq.setBounds(50, main.getHeight()/2+20, main.getWidth()-100, 150);

        nq.quoteArea.setText(fragment.quote.getText());
        nq.subject.setText(fragment.subject.getText());
        nq.teacher.setText(fragment.teacher.getText());
        nq.date.setText(fragment.date.getText());

        this.remove(newQuote);
        this.remove(send);

        this.add(update);
        this.add(delete);
        this.add(undo);

        this.add(nq);
        this.repaint();
        nq.updateAll();
    }
}

// фрагмент panel для отображения цитат в списке
class QuoteFragment extends JPanel {

    public JButton select;
    public JPanel quotePanel;
    public JLabel quote, subject, teacher, date;
    // для определения id выделенной цитаты
    public Quote q;

    public QuoteFragment(boolean canEdit, Quote _quote, RegQuotePanel context) {
        this.setLayout(null);
        this.setBackground(new Color(220, 220, 220));
        this.q = _quote;

        quotePanel = new JPanel();
        quotePanel.setLayout(null);

        quote = new JLabel(_quote.quote);
        quote.setFont(new Font("Arial", Font.ITALIC, 14));
        quote.setBounds(5, 5, 600, 60);

        subject = new JLabel(_quote.subject);
        teacher = new JLabel(_quote.teacher);
        date = new JLabel(_quote.date!=null ? _quote.date.toString() : "");

        try {
            User user = User.findUserById(_quote.user_id);
            String userName = user.login;
            String userGroup = Group.findGroupById(user.id_group).num;
            JLabel userText = new JLabel("<html><span style=\"color:#aaaaaa\">"+userName+", "+userGroup+"</span></html>");
            userText.setBounds(5, 0, 200, 30);
            quotePanel.add(userText);
        } catch (Exception e) {}

        subject.setBounds(5, 70, 200, 30);
        teacher.setBounds(600-teacher.getText().length()*5, 70, 200, 30);
        date.setBounds(650-100, 95, 100, 30);

        quotePanel.setBackground(new Color(210, 210, 210));
        quotePanel.setBounds(70, 10, 650, 130);

        quotePanel.add(quote);
        quotePanel.add(subject);
        quotePanel.add(teacher);
        quotePanel.add(date);

        this.add(quotePanel);

        if (canEdit) {
            quotePanel.setBackground(new Color(250, 250, 250));
            select = new JButton(">>");
            select.setBounds(10, 10, 50, 30);
            QuoteFragment temp = this;
            select.addActionListener(e -> context.edit(temp));
            this.add(select);
        }

        this.revalidate();
    }
}

// фрагмент panel для полей для изменения/добавления записи
class QuoteFillFragment extends JPanel {

    public JPanel attrs;
    public JTextArea quoteArea;
    public JTextField subject, teacher, date;

    public QuoteFillFragment() {
        this.setLayout(null);
        this.setBackground(new Color(220,220,220));

        quoteArea = new JTextArea();
        quoteArea.setBounds(0, 0, 700, 60);
        quoteArea.setPreferredSize(new Dimension(700, 60));

        attrs = new JPanel();
        attrs.setBackground(new Color(220,220,220));
        attrs.setLayout(new GridLayout(3, 2));
        attrs.setBounds(0, 65, 300, 90);
        attrs.setPreferredSize(new Dimension(300, 90));

        subject = new JTextField();
        teacher = new JTextField();
        date = new JTextField();

        attrs.add(new JLabel("предмет: "));
        attrs.add(subject);
        attrs.add(new JLabel("автор: "));
        attrs.add(teacher);
        attrs.add(new JLabel("дата: "));
        attrs.add(date);

        this.add(quoteArea);
        this.add(attrs);
    }

    public void repaintAll() {
        subject.setText("");
        teacher.setText("");
        date.setText("");
        quoteArea.setText("");
        attrs.revalidate();
        this.repaint();
    }
    public void updateAll() {
        attrs.revalidate();
        this.repaint();
    }
}
