/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Sebastian
 */
public class Serializar {
     /**
     * Metodo que serializa un objeto especifico , recibe ademas un string con
     * el nombre que se le va a dar al archivo
     * @param e
     * @param nombre 
     */
    public static void serializar(Object e, String nombre){
        try {
         FileOutputStream fileOut = new FileOutputStream(nombre+".txt");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(e);
         
        }catch (IOException q) {
          q.printStackTrace();
        }
    }
    
    /**
     * Metodo que se encarga de cargar los archivos serializables y asignarlos
     * a sus respectivos objetos , para ellos se ingresa el tipo de objeto y el
     * nombre del archivo
     * @param e
     * @param nombre 
     * @return  
     */
    public static Object cargar(Object e , String nombre){
        try {
            FileInputStream fileIn = new FileInputStream(nombre+".txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e =(Object)in.readObject();
            return e;
         }catch (IOException r) {
            return null;
         }catch (ClassNotFoundException c) {
            System.out.println("Error");
            return null;
      }
    }
}
