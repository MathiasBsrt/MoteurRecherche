package modele;

import java.util.List;

public class ParametreRecherche {

	private ModeRecherche modeRecherche;
	private List<Moteur> moteurs;

	public ParametreRecherche(ModeRecherche modeRecherche, List<Moteur> moteurs) {
		this.modeRecherche = modeRecherche;
		this.moteurs = moteurs;
	}

	public ModeRecherche getModeRecherche() {
		return modeRecherche;
	}

	public List<Moteur> getMoteurs() {
		return moteurs;
	}

}
