/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author h.el-hayouni
 */
@Entity
@Table(name = "configgateway")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configgateway.findAll", query = "SELECT c FROM Configgateway c"),
    @NamedQuery(name = "Configgateway.findById", query = "SELECT c FROM Configgateway c WHERE c.id = :id"),
    @NamedQuery(name = "Configgateway.findByIp", query = "SELECT c FROM Configgateway c WHERE c.ip = :ip"),
    @NamedQuery(name = "Configgateway.findByKey", query = "SELECT c FROM Configgateway c WHERE c.key = :key"),
    @NamedQuery(name = "Configgateway.findByIsActive", query = "SELECT c FROM Configgateway c WHERE c.isActive = :isActive")})
public class Configgateway implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "key")
    private String key;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private short isActive;
    @OneToMany(mappedBy = "idGateway")
    private List<Filtres> filtresList;
    @OneToMany(mappedBy = "idGateway")
    private List<Alarmesnotifiees> alarmesnotifieesList;
    @OneToMany(mappedBy = "idConfGateway")
    private List<Smscontact> smscontactList;

    public Configgateway() {
    }

    public Configgateway(Integer id) {
        this.id = id;
    }

    public Configgateway(Integer id, String ip, String key, short isActive) {
        this.id = id;
        this.ip = ip;
        this.key = key;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<Filtres> getFiltresList() {
        return filtresList;
    }

    public void setFiltresList(List<Filtres> filtresList) {
        this.filtresList = filtresList;
    }

    @XmlTransient
    public List<Alarmesnotifiees> getAlarmesnotifieesList() {
        return alarmesnotifieesList;
    }

    public void setAlarmesnotifieesList(List<Alarmesnotifiees> alarmesnotifieesList) {
        this.alarmesnotifieesList = alarmesnotifieesList;
    }

    @XmlTransient
    public List<Smscontact> getSmscontactList() {
        return smscontactList;
    }

    public void setSmscontactList(List<Smscontact> smscontactList) {
        this.smscontactList = smscontactList;
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
        if (!(object instanceof Configgateway)) {
            return false;
        }
        Configgateway other = (Configgateway) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Configgateway[ id=" + id + " ]";
    }
    
}
