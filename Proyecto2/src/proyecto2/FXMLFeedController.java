/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto2;

import Logica.Producto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import static proyecto2.FXMLLoginController.cambiarVista;

/**
 * FXML Controller class
 *
 * @author Sebastian
 */
public class FXMLFeedController implements Initializable {

    private ArrayList<Producto> productos = new ArrayList();
    /**
     * Initializes the controller class.
     */
    @FXML
    private GridPane panelProductos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createProducts();
        createGridPane();
    }

    public void createProducts() {
        Producto pochita = new Producto("Peluche de Pochita", 0);
        Producto p2 = new Producto("Carta del Matt", 0);
        Producto p3 = new Producto("Estatua del Levi", 0);
        productos.add(p3);
        productos.add(p2);
        productos.add(pochita);

    }

    public void createGridPane() {
        for (int i = 0; i < productos.size(); i++) {
            Button go = new Button();
            go.setText(String.valueOf(i));
            go.setOnAction(e -> {
                Parent vista = null;
                try {
                    FXMLDocumentController.pochita = productos.get(Integer.valueOf(go.getText()));
                    vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLDocument.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLFeedController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLLoginController.cambiarVista(e, vista);

            });
           panelProductos.add(go, 0, i);
        }
        //Primero creamos un botoncito

    }
}
