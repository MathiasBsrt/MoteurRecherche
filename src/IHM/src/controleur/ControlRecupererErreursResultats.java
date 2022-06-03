package controleur;

import java.util.List;

import modele.BDResultat;
import modele.EnsembleResultat;

public class ControlRecupererErreursResultats {

	private BDResultat bdResultat = BDResultat.getInstance();

	public ControlRecupererErreursResultats() {

	}

	public List<EnsembleResultat> recupererErreursResultats() {
		List<EnsembleResultat> resultats = bdResultat.getListeErreurs();
		return resultats;
	}

}