package com.company;

import javax.swing.*;
import java.util.Stack;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    Stack<JPanel> pagesHistory = new Stack<>();
    ServerHelper database = new ServerHelper();
    User currentUser = null;

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
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
