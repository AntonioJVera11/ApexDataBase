/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Antonio Juan Vera
 */
public class FMXLFormularioController implements Initializable {
    private Pane rootDetalleView;
    private Pane rootFMXLInterfaz;

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldCategoría;
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

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootFMXLFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootFMXLFormulario);
        
        rootFMXLInterfaz.setVisible(true);
        
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootFMXLFormulario.getScene().getRoot();
        rootMain.getChildren().remove(rootFMXLFormulario);
        
        rootFMXLInterfaz.setVisible(true);
        
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
    }
    
}
