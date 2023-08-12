package controleur;
import modele.Gala;
import modele.exceptions.AccesInterditException;
import modele.exceptions.NotEnrolledException;
import modele.exceptions.TablePleineException;
import vue.Ihm;
import modele.Personnel;
import modele.Etudiant;
import modele.Participant;

import java.io.*;
import java.time.LocalDate;
import stockage.ServiceStockage;

public class Controleur extends ServiceStockage implements Serializable {

    public Gala gala;
    public Ihm ihm;
    private Participant connecté;

    public Controleur(LocalDate date) throws NotEnrolledException, TablePleineException, IOException, AccesInterditException, ClassNotFoundException {
        File f = new File("gala.ser");
        if (f.exists()){
            gala = (Gala) charger();
        }else{
            new ServiceStockage();
            gala = new Gala(date);
            enregistrer(gala);
        }
        ihm = new Ihm();

        this.genererListeAcceptation();

        this.connection();
    }
    public void connection() throws IOException, NotEnrolledException, TablePleineException, AccesInterditException {
        ihm.afficherMessage("----Connexion----");
        int choix = ihm.choixTypeParticipant();
        if (choix == 1) {
            Etudiant etudiant = null;
            try {
                etudiant = gala.searchStudentByNum(ihm.identification());
                connecté = etudiant;
            } catch (NotEnrolledException e) {
                ihm.erreur(e.getMessage());
            }
            if (etudiant == null) {
                gestionInscription();
            } else
                gestionPlace();
        } else if (choix == 2) {
            Personnel personnel = null;
            try {
                personnel = gala.searchPersonalByNum(ihm.identification());
                connecté = personnel;
            } catch (NotEnrolledException e) {
                ihm.erreur(e.getMessage());
            }
            if (personnel == null)
                gestionInscription();
            else
                gestionPlace();
        }


    }
    public void gestionInscription() throws IOException, NotEnrolledException, TablePleineException, AccesInterditException {
        int choix = ihm.choixInsQuitter();
        if (choix==1){
            String [] data;
            ihm.afficherMessage("----Inscription----");
            int type = ihm.choixTypeParticipant();
            if (type == 1){
                data = ihm.inscriptionEtu();
                if (gala.ifInPeronnalsOrStudent(Integer.valueOf(data[0]))){
                    ihm.afficherMessage("Cet identifiant existe déjà en base !");
                    connection();
                }
                gala.addStudent(Integer.valueOf(data[0]), data[1], data[2], data[3], data[4], Integer.valueOf(data[5]));
                connection();
            }

            if (type ==2 ) {
                data = ihm.inscriptionPerso();
                gala.addPersonal(Integer.valueOf(data[0]), data[1], data[2], data[3], data[4]);
                connection();
            }
        }else
        if (choix == 2){
            System.exit(0);
        }
    }
    public void gestionPlace() throws IOException, NotEnrolledException, TablePleineException, AccesInterditException {
        int choix = ihm.choixGestionPlace();
        switch (choix){
            case 1: {
                if (gala.haveReservation(connecté.getNumero())){
                    ihm.afficherMessage("Vous aviez déjà reservé, modification impossible !");
                    System.exit(4);
                }
                if (connecté instanceof Personnel){
                    gestionPlacePerso();
                }
                if (connecté instanceof Etudiant){
                    gestionPlaceEtu();
                }
                break;
            }
            case 2: {
                ihm.afficherMessage("Désinscription de Mr/Mme "+ connecté.getNom());
                gala.cancelReservation(connecté.getNumero());
                enregistrer(gala);
                break;
            }
            case 3: {
                enregistrer(gala);
                System.exit(2);
                break;
            }
        }

    }
    public void gestionPlacePerso() throws IOException, TablePleineException, AccesInterditException, NotEnrolledException {

        int choix = ihm.choixAfficherPlan();
        if (choix ==1 ){
            //ihm.afficherMessage(gala.getTablePerso().toString()); //Debug

            ihm.afficherMessage(gala.personalTablePlan());
            ihm.afficherMessage("Vous pouvez réserver jusqu'à "+ gala.getNBRPLACEPERSONNEL()+" places (La votre incluse!)");
            int [] donnees = ihm.saisieReservation();
            try {
                gala.reservePersonalTable(donnees[0], connecté, donnees[1]);
                ihm.afficherMessage("Réservation effectuée, tarif à prévoir : "+
                        gala.searchReservationByNumber(connecté.getNumero()).getMontant());
                enregistrer(gala);
            } catch (TablePleineException e) {
                ihm.erreur(e.getMessage());
                gestionPlace();
            } catch (AccesInterditException e) {
                ihm.erreur(e.getMessage());
                gestionPlace();
            } catch (NotEnrolledException e) {
                ihm.erreur(e.getMessage());
                gestionPlace();
            }
        }
        else if (choix==2){
            //On lui choisi la première table libre
            int nbPlaces = ihm.saisieDemandeReservation();
            try {
                gala.reserveAutoPersonalTable(connecté, nbPlaces );
                enregistrer(gala);
                ihm.afficherMessage("Réservation effectuée, tarif à prévoir : "+
                        gala.searchReservationByNumber(connecté.getNumero()).getMontant());
            } catch (TablePleineException e) {
                ihm.erreur(e.getMessage());
                gestionPlace();
            }
        }
    }
    public void gestionPlaceEtu() throws IOException, NotEnrolledException, TablePleineException, AccesInterditException {

        if(gala.haveValidatedReservation(connecté.getNumero()) == false){
            int choix = ihm.choixAfficherPlanEtu();
            if (choix==1){
                if (gala.haveRequestReservation(connecté.getNumero()) == false){
                    Etudiant e = (Etudiant) connecté;
                    ihm.afficherMessage("Vous pouvez faire une demande pour "+ ( (e.getAnnee() == 5) ? 4 : 2)  +" places (La votre incluse !)");
                    int nbP = ihm.saisieDemandeReservation();
                    try {
                        gala.requestReservation(connecté,nbP);
                        enregistrer(gala);

                    } catch (AccesInterditException e1) {
                        ihm.erreur(e1.getMessage());
                        gestionPlace();
                    }
                }else{
                    ihm.afficherMessage("Vous aviez déjà une demande en attente de validation !");
                    gestionPlace();
                }
            }
        }else{
            int choix = ihm.choixValiderReservationEtu();
            if (choix == 1 ){
                ihm.afficherMessage(gala.studentTablePlan());
                //ihm.afficherMessage(gala.getTableEtu().toString());
                int numTable = ihm.validerReservation();
                try {
                    gala.reserveStudentTable(numTable, connecté.getNumero());
                    enregistrer(gala);
                    ihm.afficherMessage("Réservation effectuée, tarif à prévoir : "+
                            gala.searchReservationByNumber(connecté.getNumero()).getMontant());
                } catch (TablePleineException e) {
                    ihm.erreur(e.getMessage());
                    gestionPlace();
                } catch (NotEnrolledException e) {
                    ihm.erreur(e.getMessage());
                    gestionPlace();
                } catch (AccesInterditException e) {
                    ihm.erreur(e.getMessage());
                    gestionPlace();
                }
            }
            else if(choix==2){
                //On lui choisi la première table libre
                int nbPlaces = gala.searchValidatedReservationByNum(connecté.getNumero()).getNbrePlace(); //ihm.saisieDemandeReservation();
                try {
                    gala.reserveAutoStudentTable(connecté, nbPlaces );
                    enregistrer(gala);
                    ihm.afficherMessage("Réservation effectuée, tarif à prévoir ! "+
                            gala.searchReservationByNumber(connecté.getNumero()).getMontant());
                } catch (TablePleineException e) {
                    ihm.erreur(e.getMessage());
                    gestionPlace();
                }
            }
        }
    }
    public void genererListeAcceptation(){
        LocalDate today = LocalDate.now();
        if (gala.getDate().compareTo(today.plusMonths(1)) <= 0){
            gala.validateRequestedReservation();
        }
    }
}