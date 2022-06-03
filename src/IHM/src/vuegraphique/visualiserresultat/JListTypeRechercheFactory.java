package vuegraphique.visualiserresultat;

import javax.swing.JList;

import modele.EnsembleResultat;
import modele.TypeDocument;
import vuegraphique.recherche.TypeRecherche;

public class JListTypeRechercheFactory {

	public static JList createJList(EnsembleResultat ensembleResultat) {
		JList list = null;
		if (ensembleResultat.getTypeRecherche().equals(TypeRecherche.DOCUMENT)) {
			list = new JListDocument(ensembleResultat);
		} else {
			// TypeRecherche CRITERES
			TypeDocument typeDocument = ensembleResultat.getTypeDocument();
			switch (typeDocument) {
			case SON: {
				// IMPOSSIBLE
				break;
			}
			case IMAGE: {
				list = new JListCriteresImage(ensembleResultat);
				break;
			}
			case TEXTE: {
				list = new JListCriteresTexte(ensembleResultat);
				break;
			}
			default:
				throw new IllegalArgumentException("Le TypeDocument fourni n'est pas encore maintenu.");
			}
		}

		return list;

	}

}
