package modele;
import modele.exceptions.TablePleineException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table implements Serializable {


    private int numTable;
    private int placeLibre;
    private Map<Integer, Integer> invités;
    private ArrayList<Integer> occupants;
    public static final int capacité = 8;
    //Constructeur
    public Table(int numTable, int placeLibre) {
        this.numTable = numTable;
        this.placeLibre = placeLibre;
        this.invités = new HashMap<Integer, Integer>();
        this.occupants = new ArrayList<Integer>();
    }
    //Accesseurs
    public int getNumTable() {
        return numTable;
    }
    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }
    public int getplaceLibre() {
        return placeLibre;
    }
    public void setplaceLibre(int placeLibre) {
        this.placeLibre = placeLibre;
    }
    public ArrayList<Integer> getOccupants() {
        return occupants;
    }
    public void setOccupants(ArrayList<Integer> occupants) {
        this.occupants = occupants;
    }
    public Map<Integer, Integer> getInvites() {
        return invités;
    }
    //Méthode
    public void ajouterParticipant(Participant participant, int nbPlaces) throws TablePleineException {
        if (placeLibre>= nbPlaces){
            occupants.add(participant.getNumero());
            invités.put(participant.getNumero(), nbPlaces-1);
            placeLibre -= nbPlaces;
        }else {
            throw new TablePleineException("Plus de place(s) disponible à la table"+this.getNumTable());
        }
    }
    public void supprimerParticipant(int numParticipant){
        int nbinvités = invités.get(numParticipant);
        placeLibre += nbinvités + 1;
        invités.remove(numParticipant);
        occupants.remove(occupants.indexOf((Integer) numParticipant));
    }
    @Override
    public String toString() {
        return "{Numéro de table=" + numTable + ", placeLibre=" + placeLibre + ", occupants=" + occupants + "}\n";
    }
}

