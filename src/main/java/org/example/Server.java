package org.example;

import java.net.ServerSocket;

import java.net.*;
import java.io.*;

public class Server
{
    private final int ASSIGNMENT_PORT = 5000;
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);
                    if(line.contains("hi dawg")){
                        new DataOutputStream(socket.getOutputStream()).writeUTF("que pasó mi dawg rasca cazuelas");
                    }

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        System.out.println("Server has started");
        //Server server = new Server(5001);
        ClientListener l = new ClientListener();
        Thread th = new Thread(l);
        th.start();
    }
}
    class ClientListener implements Runnable{
        ServerSocket ss;
        Socket s;
        int count = 0;
        public void run(){
            try{
                System.out.println("Waiting for a client ...");
                ss = new ServerSocket(5000);
                while(true){

                    s = ss.accept();
                    new DataOutputStream(s.getOutputStream()).writeUTF("Tu puerto es este: " + count++);
                    Thread.sleep(3000);
                    s.close();
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                try{
                    s.close();
                }
                catch(Exception e){}
            }
        }
    }
