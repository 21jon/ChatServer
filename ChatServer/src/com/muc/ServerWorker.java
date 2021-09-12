package com.muc;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerWorker extends Thread{
    private static Socket clientSocket;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ( (line = reader.readLine()) != null ){
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            String msg = "You typed: " + line + "\n";
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        }

        clientSocket.close();
    }
}
