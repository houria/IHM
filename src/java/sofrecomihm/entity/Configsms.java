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
@Table(name = "configsms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configsms.findAll", query = "SELECT c FROM Configsms c"),
    @NamedQuery(name = "Configsms.findByIdConfSms", query = "SELECT c FROM Configsms c WHERE c.idConfSms = :idConfSms"),
    @NamedQuery(name = "Configsms.findByApiKey", query = "SELECT c FROM Configsms c WHERE c.apiKey = :apiKey"),
    @NamedQuery(name = "Configsms.findByApiSecret", query = "SELECT c FROM Configsms c WHERE c.apiSecret = :apiSecret"),
    @NamedQuery(name = "Configsms.findByUrl", query = "SELECT c FROM Configsms c WHERE c.url = :url"),
    @NamedQuery(name = "Configsms.findByFrom", query = "SELECT c FROM Configsms c WHERE c.from = :from"),
    @NamedQuery(name = "Configsms.findByIsActive", query = "SELECT c FROM Configsms c WHERE c.isActive = :isActive")})
public class Configsms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_conf_sms")
    private Integer idConfSms;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "api_key")
    private String apiKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "api_secret")
    private String apiSecret;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "from")
    private String from;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private short isActive;
    @OneToMany(mappedBy = "idSms")
    private List<Filtres> filtresList;
    @OneToMany(mappedBy = "idSms")
    private List<Alarmesnotifiees> alarmesnotifieesList;
    @OneToMany(mappedBy = "idConfSms")
    private List<Smscontact> smscontactList;

    public Configsms() {
    }

    public Configsms(Integer idConfSms) {
        this.idConfSms = idConfSms;
    }

    public Configsms(Integer idConfSms, String apiKey, String apiSecret, String url, String from, short isActive) {
        this.idConfSms = idConfSms;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.url = url;
        this.from = from;
        this.isActive = isActive;
    }

    public Integer getIdConfSms() {
        return idConfSms;
    }

    public void setIdConfSms(Integer idConfSms) {
        this.idConfSms = idConfSms;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
        hash += (idConfSms != null ? idConfSms.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configsms)) {
            return false;
        }
        Configsms other = (Configsms) object;
        if ((this.idConfSms == null && other.idConfSms != null) || (this.idConfSms != null && !this.idConfSms.equals(other.idConfSms))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Configsms[ idConfSms=" + idConfSms + " ]";
    }
    
}
