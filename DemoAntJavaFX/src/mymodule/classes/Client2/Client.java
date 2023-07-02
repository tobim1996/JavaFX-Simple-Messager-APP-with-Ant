/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.layout.VBox;
import theapp.SceneController;

/**
 *
 * @author tobim1996
 */
public class Client {
    
         private Socket socket;
          private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    
    public Client(Socket socket) {

        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {

            System.out.println("IO FEHLER");
            e.printStackTrace();
            closeEverything(socket, bufferedReader,bufferedWriter);
        }

    }
    
    public void sendMessageToServer(String messageToClient) {

        try {
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the client");
            closeEverything(socket, bufferedReader, bufferedWriter);

        }
    }
    
    public void receiveMessageFromServer(VBox vBox) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (socket.isConnected()) {

                    try {
                        String messageFromClient = bufferedReader.readLine();
                        SceneController.addLabel(messageFromClient, vBox);

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }

                }
            }

        }).start();

    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    
    
}
