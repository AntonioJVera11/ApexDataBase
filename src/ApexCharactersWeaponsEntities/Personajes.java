/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApexCharactersWeaponsEntities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio Juan Vera
 */
@Entity
@Table(name = "PERSONAJES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personajes.findAll", query = "SELECT p FROM Personajes p")
    , @NamedQuery(name = "Personajes.findById", query = "SELECT p FROM Personajes p WHERE p.id = :id")
    , @NamedQuery(name = "Personajes.findByPasiva", query = "SELECT p FROM Personajes p WHERE p.pasiva = :pasiva")
    , @NamedQuery(name = "Personajes.findByTactica", query = "SELECT p FROM Personajes p WHERE p.tactica = :tactica")
    , @NamedQuery(name = "Personajes.findByDefinitiva", query = "SELECT p FROM Personajes p WHERE p.definitiva = :definitiva")
    , @NamedQuery(name = "Personajes.findByCategoria", query = "SELECT p FROM Personajes p WHERE p.categoria = :categoria")})
public class Personajes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PASIVA")
    private String pasiva;
    @Column(name = "TACTICA")
    private String tactica;
    @Column(name = "DEFINITIVA")
    private String definitiva;
    @Column(name = "CATEGORIA")
    private String categoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personajes")
    private Collection<Armas> armasCollection;

    public Personajes() {
    }

    public Personajes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasiva() {
        return pasiva;
    }

    public void setPasiva(String pasiva) {
        this.pasiva = pasiva;
    }

    public String getTactica() {
        return tactica;
    }

    public void setTactica(String tactica) {
        this.tactica = tactica;
    }

    public String getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(String definitiva) {
        this.definitiva = definitiva;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public Collection<Armas> getArmasCollection() {
        return armasCollection;
    }

    public void setArmasCollection(Collection<Armas> armasCollection) {
        this.armasCollection = armasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personajes)) {
            return false;
        }
        Personajes other = (Personajes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ApexCharactersWeaponsEntities.Personajes[ id=" + id + " ]";
    }
    
}
