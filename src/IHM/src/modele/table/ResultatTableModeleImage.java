package modele.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import modele.EnsembleResultatImage;
import modele.ResultatImage;
import modele.os.FileHelper;
import vuegraphique.recherche.TypeRecherche;

public class ResultatTableModeleImage extends AbstractResultatTableModele<ResultatImage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604015647503467414L;

	private String[] entetes = { "Fichier", "Taux de ressemblance", "Montrer", "Ouvrir" };

	public ResultatTableModeleImage(EnsembleResultatImage ensembleResultat) {
		super(ensembleResultat);

		if (ensembleResultat.getTypeRecherche().equals(TypeRecherche.CRITERES))
			entetes[1] = "Taux de présence";

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
		ResultatImage resultat = super.getResultat(rowIndex);
		switch (columnIndex) {
		case 0: {
			return resultat.getCheminFichier();
		}
		case 1: {
			return resultat.getTauxRessemblance();
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
					FileHelper.openImageFile(file);
				}
			});
			return button;
		}
		default: {
			return "Non reconnu";
		}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 2 || columnIndex == 3;
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return double.class;
		case 2:
			return JButton.class;
		case 3:
			return JButton.class;
		default:
			return Object.class;
		}
	}

}
