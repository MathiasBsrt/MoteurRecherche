package controleur;

import modele.TypeDocument;

public class ControlDescripteur {

	public void verifierNouveauDescripteur() {
		// Vérificaiton de la présence de nouveaux descripteurs pour chacun des
		// types de document.
		TypeDocument[] typeDocuments = TypeDocument.values();
		for (TypeDocument typeDocument : typeDocuments) {
			verifierNouveauDescripteur(typeDocument);
		}
	}

	private void verifierNouveauDescripteur(TypeDocument typeDocument) {
		// TODO: Interfaçage avec le C pour recharger les descripteurs.
	}

}
