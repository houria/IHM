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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import platinesshapi.Supervision;
import platinesshapi.entity.Alarme;
import platinesshapi.entity.ArboECC;
import platinesshapi.entity.ArboEEC;

import platinesshapi.entity.BilanInterface;
import platinesshapi.entity.ECC;
import platinesshapi.entity.EEC;

import platinesshapi.util.CurrentDate;
import platinesshapi.util.ExportCSV;
import platinesshapi.util.ExportPDF;

/**
 *
 * @author Houria
 */
@ManagedBean
@SessionScoped
public class BeanInterface implements Serializable {

    @ManagedProperty(value = "#{beanIndex}")
    private BeanIndex beanIndex;
    private Session session;
    private Supervision supervision;
    private BilanInterface bilanInterface;
    private ArboECC arborescence;
    private ArboECC currentArboECC;
    private List<ECC> subECC;
    private ECC ecc;
    private List<ECC> listeECC;
    private List<Alarme> alarmes;
    private static final int ALARMES_PER_PAGE = 20;
    private int countAlarmes = 0;

    private int nbrPage = 0;

    private int currentPage = 1;

    /**
     * Creates a new instance of BeanInterface
     */
    public BeanInterface() {
        this.alarmes = new ArrayList<Alarme>();

    }

    public void setBeanIndex(BeanIndex beanIndex) {
        this.beanIndex = beanIndex;
    }

    public BilanInterface getBilanInterface() {
        return bilanInterface;
    }

    public void setBilanInterface(BilanInterface bilanInterface) {
        this.bilanInterface = bilanInterface;
    }

    public ArboECC getArborescence() {
        return arborescence;
    }

    public void setArborescence(ArboECC arborescence) {
        this.arborescence = arborescence;
    }

    public ArboECC getCurrentArboECC() {
        return currentArboECC;
    }

    public void setCurrentArboECC(ArboECC currentArboECC) {
        this.currentArboECC = currentArboECC;
    }

    public ECC getEcc() {
        return ecc;
    }

    public void setEcc(ECC ecc) {
        this.ecc = ecc;
    }

    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
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
        this.alarmes = this.supervision.getAlarmesPerPage("iecc", this.currentPage, ALARMES_PER_PAGE);
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
            } catch (IOException ex) {
                Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }

    /**
     * Pour changer le groupe ecc selectionne
     *
     * @param eec
     * @return
     */
    public String setCurrentGroupeECC(String nomGroupe) {
        this.currentArboECC = arborescence.findDeepArboECC(nomGroupe);

        this.subECC = new ArrayList<ECC>();

        ECC tmp = null;
        for (ECC ecc : this.currentArboECC.getAllECC()) {
            tmp = getECCInList(ecc.getEcc(), this.listeECC);
            if (tmp != null) {
                this.subECC.add(tmp);
            }
        }
        return "#";
    }

    public ECC getECCSessionByName(String eccName) {
        ECC eccSession = null;

        for (ECC sess : this.listeECC) {
            if (sess.getEcc().equalsIgnoreCase(eccName)) {
                eccSession = sess;
                break;
            }
        }

        return eccSession;
    }

    private ECC getECCInList(String eccName, List<ECC> list) {
        ECC ecc = null;

        if (list != null) {
            for (ECC sess : list) {
                if (sess.getEcc().equalsIgnoreCase(eccName)) {
                    ecc = sess;
                    break;
                }
            }
        }

        return ecc;
    }

    /**
     * Pour changer le eec selectionné
     *
     * @param eec
     * @return
     */
    public String setCurrentECC(String ecc) {
        this.ecc = getECCSessionByName(ecc);
        return "";
    }
    
    public String initialiser(){
        if(this.arborescence == null)
            this.arborescence = this.supervision.getArborescenceECC("Interfaces");
        
        if(this.listeECC.size()==0)
            this.listeECC = this.supervision.list_ecc_iecc();
//        
//        if(this.bilanInterface == null)
//            this.bilanInterface=this.supervision.supp_bill_iecc(CurrentDate.getCurrentDate(),"T1");
        
        return "interfaces.xhtml";
    }

    @PostConstruct
    public void init() {
        this.session = this.beanIndex.getSession();

        this.supervision = new Supervision(this.session);

        this.listeECC = new ArrayList<ECC>();

        this.alarmes = this.supervision.getAlarmesPerPage("iecc", this.currentPage, ALARMES_PER_PAGE);
        this.countAlarmes = this.supervision.getCountAlamresByModule("iecc");
        this.nbrPage = (int) Math.ceil(this.countAlarmes / ALARMES_PER_PAGE);

        

//        this.listeECC = new ArrayList<ECC>();
//
//        this.listeECC.add(new ECC("VAUCHER"));
//        this.listeECC.add(new ECC("TEST"));
//        this.listeECC.add(new ECC("AUTRE"));
//        this.listeECC.add(new ECC("IN_CCN"));
//        this.listeECC.add(new ECC("soft"));
//        this.listeECC.add(new ECC("in"));
//
//        this.arborescence = new ArboECC();
//
//        ArboECC racine = new ArboECC("Collect");
//        racine.getEccs().add(new ECC("VAUCHER"));
//        racine.getEccs().add(new ECC("TEST"));
//        racine.getEccs().add(new ECC("AUTRE"));
//
//        ArboECC fils1 = new ArboECC("IN_CCN");
//        fils1.getEccs().add(new ECC("IN_CCN"));
//
//        ArboECC fils2 = new ArboECC("echange");
//
//        ArboECC fils3 = new ArboECC("soft");
//        fils3.getEccs().add(new ECC("soft"));
//
//        ArboECC fils4 = new ArboECC("in");
//        fils4.getEccs().add(new ECC("in"));
//
//        fils2.getFils().add(fils3);
//        fils2.getFils().add(fils4);
//
//        racine.getFils().add(fils1);
//        racine.getFils().add(fils2);
//
//        this.arborescence = racine;
    }
}
