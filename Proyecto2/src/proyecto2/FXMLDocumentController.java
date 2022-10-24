/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package proyecto2;

import Logica.Oferta;
import Logica.Producto;
import Logica.Serializar;
import Logica.Usuario;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static proyecto2.FXMLLoginController.cambiarVista;
import static proyecto2.FXMLLoginController.loggerUser;

/**
 *
 * @author Sebastian
 */
public class FXMLDocumentController extends Thread implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button button;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    InputStream lector;
    ObjectInputStream objectInputStream;

    public static Producto pochita;

    @FXML
    private TextField oferta;
    @FXML
    private Text Precio;
    @FXML
    private TableColumn<Usuario, String> usernameTable;
    @FXML
    private TableColumn<Oferta, Integer> ofertaTable;
    @FXML
    private TableView<Oferta> TablaOfertas;
    private ObservableList<Oferta> ofertasSubasta = FXCollections.observableArrayList();
    @FXML
    private Button unirse;
    @FXML
    private Text nombreObj;
    @FXML
    private Text mensajeServer;
    @FXML
    private Rectangle juezBG;
    @FXML
    private ImageView juez;
    @FXML
    private Text jueztext;
    @FXML
    private ImageView productImage;
    @FXML
    private Button returnFeed;
    @FXML
    private Text ganador;
    @FXML
    private ImageView profileImage;
    @FXML
    private Text userText;
    @FXML
    private ImageView winGif;
    @FXML
    private Rectangle winBg;
    @FXML
    private Text textWin;
    @FXML
    private Button closeAlert;
    @FXML
    private Rectangle loseBg;
    @FXML
    private ImageView loseGif;

    //Metodo donde enviamos el objeto
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String of = oferta.getText();
        boolean esNumero = (of != null && of.matches("[0-9]+"));
        if (esNumero) {
            int dinero = Integer.parseInt(of);
            if (dinero > pochita.getValorActual()) {
                Oferta o = new Oferta(loggerUser, dinero);
                pochita.agregarOferta(o);
                objectOutputStream.writeObject(pochita);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Su Oferta es menor a la oferta que va ganando");
                alert.showAndWait();

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Su Oferta no es un valor numerico");
            alert.showAndWait();

        }

        if (pochita.getOfertasRealizadas().size() >= 8) {
            button.setVisible(false);
            oferta.setVisible(false);
        }
        if (pochita.getGanador() != null) {
            button.setVisible(false);
            oferta.setVisible(false);

        }
        //writer.println(loggerUser.getUsername() + " ha ofertado "+of + " Por Pochita");
    }

    //metodo que conecta el socket al servidor
    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println("");

            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            lector = socket.getInputStream();
            objectInputStream = new ObjectInputStream(lector);
            //reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metodo que pregunta si un archivo tiene infromacion adentro
    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Ponemos variables graficas invisibles.
        winGif.setVisible(false);
        textWin.setVisible(false);
        winBg.setVisible(false);
        closeAlert.setVisible(false);
        loseBg.setVisible(false);
        loseGif.setVisible(false);

        //Cargamos el icono del usuario.
        Image imagen = new Image("/assets/icons/icon" + loggerUser.getIcono() + ".png");
        //Seteamos el nombre y el icono del usuario
        profileImage.setImage(imagen);
        userText.setText(loggerUser.getUsername());
        //label.setText("Bienvenido " + loggerUser.getUsername());
        //Conectamos el socket
        connectSocket();
        //Ponemos mas variables invisibles.
        oferta.setVisible(false);
        button.setVisible(false);
        nombreObj.setText(pochita.getNombre());

        Image image1 = new Image("/assets/" + pochita.getNombre() + ".png");
        productImage.setImage(image1);
        //Preguntamos si el archivo con la informacion del producto existe
        File archivo = new File(pochita.getNombre() + ".txt");
        if (archivo.exists()) {
            System.out.println("Oe!!! Pcohita existo");

        }
        if (!isFileEmpty(archivo)) {
            //En caso de existir cargamos la informacion serializada
            pochita = (Producto) Serializar.cargar(pochita, pochita.getNombre());
        } else {
            System.out.println("No se ha serializado nada");

        }

        //TablaOfertas.getItems().clear();
        //ofertasSubasta.clear();
        //Declaramos la table y le ingresamos los datos actuales de ofertas del priducto
        usernameTable.setCellValueFactory(new PropertyValueFactory<>("ofertador"));
        ofertaTable.setCellValueFactory(new PropertyValueFactory<>("cantidadOfertada"));
        for (int i = 0; i < pochita.getOfertasRealizadas().size(); i++) {
            ofertasSubasta.add(pochita.getOfertasRealizadas().get(i));
        }
        Collections.sort(ofertasSubasta, ofertaMayor);
        if (ofertasSubasta.size() > 0) {
            Precio.setText(String.valueOf(ofertasSubasta.get(ofertasSubasta.size() - 1).getCantidadOfertada()));
        } else {
            Precio.setText(String.valueOf(pochita.getValorInicial()));

        }
        //Collections.sort(ofertasSubasta, Collections.reverseOrder());

        TablaOfertas.setItems(ofertasSubasta);
        TablaOfertas.setVisible(false);
    }

    /**
     * Metodo que recibe informacion desde el servidor, para el usuario
     */
    @Override
    public void run() {
        try {
            while (true) {
                //pochita.info();
                Producto p = (Producto) objectInputStream.readObject(); // Cargamos el objeto
                if (p.getNombre().equals(pochita.getNombre())) { //Preguntamos si es el obejto que esta viendo el usuario, para no mezclar las ofertas
                    //System.out.println("Estoy en ++++" + pochita.getNombre());
                    pochita = p;

                }

                //Preguntamos si el ganador ha sido decidido
                //Si existe ganador, anulamos la opcion de seguir haciendo ofertas y mostramos al ganador
                if (pochita.getGanador() != null) {
                    button.setVisible(false);
                    oferta.setVisible(false);
                    String winner = pochita.getGanador().getUsername();
                    System.out.println("Winner: " + winner);
                    ganador.setText("Ganador: " + winner);
                    if (FXMLLoginController.loggerUser.getUsername().equals(winner)) {
                        winGif.setVisible(true);
                        textWin.setVisible(true);
                        winBg.setVisible(true);
                        closeAlert.setVisible(true);
                        //loseBg.setVisible(false);
                        //loseGif.setVisible(false)
                    }
                    else{
                        //winGif.setVisible(true);
                        textWin.setVisible(true);
                        textWin.setText("¡Haz Perdido!¡Lo sentimos!");
                        //winBg.setVisible(true);
                        closeAlert.setVisible(true);
                        loseBg.setVisible(true);
                        loseGif.setVisible(true);
                    
                    }
                    //ganador.setText("Ganador: "+(pochita.getGanador().getUsername()));

                }
                //Serializamos el objeto para que otros usuarios que ingresen pueda ver las ofertas actuales
                Serializar.serializar(pochita, pochita.getNombre());
                ofertasSubasta.clear();// Limpiamos la vista de laas ofertas
                
                //Cargamos las ofertas recibidas por el servidor
                for (int i = 0; i < pochita.getOfertasRealizadas().size(); i++) {

                    ofertasSubasta.add(pochita.getOfertasRealizadas().get(i));
                }

                //Preguntamos si el servidor mando un mensaje relacionado con la subasta del objeto
                if (!pochita.getMensaje().equals("")) {
                    mensajeServer.setText(pochita.getMensaje());

                }
                //Ordenamos las ofertas de mayor a menor para saber aquella que va ganado
                Collections.sort(ofertasSubasta, ofertaMayor);
                //Collections.sort(ofertasSubasta, Collections.reverseOrder());
                if (!ofertasSubasta.isEmpty()) {
                    //Seteamos la oferta que va ganando.
                    Oferta mayor = ofertasSubasta.get(ofertasSubasta.size() - 1);
                    Precio.setText(String.valueOf(mayor.getCantidadOfertada()));
                }
                //TablaOfertas.getItems().clear();
                //TablaOfertas.setItems(ofertasSubasta);
                TablaOfertas.refresh();
            }
        } catch (EOFException ex) {
            //All objects are read when control is here
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo que ordena las ofertas por el producto, para poder identificar la oferta mayor.
    public Comparator<Oferta> ofertaMayor = new Comparator<Oferta>() {
        @Override
        public int compare(Oferta t, Oferta t1) {
            int oferta1 = t.getCantidadOfertada();
            int oferta2 = t1.getCantidadOfertada();
            return oferta1 - oferta2;
        }
    };

    /**
     * Metodo que nos permite unirnos a la puja, si nos interesa el producto
     * @param event 
     */
    @FXML
    private void unirsePuja(ActionEvent event) {
        oferta.setVisible(true);
        button.setVisible(true);
        unirse.setVisible(false);
        juez.setVisible(false);
        jueztext.setVisible(false);
        juezBG.setVisible(false);
        TablaOfertas.setVisible(true);
    }

    /**
     * Metodo que retorna al usuario al menu de objetos
     * @param event
     * @throws IOException 
     */
    private void volver(ActionEvent event) throws IOException {
        Parent vista;
        vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLFeed.fxml"));
        cambiarVista(event, vista);

    }

    /**
     * Metodo que retorna al usuario al feed.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void volverAlInicio(ActionEvent event) throws IOException {
        Parent vista;
        vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLFeed.fxml"));
        cambiarVista(event, vista);
    }

    /**
     * Metodos para mover la aplicacion en la pantalla de forma personalizada.
     */
    double x, y; //Coordenadas en pantalla de la aplicacion.

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

    /**
     * Metodo que oculta la ventana de notificacion de ganador o perdedor.
     * @param event 
     */
    @FXML
    private void cerrarGanador(ActionEvent event) {
        winGif.setVisible(false);
        textWin.setVisible(false);
        winBg.setVisible(false);
        closeAlert.setVisible(false);
        loseBg.setVisible(false);
        loseGif.setVisible(false);
    }

}
