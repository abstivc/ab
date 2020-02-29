package com.song.leaf.zk;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer3 implements Runnable {

    private int port;
    public SimpleServer3(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("启动服务" + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new SimpleServerHandler3(socket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SimpleServerHandler3 implements Runnable {
    private Socket socket;

    public SimpleServerHandler3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream());
            String body = null;

            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("reveive" +body);
                out.println("Hi" +body);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
