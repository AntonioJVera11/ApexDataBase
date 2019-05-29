/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import ApexCharactersWeaponsEntities.Armas;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 * FXML Controller class
 *
 * @author Antonio Juan Vera
 */
public class FMXLInterfazController implements Initializable {
    
    private EntityManager entityManager; 
    private Armas armaSeleccionada;
    
    @FXML
    private TableColumn<Armas, String> ColumnPersonajes;
    @FXML
    private TableColumn<Armas, String> ColumnNombre;
    @FXML
    private TableColumn<Armas, String> ColumnCategoria;
    @FXML
    private TableColumn<Armas, String> ColumnMunicion;
    @FXML
    private TableView<Armas> tableViewArmas;
   // private TableColumn<Armas, String> ColumnFechaInclusion;
    @FXML
    private TableColumn<Armas, String> ColumnCargador;
    @FXML
    private TableColumn<Armas, String> ColumnCadencia;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldCategoria;
    @FXML
    private AnchorPane rootFMXLInterfaz;
    private Armas armas;
    private boolean nuevoArma;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    ColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    ColumnMunicion.setCellValueFactory(new PropertyValueFactory<>("municion"));
    //ColumnFechaInclusion.setCellValueFactory(new PropertyValueFactory<>("fechainclusion"));
    ColumnCargador.setCellValueFactory(new PropertyValueFactory<>("cargador"));
    ColumnCadencia.setCellValueFactory(new PropertyValueFactory<>("cadencia"));
    ColumnPersonajes.setCellValueFactory(
        cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            if (cellData.getValue().getPersonajes() != null) {
                property.setValue(cellData.getValue().getPersonajes().getNombre());
            }
            return property;
        }); 
    tableViewArmas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                armaSeleccionada = newValue;
                if (armaSeleccionada != null) {
                    textFieldNombre.setText(armaSeleccionada.getNombre());
                    textFieldCategoria.setText(armaSeleccionada.getCategoria());
                } else {
                    textFieldNombre.setText("");
                    textFieldCategoria.setText("");
                }
            });
    }
    
    public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
    }

    public void cargarTodasArmas() {
    Query queryArmasFindAll = entityManager.createNamedQuery("Armas.findAll");
    List<Armas> listArmas = queryArmasFindAll.getResultList();
    tableViewArmas.setItems(FXCollections.observableArrayList(listArmas));
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if (armaSeleccionada != null) {
            armaSeleccionada.setNombre(textFieldNombre.getText());
            armaSeleccionada.setCategoria(textFieldCategoria.getText());
            entityManager.getTransaction().begin();
            entityManager.merge(armaSeleccionada);
            entityManager.getTransaction().commit();
            
            
            int numFilaSeleccionada = tableViewArmas.getSelectionModel().getSelectedIndex();
            tableViewArmas.getItems().set(numFilaSeleccionada, armaSeleccionada);
            
            
            TablePosition pos = new TablePosition(tableViewArmas, numFilaSeleccionada, null);
            tableViewArmas.getFocusModel().focus(pos);
            tableViewArmas.requestFocus();
        }
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FMXLFormulario.fxml"));
            Parent rootDetalleView = fxmlLoader.load();                

            rootFMXLInterfaz.setVisible(false);

            StackPane rootMain = (StackPane)rootFMXLInterfaz.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

            FMXLFormularioController fmxlFormularioController = (FMXLFormularioController) fxmlLoader.getController();  
            fmxlFormularioController.setRootInterfazController(rootFMXLInterfaz);
            fmxlFormularioController.setTableViewPrevio(tableViewArmas);
            
            armaSeleccionada = new Armas();
            System.out.println(entityManager);
            fmxlFormularioController.setArma(entityManager, armaSeleccionada, true);
            fmxlFormularioController.mostrarDatos();
        } catch (IOException ex) {
            Logger.getLogger(FMXLInterfazController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        if(armaSeleccionada != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FMXLFormulario.fxml"));
                Parent rootDetalleView = fxmlLoader.load();                

                rootFMXLInterfaz.setVisible(false);

                StackPane rootMain = (StackPane)rootFMXLInterfaz.getScene().getRoot();
                rootMain.getChildren().add(rootDetalleView);

                FMXLFormularioController fmxlFormularioController = (FMXLFormularioController) fxmlLoader.getController();  
                fmxlFormularioController.setRootInterfazController(rootFMXLInterfaz);
                fmxlFormularioController.setTableViewPrevio(tableViewArmas);
                fmxlFormularioController.setArma(entityManager, armaSeleccionada, false);
                fmxlFormularioController.mostrarDatos();

            } catch (IOException ex) {
                Logger.getLogger(FMXLInterfazController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }    
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        if (armaSeleccionada != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Seguro que desea suprimir el registro?");
            alert.setContentText(armaSeleccionada.getNombre() + " " + armaSeleccionada.getCategoria());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Aquí añadiremos lo que desee hacer el usuario
                entityManager.getTransaction().begin();
                //entityManager.merge(armaSeleccionada);
                entityManager.remove(armaSeleccionada);
                entityManager.getTransaction().commit();

                tableViewArmas.getItems().remove(armaSeleccionada);

                tableViewArmas.getFocusModel().focus(null);
                tableViewArmas.requestFocus();
            } else {
                //Aquí añadiremos lo que desee que ocurra cuando el usuario cancele
                int numFilaSeleccionada = tableViewArmas.getSelectionModel().getSelectedIndex();
                tableViewArmas.getItems().set(numFilaSeleccionada, armaSeleccionada);
                TablePosition pos = new TablePosition(tableViewArmas, numFilaSeleccionada, null);
                tableViewArmas.getFocusModel().focus(pos);
                tableViewArmas.requestFocus();
            } 
        } else {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Atención");
        alert.setHeaderText("Debe seleccionar un registro para suprimirlo");
        alert.showAndWait();
        }
    
    }
}
