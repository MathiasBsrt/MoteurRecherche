package modele.table;

import javax.swing.JTable;

import modele.EnsembleResultat;
import modele.EnsembleResultatAudio;
import modele.EnsembleResultatImage;
import modele.EnsembleResultatTexte;
import modele.TypeDocument;
import vuegraphique.recherche.TypeRecherche;
import vuegraphique.visualiserresultat.ComboCellEditorCriteres;
import vuegraphique.visualiserresultat.JTableButtonMouseListener;
import vuegraphique.visualiserresultat.JTableButtonRenderer;

public class ResultatTableFactory {

	public static JTable createTable(EnsembleResultat ensembleResultat) {
		TypeDocument typeDocument = ensembleResultat.getTypeDocument();
		JTable table = null;

		switch (typeDocument) {
		case SON: {
			EnsembleResultatAudio ensembleResultatAudio = (EnsembleResultatAudio) ensembleResultat;
			table = createTableForAudio(ensembleResultatAudio);
			break;
		}
		case IMAGE: {
			EnsembleResultatImage ensembleResultatImage = (EnsembleResultatImage) ensembleResultat;
			table = createTableForImage(ensembleResultatImage);
			break;
		}
		case TEXTE: {
			EnsembleResultatTexte ensembleResultatTexte = (EnsembleResultatTexte) ensembleResultat;
			table = createTableForTexte(ensembleResultatTexte);
			break;
		}
		default: {
			throw new IllegalArgumentException("Le TypeDocument fourni n'est pas encore maintenu.");
		}
		}

		return table;
	}

	private static JTable createTableForAudio(EnsembleResultatAudio ensembleResultat) {
		ResultatTableModeleAudio tableModel = new ResultatTableModeleAudio(ensembleResultat);
		JTable table = new JTable(tableModel);
		table.setCellSelectionEnabled(true);

		JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumnModel().getColumn(3).setCellRenderer(buttonRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(buttonRenderer);
		table.addMouseListener(new JTableButtonMouseListener(table));

		return table;
	}

	private static JTable createTableForImage(EnsembleResultatImage ensembleResultat) {
		ResultatTableModeleImage tableModel = new ResultatTableModeleImage(ensembleResultat);
		JTable table = new JTable(tableModel);
		table.setCellSelectionEnabled(true);

		JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumnModel().getColumn(2).setCellRenderer(buttonRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(buttonRenderer);
		table.addMouseListener(new JTableButtonMouseListener(table));

		return table;
	}

	private static JTable createTableForTexte(EnsembleResultatTexte ensembleResultat) {
		ResultatTableModeleTexte tableModel = new ResultatTableModeleTexte(ensembleResultat);
		JTable table = new JTable(tableModel);
		table.setCellSelectionEnabled(true);

		JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
		ComboCellEditorCriteres comboCellEditorCriteres = new ComboCellEditorCriteres();
		if (ensembleResultat.getTypeRecherche().equals(TypeRecherche.CRITERES)) {
			table.getColumnModel().getColumn(1).setCellEditor(comboCellEditorCriteres);
			table.getColumnModel().getColumn(1).setCellRenderer(comboCellEditorCriteres);
		}
		table.getColumnModel().getColumn(2).setCellRenderer(buttonRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(buttonRenderer);
		table.addMouseListener(new JTableButtonMouseListener(table));

		return table;
	}

}
