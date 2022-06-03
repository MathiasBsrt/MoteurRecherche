package testconsole;

import controleur.ControlAdmin;
import controleur.ControlDescripteur;
import controleur.ControlIndexation;
import controleur.ControlParamSeuil;
import controleur.ControlRecherche;
import controleur.ControlRecupererErreursResultats;
import controleur.ControlRecupererNouveauResultat;
import controleur.ControlRecupererResultats;
import controleur.ControlVisualiserResultat;
import modele.BDMoteur;
import modele.MoteurQuiMarcheMal;
import modele.MoteurSeekBitUnimate;
import vueconsole.BoundaryIndexer;
import vueconsole.BoundaryMenu;
import vueconsole.BoundaryModeAdmin;
import vueconsole.BoundaryParamSeuil;
import vueconsole.BoundaryRecherche;
import vueconsole.BoundarySidentifier;
import vueconsole.BoundaryVisualiserResultat;

public class TestsGlobal {

	public static void main(String[] args) {

		// Initialisation des contr�leurs et des boundaries pour le mode Administrateur.
		ControlAdmin cAdmin = new ControlAdmin();
		ControlParamSeuil controlParamSeuil = new ControlParamSeuil();
		BoundarySidentifier sIdentifier = new BoundarySidentifier(cAdmin);
		ControlIndexation cIndexation = new ControlIndexation();
		BoundaryIndexer bIndexer = new BoundaryIndexer(cIndexation);
		BoundaryParamSeuil pParamSeuil = new BoundaryParamSeuil(controlParamSeuil);
		BoundaryModeAdmin bModeAdmin = new BoundaryModeAdmin(cAdmin, bIndexer, sIdentifier, pParamSeuil);

		// Initialisation des contr�leurs et des boundaries pour la recherche.
		ControlRecherche controlRecherche = new ControlRecherche();
		ControlDescripteur controlDescripteur = new ControlDescripteur();
		BoundaryRecherche boundaryRecherche = new BoundaryRecherche(controlRecherche, controlDescripteur);

		// Initialisation des contr�leurs et des boundaries pour la visualisation des
		// r�sultats.
		ControlRecupererResultats controlRecupererResultats = new ControlRecupererResultats();
		ControlRecupererNouveauResultat controlRecupererNouveauResultat = new ControlRecupererNouveauResultat();
		ControlRecupererErreursResultats controlRecupererErreursResultats = new ControlRecupererErreursResultats();

		ControlVisualiserResultat controlVisualiserResultat = new ControlVisualiserResultat(controlRecupererResultats,
				controlRecupererNouveauResultat, controlRecupererErreursResultats);

		BoundaryVisualiserResultat boundaryVisualiserResultat = new BoundaryVisualiserResultat(
				controlVisualiserResultat);

		BoundaryMenu boundaryMenu = new BoundaryMenu(bModeAdmin, boundaryRecherche, boundaryVisualiserResultat);

		// Ajout des diff�rents moteurs disponnibles
		BDMoteur bdMoteur = BDMoteur.getInstance();
		bdMoteur.ajouterMoteur(new MoteurSeekBitUnimate());
		bdMoteur.ajouterMoteur(new MoteurQuiMarcheMal());

		boundaryMenu.lancerMenu();
	}

}
