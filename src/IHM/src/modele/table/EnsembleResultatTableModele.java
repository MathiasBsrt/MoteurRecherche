package modele.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import modele.BDMoteur;
import modele.BDResultat;
import modele.EnsembleResultat;
import modele.Moteur;
import modele.TypeDocument;
import vuegraphique.recherche.TypeRecherche;
import vuegraphique.visualiserresultat.FrameVisualiserResultat;

public class EnsembleResultatTableModele extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604015647503467414L;

	private final String[] entetes = { "Nom (éditable)", "Type de document", "Type de recherche",
			"Moteur(s) utilisé(s)", "Nb résultat", "Critères", "Date", "" };

	private List<EnsembleResultat> listEnsembleResultat = new ArrayList<>();

	private List<Moteur> moteursAMontrer = new ArrayList<>();

	private List<EnsembleResultat> ensembleResultatsAAfficher = new ArrayList<>();

	private BDResultat bdResultat = BDResultat.getInstance();
	private BDMoteur bdMoteur = BDMoteur.getInstance();

	public EnsembleResultatTableModele() {
		List<EnsembleResultat> resultats = bdResultat.getListeResultats();
		List<EnsembleResultat> erreurs = bdResultat.getListeErreurs();
		listEnsembleResultat.addAll(resultats);
		listEnsembleResultat.addAll(erreurs);

		moteursAMontrer = bdMoteur.getListeMoteur();
	}

	public void actualiser() {
		listEnsembleResultat.clear();
		List<EnsembleResultat> resultats = bdResultat.getListeResultats();
		List<EnsembleResultat> erreurs = bdResultat.getListeErreurs();
		listEnsembleResultat.addAll(resultats);
		listEnsembleResultat.addAll(erreurs);
		listEnsembleResultat.sort(new Comparator<EnsembleResultat>() {

			@Override
			public int compare(EnsembleResultat e1, EnsembleResultat e2) {
				// Trier du plus récent au plus vieux
				LocalDateTime date1 = e1.getDate();
				LocalDateTime date2 = e2.getDate();
				if (date1.isBefore(date2))
					return 1; // Donc on inverse ici
				else if (date1.isAfter(date2))
					return -1; // Et ici
				else
					return 0;
			}

		});
		actualiserResultatsAAfficher();
	}

	public void montrerMoteur(Moteur moteur) {
		if (!moteursAMontrer.contains(moteur))
			moteursAMontrer.add(moteur);
		actualiserResultatsAAfficher();
	}

	public void montrerMoteurs(List<Moteur> moteurs) {
		for (Moteur moteur : moteurs) {
			if (!moteursAMontrer.contains(moteur))
				moteursAMontrer.add(moteur);
		}
		actualiserResultatsAAfficher();
	}

	public void retirerMoteur(Moteur moteur) {
		moteursAMontrer.remove(moteur);
		actualiserResultatsAAfficher();
	}

	public void retirerToutLesMoteurs() {
		moteursAMontrer.clear();
		ensembleResultatsAAfficher.clear();
	}

	private void actualiserResultatsAAfficher() {
		ensembleResultatsAAfficher.clear();
		for (EnsembleResultat ensembleResultat : listEnsembleResultat) {
			boolean aUtiliserUnMoteur = false;
			for (Moteur moteur : moteursAMontrer) {
				if (ensembleResultat.aUtiliserLeMoteur(moteur)) {
					aUtiliserUnMoteur = true;
					break;
				}
			}

			if (aUtiliserUnMoteur)
				ensembleResultatsAAfficher.add(ensembleResultat);
		}
	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}

	@Override
	public int getRowCount() {
		return ensembleResultatsAAfficher.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EnsembleResultat ensembleResultat = ensembleResultatsAAfficher.get(rowIndex);
		switch (columnIndex) {
		case 0: {
			return ensembleResultat.getNom();
		}
		case 1: {
			return ensembleResultat.getTypeDocument();
		}
		case 2: {
			return ensembleResultat.getTypeRecherche();
		}
		case 3: {
			return ensembleResultat.getMoteurs();
		}
		case 4: {
			return ensembleResultat.getNbResultats();
		}
		case 5: {
			return ensembleResultat.getCriteres();
		}
		case 6: {
			LocalDateTime creationDate = ensembleResultat.getDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
			return creationDate.format(formatter);
		}
		case 7: {
			JButton button = new JButton("En savoir plus");
//			if (ensembleResultat.getNbResultats() > 0) {
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new FrameVisualiserResultat(ensembleResultat);
				}
			});
//			} else
//				button.setEnabled(false);

			return button;
		}
		default: {
			return "Non reconnu";
		}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0 || columnIndex == 3 || columnIndex == 5;
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return TypeDocument.class;
		case 2:
			return TypeRecherche.class;
		case 3:
			return Moteur[].class;
		case 4:
			return int.class;
		case 5:
			return String[].class;
		case 6:
			return String.class;
		case 7:
			return JButton.class;
		default:
			return Object.class;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			EnsembleResultat ensembleResultat = listEnsembleResultat.get(rowIndex);
			String nom = (String) value;
			ensembleResultat.setNom(nom);
		}
	}

}
