package modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Reservation implements Serializable, Comparable<Reservation> {

    private LocalDate date;
    private int numTable;
    private int nbrePlace;
    private double montant;
    private Participant participant;

    /**
     * @param date
     * @param numTable
     * @param participant
     * @param nbrePlace
     * @param montant
     */
    public Reservation(LocalDate date, int nbrePlace, int numTable, double montant, Participant participant){
        this.date = date;
        this.nbrePlace = nbrePlace;
        this.numTable = numTable;
        this.montant = montant;
        this.participant = participant;
    }

    public int getNumTable() {
        return numTable;
    }

    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    public int getNbrePlace() {
        return nbrePlace;
    }

    public void setNbrePlace(int nbrePlace) {
        this.nbrePlace = nbrePlace;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Participant getParticipant()
    {
        return participant;
    }

    public void setParticipant(Participant participant)
    {
        this.participant = participant;
    }

    public String toString()
    {
        return "Reservation[" + "date=" + date + ", numTable=" + numTable + ", nbrePlace(s)=" + nbrePlace + ", montant=" + montant + "]\n";
    }

    public int compareTo(Reservation o)
    {
        Etudiant etudiant1 = (Etudiant) participant;
        Etudiant etudiant2 = (Etudiant) o.getParticipant();
        return etudiant2.getAnnee()-etudiant1.getAnnee();
    }

    public static class Comparateurs
    {
        public static Comparator<Reservation> COMPARATOR = new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }
}
