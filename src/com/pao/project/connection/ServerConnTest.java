package com.pao.project.connection;

import com.pao.project.connection.talkUI.chatwindow.ChatWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ServerConnTest {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7800);
            serverSocket.setSoTimeout(10000);

            Socket socket = serverSocket.accept();

            Thread clientConn = new Thread(() -> {
                try {
                    ChatWindow serverWindow = new ChatWindow();
                    serverWindow.messageInferface(socket,false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            clientConn.start();
            clientConn.join();


            System.out.println("Connection with client ended.");

        } catch (SocketTimeoutException sock) {
            System.out.println("Connection timed out.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    static private void startTalking(Socket socket) throws IOException {
        Scanner scan = new Scanner(System.in);
        String toClient = "";
        String fromClient = "";

        while (!toClient.equals("STOP")) {

            /// READING FIRST
            InputStream inFromClient = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromClient);

            fromClient = in.readUTF();


            ///WRITING SECOND
            OutputStream outToClient = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToClient);

            System.out.println("Input message: ");
            toClient = scan.nextLine();

            out.writeUTF(toClient);
            System.out.println("Sent! Waiting..");
        }
        System.out.println("Connection ended");
    }
}
