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
import platinesshapi.entity.ArboEEC;
import platinesshapi.entity.BilanDistribution;
import platinesshapi.entity.CompteurDistribution;
import platinesshapi.entity.CompteurTraitement;
import platinesshapi.entity.EEC;
import platinesshapi.util.CurrentDate;
import platinesshapi.util.ExportCSV;
import platinesshapi.util.ExportPDF;

/**
 *
 * @author h.el-hayouni
 */
@ManagedBean
@SessionScoped

public class BeanDistribution implements Serializable {

    @ManagedProperty(value = "#{beanIndex}")
    private BeanIndex beanIndex;

    private Session session;

    private Supervision supervision;
    private BilanDistribution bilanDistribution;
    private BilanDistribution tauxMultDist;
    private ArboEEC arborescence;
    private ArboEEC currentArboEEC;
    private List<EEC> subEEC;

    private EEC eec;
    private List<EEC> listeEEC;
    private List<Alarme> alarmes;
    private static final int ALARMES_PER_PAGE = 20;
    private int countAlarmes = 0;

    private int nbrPage = 0;

    private int currentPage = 1;

    public BeanDistribution() {
        this.alarmes = new ArrayList<Alarme>();
    }

    public void setBeanIndex(BeanIndex beanIndex) {
        this.beanIndex = beanIndex;
    }

    public BilanDistribution getBilanDistribution() {
        return bilanDistribution;
    }

    public void setBilanDistribution(BilanDistribution bilanDistribution) {
        this.bilanDistribution = bilanDistribution;
    }

    public BilanDistribution getTauxMultDist() {
        return tauxMultDist;
    }

