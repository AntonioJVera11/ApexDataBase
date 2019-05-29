/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import ApexCharactersWeaponsEntities.Armas;
import ApexCharactersWeaponsEntities.Personajes;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import static org.eclipse.persistence.expressions.ExpressionOperator.as;

/**
 * FXML Controller class
 *
 * @author Antonio Juan Vera
 */
public class FMXLFormularioController implements Initializable {
    
    private Pane rootFMXLInterfaz;
    private TableView tableViewPrevio;
    private Armas armas;
    private boolean nuevoArma;
    public static final String CARPETA_FOTOS = "Fotos";
    private EntityManager entityManager;

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
    
    public void setArma(EntityManager entityManager, Armas armas, boolean nuevoArma) {
        this.entityManager = entityManager;
        System.out.println(entityManager);
        entityManager.getTransaction().begin();
        if(!nuevoArma) {
           this.armas = entityManager.find(Armas.class, armas.getId());
        } else {
            this.armas = armas;
        }
        this.nuevoArma = nuevoArma;
    }
    
    public void mostrarDatos() {
        textFieldNombre.setText(armas.getNombre());
        textFieldCategoria.setText(armas.getCategoria());
        textFieldMunicion.setText(armas.getMunicion());
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
            public Personajes fromString(String string) {
                return null;
            }
        });
        
        
        comboBoxPersonajes.setConverter(new StringConverter<Personajes>() {
            @Override
            public String toString(Personajes personaje) {
                if (personaje == null) {
                    return null;
                } else {
                    return personaje.getId() + "-" + personaje.getNombre();
                }
            }

            @Override
            public Personajes fromString(String string) {
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
        int numFilaSeleccionada;
        boolean errorFormato = false;
        
//        StackPane rootMain = (StackPane)rootFMXLFormulario.getScene().getRoot();
//        rootMain.getChildren().remove(rootFMXLFormulario);
        
        armas.setNombre(textFieldNombre.getText());
        armas.setCategoria(textFieldCategoria.getText());
        armas.setMunicion(textFieldMunicion.getText());
        
//        if(nuevoArma) {
//            entityManager.persist(armas);
//        } else{
//            entityManager.merge(armas);
//        }
//        entityManager.getTransaction().commit();
//        
//        rootFMXLFormulario.setVisible(false);
//        rootFMXLInterfaz.setVisible(true);
        
        if(!textFieldCargador.getText().isEmpty()) {
            try {
                armas.setCargador(Short.valueOf(textFieldCargador.getText()));
            } catch(NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Capacidad de cargador inválida");
                alert.showAndWait();
                textFieldCargador.requestFocus();
            }
        }
        
        if(!textFieldCadencia.getText().isEmpty()) {
            try {
                armas.setCadencia(BigDecimal.valueOf(Double.valueOf(textFieldCadencia.getText()).doubleValue()));
            } catch(NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Cadencia inválida");
                alert.showAndWait();
                textFieldCadencia.requestFocus();
            }
        }
        
        armas.setModificable(checkBoxModificable.isSelected());
        
        if(datePickerFechaInclusion.getValue() != null) {
            LocalDate localDate = datePickerFechaInclusion.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant =  zonedDateTime.toInstant();
            Date date = Date.from(instant);
            armas.setFechainclusion(date);
        } else {
            armas.setFechainclusion(null);
        }
        
        if(comboBoxPersonajes.getValue() != null) {
            armas.setPersonajes(comboBoxPersonajes.getValue());
        } else {
            Alert alert = new Alert(AlertType.INFORMATION, "Debe indicar un personaje");
            alert.showAndWait();
            errorFormato = true;
        }
        
        if(!errorFormato) {
            try {
                if (nuevoArma) {
                   entityManager.persist(armas);
                } else {
                    entityManager.merge(armas);
                }
                entityManager.getTransaction().commit();
                
                StackPane rootMain = (StackPane) rootFMXLFormulario.getScene().getRoot();
                rootMain.getChildren().remove(rootFMXLFormulario);
                rootFMXLInterfaz.setVisible(true);
                
                if (nuevoArma) {
                    tableViewPrevio.getItems().add(armas);
                    numFilaSeleccionada = tableViewPrevio.getItems().size() -1;
                    tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
                    tableViewPrevio.scrollTo(numFilaSeleccionada);
                } else {
                    numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
                    tableViewPrevio.getItems().set(numFilaSeleccionada, armas);
                }
                TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
                tableViewPrevio.getFocusModel().focus(pos);
                tableViewPrevio.requestFocus();
            } catch (RollbackException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. " + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
        }  
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
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg,png)", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(rootFMXLFormulario.getScene().getWindow());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                armas.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex ) {
                Alert alert = new Alert(AlertType.WARNING, "Nombre del archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActionButtonSuprimirFoto(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresión de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen, \n"
                + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
        alert.setContentText("Elija la opción deseada:");

        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar) {
            String imageFileName = armas.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                file.delete();
            }
            armas.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener) {
            armas.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }
    
    
}
