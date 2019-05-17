/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import ApexCharactersWeaponsEntities.Armas;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
}
