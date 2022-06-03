package testconsole;

import controleur.ControlDescripteur;
import controleur.ControlRecherche;
import modele.BDMoteur;
import modele.MoteurQuiMarcheMal;
import modele.MoteurSeekBitUnimate;
import vueconsole.BoundaryRecherche;

public class TestsCasRecherche {

	public static void main(String[] args) {
		ControlRecherche controlRecherche = new ControlRecherche();
		ControlDescripteur controlDescripteur = new ControlDescripteur();
		BoundaryRecherche boundaryRecherche = new BoundaryRecherche(controlRecherche, controlDescripteur);

		BDMoteur bdMoteur = BDMoteur.getInstance();
		bdMoteur.ajouterMoteur(new MoteurSeekBitUnimate());
		bdMoteur.ajouterMoteur(new MoteurQuiMarcheMal());

		boundaryRecherche.lancerRechercher();
	}

}
