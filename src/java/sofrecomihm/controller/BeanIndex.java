/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.controller;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import platinesshapi.SessionFactory;
import platinesshapi.Supervision;
import sofrecomihm.entity.User;

/**
 *
 * @author h.el-hayouni
 */
@ManagedBean
@SessionScoped
public class BeanIndex implements Serializable{

    /**
     * Creates a new instance of BeanIndex
     */
    private static final String HOST="10.0.0.171";
   // private static final String HOST="192.168.1.2";
   // private static final String HOST="192.160.123.85";
    //private static final String HOST="192.168.88.142";
    private static final int PORT=22;
    private User user;
    
    
    
    private JSch jsch;
    private Session session;
    
    public BeanIndex() {
           this.jsch = new JSch();
           this.user = new User();
    }
    
    public String doAuthentifier(){
        
           String result ="error";
           
           this.session = SessionFactory.getSession(HOST, user.getUserName(), user.getPassword(), PORT);
           if(session !=null)
           {
               if (session.isConnected())
               result="dashbord";
           }
           
           
           return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
    
    
}
