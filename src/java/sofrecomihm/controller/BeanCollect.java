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
import java.util.Date;
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
import platinesshapi.entity.ArboEEC;
import platinesshapi.entity.BilanCollecte;
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
public class BeanCollect implements Serializable {
    
    private static final int ALARMES_PER_PAGE= 20;

    @ManagedProperty(value = "#{beanIndex}")
    private BeanIndex beanIndex;
    private Session session;
    private Supervision supervision;
    private BilanCollecte bilanCollecte;
    private ArboEEC arborescence;
    private ArboEEC currentArboEEC;
    private List<EEC> subEEC;
    private EEC eec;
    private List<EEC> listeEEC;
    private List<Alarme> alarmes;
    
    private int countAlarmes =0;
    
    private int nbrPage =0;
    
    private int currentPage=1;
    

    public BeanCollect() {
        this.alarmes = new ArrayList<Alarme>();
    }

    public BilanCollecte getBilanCollecte() {
        return bilanCollecte;
    }
    

    public void setBilanCollecte(BilanCollecte bilanCollecte) {
        this.bilanCollecte = bilanCollecte;
    }

    public ArboEEC getArborescence() {
        return arborescence;
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
        this.alarmes = this.supervision.getAlarmesPerPage("collecte", this.currentPage, ALARMES_PER_PAGE);    
    }
    
    public void doPrecedent()
    {
        this.setCurrentPage(this.currentPage-1);
           }
    
    public void doSuivant()
    {
        this.setCurrentPage(this.currentPage+1);
       
    }

    public void setArborescence(ArboEEC arborescence) {
        this.arborescence = arborescence;
    }

    public void setBeanIndex(BeanIndex beanIndex) {
        this.beanIndex = beanIndex;
    }

    public EEC getEec() {
        return eec;
    }

    public void setEec(EEC eec) {
        this.eec = eec;
    }

    public ArboEEC getCurrentArboEEC() {
        return currentArboEEC;
    }

    public void setCurrentArboEEC(ArboEEC currentArboEEC) {
        this.currentArboEEC = currentArboEEC;
    }

    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
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
                Logger.getLogger(BeanCollect.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(BeanCollect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
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

    /**
     * *********************************************************************************************************
     */
    public int countSubEECBlocReçu() {
        int nbr = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getBilanColl() != null) {
                    nbr = nbr + sess.getBilanColl().getBlocRecus();
                }
            }
        }

