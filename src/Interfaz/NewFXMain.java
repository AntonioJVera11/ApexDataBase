/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Antonio Juan Vera
 */
public class NewFXMain extends Application {
    private EntityManagerFactory emf;
    private EntityManager em;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContactosView.fxml"));
        Parent root = fxmlLoader.load();

        emf = Persistence.createEntityManagerFactory("AgendaContactosPU");
        em = emf.createEntityManager();
        FMXLInterfazController FMXLInterfazController = (FMXLInterfazController) fxmlLoader.getController(); 
    }


    @Override
    public void stop() throws Exception {
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BDAgendaContactos;shutdown=true"); 
        } catch (SQLException ex) { 
        }

    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
