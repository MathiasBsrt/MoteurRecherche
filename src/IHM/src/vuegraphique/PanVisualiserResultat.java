package vuegraphique;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controleur.ControlVisualiserResultat;
import modele.BDMoteur;
import modele.BDResultat;
import modele.EnsembleResultat;
import modele.EnsembleResultatAudio;
import modele.EnsembleResultatImage;
import modele.EnsembleResultatTexte;
import modele.Moteur;
import modele.MoteurQuiMarcheMal;
import modele.MoteurSeekBitUnimate;
import modele.ResultatAudio;
import modele.ResultatImage;
import modele.ResultatTexte;
import modele.table.EnsembleResultatTableModele;
import vuegraphique.visualiserresultat.ComboCellEditorCriteres;
import vuegraphique.visualiserresultat.ComboCellEditorMoteur;
import vuegraphique.visualiserresultat.JTableButtonMouseListener;
import vuegraphique.visualiserresultat.JTableButtonRenderer;

public class PanVisualiserResultat extends JPanel {
	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur
	private ControlVisualiserResultat controlVisualiserResultat;
	// les attributs metiers (ex : numClient)

	// Les elements graphiques :
	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	// Declaration et creation des Button
	// Declaration et creation des TextArea
	// Declaration et creation des Labels

	private EnsembleResultatTableModele tabelModel;
	private JTable table;

	// Mise en page : les Box

	private FrameApp frame;
	private BDMoteur bdMoteur = BDMoteur.getInstance();

	public PanVisualiserResultat(
			// parametres pour l'initialisation des attributs metiers
			// parametres correspondants au controleur du cas + panels
			ControlVisualiserResultat controlVisualiserResultat
	// des cas inclus ou etendus en lien avec un acteur
	) {
		// initialisation des attributs metiers
		// initilaisation du controleur du cas + panels
		this.controlVisualiserResultat = controlVisualiserResultat;
		// des cas inclus ou etendus en lien avec un acteur
	}

