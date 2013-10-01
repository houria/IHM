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
@Table(name = "filtres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filtres.findAll", query = "SELECT f FROM Filtres f"),
    @NamedQuery(name = "Filtres.findById", query = "SELECT f FROM Filtres f WHERE f.id = :id"),
    @NamedQuery(name = "Filtres.findByModule", query = "SELECT f FROM Filtres f WHERE f.module = :module"),
    @NamedQuery(name = "Filtres.findByUrgence", query = "SELECT f FROM Filtres f WHERE f.urgence = :urgence"),
    @NamedQuery(name = "Filtres.findByMaxAlarmes", query = "SELECT f FROM Filtres f WHERE f.maxAlarmes = :maxAlarmes")})
public class Filtres implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "module")
    private String module;
    @Basic(optional = false)
    @NotNull
    @Column(name = "urgence")
    private int urgence;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_alarmes")
    private int maxAlarmes;
    @JoinColumn(name = "id_gateway", referencedColumnName = "id")
    @ManyToOne
    private Configgateway idGateway;
    @JoinColumn(name = "id_sms", referencedColumnName = "id_conf_sms")
    @ManyToOne
    private Configsms idSms;
    @JoinColumn(name = "id_mail", referencedColumnName = "id_compte")
    @ManyToOne
    private Comptemail idMail;

    public Filtres() {
    }

    public Filtres(Integer id) {
        this.id = id;
    }

    public Filtres(Integer id, String module, int urgence, int maxAlarmes) {
        this.id = id;
        this.module = module;
        this.urgence = urgence;
        this.maxAlarmes = maxAlarmes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getUrgence() {
        return urgence;
    }

    public void setUrgence(int urgence) {
        this.urgence = urgence;
    }

    public int getMaxAlarmes() {
        return maxAlarmes;
    }

    public void setMaxAlarmes(int maxAlarmes) {
        this.maxAlarmes = maxAlarmes;
    }

    public Configgateway getIdGateway() {
        return idGateway;
    }

    public void setIdGateway(Configgateway idGateway) {
        this.idGateway = idGateway;
    }

    public Configsms getIdSms() {
        return idSms;
    }

    public void setIdSms(Configsms idSms) {
        this.idSms = idSms;
    }

    public Comptemail getIdMail() {
        return idMail;
    }

    public void setIdMail(Comptemail idMail) {
        this.idMail = idMail;
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
        if (!(object instanceof Filtres)) {
            return false;
        }
        Filtres other = (Filtres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Filtres[ id=" + id + " ]";
    }
    
}
