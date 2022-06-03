package modele;

import java.util.ArrayList;
import java.util.List;

public class BDResultat {

	List<EnsembleResultat> resultats = new ArrayList<EnsembleResultat>();
	EnsembleResultat nouveauResultat;
	List<EnsembleResultat> erreursResultats = new ArrayList<EnsembleResultat>();

	private static class BDResultatHolder {
		public static BDResultat INSTANCE = new BDResultat();
	}

	public static BDResultat getInstance() {
		return BDResultatHolder.INSTANCE;
	}

	public List<EnsembleResultat> getListeResultats() {
		return resultats;
	}

	public EnsembleResultat getNouveauResultats() {
		return nouveauResultat;
	}

	public List<EnsembleResultat> getListeErreurs() {
		return erreursResultats;
	}

	public void enregistrerResultats(EnsembleResultat ensembleResultat) {
		if (ensembleResultat.getNbResultats() == 0)
			erreursResultats.add(ensembleResultat);
		else
			resultats.add(ensembleResultat);
		nouveauResultat = ensembleResultat;
	}

}
