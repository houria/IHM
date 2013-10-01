/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sofrecomihm.entity.Listemail;

/**
 *
 * @author h.el-hayouni
 */
@Stateless
public class ListemailFacade extends AbstractFacade<Listemail> {
    @PersistenceContext(unitName = "PlatineIHMPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListemailFacade() {
        super(Listemail.class);
    }
    
}
