package modele.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import modele.EnsembleResultatTexte;
import modele.ResultatTexte;
import modele.os.FileHelper;
import vuegraphique.recherche.TypeRecherche;

public class ResultatTableModeleTexte extends AbstractResultatTableModele<ResultatTexte> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604015647503467414L;

	private String[] entetes = { "Fichier", "Taux de ressemblance", "Montrer", "Ouvrir" };

	private boolean isRechercheDocument = true;

	public ResultatTableModeleTexte(EnsembleResultatTexte ensembleResultat) {
		super(ensembleResultat);

		isRechercheDocument = ensembleResultat.getTypeRecherche().equals(TypeRecherche.DOCUMENT);

		if (!isRechercheDocument)
			entetes[1] = "Nb occurence";

	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ResultatTexte resultat = super.getResultat(rowIndex);
		switch (columnIndex) {
		case 0: {
			return resultat.getCheminFichier();
		}
		case 1: {
			if (isRechercheDocument)
				return resultat.getTauxRessemblance();
			else {
				return resultat.toArray();
			}
		}

		case 2: {
			JButton button = new JButton("Montrer");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					File file = new File(resultat.getCheminFichier());
					FileHelper.openParentDirectory(file);
				}
			});
			return button;
		}
		case 3: {
			JButton button = new JButton("Ouvrir");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					File file = new File(resultat.getCheminFichier());
					FileHelper.openTexteFile(file);
				}
			});
			return button;
		}
		default: {
			return "Non reconnu";
		}
		}
	}

//	private HashMap<String, Integer> criteresToHashMap(String[] criteres) {
//		HashMap<String, Integer> occurences = new HashMap<>();
//		for (int i = 0; i < criteres.length; i++) {
//			String value = criteres[i];
//			String[] splited = value.split(":");
//			String key = splited[0];
//			int nbOccurence = Integer.valueOf(splited[1]);
//			occurences.put(key, nbOccurence);
//		}
//		return occurences;
//	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1 || columnIndex == 2 || columnIndex == 3;
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			if (isRechercheDocument)
				return double.class;
			else
				return String[].class;
		case 2:
			return JButton.class;
		case 3:
			return JButton.class;
		default:
			return Object.class;
		}
	}

}
