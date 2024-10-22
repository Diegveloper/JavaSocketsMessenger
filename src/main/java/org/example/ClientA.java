package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;

public class ClientA {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private final String HOST = "127.0.0.1";
    private final int HOST_PORT = 5000;

    // constructor to put ip address and port
    public ClientA()
    {
//        // establish a connection
//        try {
//            socket = new Socket(address, port);
//            System.out.println("Connected");
//
//            // takes input from terminal
//            input = new DataInputStream(System.in);
//
//            // sends output to the socket
//            out = new DataOutputStream(socket.getOutputStream());
//        }
//        catch (UnknownHostException u) {
//            System.out.println(u);
//            return;
//        }
//        catch (IOException i) {
//            System.out.println(i);
//            return;
//        }
//
//        // string to read message from input
//        String line = "";
//
//        // keep reading until "Over" is input
//        while (!line.equals("Over")) {
//            try {
//
//                line = input.readLine();
//                out.writeUTF(line);
//            }
//            catch (IOException i) {
//                System.out.println(i);
//            }
//        }
//
//        // close the connection
//        try {
//            input.close();
//            out.close();
//            socket.close();
//        }
//        catch (IOException i) {
//            System.out.println(i);
//        }


    }
    private void consoleLoop(String address, int port){
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String msg = "";
        try{
            System.out.println("Enter your alias");
            String alias = bf.readLine();
            System.out.println("Welcome " + alias);
            socket = new Socket(address, port);
            System.out.println("Connected");
            out = new DataOutputStream(socket.getOutputStream());
            Listener l = new Listener(socket);
            Thread t = new Thread(l);
            t.start();
            while(!msg.equals("quit();")){
                String header = alias + " at " + LocalTime.now().getHour() +":" + LocalTime.now().getMinute()+"> ";
                System.out.print(header);
                msg = bf.readLine();
                String output = header + msg;
                out.writeUTF(output);
            }

        }

        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }

        try {
            input.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    private int getPortFromServer(){
        Socket s;
        DataInputStream in;
        try{
            s = new Socket(HOST, HOST_PORT);
            in = new DataInputStream(s.getInputStream());
            System.out.println("El puerto será" + in.readUTF());
            in.close();
            s.close();
        }
        catch(Exception e){

        }
        return 0;
    }

    public static void main(String args[])
    {
        ClientA clientA = new ClientA();
        clientA.getPortFromServer();
        //clientA.consoleLoop("127.0.0.1", 5000);
    }

    class Listener implements Runnable{
        Socket s;
        Listener(Socket s){
            this.s = s;
        }
        public void run(){

            String line = "";
            while(true) {
                try {
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    line = in.readUTF();
                    System.out.println(line);
                } catch (Exception e) {
                }
            }
        }
    }
}