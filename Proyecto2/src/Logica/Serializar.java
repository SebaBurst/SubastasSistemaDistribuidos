/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import proyecto2.FXMLFeedController;

/**
 *
 * @author Sebastian
 */
public class Serializar {

    /**
     * Metodo que serializa un objeto especifico , recibe ademas un string con
     * el nombre que se le va a dar al archivo
     *
     * @param e
     * @param nombre
     */
    public static void serializar(Object e, String nombre) {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombre + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);

        } catch (IOException q) {
            q.printStackTrace();
        }
    }

    /**
     * Metodo que se encarga de cargar los archivos serializables y asignarlos a
     * sus respectivos objetos , para ellos se ingresa el tipo de objeto y el
     * nombre del archivo
     *
     * @param e
     * @param nombre
     * @return
     */
    public static Object cargar(Object e, String nombre) {
        try {
            FileInputStream fileIn = new FileInputStream(nombre + ".txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Object) in.readObject();
            return e;
        } catch (IOException r) {
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Error");
            return null;
        }
    }

    
    /**
     * Metodo que escribe el archivo cuando una subasta a seleccionado a un ganador+
     * para tener un registro.
     */
    public static void escribirArchivo() {
        Producto p2 = null;
        ArrayList<Producto> objetos = new ArrayList();
        for (int i = 0; i < FXMLFeedController.productos.size(); i++) {
            Producto p = FXMLFeedController.productos.get(i);
            p2 = (Producto) cargar(p2, p.getNombre());
            objetos.add(p2);
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("database.txt");
            pw = new PrintWriter(fichero);

            for (int i = 0; i < objetos.size(); i++) {
                pw.println("Objeto:  " + objetos.get(i).getNombre() + "\r\n");
                for (int j = 0; j < objetos.get(i).getOfertasRealizadas().size(); j++) {
                    pw.println("Oferta:  " + objetos.get(i).getOfertasRealizadas().get(j).getCantidadOfertada()
                            + "" + " - Realizada por: " + objetos.get(i).getOfertasRealizadas().get(j).getOfertador().getUsername() + "\r\n");
                }
                pw.println("Ganador: "+objetos.get(i).getGanador().getUsername()+ "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
