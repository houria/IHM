/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author h.el-hayouni
 */
@ManagedBean
@SessionScoped
public class BeanStatistiques {

    /**
     * Creates a new instance of BeanStatistiques
     */
    public BeanStatistiques() {
    }
    
    public String initialiser(){
        return "statistiques.xhtml";
    }
    
}
