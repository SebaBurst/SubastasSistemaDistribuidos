/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ClassNotFoundException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import Logica.Producto;

/**
 *
 * @author Sebastian
 */
public class Cliente extends Thread {

    private ArrayList<Cliente> clientes;
    private Socket socket;
    private ObjectInputStream objectInput;
    private InputStream lector;
    private OutputStream outputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    //private BufferedReader reader;

    //Constructor
    public Cliente(Socket socket, ArrayList<Cliente> clientes) {
        try {
            this.socket = socket;
            this.clientes = clientes;
            this.lector = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            this.objectInputStream = new ObjectInputStream(lector);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //this.writer = new PrintWriter(socket.getOutputStream(), true);

    }

    public void readObject() {
        try {
            Producto pochita;
            while ((pochita = (Producto) objectInputStream.readObject()) != null) {
                    //Producto csm = (Producto) objectInputStream.readObject();
                    //csm.info();
                    for (int i = 0; i < clientes.size(); i++) {
                        clientes.get(i).objectOutputStream.writeObject(pochita);
                    }
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        readObject();
    }

}
