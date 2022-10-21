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
    private int cantidadOfertada;
    private Usuario ofertador;

    public Oferta(Usuario ofertador, int cantidadOfertada) {
        this.ofertador = ofertador;
        this.cantidadOfertada = cantidadOfertada;
    }

    public int getCantidadOfertada() {
        return cantidadOfertada;
    }

    public Usuario getOfertador() {
        return ofertador;
    }
    
    
    
    
    
    
    
    
    
    
}
