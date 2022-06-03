package modele;

public abstract class Resultat {

	protected String cheminFichier;
	protected double tauxRessemblance;
	protected String[] criteres; // Mise � jour lors de l'ajout dans un ensemble de r�sultat
	protected TypeDocument typeDocument;

	protected Moteur moteur;

	public Resultat(String cheminFichier, double tauxRessemblance, TypeDocument typeDocument, Moteur moteur) {
		this.cheminFichier = cheminFichier;
		this.tauxRessemblance = tauxRessemblance;
		this.typeDocument = typeDocument;
		this.moteur = moteur;
	}

	void setCriteres(String... criteres) {
		this.criteres = criteres;
	}

	public String getCheminFichier() {
		return cheminFichier;
	}

	public double getTauxRessemblance() {
		return tauxRessemblance;
	}

	public String[] getCriteres() {
		return criteres;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public Moteur getMoteur() {
		return moteur;
	}

}
