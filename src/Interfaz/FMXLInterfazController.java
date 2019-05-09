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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Antonio Juan Vera
 */
public class FMXLInterfazController implements Initializable {

    @FXML
    private TableColumn<Armas, Integer> ColumnPersonajes;
    @FXML
    private TableColumn<Armas, String> ColumnNombre;
    @FXML
    private TableColumn<Armas, String> ColumnCategoria;
    @FXML
    private TableColumn<Armas, String> ColumnMunicion;
    @FXML
    private TableView<?> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    ColumnPersonajes.setCellValueFactory(new PropertyValueFactory<>("personajes"));
    ColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    ColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    ColumnMunicion.setCellValueFactory(new PropertyValueFactory<>("munición"));
    }

    public void cargarTodasArmas() {
    Query queryArmasFindAll = entityManager.createNamedQuery("Armas.findAll");
    List<Armas> listArmas = queryArmasFindAll.getResultList();
    tableViewContactos.setItems(FXCollections.observableArrayList(listArmas));
    } 
    
}
