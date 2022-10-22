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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import static proyecto2.FXMLLoginController.cambiarVista;
import static proyecto2.FXMLLoginController.loggerUser;

/**
 * FXML Controller class
 *
 * @author Sebastian
 */
public class FXMLFeedController implements Initializable {

    private static final String PRINCIPAL_BOTON = ""
            + "-fx-background-color: transparent;"
            + "-fx-text-fill: transparent;"; //Estilo CSS para volver los botones del Gridpane transparentes.

    //Estilos css para los botones Hover 
    private static final String HOVER_BOTON = ""
            + "-fx-background-color: black;\n"
            + "    -fx-opacity: 0.3;\n"
            + "   -fx-scale-y: 1.1;"
            + "   -fx-scale-x: 1.1;"
            + " -fx-border-color: transparent;"
            + " -fx-border: 0px;"
            + "-fx-font-weight: bold; "
            + "-fx-text-fill:white;"
            + "-fx-background-radius:0px;";

    private ArrayList<Producto> productos = new ArrayList();
    /**
     * Initializes the controller class.
     */
    @FXML
    private GridPane panelProductos;
    @FXML
    private Text usernameText;
    @FXML
    private Rectangle iconBG;
    @FXML
    private ImageView profileImg;
    private boolean hideProfile = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameText.setText(loggerUser.getUsername());
        iconBG.setVisible(false);
        profileImg.setOnMouseClicked(e -> {
            if (hideProfile) {
                iconBG.setVisible(true);
                hideProfile = false;
            } else {
                iconBG.setVisible(false);
                hideProfile = true;
            }

        });

        loggerUser.getUsername();
        createProducts();
        createGridPane();
    }

    public void createProducts() {
        Producto pochita = new Producto("Pochita", 0);
        Producto p2 = new Producto("Martillo de Thor", 0);
        Producto p3 = new Producto("Platanos", 0);
        Producto p4 = new Producto("Chupalla", 0);
        productos.add(pochita);
        productos.add(p2);
        productos.add(p4);
        productos.add(p3);

    }

    public void createGridPane() {
        for (int i = 0; i < productos.size(); i++) {
            ImageView imagen = createImage(productos.get(i).getNombre());
            panelProductos.add(imagen, i, 0);
            Button botonAcceso = createButton(i, imagen, productos.get(i).getNombre());
            //Button botonAcceso = new Button();

            botonAcceso.setOnAction(e -> {
                Parent vista = null;
                FXMLDocumentController.pochita = productos.get(Integer.valueOf(botonAcceso.getText()));
                try {
                   
                    vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLDocument.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLFeedController.class.getName()).log(Level.SEVERE, null, ex);
                }

                FXMLLoginController.cambiarVista(e, vista);

            });
            panelProductos.add(botonAcceso, i, 0);
        }
        //Primero creamos un botoncitoos un botoncito

    }

    public Button createButton(int number, ImageView imagen, String nombre) {
        Button go = new Button();
        go.setText(String.valueOf(number));
        go.setMinWidth(225);
        go.setMinHeight(325);
        go.setMaxWidth(225);
        go.setMaxHeight(325);
        go.setStyle(PRINCIPAL_BOTON);
        Image image1 = new Image("/assets/productsCards/" + nombre + ".png");
        Image image = imagen.getImage();
        go.setOnMouseEntered(e -> {
            imagen.setImage(image1);
            imagen.setStyle("-fx-scale-y: 1.1;"
                    + "   -fx-scale-x: 1.1;");
            imagen.toFront();
            go.setStyle(HOVER_BOTON);
            go.toFront();

        });
        go.setOnMouseExited(e -> {
            imagen.setImage(image);
            imagen.setFitHeight(325);
            imagen.setFitWidth(225);
            imagen.setStyle("-fx-scale-y: 1;"
                    + "   -fx-scale-x: 1;");
            go.setStyle(PRINCIPAL_BOTON);
        });

        return go;

    }

    public ImageView createImage(String nombre) {
        ImageView imagen = null;

        imagen = new ImageView("/assets/productsCards/" + nombre + ".png");
        imagen.setFitWidth(225);// Se asignan las dimensiones de la imagen
        imagen.setFitHeight(325);
        imagen.setFocusTraversable(true);
        imagen.setSmooth(true);

        return imagen;
    }

}
