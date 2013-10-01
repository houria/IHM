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
@Table(name = "comptemail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comptemail.findAll", query = "SELECT c FROM Comptemail c"),
    @NamedQuery(name = "Comptemail.findByIdCompte", query = "SELECT c FROM Comptemail c WHERE c.idCompte = :idCompte"),
    @NamedQuery(name = "Comptemail.findByMail", query = "SELECT c FROM Comptemail c WHERE c.mail = :mail"),
    @NamedQuery(name = "Comptemail.findByMotDePasse", query = "SELECT c FROM Comptemail c WHERE c.motDePasse = :motDePasse"),
    @NamedQuery(name = "Comptemail.findByIsActive", query = "SELECT c FROM Comptemail c WHERE c.isActive = :isActive")})
public class Comptemail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_compte")
    private Integer idCompte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mail")
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mot_de_passe")
    private String motDePasse;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private short isActive;
    @OneToMany(mappedBy = "idMail")
    private List<Filtres> filtresList;
    @OneToMany(mappedBy = "idMail")
    private List<Alarmesnotifiees> alarmesnotifieesList;
    @OneToMany(mappedBy = "idCompte")
    private List<Listemail> listemailList;

    public Comptemail() {
    }

    public Comptemail(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public Comptemail(Integer idCompte, String mail, String motDePasse, short isActive) {
        this.idCompte = idCompte;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.isActive = isActive;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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
    public List<Listemail> getListemailList() {
        return listemailList;
    }

    public void setListemailList(List<Listemail> listemailList) {
        this.listemailList = listemailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompte != null ? idCompte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comptemail)) {
            return false;
        }
        Comptemail other = (Comptemail) object;
        if ((this.idCompte == null && other.idCompte != null) || (this.idCompte != null && !this.idCompte.equals(other.idCompte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Comptemail[ idCompte=" + idCompte + " ]";
    }
    
}
