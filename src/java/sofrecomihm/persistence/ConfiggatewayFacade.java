/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sofrecomihm.entity.Configgateway;

/**
 *
 * @author h.el-hayouni
 */
@Stateless
public class ConfiggatewayFacade extends AbstractFacade<Configgateway> {
    @PersistenceContext(unitName = "PlatineIHMPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiggatewayFacade() {
        super(Configgateway.class);
    }
    
}
