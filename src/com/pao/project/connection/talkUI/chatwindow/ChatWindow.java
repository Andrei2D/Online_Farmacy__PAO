package com.pao.project.connection.talkUI.chatwindow;

import com.pao.project.connection.talkUI.Message;
import com.pao.project.connection.talkUI.panel.MessPanel;
import com.sun.java.accessibility.util.SwingEventMonitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ChatWindow extends JFrame {
    private JPanel mainPanel;
    private JTextArea inputArea;
    private JButton sendBtn;
    private JList<Message> msgList;
    private JScrollPane scrollPanel;
    private DefaultListModel<Message> messListModel;

    private volatile Boolean isActive;
    private String currentUser = "A", otherUser = "B";

    public ChatWindow() {

        $$$setupUI$$$();
        setTitle("Message window");
        setContentPane(mainPanel);
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messListModel = new DefaultListModel<>();

        msgList.setModel(messListModel);
        msgList.setCellRenderer(new MessPanel());


//        String name = JOptionPane.showInputDialog("Input your name:");
//        System.out.println(name);

        System.out.println("UI thread: " + Thread.currentThread().getName());
    }

    //TODO WATCH THIS
    public static void main(String[] args) {

        ChatWindow window = new ChatWindow();
        window.something();
        System.out.println("This thread: " + Thread.currentThread().getName());

    }


    public void messageInferface(Socket socket, Boolean inputFirst) throws IOException {

        isActive = true;

        /// USERNAME PART
        if (inputFirst) {
            setTitle("Client chat");

            currentUser = JOptionPane.showInputDialog("Client username:");
            sendMessage(socket, currentUser);
            receiveMessageMode();
            otherUser = waitMessage(socket);
            sendMessageMode();
        } else {
            setTitle("Server chat");

            receiveMessageMode();
            otherUser = waitMessage(socket);
            currentUser = JOptionPane.showInputDialog("Server username:");
            sendMessage(socket, currentUser);
        }

        /// SEND BTN SETUP
        final Boolean[] waiting = new Boolean[1];
        waiting[0] = !inputFirst;

        sendBtn.addActionListener(l -> {
            String message = inputArea.getText();
            receiveMessageMode();
            addMessage(currentUser, message, true);
            waiting[0] = true;

            try {
                sendMessage(socket, message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /// CLOSE EVENT
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    sendMessage(socket, "STOP");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                super.windowClosing(e);
            }
        });

        /// MESSAGE WAIT LOOP
        while (isActive) {
            if (waiting[0]) {
                String messToGet = waitMessage(socket);
                addMessage(otherUser, messToGet, false);
                sendMessageMode();
                waiting[0] = false;
            }


        }
    }

    void sendMessage(Socket socket, String message) throws IOException {
        DataOutputStream outToOther = new DataOutputStream(
                socket.getOutputStream());
        outToOther.writeUTF(message);
    }

    String waitMessage(Socket socket) throws IOException {
        String toReturn;
        DataInputStream inFromUser = new DataInputStream(
                socket.getInputStream()
        );
        toReturn = inFromUser.readUTF();

        return toReturn;
    }

    void something() {
        Scanner scanner = new Scanner(System.in);
        int nr = 1;

        while (nr > 0) {
            nr = scanner.nextInt();
            if (nr % 2 == 1) receiveMessageMode();
            else sendMessageMode();
        }
        scanner.close();
        System.out.println("Done");
    }

    void receiveMessageMode() {
        inputArea.setText("Waiting for a message..");
        inputArea.setEnabled(false);
        sendBtn.setVisible(false);
    }

    void sendMessageMode() {
        inputArea.setText("");
        inputArea.setEnabled(true);
        sendBtn.setVisible(true);
    }


    private void createUIComponents() {
        msgList = new JList<>();
        scrollPanel = new JScrollPane(msgList);
    }

    void addMessage(String who, String what, Boolean isMe) {
        if (what.equals("STOP")) {
            messListModel.addElement(new Message(
                    "", "Session ended", true
            ));
            receiveMessageMode();
            isActive = false;
            JOptionPane.showMessageDialog(this,
                    "Session ended by " + who);
            dispose();
        }

        Message mess = new Message(who, what, isMe);
        messListModel.addElement(mess);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(5, 5, 5, 5), -1, -1));
        mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        inputArea = new JTextArea();
        inputArea.setEnabled(true);
        panel1.add(inputArea, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        sendBtn = new JButton();
        sendBtn.setText("Send");
        panel1.add(sendBtn, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mainPanel.add(scrollPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
