package modele;

import vuegraphique.recherche.TypeRecherche;

public class EnsembleResultatImage extends EnsembleResultat<ResultatImage> {

	public EnsembleResultatImage(Moteur[] moteursUtilises, String fichierOriginal) {
		super(TypeDocument.IMAGE, TypeRecherche.DOCUMENT, moteursUtilises,
				String.format("Se baser sur le fichier '%s'.", fichierOriginal));
	}

	public EnsembleResultatImage(Moteur[] moteursUtilises, String... couleurs) {
		super(TypeDocument.IMAGE, TypeRecherche.CRITERES, moteursUtilises, couleurs);
	}

}
