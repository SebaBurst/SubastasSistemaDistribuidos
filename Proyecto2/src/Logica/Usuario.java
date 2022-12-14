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
public class Usuario implements Serializable{
    
    private final String nombre;
    private final String username;
    private final String password;
    private int icono = 0;

    
    public Usuario(String nombre, String username, String password) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
    }

    
    public void setIcono(int icono){
        this.icono = icono;
    
    }
    public int getIcono() {
        return icono;
    }
    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username;
    }
  
}
