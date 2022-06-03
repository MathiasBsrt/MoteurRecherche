package vuegraphique.recherche;

import java.awt.Component;
import java.util.List;

import modele.TypeDocument;

public abstract class CriteresDocument {

	protected TypeDocument typeDocument;

	public CriteresDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

	public abstract Component getSelector();

	public abstract List<String> getCriteres();

	public abstract boolean isTypeRechercheSelectionne();

	public abstract TypeRecherche getTypeRecherche();

	@Override
	public String toString() {
		return typeDocument.toString();
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

}