    public void setTauxMultDist(BilanDistribution tauxMultDist) {
        this.tauxMultDist = tauxMultDist;
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
        this.alarmes = this.supervision.getAlarmesPerPage("distribution", this.currentPage, ALARMES_PER_PAGE);
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
                Logger.getLogger(BeanDistribution.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(BeanDistribution.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }

    public ArboEEC getArborescence() {
        return arborescence;
    }

    public void setArborescence(ArboEEC arborescence) {
        this.arborescence = arborescence;
    }

    public List<EEC> getListeEEC() {
        return listeEEC;
    }

    public void setListeEEC(List<EEC> listeEEC) {
        this.listeEEC = listeEEC;
    }

    public EEC getEec() {
        return eec;
    }

    public void setEec(EEC eec) {
        this.eec = eec;
    }

    /**
     * Pour changer le eec selectionné
     *
     * @param eec
     * @return
     */
    public String setCurrentEEC(String eec) {
        this.eec = getEECSessionByName(eec);
        return "";
    }

    public ArboEEC getCurrentArboEEC() {
        return currentArboEEC;
    }

    public void setCurrentArboEEC(ArboEEC currentArboEEC) {
        this.currentArboEEC = currentArboEEC;
    }

    /**
     * Pour changer le groupe eec selectionne
     *
     * @param eec
     * @return
     */
    public String setCurrentGroupeEEC(String nomGroupe) {
        this.currentArboEEC = arborescence.findDeepArboEEC(nomGroupe);

        this.subEEC = new ArrayList<EEC>();

        EEC tmp = null;
        for (EEC eec : this.currentArboEEC.getAllEEC()) {
            tmp = getEECInList(eec.getEec(), this.listeEEC);
            if (tmp != null) {
                this.subEEC.add(tmp);
            }
        }
        return "#";
    }

    private EEC getEECInList(String eecName, List<EEC> list) {
        EEC eec = null;

        if (list != null) {
            for (EEC sess : list) {
                if (sess.getEec().equalsIgnoreCase(eecName)) {
                    eec = sess;
                    break;
                }
            }
        }

        return eec;
    }

    public List<EEC> getListeEECSession() {
        return listeEEC;
    }

    public void setListeEECSession(List<EEC> listeEECSession) {
        this.listeEEC = listeEECSession;
    }

    public EEC getEECSessionByName(String eecName) {
        EEC eecSession = null;

        for (EEC sess : this.listeEEC) {
            if (sess.getEec().equalsIgnoreCase(eecName)) {
                eecSession = sess;
                break;
            }
        }

        return eecSession;
    }

    /**
     * *********************************************************************************************************
     */
    public int getTotalDCIecc() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist() != null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_iecc() != null) {
                        if (!compteur.getDc_iecc().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_iecc());
                        }
                    }

                }
            }
        }
        return total;
    }

    public int getTotalDCRecyclage() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist() != null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_recyclage() != null) {
                        if (!compteur.getDc_recyclage().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_recyclage());
                        }
                    }

                }
            }
        }
        return total;
    }

    public int getTotalDCCorrelation() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist() != null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_correlation() != null) {
                        if (!compteur.getDc_correlation().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_correlation());
                        }
                    }

                }
            }
        }
        return total;
    }

    public int getTotalDCRedistribution() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist() != null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_redistribution() != null) {
                        if (!compteur.getDc_redistribution().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_redistribution());
                        }
                    }

                }
            }
        }
        return total;
    }

    public int getTotalDCErrone() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist() != null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_errone() != null) {
                        if (!compteur.getDc_errone().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_errone());
                        }
                    }

                }
            }
        }
        return total;
    }

    public int getTotalDCElimine() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist()!= null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_elimine() != null) {
                        if (!compteur.getDc_elimine().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_elimine());
                        }
                    }

                }
            }
        }
        return total;
    }

    public int getTotalDCDivers() {
        int total = 0;
        if (this.eec != null) {
            if (this.eec.getCompteursDist() != null) {
                for (CompteurDistribution compteur : this.eec.getCompteursDist()) {
                    if (compteur.getDc_divers() != null) {
                        if (!compteur.getDc_divers().equalsIgnoreCase("")) {
                            total = total + Integer.parseInt(compteur.getDc_divers());
                        }
                    }

                }
            }
        }
        return total;
    }

    public String initialiser(){
        if(this.arborescence == null)
            this.arborescence = this.supervision.getArborescenceEEC("Distribution");
        
        if(this.listeEEC.size()==0)
            this.listeEEC = this.supervision.list_eec_dist();
        
        if(this.bilanDistribution == null)
            this.bilanDistribution = this.supervision.supp_bill_dist(CurrentDate.getCurrentDate(), "T1");
        
        return "distribution.xhtml";
    }
    /**
     * *********************************************************************************************************
     */
    @PostConstruct
    public void init() {
        this.session = this.beanIndex.getSession();

        this.supervision = new Supervision(this.session);

        this.listeEEC = new ArrayList<EEC>();

        this.alarmes = this.supervision.getAlarmesPerPage("distribution", this.currentPage, ALARMES_PER_PAGE);
        this.countAlarmes = this.supervision.getCountAlamresByModule("distribution");
        this.nbrPage = (int) Math.ceil(this.countAlarmes / ALARMES_PER_PAGE);

        


//        
//        this.listeEEC = new ArrayList<EEC>();
//
//        this.listeEEC.add(new EEC("VAUCHER"));
//        this.listeEEC.add(new EEC("TEST"));
//        this.listeEEC.add(new EEC("AUTRE"));
//        this.listeEEC.add(new EEC("IN_CCN"));
//        this.listeEEC.add(new EEC("soft"));
//        this.listeEEC.add(new EEC("in"));
//
//        this.arborescence = new ArboEEC();
//
//        ArboEEC racine = new ArboEEC("Collect");
//        racine.getEccs().add(new EEC("VAUCHER"));
//        racine.getEccs().add(new EEC("TEST"));
//        racine.getEccs().add(new EEC("AUTRE"));
//
//        ArboEEC fils1 = new ArboEEC("IN_CCN");
//        fils1.getEccs().add(new EEC("IN_CCN"));
//
//        ArboEEC fils2 = new ArboEEC("echange");
//
//        ArboEEC fils3 = new ArboEEC("soft");
//        fils3.getEccs().add(new EEC("soft"));
//
//        ArboEEC fils4 = new ArboEEC("in");
//        fils4.getEccs().add(new EEC("in"));
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
