package vuegraphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import controleur.ControlIndexation;
import modele.TypeDocument;

public class PanIndexer extends JPanel implements IUseChoixTypeIndexation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur
	private ControlIndexation controlIndexation;
	private PanChoixTypeIndexation panChoixTypeIndexation;
	// private ThreadSimulationIndexation threadSimulationIndexation = new
	// ThreadSimulationIndexation();
	// les attributs metiers (ex : numClient)
	private TypeDocument typeDocument;
	private int docOuFich = 0;
	private boolean indexationTermine = false;
	// Les elements graphiques :
	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	private JComboBox<String> comboBoxTypeFichier = new JComboBox<>();
	private JComboBox<String> comboBoxDocOuFich = new JComboBox<>();

	// Declaration et creation des Button
	private JButton validerCriteres = new JButton();
	private JButton terminer = new JButton();
	private Box boxValiderChoix = Box.createVerticalBox();
	// Declaration et creation des TextArea
	JTextArea texteListe = new JTextArea();
	// Declaration et creation des Labels
	JLabel texteIndexation;
	// Mise en page : les Box
	private Box boxMiseEnPage = Box.createVerticalBox();
	private Box boxChoixTypeFichier = Box.createHorizontalBox();
	private Box boxChoixDocOuFich = Box.createHorizontalBox();

	private Box boxMiseEnPageIndexation = Box.createVerticalBox();
	private Box boxListeIndexes = Box.createHorizontalBox();
	private Box boxTerminer = Box.createHorizontalBox();
	private FrameApp frameApp;

	public PanIndexer(
			// parametres pour l�initialisation des attributs m�tiers
			// parametres correspondants au controleur du cas + panel des cas inclus ou
			// etendus
			// en relation avec un acteur
			ControlIndexation controlIndexation, PanChoixTypeIndexation panChoixTypeIndexation) {
		// initialisation des attributs metiers
		// initilaisation du controleur du cas + panels
		// des cas inclus ou etendus en lien avec un acteur
		this.controlIndexation = controlIndexation;
		this.panChoixTypeIndexation = panChoixTypeIndexation;
	}

	// M�thode d�initialisation du panel
	public void initialisation(FrameApp frameApp) {
		this.frameApp = frameApp;
		// mise en forme du panel (couleur, �)
		setBackground(Color.WHITE);
		// creation des diff�rents elements graphiques (JLabel, Combobox, Button,
		// TextAera �)

		texteListe.setRows(20);
		texteListe.setColumns(20);
		texteListe.setLineWrap(true);
		texteListe.setEditable(false);
		JScrollPane scroll = new JScrollPane(texteListe);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		texteIndexation = new JLabel("Indexation en cours...");
		texteIndexation.setFont(policeTitre);
		JLabel texteIndexer = new JLabel("Paramètres d'indexation");
		texteIndexer.setFont(policeTitre);
		texteIndexer.setHorizontalAlignment(JLabel.CENTER);

		JLabel texteTypeFichier = new JLabel("Quel type de document voulez-vous indexer ?");
		texteTypeFichier.setFont(policeParagraphe);
		JLabel texteDocOuFich = new JLabel("Voulez vous indexer un fichier ou un dossier ?");
		texteDocOuFich.setFont(policeParagraphe);
		comboBoxDocOuFich.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				docOuFich = comboBoxDocOuFich.getSelectedIndex();
			}
		});

		comboBoxTypeFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int typeFichier = comboBoxTypeFichier.getSelectedIndex();
				switch (typeFichier) {
				case 1:
					typeDocument = TypeDocument.SON;
					break;
				case 2:
					typeDocument = TypeDocument.IMAGE;
					break;
				case 3:
					typeDocument = TypeDocument.TEXTE;
					break;
				default:
					typeDocument = null;
					break;
				}
			}
		});

		validerCriteres.setText("Valider");
		validerCriteres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeDocument != null && docOuFich != 0) {
					selectionFichierDossier(typeDocument, docOuFich);
				}
			}

		});
		terminer.setText("Terminer");
		terminer.setVisible(false);
		terminer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (indexationTermine == true) {
					terminer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					fin();

				}
			}
		});
		// mise en page : placements des differents elements graphiques dans des Box
		boxMiseEnPage.add(Box.createHorizontalGlue());
		boxChoixTypeFichier.add(Box.createHorizontalGlue());
		boxValiderChoix.add(Box.createHorizontalGlue());
		boxValiderChoix.add(Box.createHorizontalGlue());

		boxMiseEnPage.add(justifierCenter(texteIndexer));
		boxMiseEnPage.add(Box.createRigidArea(new Dimension(0, 30)));
		boxMiseEnPage.add(Box.createRigidArea(new Dimension(10, 0)));
		boxChoixTypeFichier.add(texteTypeFichier);
		boxChoixTypeFichier.add(Box.createRigidArea(new Dimension(0, 30)));
		boxChoixTypeFichier.add(Box.createRigidArea(new Dimension(10, 0)));

		boxChoixDocOuFich.add(texteDocOuFich);
		boxChoixDocOuFich.add(Box.createRigidArea(new Dimension(0, 30)));
		boxChoixDocOuFich.add(Box.createRigidArea(new Dimension(10, 0)));

		boxMiseEnPage.add(boxChoixTypeFichier);
		boxMiseEnPage.add(boxChoixDocOuFich);
		boxValiderChoix.add(validerCriteres);

		boxTerminer.add(terminer);
		boxMiseEnPageIndexation.add(texteIndexation);
		boxMiseEnPageIndexation.add(Box.createRigidArea(new Dimension(0, 30)));
		boxMiseEnPageIndexation.add(Box.createRigidArea(new Dimension(10, 0)));
		boxMiseEnPageIndexation.add(scroll);
		boxMiseEnPageIndexation.add(Box.createRigidArea(new Dimension(0, 30)));
		boxMiseEnPageIndexation.add(Box.createRigidArea(new Dimension(10, 0)));
		boxMiseEnPageIndexation.add(boxListeIndexes);
		boxMiseEnPageIndexation.add(Box.createRigidArea(new Dimension(0, 30)));
		boxMiseEnPageIndexation.add(Box.createRigidArea(new Dimension(10, 0)));
		boxMiseEnPageIndexation.add(boxTerminer);
		// mise en page : placements des differentes box dans une box principale
		boxChoixDocOuFich.add(comboBoxDocOuFich);
		boxChoixTypeFichier.add(comboBoxTypeFichier);
		boxMiseEnPage.add(boxValiderChoix);
		this.add(boxMiseEnPageIndexation);
		boxMiseEnPageIndexation.setVisible(false);
		// mise en page : ajout de la box principale dans le panel
		this.add(boxMiseEnPage);
	}

	// Methode correspondante au nom du cas
	public void indexer( /* parametres metiers */ ) {
		this.terminer.setVisible(false);
		this.texteListe.setText("");
		this.boxMiseEnPage.setVisible(true);
		this.boxMiseEnPageIndexation.setVisible(false);
		affichageMenu();
	}

	// Methodes priv�es pour le bon deroulement du cas
	private void affichageMenu() {
		comboBoxDocOuFich.removeAllItems();
		comboBoxDocOuFich.addItem("");
		comboBoxDocOuFich.addItem("Fichier seul");
		comboBoxDocOuFich.addItem("Dossier complet");

		comboBoxTypeFichier.removeAllItems();
		comboBoxTypeFichier.addItem("");
		comboBoxTypeFichier.addItem("Son");
		comboBoxTypeFichier.addItem("Image");
		comboBoxTypeFichier.addItem("Texte");

	}

	@Override
	public void retourChoixTypeIndexation(boolean indexation, String chemin) {
		this.panChoixTypeIndexation.setVisible(false);
		indexation(chemin);

	}

	private void selectionFichierDossier(TypeDocument typeDocument, int docOuFich) {
		boxMiseEnPage.setVisible(false);
		panChoixTypeIndexation.setVisible(true);
		this.repaint();
		panChoixTypeIndexation.choixTypeIndexation(typeDocument, docOuFich, this);
		;
	}

	private void indexation(String chemin) {
		Timer chrono = new Timer();
		this.setVisible(true);
		boxMiseEnPage.setVisible(false);
		boxMiseEnPageIndexation.setVisible(true);
		this.repaint();
		if (docOuFich == 1) {
			String fichier = controlIndexation.lancerIndexationFichier(typeDocument, chemin);
			texteListe.append(fichier);
			terminer.setVisible(true);
			texteIndexation.setText("Indexation terminée");
			this.indexationTermine = true;

		} else {
			ListIterator<String> fichiers = controlIndexation.lancerIndexationDossier(typeDocument, chemin)
					.listIterator();
			chrono.schedule(new TimerTask() {
				@Override
				public void run() {
					if (fichiers.hasNext()) {
						texteListe.append(fichiers.next() + "\n");
					} else {
						texteIndexation.setText("Indexation terminée");
						terminer.setVisible(true);
						indexationTermine = true;

						cancel();
					}

				}
			}, 300, 300);

		}
	}

	private void fin() {
		frameApp.afficherMenuAdministrateur();

	}

	private Component justifierCenter(JLabel label) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue()); // On rajoute simplement une box sur la gauche et sur la droite du label
		b.add(label);
		b.add(Box.createHorizontalGlue());
		return b;
	}

}
