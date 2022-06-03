package controleur;

import java.util.Optional;

import modele.BDResultat;
import modele.EnsembleResultat;

public class ControlRecupererNouveauResultat {

	private BDResultat bdResultat = BDResultat.getInstance();
	
	public ControlRecupererNouveauResultat() {
		
	}
	
	public Optional<EnsembleResultat> recupererNouveauResultat() {
		EnsembleResultat resultat = bdResultat.getNouveauResultats();
		if(resultat == null) return Optional.empty();
		else return Optional.of(resultat);
	}

}