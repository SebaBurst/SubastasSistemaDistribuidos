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
    //Arraylist que tendra una lista de los hilos de los clientes que se unan a la aplicacion.
    private static ArrayList<Cliente> clientes = new ArrayList();
   
    public static void main(String[] args) {
        //System.out.println("Hola Mundo");
        //Declaramos las variables para recibir a los usuarios
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(8889);
            while (true) { //Ingresamos en un while true, para recibir n, usuarios
                System.out.println("Esperando a los personajes.....");
                //InputStream o = socket.getInputStream();
                socket = serverSocket.accept(); //Si se envia una peticion de socket, aceptamos al usuario.
                System.out.println("Estoy super duper conectado....");
                Cliente clientThread = new Cliente(socket, clientes); //Creamos un nuevo hilo cliente, y le mandamos el resto de usuarios de la app
                clientes.add(clientThread); //Agregamos al cliente en nuestro arreglo.
                clientThread.start(); // Iniciamos el hilo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