	// Methode d'initialisation du panel
	public void initialisation(FrameApp frame) {
		// mise en forme du panel (couleur, ...)
		// creation des differents elements graphiques (JLabel, Combobox, Button,
		// TextAera ...)
		this.frame = frame;

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		/**
		 * SIMULATION D'AJOUT DE RESULTAT DEBUG
		 */
		Moteur moteur1 = new MoteurSeekBitUnimate();
		Moteur moteur2 = new MoteurQuiMarcheMal();
		EnsembleResultatAudio ensembleResultatAudio = new EnsembleResultatAudio(new Moteur[] { moteur1, moteur2 },
				"/home/mathias/pfr/src/module_audio/WAV_FILES/jingle_fi.wav");

		ResultatAudio res12 = new ResultatAudio("/home/mathias/pfr/src/module_audio/WAV_FILES/cymbale.wav", 10, 0,
				moteur2);
		ResultatAudio res2 = new ResultatAudio("/home/mathias/pfr/src/module_audio/WAV_FILES/corpus_fi.wav", 100, 29.3,
				moteur1);
		ResultatAudio res22 = new ResultatAudio("/home/mathias/pfr/src/module_audio/WAV_FILES/corpus_fi.wav", 10, 27.3,
				moteur2);

		ensembleResultatAudio.add(res12);
		ensembleResultatAudio.add(res2);
		ensembleResultatAudio.add(res22);

		BDResultat.getInstance().enregistrerResultats(ensembleResultatAudio);

		EnsembleResultatImage ensembleResultatImage = new EnsembleResultatImage(new Moteur[] { moteur1, moteur2 },
				"0:255:0", "255:0:0");

		ResultatImage resI1 = new ResultatImage("/home/mathias/pfr/src/module_image/tests/TEST_RGB/01.jpg", 100,
				moteur1);
		ResultatImage resI12 = new ResultatImage("/home/mathias/pfr/src/module_image/tests/TEST_RGB/01.jpg", 10,
				moteur2);
		ResultatImage resI22 = new ResultatImage("/home/mathias/pfr/src/module_image/tests/TEST_NB/51.bmp", 100,
				moteur1);
		ResultatImage resI2 = new ResultatImage("/home/mathias/pfr/src/module_image/tests/TEST_NB/51.bmp", 10, moteur2);

		ensembleResultatImage.add(resI1);
		ensembleResultatImage.add(resI12);
		ensembleResultatImage.add(resI2);
		ensembleResultatImage.add(resI22);

		BDResultat.getInstance().enregistrerResultats(ensembleResultatImage);

		EnsembleResultatTexte ensembleResultatTexte = new EnsembleResultatTexte(new Moteur[] { moteur1, moteur2 },
				"stade", "football");

		ResultatTexte resT1 = new ResultatTexte(
				"/home/mathias/pfr/src/module_texte/Textes_UTF8/27-Le_Stade_de_France_s_ouvre_utf8.xml", 100, moteur1);
		resT1.setNbOccurence("football", 50);
		ResultatTexte resT12 = new ResultatTexte(
				"/home/mathias/pfr/src/module_texte/Textes_UTF8/27-Le_Stade_de_France_s_ouvre_utf8.xml", 100, moteur2);
		resT12.setNbOccurence("football", 10);
		ResultatTexte resT2 = new ResultatTexte(
				"/home/mathias/pfr/src/module_texte/Textes_UTF8/28-A_Hossegor,_Kelly_Slater_peut_utf8.xml", 100,
				moteur1);
		resT2.setNbOccurence("Hossegor", 10);
		ResultatTexte resT22 = new ResultatTexte(
				"/home/mathias/pfr/src/module_texte/Textes_UTF8/28-A_Hossegor,_Kelly_Slater_peut_utf8.xml", 100,
				moteur2);
		resT22.setNbOccurence("Hossegor", 1);

		ensembleResultatTexte.add(resT1);
		ensembleResultatTexte.add(resT12);
		ensembleResultatTexte.add(resT2);
		ensembleResultatTexte.add(resT22);

		BDResultat.getInstance().enregistrerResultats(ensembleResultatTexte);

		/**
		 * FIN DEBUG
		 */

		JLabel titre = new JLabel("Visualisation des résultats");
		titre.setFont(policeTitre);
		titre.setHorizontalAlignment(JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(titre, c);

		List<EnsembleResultat> listEnsembleResultat = controlVisualiserResultat.recupererResultats();
		tabelModel = new EnsembleResultatTableModele();
		table = new JTable(tabelModel);
		table.setCellSelectionEnabled(true);

		ComboCellEditorMoteur comboCellEditorMoteur = new ComboCellEditorMoteur();
		table.getColumnModel().getColumn(3).setCellEditor(comboCellEditorMoteur);
		table.getColumnModel().getColumn(3).setCellRenderer(comboCellEditorMoteur);

		ComboCellEditorCriteres comboCellEditorCriteres = new ComboCellEditorCriteres();
		table.getColumnModel().getColumn(5).setCellEditor(comboCellEditorCriteres);
		table.getColumnModel().getColumn(5).setCellRenderer(comboCellEditorCriteres);

		JTableButtonRenderer buttonRenderer = new JTableButtonRenderer();
		table.getColumnModel().getColumn(7).setCellRenderer(buttonRenderer);
		table.addMouseListener(new JTableButtonMouseListener(table));

		table.setRowHeight(20);

		JScrollPane scrollPaneTable = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 300; // make this component tall
		c.weighty = 0.9;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		add(scrollPaneTable, c);

		Box boxBas = Box.createHorizontalBox();
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuUtilisateur();
			}
		});
		btnRetour.setPreferredSize(new Dimension(200, 200));
		btnRetour.setMaximumSize(new Dimension(200, 200));

		Box boxMoteurs = initListMoteurs();

		boxBas.add(Box.createHorizontalStrut(50));
		boxBas.add(btnRetour);
		boxBas.add(Box.createHorizontalStrut(200));
		boxBas.add(boxMoteurs);
		boxBas.add(Box.createHorizontalStrut(50));

		c.fill = GridBagConstraints.NONE;
		c.ipady = 50; // make this component tall
		c.ipadx = 150; // make this component tall
		c.weighty = 0.9;
		c.gridwidth = 3;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 2;
		add(boxBas, c);

		c = new GridBagConstraints();
	}

	private Box initListMoteurs() {
		Box boxMoteurs = Box.createHorizontalBox();
		JLabel lblMoteurs = new JLabel("Trier par moteurs:");
		boxMoteurs.add(lblMoteurs);
		boxMoteurs.add(Box.createHorizontalStrut(50));
		List<Moteur> moteurs = bdMoteur.getListeMoteur();

		JList<Moteur> listMoteurs = new JList(moteurs.toArray());
		listMoteurs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listMoteurs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listMoteurs.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(listMoteurs);
		listScroller.setPreferredSize(new Dimension(2000, 1500));
		boxMoteurs.add(listScroller);

		listMoteurs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				List<Moteur> moteursSelectionnes = listMoteurs.getSelectedValuesList();
				EnsembleResultatTableModele modele = (EnsembleResultatTableModele) table.getModel();
				modele.retirerToutLesMoteurs();
				modele.montrerMoteurs(moteursSelectionnes);
				table.repaint();
				table.revalidate();
			}
		});

		return boxMoteurs;
	}

	// Methode correspondante au nom du cas
	public void visualiserResultat( /* parametres metiers */) {
		tabelModel.actualiser();
		table.repaint();
		table.revalidate();
	}

	// Methodes privees pour le bon deroulement du cas
	private Component justifierGauche(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(label);
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la droite du label
		return b;
	}

	private Component justifierCenter(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la gauche et sur la droite du label
		b.add(label);
		b.add(Box.createHorizontalGlue());
		return b;
	}

}
