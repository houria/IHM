/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author h.el-hayouni
 */
@Entity
@Table(name = "smscontact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Smscontact.findAll", query = "SELECT s FROM Smscontact s"),
    @NamedQuery(name = "Smscontact.findById", query = "SELECT s FROM Smscontact s WHERE s.id = :id"),
    @NamedQuery(name = "Smscontact.findByNom", query = "SELECT s FROM Smscontact s WHERE s.nom = :nom"),
    @NamedQuery(name = "Smscontact.findByNumero", query = "SELECT s FROM Smscontact s WHERE s.numero = :numero")})
public class Smscontact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "numero")
    private String numero;
    @JoinColumn(name = "id_conf_gateway", referencedColumnName = "id")
    @ManyToOne
    private Configgateway idConfGateway;
    @JoinColumn(name = "id_conf_sms", referencedColumnName = "id_conf_sms")
    @ManyToOne
    private Configsms idConfSms;

    public Smscontact() {
    }

    public Smscontact(Integer id) {
        this.id = id;
    }

    public Smscontact(Integer id, String nom, String numero) {
        this.id = id;
        this.nom = nom;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Configgateway getIdConfGateway() {
        return idConfGateway;
    }

    public void setIdConfGateway(Configgateway idConfGateway) {
        this.idConfGateway = idConfGateway;
    }

    public Configsms getIdConfSms() {
        return idConfSms;
    }

    public void setIdConfSms(Configsms idConfSms) {
        this.idConfSms = idConfSms;
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
        if (!(object instanceof Smscontact)) {
            return false;
        }
        Smscontact other = (Smscontact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Smscontact[ id=" + id + " ]";
    }
    
}
