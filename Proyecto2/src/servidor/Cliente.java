/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import Logica.Oferta;
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
import Logica.Usuario;
import java.util.Collections;
import java.util.Comparator;
import proyecto2.FXMLLoginController;

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
                ArrayList<Oferta> ofertas = pochita.getOfertasRealizadas();
                Collections.sort(ofertas, ofertaMayor);
                Oferta mayor = ofertas.get(ofertas.size()-1);
                pochita.setValorActual(mayor.getCantidadOfertada());

                if (pochita.getOfertasRealizadas().size() == 3) {
                    pochita.setMensaje("Ladys and gentlemans  - " + pochita.getNombre() + ""
                             + " Esta a punto de ser rematado en " + mayor.getCantidadOfertada());
                } else if (pochita.getOfertasRealizadas().size() == 4) {
                    pochita.setMensaje("Apurence ineptos que el producto:   - " + pochita.getNombre() + ""
                            + " Esta a punto de ser rematado en " + mayor.getCantidadOfertada());
                }else if (pochita.getOfertasRealizadas().size() == 5) {
                    pochita.setMensaje("Solo esperaremos 3 ofertas mas antes que el producto:   - " + pochita.getNombre() + ""
                            + " sea rematado en " + mayor.getCantidadOfertada());
                } else if (pochita.getOfertasRealizadas().size() == 6) {
                    pochita.setMensaje("Subanle a la oferta !!! Solo esperaremos 2 ofertas mas antes que el producto:   - " + pochita.getNombre() + ""
                             + " sea rematado en " + mayor.getCantidadOfertada());
                } else if (pochita.getOfertasRealizadas().size() == 7) {
                    pochita.setMensaje("ULTIMO LLAMADO... ADICTOS A LAS APUESTAS !!! La siguiente oferta se lleva el producto:    - " + pochita.getNombre() + ""
                    );
                } else if (pochita.getOfertasRealizadas().size() >= 8) {
                    pochita.setMensaje("Tenemos Ganador!!! ");
                    Oferta ofertaFinal = ofertas.get(ofertas.size() - 1);
                    pochita.setGanador(ofertaFinal.getOfertador());
                }
                //Producto csm = (Producto) objectInputStream.readObject();
                //csm.info();
                for (int i = 0; i < clientes.size(); i++) {
                    clientes.get(i).objectOutputStream.writeObject(pochita);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Metodo que ordena las ofertas por el producto, para poder identificar la oferta mayor.
    public Comparator<Oferta> ofertaMayor = new Comparator<Oferta>() {
        @Override
        public int compare(Oferta t, Oferta t1) {
            int oferta1 = t.getCantidadOfertada();
            int oferta2 = t1.getCantidadOfertada();
            return oferta1 - oferta2;
        }
    };

    @Override
    public void run() {
        readObject();
    }

}
