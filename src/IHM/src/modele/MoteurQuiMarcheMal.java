package modele;

public class MoteurQuiMarcheMal extends Moteur {

	public MoteurQuiMarcheMal() {
		super("Moteur QuiMarcheMal", 99);
	}

	@Override
	public EnsembleResultat rechercheParDocument(String cheminFichier, TypeDocument typeDocument,
			ParametreRecherche param) {
		// Simulation des r�sultats pour un recherche par document.
		switch (typeDocument) {
		case IMAGE: {
			return simulerResultatImage(cheminFichier);
		}
		case SON: {
			return simulerResultatSon(cheminFichier);
		}
		default: {
			return simulerResultatTexte(cheminFichier);
		}
		}
	}

	@Override
	public EnsembleResultat rechercheParCritere(TypeDocument typeDocument, String[] criteres,
			ParametreRecherche param) {
		switch (typeDocument) {
		// Simulation des r�sultats pour un recherche par crit�res.
		case IMAGE: {
			return simulerResultatImage(criteres);
		}
		case SON: {
			return simulerResultatSon(criteres);
		}
		default: {
			return simulerResultatTexte(criteres);
		}
		}
	}

	private EnsembleResultat simulerResultatSon(String cheminFichier) {
		EnsembleResultatAudio ensembleResultat = new EnsembleResultatAudio(new Moteur[] { this }, cheminFichier);
		ensembleResultat.add(new ResultatAudio("chemin/fichier/1.wav", 10, this));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/2.wav", 7, this));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/3.wav", 6, this));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/4.wav", Math.PI / 10, this));
		return ensembleResultat;
	}

	private EnsembleResultat simulerResultatSon(String[] criteres) {
		EnsembleResultatAudio ensembleResultat = new EnsembleResultatAudio(new Moteur[] { this }, criteres[0]);
		ensembleResultat.add(new ResultatAudio("chemin/fichier/1.wav", 10, this));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/2.wav", 7, this));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/3.wav", 6, this));
		ensembleResultat.add(new ResultatAudio("chemin/fichier/4.wav", Math.PI / 10, this));
		return ensembleResultat;
	}

	private EnsembleResultat simulerResultatImage(String cheminFichier) {
		EnsembleResultatImage ensembleResultat = new EnsembleResultatImage(new Moteur[] { this }, cheminFichier);
		ensembleResultat.add(new ResultatImage("chemin/fichier/1.jpg", 10, this));
		ensembleResultat.add(new ResultatImage("chemin/fichier/2.bmp", 7, this));
		ensembleResultat.add(new ResultatImage("chemin/fichier/3.bmp", 6, this));
		ensembleResultat.add(new ResultatImage("chemin/fichier/4.jpg", Math.PI / 10, this));
		return ensembleResultat;
	}

	private EnsembleResultat simulerResultatImage(String[] criteres) {
		EnsembleResultatImage ensembleResultat = new EnsembleResultatImage(new Moteur[] { this }, criteres);
		ensembleResultat.add(new ResultatImage("chemin/fichier/1.jpg", 10, this));
		ensembleResultat.add(new ResultatImage("chemin/fichier/2.bmp", 7, this));
		ensembleResultat.add(new ResultatImage("chemin/fichier/3.bmp", 6, this));
		ensembleResultat.add(new ResultatImage("chemin/fichier/4.jpg", Math.PI / 10, this));
		return ensembleResultat;
	}

	private EnsembleResultat simulerResultatTexte(String cheminFichier) {
		EnsembleResultatTexte ensembleResultat = new EnsembleResultatTexte(new Moteur[] { this }, cheminFichier);
		ensembleResultat.add(new ResultatTexte("chemin/fichier/1.txt", 10, this));
		ensembleResultat.add(new ResultatTexte("chemin/fichier/2.txt", 7, this));
		ensembleResultat.add(new ResultatTexte("chemin/fichier/3.txt", 6, this));
		ensembleResultat.add(new ResultatTexte("chemin/fichier/4.txt", Math.PI / 10, this));
		return ensembleResultat;
	}

	private EnsembleResultat simulerResultatTexte(String[] criteres) {
		EnsembleResultatTexte ensembleResultat = new EnsembleResultatTexte(new Moteur[] { this }, criteres);
		ensembleResultat.add(new ResultatTexte("chemin/fichier/1.txt", 10, this));
		ensembleResultat.add(new ResultatTexte("chemin/fichier/2.txt", 7, this));
		ensembleResultat.add(new ResultatTexte("chemin/fichier/3.txt", 6, this));
		ensembleResultat.add(new ResultatTexte("chemin/fichier/4.txt", Math.PI / 10, this));
		return ensembleResultat;
	}

}
