package controleur;

import java.util.List;
import java.util.Optional;

import modele.EnsembleResultat;

public class ControlVisualiserResultat {

	private ControlRecupererResultats controlRecupererResultats;
	private ControlRecupererNouveauResultat controlRecupererNouveauResultat;
	private ControlRecupererErreursResultats controlRecupererErreursResultats;

	public ControlVisualiserResultat(ControlRecupererResultats controlRecupererResultats,
			ControlRecupererNouveauResultat controlRecupererNouveauResultat,
			ControlRecupererErreursResultats controlRecupererErreursResultats) {
		this.controlRecupererResultats = controlRecupererResultats;
		this.controlRecupererNouveauResultat = controlRecupererNouveauResultat;
		this.controlRecupererErreursResultats = controlRecupererErreursResultats;
	}
	
	public List<EnsembleResultat> recupererResultats() {
		return controlRecupererResultats.recupererResultats();
	}
	
	public Optional<EnsembleResultat> recupererNouveauResultat() {
		return controlRecupererNouveauResultat.recupererNouveauResultat();
	}
	
	public List<EnsembleResultat> recupererErreursResultats() {
		return controlRecupererErreursResultats.recupererErreursResultats();
	}
	
}
