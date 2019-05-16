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
import javafx.scene.Scene;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FMXLInterfaz.fxml"));
        Parent root = fxmlLoader.load();

        emf = Persistence.createEntityManagerFactory("ApexCharacters_WeaponsPU");
        em = emf.createEntityManager();
        
        FMXLInterfazController FMXLInterfazController = (FMXLInterfazController) fxmlLoader.getController(); 
        FMXLInterfazController.setEntityManager(em);
        FMXLInterfazController.cargarTodasArmas();
        
        Scene scene = new Scene(root, 1280, 720);

        primaryStage.setTitle("Base de Datos Apex Legends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:ApexDB;shutdown=true"); 
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
