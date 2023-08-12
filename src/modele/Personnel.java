package modele;

import java.io.Serializable;

public class Personnel extends Participant implements Serializable {

    public Personnel(){ super(); }

    /**
     *
     * @param numero numéro du personnel
     * @param nom nom du personnel
     * @param prenom prenom du personnel
     * @param tel numero de téléphone du personnel
     * @param mail mail du personnel
     */

    public Personnel(int numero, String nom, String prenom, String mail, String tel){
        super(numero,nom,prenom,mail,tel);
    }


}
