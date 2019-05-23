package com.pao.project.connection;

import com.pao.project.connection.talkUI.chatwindow.ChatWindow;

import javax.print.attribute.standard.JobMessageFromOperator;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnTest {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7800);

            Thread serverConn = new Thread( () -> {
            ChatWindow clientWindow = new ChatWindow();
                try {
                    clientWindow.messageInferface(socket, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverConn.start();
            serverConn.join();

            System.out.println("Connection with server ended.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static private void startTalking(Socket socket) throws IOException {

        Scanner scan = new Scanner(System.in);
        String fromServer = "";

        while (!fromServer.equals("STOP")) {

            /// WRITING FIRST
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            System.out.println("Input message: ");

            out.writeUTF(scan.nextLine());
            System.out.println("Sent! Waiting..");

            /// READING SECOND
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            fromServer = in.readUTF();
            System.out.println("SERVER: " + fromServer);
        }
        System.out.println("Connection ended");
    }

}
