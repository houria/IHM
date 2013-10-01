/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sofrecomihm.entity.Filtres;

/**
 *
 * @author h.el-hayouni
 */
@Stateless
public class FiltresFacade extends AbstractFacade<Filtres> {
    @PersistenceContext(unitName = "PlatineIHMPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FiltresFacade() {
        super(Filtres.class);
    }
    
}
