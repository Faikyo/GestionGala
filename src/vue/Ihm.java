package vue;

import java.util.Scanner;

public class Ihm {
    Scanner sc = new Scanner(System.in);
    public Ihm(){

    }
    public void afficherMessage(String s){
        System.out.println(s);
    }

    public void erreur(String s){
        System.out.println("Erreur!!! "+s);
    }

    public int saisieInt(){
        int c;
        while(!sc.hasNextInt()){ // vérifie si la saisie est un entier
            System.out.println("Entier attendu !!");
            sc.next();
        }
        c=sc.nextInt();
        return c;
    }
    public String saisieString(){
        Scanner scan = new Scanner(System.in);
        String s = null;
        boolean validInput = false;
        do{
            s = scan.nextLine();
            if(s.matches("[a-zA-Z]*")){// verifie si l'entrée ne contient que des lettres
                validInput=true;
            }
            else{
                System.out.println("Saisie invalide, une chaine de caratères est attendue !");
            }
        }while(!validInput);
        return s;
    }
    public static String saisieEmail(){
        Scanner scan = new Scanner(System.in);
        String s = null;
        boolean validInput = false;
        do{
            s = scan.nextLine();
            if(s.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z](.+)[a-zA-Z]")){// verifie si l'entrée est en format e-mail
               validInput=true;
            }
            else{
                System.out.println("Saisie incorrecte, entrez un e-mail valide !");
            }
        }while(!validInput);
        return s;
    }

    public int identification(){
        System.out.println("Identification");
        return saisieInt();
    }

    public int choixTypeParticipant(){
        int choix;
        do {
            System.out.println("Saisir 1 pour Etudiant, 2 pour Personnel");
            choix = saisieInt();
        }while (choix > 3 || choix < 0 );
        return choix;
    }

    public int choixInsQuitter(){
        int choix;
        do {
            System.out.println("Saisir 1 pour S'inscrire, 2 pour Quitter");
            choix = saisieInt();
        }while (choix > 3 || choix < 0 );
        return choix;
    }

    public String[] inscriptionPerso(){
        String [] donnees = new String[5];
        System.out.println("Saisissez votre numéro ID !");
        donnees[0] = Integer.toString(saisieInt());sc.nextLine();
        System.out.println("Saisissez votre nom !");
        donnees[1] = saisieString();
        System.out.println("Saisissez votre prénom !");
        donnees[2] = saisieString();
        System.out.println("Saisissez votre numéro de téléphone !");
        donnees[3] = Integer.toString(saisieInt());sc.nextLine();
        System.out.println("Saisissez votre e-mail !");
        donnees[4] = saisieEmail();

        return donnees;
    }

    public String[] inscriptionEtu(){
        String [] donnees = new String[6];
        System.out.println("Saisissez votre numéro ID !");
        donnees[0] = Integer.toString(saisieInt());sc.nextLine();
        System.out.println("Saisissez votre nom !");
        donnees[1] = saisieString();
        System.out.println("Saisissez votre prénom !");
        donnees[2] = saisieString();
        System.out.println("Saisissez votre numéro de téléphone !");
        donnees[3] = Integer.toString(saisieInt());sc.nextLine();
        System.out.println("Saisissez votre e-mail !");
        donnees[4] = saisieEmail();
        System.out.println("Saisissez votre année d'études !");
        donnees[5] = Integer.toString(saisieInt());

        return donnees;
    }



    public int choixGestionPlace(){
        int choix;
        do {
            System.out.println("1-Gérer les places, 2-Se désinscrire, 3-Quitter");
            choix = saisieInt();
        }while (choix > 3 || choix < 0 );
        return choix;
    }

    public int choixAfficherPlan(){
        int choix;
        do {
            System.out.println("1-Afficher le plan, 2-Réserver à la première table libre");
            choix = saisieInt();
        }while (choix > 2 || choix < 0 );
        return choix;
    }
    public int choixAfficherPlanEtu(){
        int choix;
        do {
            System.out.println("1-Faire une demande de réservation");
            choix = saisieInt();
        }while (choix !=1 );
        return choix;
    }

    public int choixValiderReservationEtu(){
        System.out.println("---Valider votre réservation---");
        int choix;
        do {
            System.out.println("1-Afficher le plan, 2-Réserver à la première place disponible");
            choix = saisieInt();
        }while (choix > 2|| choix < 0 );
        return choix;
    }


    public int[] saisieReservation(){
        System.out.println("Saisissez le numéro de table !");
        int numTable = saisieInt();
        System.out.println("Saisissez le nbre de places !");
        int nbInvites = saisieInt();
        int [] tab = {numTable, nbInvites};
        return tab;
    }

    public int validerReservation(){
        System.out.println("Saisissez le numéro de table !");
        int numTable = saisieInt();sc.nextLine();
        return numTable;
    }

    public int saisieDemandeReservation(){
        System.out.println("Saisissez le nbre de places !");
        int nbInvites = saisieInt();
        return nbInvites;
    }

}
