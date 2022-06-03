package modele;

import vuegraphique.recherche.TypeRecherche;

public class EnsembleResultatTexte extends EnsembleResultat<ResultatTexte> {

	public EnsembleResultatTexte(Moteur[] moteursUtilises, String fichierOriginal) {
		super(TypeDocument.TEXTE, TypeRecherche.DOCUMENT, moteursUtilises,
				String.format("Se baser sur le fichier '%s'.", fichierOriginal));
	}

	public EnsembleResultatTexte(Moteur[] moteursUtilises, String... criteres) {
		super(TypeDocument.TEXTE, TypeRecherche.CRITERES, moteursUtilises, criteres);
	}

}
