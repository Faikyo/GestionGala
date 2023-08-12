package modele;

import java.io.Serializable;
import java.util.Objects;

public class Participant implements Serializable {

    protected int numero;
    protected String nom;
    protected String prenom;
    protected String mail;
    protected String tel;



    public Participant(){

    }
    public Participant (int numero, String nom, String prenom, String mail, String tel){
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;

    }
    /**
     * Retourne le numéro
     * @return le numéro du participant
     */

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Retourne le nom
     * @return le nom du participant
     */

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne prenom
     * @return le prenom du partcipant
     */
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne numéro de téléphone
     * @return le numéro de téléphone du participant
     */
    public String getTel() {
        return tel;
    }


    /**
     *
     * @param tel numéro de téléphone du participant
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Retourne mail
     * @return l'adresse mail du participant
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @param mail e-mail du participant
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Retourne le toString du participant
     * @return les attributs du participant
     */
    @Override
    public String toString() {
        return "Participant{" +
                "numero=" + numero +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
