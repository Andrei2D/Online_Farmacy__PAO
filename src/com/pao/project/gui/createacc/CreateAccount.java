package com.pao.project.gui.createacc;

import javax.swing.*;

public class CreateAccount extends JFrame{
    private JPanel mainPanel;
    private JPanel topWritings;
    private JLabel accCreateTop;
    private JLabel descrTopLabel;
    private JPanel usernameArea;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel usernameStatus;
    private JPanel passFirstPanel;
    private JLabel passFirstWarning;
    private JPasswordField passFirstField;
    private JPanel passSecondPanel;
    private JPasswordField passSecondField;
    private JPanel emailPanel;
    private JTextField emailField;
    private JPanel phonePanel;
    private JTextField phoneInputField;
    private JPanel btnPanel;
    private JButton submitButton;
    private JLabel passSecondWarning;
    private JButton backButton;

    public CreateAccount() {
        setTitle("Create account");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
