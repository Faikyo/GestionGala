package modele;

import modele.exceptions.AccesInterditException;
import modele.exceptions.NotEnrolledException;
import modele.exceptions.TablePleineException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Gala implements Serializable {

    /**
     * On choisit le Map car elle est très efficace
     * pour la recherche d'un service à partir d'un
     * simple numéro que nous avons choisit comme la
     * clé de la collection
     */
    private LocalDate date;
    private Map<Integer, Table> listeTable;
    private Map<Integer, Personnel> listePersonnels;
    private Map<Integer, Etudiant> listeEtudiants;
    private Map<Integer, Reservation> listeReservationDem;
    private Map<Integer, Reservation> listeReservationAcc;
    private Map<Integer, Reservation> listeReservation;
    private static int TARIF1 = 10;
    private static int TARIF2 = 15;
    private static int TARIF3 = 20;
    private static int NBRTABLEETUDIANT = 15;
    private static int NBRTABLEPERSONNEL = 10;
    private static int NBRPLACEETUDIANT = 2;
    private static int NBRPLACEPERSONNEL = 2;
    private static int NBRPLACEETUDIANTSUP = 4;

    public static int getNBRPLACEPERSONNEL() {
        return NBRPLACEPERSONNEL;
    }

    /**
     *
     * @param date un paramètre de type LocalDate qui permet de définir la date du début du gala
     */

    public Gala(LocalDate date)
    {
        this.date = date;
        this.listeEtudiants = new HashMap<Integer, Etudiant>();
        this.listePersonnels = new HashMap<Integer, Personnel>();
        this.listeReservationAcc = new HashMap<Integer, Reservation>();
        this.listeReservation = new HashMap<Integer, Reservation>();
        this.listeReservationDem = new HashMap<Integer, Reservation>();
        this.listeTable = new HashMap<Integer, Table>();
        // On initialise le gala en chargeant les tables, en fournissant la liste 
        // des etudiants ainsi que la liste des personnels
        galaInitialisation();
    }

    /**
     * Cette fonction lit dans le fichier etudiants ligne par ligne
     * pour créer la liste des etudiants de l'établissement
     */
    public void readStudentFile()
    {
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File("Participant/etudiants.txt"));
            while(scanner.hasNext())
            {
                // Etant donné que il n'y a aucune erreur d'ordre dans le remplisssage du fichier 
                // des etudiants On precede dans l'ordre en recuperant le numero d'etudiant 
                // le prenom, le nom, le numero de telephone, l'email
                int numEtudiant = scanner.nextInt();
                String prenom = scanner.next();
                String nom = scanner.next();
                Double numTelEtudiant = scanner.nextDouble();
                String email = scanner.next();
                int niveauEtudiant = scanner.nextInt();
                
                /*Comme le numero de telephone de l'etudiant recupéré dans le fichier double 
                et que la variable attendu par le constructeur de la classe etudiant 
                est de type string, on utilise la fonction valueof de String pour l'utiliser comme un string*/
                Etudiant etudiant = new Etudiant(numEtudiant, nom, prenom, email, String.valueOf(numTelEtudiant),  niveauEtudiant);
                listeEtudiants.put(numEtudiant, etudiant);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction lit dans le fichier etudiants ligne par ligne
     * pour créer la liste des personnels de l'établissement
     */
    public void readPersonalFile()
    {
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File("Participant/personnel.txt"));
            while(scanner.hasNext())
            {
                int numEPersonnel = scanner.nextInt();
                String prenom = scanner.next();
                String nom = scanner.next();
                Double numTelPersonnel = scanner.nextDouble();
                String email = scanner.next();
                /*Comme le numero de telephone du personnel recupéré dans le fichier double 
                et que la variable attendu par le constructeur de la classe personnel 
                est de type string, on utilise la fonction valueof de String pour l'utiliser comme un string*/
                Personnel personnel = new Personnel(numEPersonnel, nom, prenom, email, String.valueOf(numTelPersonnel));
                listePersonnels.put(numEPersonnel, personnel);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction remplit la liste des tables lors de l'appel du constructeur de gala
     */
    public void tableInitialisation()
    {
        for(int i=0; i<=(NBRTABLEETUDIANT+NBRTABLEPERSONNEL); i++)
        {
            Table t = new Table(i, Table.capacité);
            listeTable.put(i, t);
        }
    }

    /**
     * Cette fonction permet d'initialiser le gala en creant
     * les listes des personnels, etudiants et tables
     */
    public void galaInitialisation()
    {
        tableInitialisation();
        readPersonalFile();
        readStudentFile();
    }

    public LocalDate getDate()
    {
        return this.date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    /**
     * Elle permet d'ajouter à la liste des etudiants un nouvel etudiant lors d'une nouvelle inscription 
     * @param numEtudiant doit être un entier
     * @param nom doit être une chaîne de caractère
     * @param prenom doit être une chaîne de caractère
     * @param email doit être une chaîne de caractère
     * @param numTel doit être une chaîne de caractère
     * @param niveauEtude doit être un entier
     * @return un objet de type étudiant
     */
    public Etudiant addStudent(int numEtudiant, String nom, String prenom, String email, String numTel, int niveauEtude)
    {
        Etudiant etudiant = new Etudiant(numEtudiant, nom, prenom, numTel, email, niveauEtude);
        listeEtudiants.put(etudiant.getNumero(), etudiant);
        return etudiant;
    }

    /**
     * On cherche un étudiant dans la liste des étudiants par son numéro
     * @param numEtudiant doit être de type entier
     * @return un objet de type étudiant si celui n'est pas nul
     * @throws NotEnrolledException Si le numéro passé en paramètre n'appartient à aucun étudiant 
     * le programmera nous renverra un message et arrêtera son éxecution
     */
    public Etudiant searchStudentByNum(int numEtudiant) throws NotEnrolledException
    {
        Etudiant etudiant = listeEtudiants.get(numEtudiant);
        if(etudiant==null){
            throw new NotEnrolledException("Ce numéro n'est à aucun participant !");
        }
        return etudiant;
    }

    /**
     * On récupère la liste des tables des étudiants qui sont dans la table listeTable
     * @return un objet de type liste contenant des tables
     */
    public List<Table> studentTable()
    {
        List<Table> tableList = new ArrayList<Table>();
        for(int i=NBRTABLEPERSONNEL; i<(NBRTABLEPERSONNEL+NBRTABLEETUDIANT); i++){
            tableList.add(listeTable.get(i+1));
        }
        return tableList;
    }

    /**
     * 
     * @return une chaîne de caractère contenant la description des tables des étudiants 
     */
    public String studentTablePlan()
    {
        String s="";
        List<Table> tableList = studentTable();
        for(int i=0; i<(NBRTABLEETUDIANT); i++){
            Table table = tableList.get(i);
            s+= "Table "+ "N° "+table.getNumTable() +" : Place(s) Libre(s) "+table.getplaceLibre()
                    +", Occupant.s : ";
            for(int k=0; k<table.getOccupants().size(); k++){
                Etudiant etudiants = listeEtudiants.get(table.getOccupants().get(k));
                String nom = etudiants.getNom();
                int numero = etudiants.getNumero();
                if(table.getInvites().get(numero)>0)
                    nom +=" et "+table.getInvites().get(numero) + " Accompagnant(s)";

                s+= nom + " , " ;
            }
            s+="\n";
        }

        return s;
    }

    /**
     * Elle permet d'ajouter à la liste des étudiants un nouvel personnel lors d'une nouvelle inscription
     * @param numPer doit être un entier 
     * @param nom doit être une chaîne de caractère
     * @param prenom doit être une chaîne de caractère
     * @param numTel doit être une chaîne de caractère
     * @param email doit être une chaîne de caractère
     * @return O%n retourne un objet de type personnel
     */
    public Personnel addPersonal(int numPer, String nom, String prenom, String numTel, String email)
    {
        Personnel personnel = new Personnel(numPer, nom, prenom, email, numTel);
        listePersonnels.put(personnel.getNumero(), personnel);
        return personnel;
    }

    /**
     * On cherche un étudiant dans la liste des étudiants par son numéro
     * @param numPer doit être de type entier
     * @return un objet de type étudiant si celui n'est pas nul
     * @throws NotEnrolledException Si le numéro passé en paramètre n'appartient à aucun personnel 
     * le programmera nous renverra un message et arrêtera son exécution
     */
    public Personnel searchPersonalByNum(int numPer) throws NotEnrolledException
    {
        Personnel personnel = listePersonnels.get(numPer);
        if(personnel==null){
            throw new NotEnrolledException("Ce numéro n'est à aucun participant !");
        }
        return personnel;
    }

    /**
     * @return une chaîne de caractère contenant la description des tables des étudiants 
     */
    public String personalTablePlan()
    {
        String s="";
        List<Table> tableList = personalTable();
        for(int i=0; i<NBRTABLEPERSONNEL; i++){
            Table table = tableList.get(i);
            s+= "Table "+ "N° "+table.getNumTable() +" : Place(s) Libre(s) "+table.getplaceLibre()
                    +", Occupant.s : ";
            for(int k=0; k<table.getOccupants().size(); k++){
                Personnel personnel = listePersonnels.get(table.getOccupants().get(k));
                String nom = personnel.getNom();
                int numero = personnel.getNumero();
                if(table.getInvites().get(numero)>0)
                    nom +=" et " + table.getInvites().get(numero)+"  Accompagnant.s";
                s+= nom + " , " ;
            }
            s+="\n";
        }
        return s;
    }

    /**
     * 
     * @return un objet de type List contenant une liste des tables de personnel
     */
    public List<Table> personalTable()
    {
        List<Table> tableList = new ArrayList<Table>();
        for(int i=0; i<NBRTABLEPERSONNEL; i++) {
            tableList.add(listeTable.get(i+1));
        }
        return tableList;
    }

    /**
     * On cherche à savoir si le numéro passé en paramètre appartient à un étudiant ou un personnel déjà inscrit
     * @param numero doit être un entier
     * @return un boolean
     */
    public boolean ifInPeronnalsOrStudent(int numero)
    {
        return listePersonnels.containsKey(numero) || listeEtudiants.containsKey(numero);
    }

    /**
     * On cherche une réservation dans la liste des réservations par son numéro qui représente la clé du Map
     * @param numero doit être de type entier
     * @return un objet de type réservation si celui n'est pas nul
     * @throws NotEnrolledException Si le numéro passé en paramètre n'appartient à aucun personnel
     * le programmera nous renverra un message et arrêtera son exécution
     */
    public Reservation searchReservationByNumber(int numero) throws NotEnrolledException
    {
        Reservation reservation = listeReservation.get(numero);
        if(reservation==null)
            throw new NotEnrolledException("Il n'y a aucune réservation avec ce numéro !");
        return listeReservation.get(numero);
    }

    /**
     * On cherche à savoir si le numéro passé en paramètre est la clé d'une réservation
     * @param numero doit être un entier
     * @return un boolean
     */
    public boolean haveReservation(int numero)
    {
        return listeReservation.containsKey(numero);
    }

    /**
     * Elle est appelée pour désincrire un pqrticipant de la liste de réservation et des tables
     * @param numero doit être un entier qui représente la clé de la collection Map
     */
    public void cancelReservation(int numero)
    {
        if(listeReservation.get(numero) != null)
        {
            /* S'il existe une réservation liée au numéro passé en paramètre
            *On cherche le numéro de la table sur laquelle il est affecté
            * pour supprimer son numéro de la table ainsi que de la liste de réservation
             */
            int numTable = listeReservation.get(numero).getNumTable();
            Table table = listeTable.get(numTable);
            table.supprimerParticipant(numero);
            listeReservation.remove(numero);
        }
        else
        {
            if(listePersonnels.get(numero)!=null){
                /*
                Si le numéro n'existe dans la liste de réservation on cherche s'il est lié à un personnel
                dans la liste de ces dernier puis le supprimé
                 */
                listePersonnels.remove(numero);
            }
            else if(listeEtudiants.get(numero)!=null) {
                /*
                Si le numéro n'existe dans la liste de réservation et dans la liste des personnel
                 on cherche s'il est lié à un étudiant dans leur liste puis le supprimé
                 */
                listeEtudiants.remove(numero);
            }
        }
    }

    /**
     *
     * @param numTable doit être un entier
     * @param participant doit être un objet de type Participat
     * @param nbrePlace doit être un entier
     * @throws NotEnrolledException elle sera appelé si le numéro de la table passé en paramètre
     * ne convent aux numéros des tables des personnels compris entre 1 et 10
     * @throws AccesInterditException Idem si le nombre de plae de reservation demandé depasse le nombre
     * autorisé de place des personnels et le programmera s'arrêtera
     * @throws TablePleineException Elle est appelé par la fonction ajouterParticipant de l'objet
     * lors de l'association à un numero dans la liste des tables
     */
    public void reservePersonalTable(int numTable, Participant participant, int nbrePlace) throws
            NotEnrolledException, AccesInterditException, TablePleineException
    {
        if(listeTable.get(numTable)==null)
            throw new NotEnrolledException("Ce numéro ne correspond à aucun de ceux de nos tables !");
        else if(nbrePlace>NBRPLACEPERSONNEL) {
            throw new AccesInterditException("Vous ne pouvez pas réserver " + (nbrePlace) + " Place. Vous n'avez " +
                    "droit qu'à " + NBRPLACEPERSONNEL + " place(s) la votre incluse !");
        }
        else if(numTable<NBRTABLEPERSONNEL+NBRTABLEETUDIANT && numTable>NBRTABLEPERSONNEL){
            throw new AccesInterditException("Cette table n'est pas pour les personnels, veuillez choisir un numero entre"
            +" 1 et "+NBRTABLEPERSONNEL+"!");
        }
        // On ajoute à la table dont son numéro est passé en paramètre un personnel
        // avec le nombre de place réservation souhaité
        listeTable.get(numTable).ajouterParticipant(participant, nbrePlace);
        // On attribue une réservation au personnel puis on l'ajoute à la liste des réservations
        Reservation r = new Reservation(LocalDate.now(), nbrePlace, numTable, nbrePlace*TARIF3, participant);
        listeReservation.put(participant.getNumero(), r);
    }

    /**
     * On attribue au hazard une table de réservation à un personnel
     * @param participant
     * @param nbrePlace
     * @throws TablePleineException Elle est appelée par la fonction ajouteParticipant
     */
    public void reserveAutoPersonalTable(Participant participant, int nbrePlace) throws TablePleineException
    {
        for(int i=0; i<NBRTABLEPERSONNEL; i++) {
            if(listeTable.get(i+1).getplaceLibre()>=nbrePlace) {
                // On ajoute à la table dont son numéro est passé en paramètre un personnel
                // avec le nombre de place réservation souhaité
                listeTable.get(i+1).ajouterParticipant(participant, nbrePlace);
                // On attribue une réservation au personnel puis on l'ajoute à la liste des réservations
                Reservation reservation = new Reservation(LocalDate.now(), nbrePlace, i+1, nbrePlace*TARIF3, participant);
                listeReservation.put(participant.getNumero(), reservation);
                break; // On arrête l'exécution du code une fois la reservation faite pour éviter le passage à une autre table
            }
        }
    }

    public void requestReservation(Participant participant, int nbrePlace) throws AccesInterditException
    {
        Etudiant etudiant = (Etudiant) participant;
        double montant = (etudiant.getAnnee()==5 ? TARIF1:TARIF2);
        int nbrePlaceMax = (etudiant.getAnnee()==5 ? NBRPLACEETUDIANTSUP : NBRPLACEETUDIANT );
        if(nbrePlace>nbrePlaceMax) {
            throw new AccesInterditException("Vous ne pouvez pas réserver "+nbrePlace+" place. Vous n'avez" +
                    "droit qu'à "+nbrePlaceMax+" place(s) la votre incluse !");
        }

        Reservation reservation = new Reservation(LocalDate.now(), nbrePlace, 0,montant+(nbrePlace-1)*TARIF3, participant);
        listeReservationDem.put(participant.getNumero(), reservation);

    }

    public void validateRequestedReservation()
    {
        /*
        On trie d'abord les reservations suivant le niveau d'étude le plus haut, d'où la necessité
        d'utilisation d'une liste non chaîné
         */

        ArrayList<Reservation> reservations = new ArrayList<Reservation>(listeReservationDem.values());
        Collections.sort(reservations);

        if(reservations.isEmpty()==true) return;

        /*
        On caste les particiapnts en etudiant pour récupérer leur année d'étude afin de priorisé ceux de derniere
        année lors de la réservation
        */
        Etudiant etudiant = (Etudiant) reservations.get(0).getParticipant();
        int derniereAnneeEtude = etudiant.getAnnee();

        // On recupère le nombre totale de place reservé aux etudiants
        int nbrePlaceMax = NBRTABLEPERSONNEL*Table.capacité;

        // On initialise à 0 le nbre de place affecté déjà à des etudiants
        int nbrePlaceAffecte = 0;

        for(int i=0; i<reservations.size(); i++) {
            etudiant = (Etudiant) reservations.get(i).getParticipant();
            int nbrePlaceDemande = reservations.get(i).getNbrePlace();
            if((nbrePlaceMax>=(nbrePlaceDemande+nbrePlaceAffecte))&&(etudiant.getAnnee()==derniereAnneeEtude)) {
                nbrePlaceAffecte+=nbrePlaceDemande;

                // On ajoute l'étudiant à la liste des demandes accordées
                listeReservationAcc.put(etudiant.getNumero(), reservations.get(i));

                // On le retire de la liste de demande de reservation
                listeReservationDem.remove(etudiant.getNumero());
            } else {
                // Si on a pas d'etudiant de la derniere annee ou bien qu'il n'y a pas de places
                // disponible on sort de la boucle
                break;
            }

            // On trie les restes de reservations par date pour les autres etudiants
            // On fait la reaffectation de la variable parceque la liste de demande
            // de reservation est mise à jour
            reservations = new ArrayList<Reservation>(listeReservationDem.values());
            Collections.sort(reservations, Reservation.Comparateurs.COMPARATOR);

            if(reservations.isEmpty()==true) return;

            for(int k=0; k<reservations.size(); k++) {
                nbrePlaceDemande = reservations.get(i).getNbrePlace();
                etudiant = (Etudiant) reservations.get(i).getParticipant();

                if(nbrePlaceMax>=(nbrePlaceDemande+nbrePlaceAffecte)) {
                    nbrePlaceAffecte += nbrePlaceDemande;

                    // On l'ajoute à la liste de reservation accordée
                    listeReservationAcc.put(etudiant.getNumero(), reservations.get(i));

                    // On le supprime de liste de demande de reservation
                    listeReservationDem.remove(etudiant.getNumero());
                }
            }
        }
    }

    /**
     *
     * @param participant
     * @param nbrePlace
     * @throws TablePleineException
     */
    public void reserveAutoStudentTable(Participant participant, int nbrePlace) throws TablePleineException
    {
        Etudiant etudiant = (Etudiant)participant;
        double montant = (etudiant.getAnnee()==5 ? TARIF1:TARIF2);
        for(int i=NBRTABLEPERSONNEL; i<(NBRTABLEPERSONNEL+NBRTABLEETUDIANT); i++) {
            if(listeTable.get(i+1).getplaceLibre()>=nbrePlace) {
                Reservation reservation = new Reservation(LocalDate.now(), nbrePlace, i+1, (montant+(nbrePlace-1)*TARIF3), participant);
                listeTable.get(i+1).ajouterParticipant(participant, nbrePlace);
                listeReservation.put(participant.getNumero(), reservation);
                break;
            }
        }
    }

    public void reserveStudentTable(int numTable, int numero)
            throws NotEnrolledException, AccesInterditException, TablePleineException
    {
        if(numTable<=NBRTABLEPERSONNEL && numTable>=1) {
            throw new AccesInterditException("Desolé cette table n'est pas à réserver aux étudiants. Veuillez" +
                    " choisir un numéro de table "+" compris entre "+NBRTABLEPERSONNEL+" et "
                    +(NBRTABLEETUDIANT+NBRTABLEPERSONNEL));
        }
        Reservation reservation = listeReservationAcc.get(numero);
        if(listeTable.get(numTable)==null) {
            throw new NotEnrolledException("Aucune table n'est affectée à ce numéro. Veuillez choisir un numéro de table" +
                    " compris entre "+NBRTABLEPERSONNEL+" et "+NBRTABLEETUDIANT+NBRTABLEPERSONNEL);
        }
        listeTable.get(numTable).ajouterParticipant(reservation.getParticipant(), reservation.getNbrePlace());
        reservation.setNumTable(numTable);
        listeReservation.put(numero, reservation);

        // On le retire de la liste de reservation accordée
        listeReservationAcc.remove(numero);
    }

    public boolean haveValidatedReservation(int numero)
    {
        return (listeReservationAcc.containsKey(numero));
    }

    public Reservation searchValidatedReservationByNum(int numero)
    {
        return listeReservationAcc.get(numero);
    }

    public boolean haveRequestReservation(int numero)
    {
        return (listeReservationDem.containsKey(numero));
    }

    public Reservation searchRequestedReservationByNum(int numero)
    {
        return listeReservationDem.get(numero);
    }

}
