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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    private TableView tableViewPrevio;
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
    ColumnMunicion.setCellValueFactory(new PropertyValueFactory<>("munición"));
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
            (ObservableValue<? extends Armas> observable, Armas oldValue, Armas newValue) -> {
                armaSeleccionada = newValue;
                if (armaSeleccionada != null) {
                    textFieldNombre.setText(armaSeleccionada.getNombre());
                    textFieldCategoria.setText(armaSeleccionada.getCategoria());
                }
            });
    }
    
    public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
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
            
            int numFilaSeleccionada;
            if(nuevoArma) {
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
            fmxlFormularioController.setArmas(entityManager, armaSeleccionada, true);
            fmxlFormularioController.mostrarDatos();
        } catch (IOException ex) {
            //Logger.getLogger(FMXLInterfazController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FMXLFormulario.fxml"));
            Parent rootDetalleView = fxmlLoader.load();                

            rootFMXLInterfaz.setVisible(false);

            StackPane rootMain = (StackPane)rootFMXLInterfaz.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

            FMXLFormularioController fmxlFormularioController = (FMXLFormularioController) fxmlLoader.getController();  
            fmxlFormularioController.setRootInterfazController(rootFMXLInterfaz);
            fmxlFormularioController.setArmas(entityManager, armaSeleccionada, false);
            fmxlFormularioController.mostrarDatos();

        } catch (IOException ex) {
            //Logger.getLogger(FMXLInterfazController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
    }
}