        return nbr;
    }

    public int countSubEECBlocPerdu() {
        int nbr = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getBilanColl() != null) {
                    nbr = nbr + sess.getBilanColl().getBlocPerdus();
                }
            }
        }

        return nbr;
    }

    public int countSubEECBlocRejete() {
        int nbr = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getBilanColl() != null) {
                    nbr = nbr + sess.getBilanColl().getBlocRejetes();
                }
            }
        }

        return nbr;
    }

    public int countSubEECFichierReçu() {
        int nbr = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getBilanColl() != null) {
                    nbr = nbr + sess.getBilanColl().getFichierRecus();
                }
            }
        }

        return nbr;
    }

    public int countSubEECFichierRejete() {
        int nbr = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getBilanColl()!= null) {
                    nbr = nbr + sess.getBilanColl().getFichierRejetes();
                }
            }
        }

        return nbr;
    }

    /**
     * ****************************************************************************************************************
     */
    public int countSubEECSessionArret() {
        int countArret = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getEtatSession() != null) {
                    if (sess.getEtatSession().equalsIgnoreCase("arret")) {
                        countArret++;
                    }
                }
            }
        }

        return countArret;
    }

    public int countSubEECSessionNormal() {
        int countNormale = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getEtatSession() != null) {
                    if (!sess.getEtatSession().equalsIgnoreCase("arret")) {
                        countNormale++;
                    }
                }
            }
        }

        return countNormale;
    }

    public int countSubEECSessionEssaie() {
        int countEssaie = 0;
        if (this.subEEC != null) {
            for (EEC sess : this.subEEC) {
                if (sess.getEtatSession() != null) {
                    if (!sess.getEtatSession().equalsIgnoreCase("arret") && !sess.getEtatSession().equalsIgnoreCase("normal")) {
                        countEssaie++;
                    }
                }
            }
        }

        return countEssaie;
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

    public List<EEC> getListeEECSession() {
        return listeEEC;
    }

    public void setListeEECSession(List<EEC> listeEECSession) {
        this.listeEEC = listeEECSession;
    }

    public int etatEECSessionNormal() {
        return (this.eec.getEtatSession().equalsIgnoreCase("normal") ? 1 : 0);
    }

    public int etatEECSessionArret() {
        return (this.eec.getEtatSession().equalsIgnoreCase("arret") ? 1 : 0);
    }

    public int etatEECSessionEssaie() {
        return ((!this.eec.getEtatSession().equalsIgnoreCase("normal") && !this.eec.getEtatSession().equalsIgnoreCase("arret")) ? 1 : 0);
    }

    public int countEECSessionArret() {
        int countArret = 0;
        if (this.listeEEC != null) {
            for (EEC sess : this.listeEEC) {
                if (sess.getEtatSession() != null) {
                    if (sess.getEtatSession().equalsIgnoreCase("arret")) {
                        countArret++;
                    }
                }
            }
        }

        return countArret;
    }

    public int countEECSessionNormal() {
        int countNormale = 0;

        if (this.listeEEC != null) {
            for (EEC sess : this.listeEEC) {
                if (sess.getEtatSession() != null) {
                    if (!sess.getEtatSession().equalsIgnoreCase("arret")) {
                        countNormale++;
                    }
                }
            }

        }

        return countNormale;
    }

    public int countEECSessionEssaie() {
        int countEssaie = 0;
        if (this.listeEEC != null) {
            for (EEC sess : this.listeEEC) {
                if (sess.getEtatSession() != null) {
                    if (!sess.getEtatSession().equalsIgnoreCase("arret") && !sess.getEtatSession().equalsIgnoreCase("normal")) {
                        countEssaie++;
                    }
                }
            }
        }

        return countEssaie;
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

    public int countEECLiaisonSecours() {
        int countSecours = 0;

        if (this.listeEEC != null) {
            for (EEC liais : this.listeEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if (liais.getEtatLisaisonSecours().equalsIgnoreCase("ok")) {
                        countSecours++;
                    }
                }
            }
        }

        return countSecours;

    }

    public int countEECLiaisonNormal() {
        int countNormale = 0;

        if (this.listeEEC != null) {
            for (EEC liais : this.listeEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if (liais.getEtatLiaisonNormal().equalsIgnoreCase("ok")) {
                        countNormale++;
                    }
                }
            }
        }

        return countNormale;
    }

    public int countEECLiaisonHSNormal() {

        int countHS = 0;
        if (this.listeEEC != null) {
            for (EEC liais : this.listeEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if ((!liais.getEtatLiaisonNormal().equalsIgnoreCase("ok"))) {
                        countHS++;
                    }
                }
            }
        }

        return countHS;
    }

    public int countEECLiaisonHSSecours() {

        int countHS = 0;
        if (this.listeEEC != null) {
            for (EEC liais : this.listeEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if ((!liais.getEtatLisaisonSecours().equalsIgnoreCase("ok"))) {
                        countHS++;
                    }
                }
            }
        }

        return countHS;
    }

    public int countSubEECLiaisonSecours() {
        int countSecours = 0;

        if (this.subEEC != null) {
            for (EEC liais : this.subEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if (liais.getEtatLisaisonSecours().equalsIgnoreCase("ok")) {
                        countSecours++;
                    }
                }
            }
        }

        return countSecours;

    }

    public int countSubEECLiaisonNormal() {
        int countNormale = 0;
        if (this.subEEC != null) {
            for (EEC liais : this.subEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if (liais.getEtatLiaisonNormal().equalsIgnoreCase("ok")) {
                        countNormale++;
                    }
                }
            }
        }

        return countNormale;
    }

    public int countSubEECLiaisonHSNormal() {

        int countHS = 0;
        if (this.subEEC != null) {
            for (EEC liais : this.subEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if ((!liais.getEtatLiaisonNormal().equalsIgnoreCase("ok"))) {
                        countHS++;
                    }
                }
            }
        }

        return countHS;
    }

    public int countSubEECLiaisonHSSecours() {

        int countHS = 0;
        if (this.subEEC != null) {
            for (EEC liais : this.subEEC) {
                if (liais.getEtatLiaisonNormal() != null) {
                    if ((!liais.getEtatLisaisonSecours().equalsIgnoreCase("ok"))) {
                        countHS++;
                    }
                }
            }
        }

        return countHS;
    }

    public EEC getEECLiaisonByName(String eecName) {

        EEC eecLiaison = null;
        for (EEC liais : this.listeEEC) {
            if (liais.getEec().equalsIgnoreCase(eecName)) {
                eecLiaison = liais;
            }
        }
        return eecLiaison;
    }
    
    public String initialiser(){
        
        if(this.listeEEC.size()==0)
            this.listeEEC = this.supervision.list_sess_coll();
        
        if(this.arborescence == null)
            this.arborescence = this.supervision.getArborescenceEEC("collecte");
        
        if(this.bilanCollecte == null)
            this.bilanCollecte = this.supervision.supp_bill_coll(CurrentDate.getCurrentDate(),"T1 T2");
        
        return "collecte.xhtml";
    }

    @PostConstruct
    public void init() {
        this.session = this.beanIndex.getSession();

        this.supervision = new Supervision(this.session);

        this.listeEEC = new ArrayList<EEC>();

        this.alarmes = this.supervision.getAlarmesPerPage("collecte", this.currentPage, ALARMES_PER_PAGE);
        this.countAlarmes = this.supervision.getCountAlamresByModule("collecte");
        
        this.nbrPage = (int)Math.ceil(this.countAlarmes/ALARMES_PER_PAGE);
        
        
        
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
