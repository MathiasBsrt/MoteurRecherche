package modele;

import vuegraphique.recherche.TypeRecherche;

public class EnsembleResultatAudio extends EnsembleResultat<ResultatAudio> {

	public EnsembleResultatAudio(Moteur[] moteursUtilises, String fichierOriginal) {
		super(TypeDocument.SON, TypeRecherche.DOCUMENT, moteursUtilises,
				String.format("Se baser sur le fichier '%s'.", fichierOriginal));
	}

}
