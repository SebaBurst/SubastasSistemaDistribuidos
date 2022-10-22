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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

    //Metodo donde enviamos el objeto
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String of = oferta.getText();

        System.out.println("");
        System.out.println("##################################################");
        System.out.println("Informacion en el boton");
        pochita.info();
        System.out.println("##################################################");
        System.out.println("");

        boolean esNumero = (of != null && of.matches("[0-9]+"));
        if (esNumero) {
            int dinero = Integer.parseInt(of);
            Oferta o = new Oferta(loggerUser, dinero);
            pochita.agregarOferta(o);
            objectOutputStream.writeObject(pochita);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Su Oferta no es un valor numerico");
            alert.showAndWait();

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

    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //label.setText("Bienvenido " + loggerUser.getUsername());
        connectSocket();
        oferta.setVisible(false);
        button.setVisible(false);
        nombreObj.setText(pochita.getNombre());

        Image image1 = new Image("/assets/" + pochita.getNombre() + ".png");

        productImage.setImage(image1);

        File archivo = new File(pochita.getNombre() + ".txt");
        if (archivo.exists()) {
            System.out.println("Oe!!! Pcohita existo");

        }
        if (!isFileEmpty(archivo)) {
            pochita = (Producto) Serializar.cargar(pochita, pochita.getNombre());
        } else {
            System.out.println("No se ha serializado nada");

        }

        //TablaOfertas.getItems().clear();
        //ofertasSubasta.clear();
        usernameTable.setCellValueFactory(new PropertyValueFactory<>("ofertador"));
        ofertaTable.setCellValueFactory(new PropertyValueFactory<>("cantidadOfertada"));
        for (int i = 0; i < pochita.getOfertasRealizadas().size(); i++) {
            ofertasSubasta.add(pochita.getOfertasRealizadas().get(i));
        }
        Collections.sort(ofertasSubasta, ofertaMayor);
        if (ofertasSubasta.size() > 0) {
            Precio.setText(String.valueOf(ofertasSubasta.get(ofertasSubasta.size() - 1).getCantidadOfertada()));
        }
        //Collections.sort(ofertasSubasta, Collections.reverseOrder());

        TablaOfertas.setItems(ofertasSubasta);
        TablaOfertas.setVisible(false);
    }

    //Mandar las lista de las ofertas y no el producto, pero la ofertas tendran identificador para poder filtrarlas.
    //Metodo que lee el string mandadado desde el servidor... :(
    @Override
    public void run() {
        try {
            while (true) {
                pochita.info();
                Producto p = (Producto) objectInputStream.readObject();
                if(p.getNombre().equals(pochita.getNombre())){
                    System.out.println("Estoy en ++++" + pochita.getNombre());
                    pochita = p;
                
                }
                Serializar.serializar(pochita, pochita.getNombre());
                ofertasSubasta.clear();
                for (int i = 0; i < pochita.getOfertasRealizadas().size(); i++) {

                    ofertasSubasta.add(pochita.getOfertasRealizadas().get(i));
                }

                if (!pochita.getMensaje().equals("")) {
                    mensajeServer.setText(pochita.getMensaje());

                }
                Collections.sort(ofertasSubasta, ofertaMayor);
                //Collections.sort(ofertasSubasta, Collections.reverseOrder());
                if (!ofertasSubasta.isEmpty()) {
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

    private void volver(ActionEvent event) throws IOException {
        Parent vista;
        vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLFeed.fxml"));
        cambiarVista(event, vista);

    }

    @FXML
    private void volverAlInicio(ActionEvent event) throws IOException {
        Parent vista;
        vista = (AnchorPane) FXMLLoader.load(getClass().getResource("/proyecto2/FXMLFeed.fxml"));
        cambiarVista(event, vista);
    }

}
