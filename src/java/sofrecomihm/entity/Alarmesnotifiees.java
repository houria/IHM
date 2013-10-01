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
@Table(name = "alarmesnotifiees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alarmesnotifiees.findAll", query = "SELECT a FROM Alarmesnotifiees a"),
    @NamedQuery(name = "Alarmesnotifiees.findById", query = "SELECT a FROM Alarmesnotifiees a WHERE a.id = :id"),
    @NamedQuery(name = "Alarmesnotifiees.findByIdDistant", query = "SELECT a FROM Alarmesnotifiees a WHERE a.idDistant = :idDistant"),
    @NamedQuery(name = "Alarmesnotifiees.findByDateNotification", query = "SELECT a FROM Alarmesnotifiees a WHERE a.dateNotification = :dateNotification"),
    @NamedQuery(name = "Alarmesnotifiees.findByUrgence", query = "SELECT a FROM Alarmesnotifiees a WHERE a.urgence = :urgence"),
    @NamedQuery(name = "Alarmesnotifiees.findByModule", query = "SELECT a FROM Alarmesnotifiees a WHERE a.module = :module"),
    @NamedQuery(name = "Alarmesnotifiees.findByDateDeclanche", query = "SELECT a FROM Alarmesnotifiees a WHERE a.dateDeclanche = :dateDeclanche")})
public class Alarmesnotifiees implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_distant")
    private int idDistant;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "date_notification")
    private String dateNotification;
    @Basic(optional = false)
    @NotNull
    @Column(name = "urgence")
    private int urgence;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "module")
    private String module;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_declanche")
    private int dateDeclanche;
    @JoinColumn(name = "id_gateway", referencedColumnName = "id")
    @ManyToOne
    private Configgateway idGateway;
    @JoinColumn(name = "id_sms", referencedColumnName = "id_conf_sms")
    @ManyToOne
    private Configsms idSms;
    @JoinColumn(name = "id_mail", referencedColumnName = "id_compte")
    @ManyToOne
    private Comptemail idMail;

    public Alarmesnotifiees() {
    }

    public Alarmesnotifiees(Integer id) {
        this.id = id;
    }

    public Alarmesnotifiees(Integer id, int idDistant, String dateNotification, int urgence, String module, int dateDeclanche) {
        this.id = id;
        this.idDistant = idDistant;
        this.dateNotification = dateNotification;
        this.urgence = urgence;
        this.module = module;
        this.dateDeclanche = dateDeclanche;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdDistant() {
        return idDistant;
    }

    public void setIdDistant(int idDistant) {
        this.idDistant = idDistant;
    }

    public String getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(String dateNotification) {
        this.dateNotification = dateNotification;
    }

    public int getUrgence() {
        return urgence;
    }

    public void setUrgence(int urgence) {
        this.urgence = urgence;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getDateDeclanche() {
        return dateDeclanche;
    }

    public void setDateDeclanche(int dateDeclanche) {
        this.dateDeclanche = dateDeclanche;
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
        if (!(object instanceof Alarmesnotifiees)) {
            return false;
        }
        Alarmesnotifiees other = (Alarmesnotifiees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Alarmesnotifiees[ id=" + id + " ]";
    }
    
}
