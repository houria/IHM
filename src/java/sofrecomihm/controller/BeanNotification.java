/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sofrecomihm.entity.Comptemail;
import sofrecomihm.persistence.ComptemailFacade;

/**
 *
 * @author Houria
 */
@ManagedBean
@SessionScoped
public class BeanNotification {

    private Comptemail compteActuel;

    @EJB
    private ComptemailFacade compteMailFacade;

    public BeanNotification() {
        
    }

    public Comptemail getCompteActuel() {
        return compteActuel;
    }

    public void setCompteActuel(Comptemail compteActuel) {
        this.compteActuel = compteActuel;
    }

    public void changeCompteActuel() {
        if (this.compteActuel.getIdCompte() == 0) {
            this.compteMailFacade.create(this.compteActuel);
        } else {
            this.compteMailFacade.edit(this.compteActuel);
        }
    }

    public String suppprimerCompteActuel() {
        this.compteMailFacade.remove(this.compteActuel);
        this.compteActuel = new Comptemail(0, "", "", (short) 0);
        return "";
    }

    public String initialiser() {
        if (this.compteActuel == null) {
            if (this.compteMailFacade.findAll() != null) {
                if (this.compteMailFacade.findAll().size() > 0) {
                    this.compteActuel = this.compteMailFacade.findRange(new int[]{0, 1}).get(0);
                    if(this.compteActuel == null)
                        this.compteActuel = new Comptemail(0, "", "", (short) 0);
                }
            }
        }
        
        return "notification.xhtml";
    }

    @PostConstruct
    public void init() {

    }

}
