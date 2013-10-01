/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofrecomihm.controller;

import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import platinesshapi.Supervision;
import platinesshapi.entity.Alarme;
import platinesshapi.util.ExportCSV;
import platinesshapi.util.ExportPDF;


/**
 *
 * @author h.el-hayouni
 */
@ManagedBean
@SessionScoped
public class BeanAlarmes implements Serializable {

    private static final int ALARMES_PER_PAGE = 10;

    @ManagedProperty(value = "#{beanIndex}")
    private BeanIndex beanIndex;
    private Session session;
    private Supervision supervision;
    private List<Alarme> alarmes;

    private int countAlarmes = 0;

    private int nbrPage = 0;

    private int currentPage = 1;

    public BeanAlarmes() {

        this.alarmes = new ArrayList<Alarme>();
    }

    public void setBeanIndex(BeanIndex beanIndex) {
        this.beanIndex = beanIndex;
    }

    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    public int getNbrPage() {
        return nbrPage;
    }

    public void setNbrPage(int nbrPage) {
        this.nbrPage = nbrPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.alarmes = this.supervision.getAllAlarmesPerPage(this.currentPage, ALARMES_PER_PAGE);      
    }
    
    

    public int getCountAlarmes() {
        return countAlarmes;
    }

    public void setCountAlarmes(int countAlarmes) {
        this.countAlarmes = countAlarmes;
    }
    
     public void doPrecedent() {
        this.setCurrentPage(this.currentPage - 1);
    }

    public void doSuivant() {
        this.setCurrentPage(this.currentPage + 1);

    }

    public String doExportAlarmesPDF() {
        if (this.alarmes != null) {
            System.out.println("export pdf");
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");

            response.setHeader("Content-Disposition", "attachment; filename=Alarmes.pdf");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "No-cache");

            try {
                ExportPDF.getInstance().exportAlarmes("Alarmes.pdf", this.alarmes, response.getOutputStream());
                response.getOutputStream().close();
            } catch (IOException ex) {
                Logger.getLogger(BeanAlarmes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }

    public String doExportAlarmesCSV() {
        if (this.alarmes != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/csv");

            response.setHeader("Content-Disposition", "attachment; filename=Alarmes.csv");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "No-cache");

            try {
                ExportCSV.getInstance().exportAlarmes("Alarmes.csv", this.alarmes, ";", response.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(BeanAlarmes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "error.xhtml";
    }

    @PostConstruct
    public void init() {
        this.session = this.beanIndex.getSession();

        this.supervision = new Supervision(this.session);

        this.alarmes = this.supervision.getAllAlarmes(ALARMES_PER_PAGE);
        this.countAlarmes = this.supervision.getCountAllAlamres();

        this.nbrPage = (int) Math.ceil(this.countAlarmes / ALARMES_PER_PAGE);
        this.alarmes = this.supervision.getAllAlarmesPerPage(this.currentPage, ALARMES_PER_PAGE);
    }

}
