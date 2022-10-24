/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto2;

import Logica.Producto;
import Logica.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sebastian
 */
public class FXMLLoginController implements Initializable {

    
    //Declaracion de variables
    @FXML
    private TextField nameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox checklogin;
    public static Producto pochita;
    public static ArrayList<Usuario> usuarios = new ArrayList();
    public ArrayList<Usuario> usuariosEnLinea = new ArrayList();
    public static Usuario loggerUser = null;

    
    
    /**
     * Metodo para crear usuarios y agregarlos al sistema
     */
    public void crearUsuarios() {
        Usuario usuario1 = new Usuario("Poio", "Poio", "password");
        Usuario usuario2 = new Usuario("Sergio", "Serjo", "password");
        Usuario usuario4 = new Usuario("Shipus", "ElChupalla", "password");
        Usuario usuario5 = new Usuario("Shipus", "Batman", "password");
        Usuario usuario6 = new Usuario("Shipus", "Roberto", "password");

        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario4);
        usuarios.add(usuario5);
        usuarios.add(usuario6);
    }

    
    /**
     * Metodo que inicializa la vista
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearUsuarios();
        pochita = new Producto("Peluche de Pochita", 0);
    }

    /**
     * Metodo que manda el realiza la autentificaicion del sistema.
     * Verifica que los datos ingresados por el cliente sean compatibles con algun usuario dentro
     * de la lista de usuarios
     * @param event
     * @throws IOException 
     */
    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        //Ciclo que for que compara los parametos ingresados por el usuario para ver si existe dentro del sistema.
        for (Usuario usuario : usuarios) {
            if (nameTextField.getText().equals(usuario.getUsername())) {
                if (passwordTextField.getText().equals(usuario.getPassword())) {
                    usuariosEnLinea.add(usuario);
                    loggerUser = usuario;
                    Parent vista;
                    vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLFeed.fxml"));
                    cambiarVista(event, vista);
                }

            }
        }

    }

    /**
     * Metodo que se encarga de cambiar de una escena a otra dentro de la
     * plataforma.
     *
     * @param e
     * @param vistaNueva
     */
    public static void cambiarVista(ActionEvent e, Parent vistaNueva) {
        Scene nuevaEscena;
        nuevaEscena = new Scene(vistaNueva);
        Stage vistaActual;
        vistaActual = (Stage) ((Node) e.getSource()).getScene().getWindow();
        vistaActual.setScene(nuevaEscena);

    }

    double x, y;//Coordenadas de la aplicacion en la pantalla.

    
    /**
     * A continuacion se presentan los metodos para arrastrar, cerrar y minimizar la aplicacion
     * de forma personalizada
     * @param event 
     */
    @FXML
    private void arrastar(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    private void presionar(MouseEvent event) {

        x = event.getSceneX();
        y = event.getSceneY();

    }

    @FXML
    private void cerrar(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    private void minimizar(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
