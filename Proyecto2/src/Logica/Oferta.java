/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;

/**
 *
 * @author Sebastian
 */
public class Oferta implements Serializable{
    private int cantidadOfertada; //Cantidad ofrecida por el usuario
    private Usuario ofertador; // Se guarda al usuario que dio la oferta.

    
    //Cosntructor
    public Oferta(Usuario ofertador, int cantidadOfertada) {
        this.ofertador = ofertador;
        this.cantidadOfertada = cantidadOfertada;
    }

    //Metodos get y set
    public int getCantidadOfertada() {
        return cantidadOfertada;
    }

    public Usuario getOfertador() {
        return ofertador;
    }
    
    
    
    
    
    
    
    
    
    
}
