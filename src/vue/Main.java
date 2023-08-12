package vue;

import controleur.Controleur;
import modele.exceptions.AccesInterditException;
import modele.exceptions.NotEnrolledException;
import modele.exceptions.TablePleineException;

import java.io.IOException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws NotEnrolledException, TablePleineException, IOException, ClassNotFoundException, AccesInterditException {
	// write your code here
   Controleur c1 = new Controleur(LocalDate.now());
    }
}
