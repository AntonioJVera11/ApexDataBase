/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApexCharactersWeapons;

import ApexCharactersWeaponsEntities.Armas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Antonio Juan Vera
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ApexCharactersWeapons");
        EntityManager em = emf.createEntityManager();
        
        Query queryArmas = em.createNamedQuery("Armas.findAll");
        List<Armas> listArmas = queryArmas.getResultList();
        
        for(Armas Armas : listArmas) {
        System.out.println(Armas.getNombre());
        };
        // Modificación de objetos
        Query queryListCarabina = em.createNamedQuery("Equipo.findByNombre");
        queryListCarabina.setParameter("nombre", "Carabina");
        List<Armas> listCarabina = queryListCarabina.getResultList();
        em.getTransaction().begin();
        
        for(Armas ArmasCarabina : listCarabina) {
            ArmasCarabina.setCategoria("Rifle ligero");
            em.merge(ArmasCarabina);
        }
        em.getTransaction().commit();
        
        //Eliminación de objetos
        Armas ArmasFoto = em.find(Armas.class, 2);
        if(ArmasFoto != null) {
            em.remove(ArmasFoto);
        } else {
            System.out.println("No hay ningun equipo con ID=1");
        }
    }
}
    

