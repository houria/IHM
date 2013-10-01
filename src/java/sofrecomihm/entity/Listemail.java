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
@Table(name = "listemail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listemail.findAll", query = "SELECT l FROM Listemail l"),
    @NamedQuery(name = "Listemail.findByIdMail", query = "SELECT l FROM Listemail l WHERE l.idMail = :idMail"),
    @NamedQuery(name = "Listemail.findByMail", query = "SELECT l FROM Listemail l WHERE l.mail = :mail")})
public class Listemail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mail")
    private Integer idMail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mail")
    private String mail;
    @JoinColumn(name = "id_compte", referencedColumnName = "id_compte")
    @ManyToOne
    private Comptemail idCompte;

    public Listemail() {
    }

    public Listemail(Integer idMail) {
        this.idMail = idMail;
    }

    public Listemail(Integer idMail, String mail) {
        this.idMail = idMail;
        this.mail = mail;
    }

    public Integer getIdMail() {
        return idMail;
    }

    public void setIdMail(Integer idMail) {
        this.idMail = idMail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Comptemail getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Comptemail idCompte) {
        this.idCompte = idCompte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMail != null ? idMail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listemail)) {
            return false;
        }
        Listemail other = (Listemail) object;
        if ((this.idMail == null && other.idMail != null) || (this.idMail != null && !this.idMail.equals(other.idMail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sofrecomihm.entity.Listemail[ idMail=" + idMail + " ]";
    }
    
}
