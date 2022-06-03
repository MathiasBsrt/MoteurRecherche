package vuegraphique.visualiserresultat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import modele.EnsembleResultat;
import modele.Moteur;
import modele.TypeDocument;
import modele.os.FileHelper;
import modele.table.AbstractResultatTableModele;
import modele.table.ResultatTableFactory;
import vuegraphique.recherche.TypeRecherche;

public class FrameVisualiserResultat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4759304766276865323L;

	private JPanel panAccueil = new JPanel();

	private Box boxInformations = Box.createVerticalBox();

	private JTable table;

	private EnsembleResultat ensembleResultat;

	public FrameVisualiserResultat(EnsembleResultat ensembleResultat) {
		this.ensembleResultat = ensembleResultat;

		TypeDocument typeDocument = ensembleResultat.getTypeDocument();
		int nbResultat = ensembleResultat.getNbResultats();

		String title = String.format("SeekBit - Visualisation d'un resultat %s - Resultat(s): %d",
				typeDocument.toString(), nbResultat);

		setTitle(title);
		setSize(900, 400);

		initialisation();

		setVisible(true);
	}

	public void initialisation() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel texteAccueil = new JLabel("Informations");
		texteAccueil.setFont(new Font("Calibri", Font.BOLD, 24));
		panAccueil.add(texteAccueil);
		panAccueil.setVisible(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.3;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 0;
		add(panAccueil, c);
		boxInformations.add(Box.createVerticalStrut(10));

		initTypeDocument();

		initTypeRecherche();

		initListMoteurs();

		initListCriteres();

		if (ensembleResultat.getTypeRecherche().equals(TypeRecherche.DOCUMENT))
			initBtnOuvrirDocument();

		boxInformations.setBorder(BorderFactory.createTitledBorder("Informations sur les résultats"));

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.7;
		c.weightx = 0.3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(boxInformations, c);

		table = ResultatTableFactory.createTable(ensembleResultat);

		JScrollPane scrollPaneTable = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		c.fill = GridBagConstraints.BOTH;
		c.ipady = 300; // make this component tall
		c.weighty = 0.3;
		c.weightx = 0.7;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(scrollPaneTable, c);

		Dimension tableSize = table.getPreferredSize();
		int tableWidth = tableSize.width;
		table.getColumnModel().getColumn(0).setPreferredWidth((int) (tableWidth * 0.9));
		table.getColumnModel().getColumn(1).setPreferredWidth((int) (tableWidth * 0.1));

	}

	private Component justifierGauche(Component component) {
		Box b = Box.createHorizontalBox();
		b.add(component);
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la droite du label
		return b;
	}

	private void initTypeDocument() {
		Box boxTypeDocument = Box.createHorizontalBox();
		JLabel lblTypeDocument = new JLabel(String.format("Type de documents:"));
		boxTypeDocument.add(lblTypeDocument);
		boxTypeDocument.add(Box.createHorizontalStrut(50));
		boxTypeDocument.add(new JLabel(ensembleResultat.getTypeDocument().toString()));
		boxInformations.add(justifierGauche(boxTypeDocument));
		boxInformations.add(Box.createVerticalStrut(10));
	}

	private void initTypeRecherche() {
		Box boxTypeRecherche = Box.createHorizontalBox();
		JLabel lblTypeRecherche = new JLabel(String.format("Type de recherche:"));
		boxTypeRecherche.add(lblTypeRecherche);
		boxTypeRecherche.add(Box.createHorizontalStrut(50));
		boxTypeRecherche.add(new JLabel(ensembleResultat.getTypeRecherche().toString()));
		boxInformations.add(justifierGauche(boxTypeRecherche));
		boxInformations.add(Box.createVerticalStrut(10));
	}

	private void initListMoteurs() {
		Box boxMoteurs = Box.createHorizontalBox();
		JLabel lblMoteurs = new JLabel("Liste des moteurs utilisés:");
		boxMoteurs.add(lblMoteurs);
		boxMoteurs.add(Box.createHorizontalStrut(50));
		JList<Moteur> listMoteurs = new JList<>(ensembleResultat.getMoteurs());
		listMoteurs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listMoteurs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listMoteurs.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(listMoteurs);
		listScroller.setPreferredSize(new Dimension(100, 80));
		boxMoteurs.add(listScroller);
		boxInformations.add(boxMoteurs);
		boxInformations.add(Box.createVerticalStrut(10));

		listMoteurs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				List<Moteur> moteursSelectionnes = listMoteurs.getSelectedValuesList();
				AbstractResultatTableModele modele = (AbstractResultatTableModele) table.getModel();
				modele.retirerToutLesMoteurs();
				modele.montrerMoteurs(moteursSelectionnes);
				table.repaint();
				table.revalidate();
			}
		});
	}

	private void initListCriteres() {
		Box boxCriteres = Box.createHorizontalBox();
		JLabel lblCriteres = new JLabel("Liste des critères:");
		boxCriteres.add(lblCriteres);
		boxCriteres.add(Box.createHorizontalStrut(50));
		String[] criteres = ensembleResultat.getCriteres();
		JList list = JListTypeRechercheFactory.createJList(ensembleResultat);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		JScrollPane listCriteresScroller = new JScrollPane(list);
		listCriteresScroller.setPreferredSize(new Dimension(100, 80));
		boxCriteres.add(listCriteresScroller);
		boxInformations.add(boxCriteres);
		boxInformations.add(Box.createVerticalStrut(50));
	}

	private void initBtnOuvrirDocument() {
		JButton btn = new JButton("Ouvrir le document");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String cheminFichier = EnsembleResultat.getCheminFichierRechercheDocument(ensembleResultat);
				File file = new File(cheminFichier);
				FileHelper.openParentDirectory(file);
			}
		});
		boxInformations.add(btn);
		boxInformations.add(Box.createVerticalStrut(50));
	}

}
