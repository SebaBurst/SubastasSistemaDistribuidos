/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class Producto implements Serializable{
    private String nombre;
    private int valorInicial;
    private boolean vendido = false;
    private int valorActual;
    private ArrayList<Oferta>ofertasRealizadas = new ArrayList();
    
    
    public Producto(String nombre, int valorInicial) {
        this.nombre = nombre;
        this.valorInicial = valorInicial;
    }

    
    public void agregarOferta(Oferta oferta){
        ofertasRealizadas.add(oferta);
    }

    public ArrayList<Oferta> getOfertasRealizadas() {
        return ofertasRealizadas;
    }
    
    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public int getValorActual() {
        return valorActual;
    }

    public void setValorActual(int valorActual) {
        this.valorActual = valorActual;
    }
    
    
    public void info(){
        System.out.println("Informacion del producto");
        System.out.println("Nombre: "+nombre);
        System.out.println("Ofertas: ");
        for (int i = 0; i < ofertasRealizadas.size(); i++) {
            System.out.println("Oferta Numero "+i);
            Oferta o = ofertasRealizadas.get(i);
            System.out.println("Nombre: "+o.getOfertador().getUsername());
            System.out.println("Oferta Realizada: "+o.getCantidadOfertada());
        }
    
    
    }
    
    
    
    
}
