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

public class ChatWindow extends JFrame{
    private JPanel mainPanel;
    private JTextArea inputArea;
    private JButton sendBtn;
    private JList<Message> msgList;
    private JScrollPane scrollPanel;
    private DefaultListModel<Message> messListModel;

    private volatile Boolean isActive;
    private String currentUser = "A", otherUser = "B";

    public ChatWindow() {

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
        if(inputFirst) {
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

        sendBtn.addActionListener( l -> {
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
        while(isActive) {
            if(waiting[0]) {
                String messToGet = waitMessage(socket);
                addMessage(otherUser,messToGet,false);
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

    String waitMessage(Socket socket) throws IOException{
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

        while(nr > 0) {
            nr = scanner.nextInt();
            if(nr % 2 == 1) receiveMessageMode();
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
        if(what.equals("STOP") ) {
            messListModel.addElement(new Message(
                    "", "Session ended", true
            ));
            receiveMessageMode();
            isActive = false;
        }

        Message mess = new Message(who, what, isMe);
        messListModel.addElement(mess);
    }
}
