package controleur;

import modele.TypeDocument;

public class ControlParamSeuil {
	private int seuilTexte = 10;
	private int seuilAudio = 18;
	private int seuilImage = 5;

	private int nbApparition = 5;

	public void modificationSeuil(TypeDocument typeDoc, int seuil) {
		switch (typeDoc) {
		case SON: // Audio
			// Simulation
			this.seuilAudio = seuil;
			System.out.println("Appel de l'interfaçage fictif pour changer le seuil des documents audio....");
			break;
		case TEXTE: // Texte
			// Simulation
			this.seuilTexte = seuil;
			System.out.println("Appel de l'interfaçage fictif pour changer le seuil des documents texte....");
			break;
		case IMAGE: // Image
			// Simulation
			this.seuilImage = seuil;
			System.out.println("Appel de l'interfaçage fictif pour changer le seuil des documents image....");
			break;

		default:
			break;
		}
	}

	public void modificationNbApparition(TypeDocument typeDoc, int nbApparition) {
		if (typeDoc == TypeDocument.TEXTE) {
			this.nbApparition = nbApparition;
			System.out.println("Appel interfaçage modification nb apparition");
		}

	}

	/**
	 * @return the nbApparition
	 */
	public int getNbApparitionTexte() {
		return nbApparition;
	}

	/**
	 * @param nbApparition the nbApparition to set
	 */
	public void setNbApparitionTexte(int nbApparition) {
		this.nbApparition = nbApparition;
	}

	/**
	 * @return the seuilTexte
	 */
	public int getSeuilTexte() {
		return seuilTexte;
	}

	/**
	 * @param seuil the seuilTexte to set
	 */
	public void setSeuilTexte(int seuil) {
		this.seuilTexte = seuil;
	}

	/**
	 * @return the seuilAudio
	 */
	public int getSeuilAudio() {
		return seuilAudio;
	}

	/**
	 * @param seuilAudio the seuilAudio to set
	 */
	public void setSeuilAudio(int seuilAudio) {
		this.seuilAudio = seuilAudio;
	}

	/**
	 * @return the seuilImage
	 */
	public int getSeuilImage() {
		return seuilImage;
	}

	/**
	 * @param seuilImage the seuilImage to set
	 */
	public void setSeuilImage(int seuilImage) {
		this.seuilImage = seuilImage;
	}
}
