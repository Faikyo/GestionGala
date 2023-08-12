package modele;

import java.io.Serializable;

public class Etudiant extends Participant implements Serializable {

    private int annee;


    public Etudiant(){super();}

    public Etudiant( int numero, String nom, String prenom, String mail, String tel, int annee){

        super(numero,nom,prenom,mail,tel);
        this.annee = annee;
    }

    /**
     * Retourne l'année
     * @return l'année d'étude
     */
    public int getAnnee() {
        return annee;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "numero=" + numero +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                ", annee='" + annee + '\'' +
                '}';
    }

}
