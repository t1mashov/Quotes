package com.company;

import javax.swing.*;
import java.util.Stack;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    Stack<JPanel> pagesHistory = new Stack<>();
    ServerHelper database;
    User currentUser = null;
    TableObjectsBox table = new TableObjectsBox(this);

    private int width = 800;
    private int height = 600;

    public Main() {
        start();

        // переход на первую страницу
        JPanel pageStart = new StartPagePanel(this);
        pageStart.setBounds(0, 0, width, height);
        this.changePage(pageStart);
    }

    public void changePage(JPanel panel) {
        this.getContentPane().removeAll();
        panel.setBounds(0, 0, width, height);
        this.add(panel);
        this.pagesHistory.add(panel);
        this.revalidate();
        this.repaint();
    }

    public void previousPage() {
        this.pagesHistory.pop();
        changePage(this.pagesHistory.pop());
    }

    private void start(){
        this.setSize(width, height);
        this.setTitle("Quotes");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
