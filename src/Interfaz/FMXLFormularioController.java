/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import ApexCharactersWeaponsEntities.Armas;
import ApexCharactersWeaponsEntities.Personajes;
import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Antonio Juan Vera
 */
public class FMXLFormularioController implements Initializable {
    private Pane rootDetalleView;
    private Pane rootFMXLInterfaz;
    private TableView tableViewPrevio;
    private Armas armas;
    private EntityManager entityManager;
    private boolean nuevoArma;
    public static final String CARPETA_FOTOS = "Fotos";

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldMunicion;
    @FXML
    private TextField textFieldCargador;
    @FXML
    private TextField textFieldCadencia;
    @FXML
    private ComboBox<Personajes> comboBoxPersonajes;
    @FXML
    private AnchorPane rootFMXLFormulario;
    @FXML
    private TextField textFieldCategoria;
    @FXML
    private CheckBox checkBoxModificable;
    @FXML
    private DatePicker datePickerFechaInclusion;
    @FXML
    private ImageView imageViewFoto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setRootInterfazController (Pane rootFMXLInterfaz) {
        this.rootFMXLInterfaz = rootFMXLInterfaz;
    }
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }
    
    public void setArma(EntityManager entitymanager, Armas armas, boolean nuevoArma) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        if(!nuevoArma) {
           this.armas = entityManager.find(Armas.class, armas.getId());
        } else {
            this.armas = armas;
        }
        this.nuevoArma = nuevoArma;
        entityManager.getTransaction().commit();
    }
    
    public void mostrarDatos() {
        textFieldNombre.setText(armas.getNombre());
        textFieldCategoria.setText(armas.getNombre());
        textFieldMunicion.setText(armas.getNombre());
        textFieldCargador.setText(armas.getNombre());
        textFieldCadencia.setText(armas.getNombre());
        //Falta implementar el código para el resto de controles
        if (armas.getCadencia() != null) {
            textFieldCadencia.setText(armas.getCadencia().toString());
        }
        if (armas.getCargador() != null) {
            textFieldCargador.setText(armas.getCargador().toString());
        }
        if (armas.getModificable() != null) {
            checkBoxModificable.setSelected(armas.getModificable());
        }
        if (armas.getFechainclusion() != null) {
            Date date = armas.getFechainclusion();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFechaInclusion.setValue(localDate);
        }
        
        Query queryPersonajesFindAll = entityManager.createNamedQuery("Personajes.findAll");
        List listPersonajes = queryPersonajesFindAll.getResultList();
        comboBoxPersonajes.setItems(FXCollections.observableList(listPersonajes));
        
        if (armas.getPersonajes() != null) {
            comboBoxPersonajes.setValue(armas.getPersonajes());
        }
        
        comboBoxPersonajes.setCellFactory((ListView<Personajes> l) -> new ListCell<Personajes>() {
            @Override
           protected void updateItem(Personajes personaje, boolean empty) {
                super.updateItem(personaje, empty);
                if (personaje == null || empty) {
                    setText("");
                } else {
                    setText(personaje.getId() + "-" + personaje.getNombre());
                }
            }
            //@Override
            public Personajes fromString(String userId) {
                return null;
            }
        });
        
        if(armas.getFoto() != null) {
            String imageFileName = armas.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootFMXLFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootFMXLFormulario);
        
        armas.setNombre(textFieldNombre.getText());
        armas.setCategoria(textFieldCategoria.getText());
        armas.setMunicion(textFieldMunicion.getText());
        
        if(nuevoArma) {
            entityManager.persist(armas);
        } else{
            entityManager.merge(armas);
        }
        entityManager.getTransaction().commit();
        
        rootFMXLFormulario.setVisible(false);
        rootFMXLInterfaz.setVisible(true);
        
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootFMXLFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootFMXLFormulario);
        
        rootFMXLInterfaz.setVisible(true);
        
        entityManager.getTransaction().rollback();
        
        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
        
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
    }

    
}
