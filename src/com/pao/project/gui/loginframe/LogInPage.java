package com.pao.project.gui.loginframe;

import com.pao.project.CcStrings;

import javax.swing.*;

public class LogInPage extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JButton newAccountButton;
    private JButton forgotPassButton;
    private JButton logInButton;
    private JPanel upperPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel loginBtnPanel;
    private JPanel otherBtnsPanel;
    private JPanel descrTxtPanel;
    private JPanel logInTxtPanel;
    private JLabel usernameTxt;
    private JPasswordField passwordField;
    private JLabel passwordTxt;
    private JLabel logInTitle;
    private JLabel farmNameUpperLaber;
    private JLabel catchPhrazeLabel;

    public LogInPage() {
        setTitle("Log in page");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}