/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import ApexCharactersWeaponsEntities.Armas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;

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

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldMunicion;
    @FXML
    private DatePicker dataPickerFechaInclusion;
    @FXML
    private TextField textFieldCargador;
    @FXML
    private TextField textFieldCadencia;
    @FXML
    private ComboBox<?> comboBoxPersonajes;
    @FXML
    private RadioButton radioButtonSi;
    @FXML
    private RadioButton radioButtonNo;
    @FXML
    private AnchorPane rootFMXLFormulario;
    @FXML
    private TextField textFieldCategoria;

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
    
    public void mostrarDatos() {
        textFieldNombre.setText(armas.getNombre());
        textFieldCategoria.setText(armas.getNombre());
        textFieldMunicion.setText(armas.getNombre());
        textFieldCargador.setText(armas.getNombre());
        textFieldCadencia.setText(armas.getNombre());
        //Falta implementar el código para el resto de controles
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

    void setTableViewPrevio(TableView<Armas> tableViewArmas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setArmas(EntityManager entityManager, Armas armaSeleccionada, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
