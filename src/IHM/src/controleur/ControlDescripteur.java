package controleur;

import modele.TypeDocument;

public class ControlDescripteur {

	public void verifierNouveauDescripteur() {
		// V�rificaiton de la pr�sence de nouveaux descripteurs pour chacun des
		// types de document.
		TypeDocument[] typeDocuments = TypeDocument.values();
		for (TypeDocument typeDocument : typeDocuments) {
			verifierNouveauDescripteur(typeDocument);
		}
	}

	private void verifierNouveauDescripteur(TypeDocument typeDocument) {
		// TODO: Interfa�age avec le C pour recharger les descripteurs.
	}

}
