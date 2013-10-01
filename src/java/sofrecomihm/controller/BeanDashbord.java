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
import platinesshapi.entity.BilanCollecte;
import platinesshapi.entity.BilanDistribution;
import platinesshapi.entity.BilanInterface;
import platinesshapi.entity.BilanRequete;
import platinesshapi.entity.BilanSuppervision;
import platinesshapi.entity.BilanTraitement;
import platinesshapi.entity.EEC;
import platinesshapi.entity.AlarmesActives;
import platinesshapi.entity.ArticleDistribution;
import platinesshapi.util.CurrentDate;
import platinesshapi.util.ExportCSV;
import platinesshapi.util.ExportPDF;

/**
 *
 * @author h.el-hayouni
 */
@ManagedBean
@SessionScoped
public class BeanDashbord implements Serializable {

    @ManagedProperty(value = "#{beanIndex}")
    private BeanIndex beanIndex;

    private Session session;
    private Supervision supervision;
    private BilanSuppervision bilanSuppervision;
    private AlarmesActives alarmesActives;
    private List<Alarme> alarmes;
    private List<Alarme> alarmesTotal;
    private static final int ALARMES_PER_PAGE = 20;

    private int countAlarmes = 0;
    private int countAllAlarmes = 0;

    private int nbrPage = 0;

    private int currentPage = 1;

    public BeanDashbord() {
        this.alarmes = new ArrayList<Alarme>();
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public void setSupervision(Supervision supervision) {
        this.supervision = supervision;
    }

    @PostConstruct
    public void doGetBilanSuppervision() {

        this.session = this.beanIndex.getSession();
        this.supervision = new Supervision(this.session);

        this.bilanSuppervision = new BilanSuppervision();
        this.alarmesActives = new AlarmesActives();
        this.alarmesActives = this.supervision.sup_alarme();

        this.alarmes = this.supervision.getAlarmesByModule("platine", 20);
        this.countAlarmes = this.supervision.getCountAlamresByModule("platine");

        this.nbrPage = (int) Math.ceil(this.countAlarmes / ALARMES_PER_PAGE);

        this.alarmes = this.supervision.getAlarmesPerPage("platine", this.currentPage, ALARMES_PER_PAGE);

        this.bilanSuppervision = this.supervision.sup_platine(CurrentDate.getCurrentDate());
    }

    public void setBeanIndex(BeanIndex beanIndex) {
        this.beanIndex = beanIndex;
    }

    public BilanSuppervision getBilanSuppervision() {
        return bilanSuppervision;
    }

    public void setBilanSuppervision(BilanSuppervision bilanSuppervision) {
        this.bilanSuppervision = bilanSuppervision;
    }

  

    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    public AlarmesActives getAlarmesActives() {
        return alarmesActives;
    }

    public void setAlarmesActives(AlarmesActives alarmesActives) {
        this.alarmesActives = alarmesActives;
    }

    public int getCountAlarmes() {
        return countAlarmes;
    }

    public void setCountAlarmes(int countAlarmes) {
        this.countAlarmes = countAlarmes;
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
        this.alarmes = this.supervision.getAlarmesPerPage("platine", this.currentPage, ALARMES_PER_PAGE);
    }

    public void doPrecedent() {
        this.setCurrentPage(this.currentPage - 1);
    }

    public void doSuivant() {
        this.setCurrentPage(this.currentPage + 1);

    }

    public String doExportAlarmesPDF() {
        if (this.alarmes != null) {
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
                Logger.getLogger(BeanDashbord.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(BeanDashbord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }
}
