/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApexCharactersWeaponsEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio Juan Vera
 */
@Entity
@Table(name = "ARMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Armas.findAll", query = "SELECT a FROM Armas a")
    , @NamedQuery(name = "Armas.findById", query = "SELECT a FROM Armas a WHERE a.id = :id")
    , @NamedQuery(name = "Armas.findByNombre", query = "SELECT a FROM Armas a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Armas.findByCategoria", query = "SELECT a FROM Armas a WHERE a.categoria = :categoria")
    , @NamedQuery(name = "Armas.findByMunicion", query = "SELECT a FROM Armas a WHERE a.municion = :municion")
    , @NamedQuery(name = "Armas.findByFechainclusion", query = "SELECT a FROM Armas a WHERE a.fechainclusion = :fechainclusion")
    , @NamedQuery(name = "Armas.findByCargador", query = "SELECT a FROM Armas a WHERE a.cargador = :cargador")
    , @NamedQuery(name = "Armas.findByCadencia", query = "SELECT a FROM Armas a WHERE a.cadencia = :cadencia")
    , @NamedQuery(name = "Armas.findByModificable", query = "SELECT a FROM Armas a WHERE a.modificable = :modificable")
    , @NamedQuery(name = "Armas.findByFoto", query = "SELECT a FROM Armas a WHERE a.foto = :foto")})
public class Armas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CATEGORIA")
    private String categoria;
    @Column(name = "MUNICION")
    private String municion;
    @Column(name = "FECHAINCLUSION")
    @Temporal(TemporalType.DATE)
    private Date fechainclusion;
    @Column(name = "CARGADOR")
    private Short cargador;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CADENCIA")
    private BigDecimal cadencia;
    @Column(name = "MODIFICABLE")
    private Boolean modificable;
    @Column(name = "FOTO")
    private String foto;
    @JoinColumn(name = "PERSONAJES", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Personajes personajes;

    public Armas() {
    }

    public Armas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMunicion() {
        return municion;
    }

    public void setMunicion(String municion) {
        this.municion = municion;
    }

    public Date getFechainclusion() {
        return fechainclusion;
    }

    public void setFechainclusion(Date fechainclusion) {
        this.fechainclusion = fechainclusion;
    }

    public Short getCargador() {
        return cargador;
    }

    public void setCargador(Short cargador) {
        this.cargador = cargador;
    }

    public BigDecimal getCadencia() {
        return cadencia;
    }

    public void setCadencia(BigDecimal cadencia) {
        this.cadencia = cadencia;
    }

    public Boolean getModificable() {
        return modificable;
    }

    public void setModificable(Boolean modificable) {
        this.modificable = modificable;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Personajes getPersonajes() {
        return personajes;
    }

    public void setPersonajes(Personajes personajes) {
        this.personajes = personajes;
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
        if (!(object instanceof Armas)) {
            return false;
        }
        Armas other = (Armas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ApexCharactersWeaponsEntities.Armas[ id=" + id + " ]";
    }
    
}
