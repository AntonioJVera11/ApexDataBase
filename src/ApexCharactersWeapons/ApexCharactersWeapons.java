/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApexCharactersWeapons;

import ApexCharactersWeaponsEntities.Armas;
import ApexCharactersWeaponsEntities.Personajes;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Antonio Juan Vera
 */
public class ApexCharactersWeapons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Conectar con la base de datos
        Map<String, String> emfProperties = new HashMap<String, String>();
        emfProperties.put("javax.persistence.schema-generation.database.action", "create");
        emfProperties.put("javax.persistence.jdbc.url", "jdbc:derby:ApexDB;create=true");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ApexCharacters_WeaponsPU", emfProperties);
        EntityManager em = emf.createEntityManager();

        // REALIZAR AQUÍ LAS OPERACIONES SOBRE LA BASE DE DATOS     
        Personajes wraith = new Personajes();
        wraith.setCategoria("Artillero");
        wraith.setDefinitiva("Portal astral");
        wraith.setPasiva("Esquizofrenia amistosa");
        wraith.setTactica("Fase");  
        wraith.setNombre("Wraith");
        
        
        Personajes octane = new Personajes();
        octane.setCategoria("Artillero");
        octane.setDefinitiva("Jump pad");
        octane.setPasiva("Regeneración");
        octane.setTactica("Chute de adrenalina");
        octane.setNombre("Octane");
        
        
        Personajes lifeline = new Personajes();
        lifeline.setCategoria("Apoyo");
        lifeline.setDefinitiva("Paquete de ayuda");
        lifeline.setPasiva("Curación acelerada");
        lifeline.setTactica("Drón de curación");
        
        
        Personajes bloodhound = new Personajes();
        bloodhound.setCategoria("Rastreador");
        bloodhound.setDefinitiva("Ensicopatamiento");
        bloodhound.setPasiva("Rastreo temporal");
        bloodhound.setTactica("Escaneo holográfico");
        bloodhound.setNombre("Bloodhound");
        
        
        Personajes gibraltar = new Personajes();
        gibraltar.setCategoria("Protector");
        gibraltar.setDefinitiva("Bombardeo defensivo");
        gibraltar.setPasiva("Disparo protegido");
        gibraltar.setTactica("Cúpula protectora");
        gibraltar.setNombre("Gibraltar");
        
        
        Personajes pathfinder = new Personajes();
        pathfinder.setCategoria("Apoyo");
        pathfinder.setDefinitiva("Zipline");
        pathfinder.setPasiva("Rastreo de zona");
        pathfinder.setTactica("Gancho");
        pathfinder.setNombre("Pathfinder");
        
        
        Personajes bangalore = new Personajes();
        bangalore.setCategoria("Artillero");
        bangalore.setDefinitiva("Bombardeo ofensivo");
        bangalore.setPasiva("Velocidad en combate");
        bangalore.setTactica("Humo táctico");
        bangalore.setNombre("Bangalore");
        
        
        Personajes caustic = new Personajes();
        caustic.setCategoria("Protector");
        caustic.setDefinitiva("Bomba de gas");
        caustic.setPasiva("Visión tóxica");
        caustic.setTactica("Trampas de gas");
        caustic.setNombre("Caustic");
        
        
        Personajes mirage = new Personajes();
        mirage.setCategoria("Artillero");
        mirage.setDefinitiva("Invisibilidad");
        mirage.setPasiva("Muerte sigilosa");
        mirage.setTactica("Señuelo");
        mirage.setNombre("Mirage");
        
        /*--------------------------------------------------*/
        
        Armas wingman = new Armas();
        wingman.setCadencia(BigDecimal.valueOf(250));
        wingman.setCargador(Short.decode("4"));
        wingman.setNombre("Wingman");
        wingman.setCategoria("Pistola");
        wingman.setMunicion("Pesada");
        wingman.setPersonajes(mirage);
        
        
        Armas carabina = new Armas();
        carabina.setCadencia(BigDecimal.valueOf(720));
        carabina.setCargador(Short.decode("18"));
        carabina.setNombre("Carabina R-301");
        carabina.setCategoria("Rifle de Asalto");
        carabina.setMunicion("Ligera");
        carabina.setPersonajes(bangalore); 
        
        
        
        em.getTransaction().begin();
        
        em.persist(wraith);
        em.persist(octane);
        em.persist(lifeline);
        em.persist(bloodhound);
        em.persist(gibraltar);
        em.persist(pathfinder);
        em.persist(bangalore);
        em.persist(caustic);
        em.persist(mirage);
        
        em.persist(wingman);
        em.persist(carabina);
        
        
        em.getTransaction().commit();
        // Cerrar la conexión con la base de datos
        em.close();
        emf.close();
        try {
            DriverManager.getConnection("jdbc:derby:ApexDB;shutdown=true");
        } catch (SQLException ex) {
        }
    }
}