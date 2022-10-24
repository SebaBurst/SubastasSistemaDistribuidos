/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto2;

import Logica.Producto;
import Logica.Serializar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

    public static ArrayList<Producto> productos = new ArrayList();
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
    @FXML
    private GridPane gridIcon;

    /**
     * Metodo que inicializa la ventana
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameText.setText(loggerUser.getUsername());
        iconBG.setVisible(false);
        profileImg.setOnMouseClicked(e -> {
            if (hideProfile) {
                iconBG.setVisible(true);
                hideProfile = false;
                gridIcon.setVisible(true);
            } else {
                iconBG.setVisible(false);
                gridIcon.setVisible(false);

                hideProfile = true;
            }

        });
        Image imagen = new Image("/assets/icons/icon" + loggerUser.getIcono() + ".png");
        profileImg.setImage(imagen);
        loggerUser.getUsername();
        if (productos.size() == 0) {
            createProducts();
        }

        createGridPane();
        cargarIconos();
    }

    /**
     * Metodo que crea productos en el sistema 
     */
    public void createProducts() {
        //Creamos productos y le seteamos un valor inicial.
        Producto pochita = new Producto("Pochita", 3000);
        Producto p2 = new Producto("Martillo de Thor", 10000);
        Producto p3 = new Producto("Platanos", 1000);
        Producto p4 = new Producto("Chupalla", 6000);
        pochita.setValorActual(3000);
        p2.setValorActual(10000);
        p3.setValorActual(1000);
        p4.setValorActual(6000);

        productos.add(pochita);
        productos.add(p2);
        productos.add(p4);
        productos.add(p3);

    }

    /**
     * Metodo que crea la vista de los productos dentro de un gridpane.
     */
    public void createGridPane() {
        for (int i = 0; i < productos.size(); i++) {
            ImageView imagen = createImage(productos.get(i).getNombre());
            panelProductos.add(imagen, i, 0);
            Button botonAcceso = createButton(i, imagen, productos.get(i).getNombre());
            //Button botonAcceso = new Button();

            botonAcceso.setOnAction(e -> {
                Parent vista = null;
                FXMLDocumentController.pochita = productos.get(Integer.valueOf(botonAcceso.getText()));
                Producto producto = productos.get(Integer.valueOf(botonAcceso.getText()));
                Producto productoInfo = null;

                File archivo = new File(producto.getNombre() + ".txt");
                if (archivo.exists()) {
                    System.out.println("Oe!!! Pcohita existo");

                } else {
                    System.out.println("El Gatooo!!");
                }
                if (!isFileEmpty(archivo)) {
                    productoInfo = (Producto) Serializar.cargar(productoInfo, producto.getNombre());
                    if (productoInfo.getGanador() != null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("El Objeto ya ha sido vendido");
                        alert.showAndWait();
                    } else {
                        try {

                            vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLDocument.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLFeedController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        FXMLLoginController.cambiarVista(e, vista);

                    }
                } else {
                    try {

                        vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLDocument.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLFeedController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    FXMLLoginController.cambiarVista(e, vista);

                }

            });
            panelProductos.add(botonAcceso, i, 0);
        }
        //Primero creamos un botoncitoos un botoncito

    }

    /**
     * Metodo que crea el boton de acceso a la vista del producto que se une a la imagen del gridpane.
     * @param number
     * @param imagen
     * @param nombre
     * @return 
     */
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

    /**
     * Metodo que retorna una imagen creada desde un url.
     * @param nombre
     * @return 
     */
    public ImageView createImage(String nombre) {
        ImageView imagen = null;

        imagen = new ImageView("/assets/productsCards/" + nombre + ".png");
        imagen.setFitWidth(225);// Se asignan las dimensiones de la imagen
        imagen.setFitHeight(325);
        imagen.setFocusTraversable(true);
        imagen.setSmooth(true);

        return imagen;
    }

    /**
     * Metodo que carga los iconos de usuarios en la aplicacion.
     */
    public void cargarIconos() {
        Image imagen3 = null;
        for (int i = 0; i < 6; i++) {
            ImageView imagen = null;// Se inicializa una imageView para usarlo como miniatura
            imagen = new ImageView("/assets/icons/icon" + i + ".png");
            //DropShadow ds = new DropShadow(18, Color.BLACK);
            //imagen.setEffect(ds);
            imagen.setFitWidth(68);// Se asignan las dimensiones de la imagen
            imagen.setFitHeight(68);
            imagen.setFocusTraversable(true);
            imagen.setSmooth(true);
            gridIcon.add(imagen, i, 0);
            Button botonAcceso = new Button();
            botonAcceso.setStyle(PRINCIPAL_BOTON);

            botonAcceso.setMinWidth(68);
            botonAcceso.setMinHeight(68);
            botonAcceso.setMaxWidth(68);
            botonAcceso.setMaxHeight(68);
            botonAcceso.setOnMouseEntered(e -> {

                botonAcceso.setStyle(HOVER_BOTON);

            });
            botonAcceso.setOnMouseExited(e -> {
                botonAcceso.setStyle(PRINCIPAL_BOTON);
            });
            botonAcceso.setText(Integer.toString(i));
            botonAcceso.setOnAction((ActionEvent event) -> {

                FXMLLoginController.loggerUser.setIcono(Integer.parseInt(botonAcceso.getText()));
                cargarImagenIcono(Integer.parseInt(botonAcceso.getText()));
            });
            gridIcon.add(botonAcceso, i, 0);

        }

    }

    /**
     * Metodo que asigna una imagen al boton del icono de usuarios
     * @param i 
     */
    public void cargarImagenIcono(int i) {
        Image imagen = new Image("/assets/icons/icon" + i + ".png");
        profileImg.setImage(imagen);

    }

    /**
     * Metodo que pregunta si un archivo es vacio.
     * @param file
     * @return 
     */
    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    /**
     * Metodos para mover, cerrar y minimizar la aplicacion en la pantalla.
     */
    double x, y;

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
