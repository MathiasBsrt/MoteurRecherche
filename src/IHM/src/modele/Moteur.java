package modele;

public abstract class Moteur {

	private String nom;
	private int groupeDev;

	public Moteur(String nom, int groupeDev) {
		this.nom = nom;
		this.groupeDev = groupeDev;
	}

	public String getNom() {
		return nom;
	}

	public int getGroupeDev() {
		return groupeDev;
	}

	public abstract EnsembleResultat rechercheParDocument(String cheminFichier, TypeDocument typeDocument,
			ParametreRecherche param);

	public abstract EnsembleResultat rechercheParCritere(TypeDocument typeDocument, String[] criteres,
			ParametreRecherche param);

	@Override
	public String toString() {
		return String.format("%s (%d)", nom, groupeDev);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Moteur))
			return false;
		Moteur moteur = (Moteur) o;
		return moteur.groupeDev == this.groupeDev && moteur.nom.equals(this.nom);
	}

}
