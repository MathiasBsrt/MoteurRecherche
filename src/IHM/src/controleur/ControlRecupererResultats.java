package controleur;

import java.util.List;

import modele.BDResultat;
import modele.EnsembleResultat;

public class ControlRecupererResultats {

	private BDResultat bdResultat = BDResultat.getInstance();
	
	public ControlRecupererResultats() {
		
	}
	
	public List<EnsembleResultat> recupererResultats() {
		List<EnsembleResultat> resultats = bdResultat.getListeResultats();
		return resultats;		
	}

}