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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    ColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    ColumnMunicion.setCellValueFactory(new PropertyValueFactory<>("munición"));
    ColumnPersonajes.setCellValueFactory(
        cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            if (cellData.getValue().getPersonajes() != null) {
                property.setValue(cellData.getValue().getPersonajes().getNombre());
            }
            return property;
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
}
