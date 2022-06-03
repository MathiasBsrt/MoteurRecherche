package modele.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import modele.EnsembleResultatAudio;
import modele.ResultatAudio;
import modele.os.FileHelper;

public class ResultatTableModeleAudio extends AbstractResultatTableModele<ResultatAudio> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604015647503467414L;

	private final String[] entetes = { "Fichier", "Taux de ressemblance", "Temps trouvé", "Montrer", "Ouvrir" };

	public ResultatTableModeleAudio(EnsembleResultatAudio ensembleResultat) {
		super(ensembleResultat);

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
		ResultatAudio resultat = super.getResultat(rowIndex);
		switch (columnIndex) {
		case 0: {
			return resultat.getCheminFichier();
		}
		case 1: {
			return resultat.getTauxRessemblance();
		}
		case 2: {
			return resultat.getTempsTrouve();
		}
		case 3: {
			JButton button = new JButton("Montrer");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					File file = new File(resultat.getCheminFichier());
					FileHelper.openParentDirectory(file);
				}
			});
			return button;
		}
		case 4: {
			JButton button = new JButton("Ouvrir");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					File file = new File(resultat.getCheminFichier());
					FileHelper.openAudioFile(file, resultat.getTempsTrouve());
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
		return columnIndex == 3 || columnIndex == 4;
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return double.class;
		case 2:
			return double.class;
		case 3:
			return JButton.class;
		case 4:
			return JButton.class;
		default:
			return Object.class;
		}
	}

}
