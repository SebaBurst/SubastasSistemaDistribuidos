/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class Server {
    private static ArrayList<Cliente> clientes = new ArrayList();
   
    public static void main(String[] args) {
        System.out.println("Hola Mundo");
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(8889);
            while (true) {
                System.out.println("Esperando a los personajes.....");
                //InputStream o = socket.getInputStream();
                
                socket = serverSocket.accept();
                System.out.println("Estoy super duper conectado....");
                Cliente clientThread = new Cliente(socket, clientes);
                clientes.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
