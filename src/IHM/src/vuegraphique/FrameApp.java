package vuegraphique;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.ControlAdmin;
import controleur.ControlIndexation;
import controleur.ControlParamSeuil;
import controleur.ControlRecherche;
import controleur.ControlVisualiserResultat;

public class FrameApp extends JFrame {

	private MenuBar menuDebuggage = new MenuBar();

	private PanMenuPrincipal panAccueil;
	private JPanel panContents = new JPanel();
	private CardLayout cardLayout = new CardLayout();

	private PanParamSeuil panParamSeuil;
	private PanMenuUtilisateur panMenuUtilisateur = new PanMenuUtilisateur();
	private PanRecherche panRecherche;
	private PanVisualiserResultat panVisualiserResultat;
	private PanCreationCompteAdministrateur panCreationCompteAdministrateur;
	private PanIdentificationAdministrateur panIdentificationAdministrateur;
	private PanMenuAdministrateur panMenuAdministrateur;
	private PanChoixTypeIndexation panChoixTypeIndex;
	private PanIndexer panIndexer;

	public FrameApp(ControlAdmin cAdmin, ControlRecherche controlRecherche, ControlParamSeuil cParamSeuil,
			ControlVisualiserResultat controlVisualiserResultat, ControlIndexation controlIndexation) {
		setTitle("SeekBit");
		setSize(1080, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panAccueil = new PanMenuPrincipal(cAdmin);
		panParamSeuil = new PanParamSeuil(cParamSeuil);
		panRecherche = new PanRecherche(controlRecherche);
		panVisualiserResultat = new PanVisualiserResultat(controlVisualiserResultat);

		panCreationCompteAdministrateur = new PanCreationCompteAdministrateur(cAdmin);
		panMenuAdministrateur = new PanMenuAdministrateur(cAdmin);
		panIdentificationAdministrateur = new PanIdentificationAdministrateur(cAdmin);

		panChoixTypeIndex = new PanChoixTypeIndexation(controlIndexation);
		panIndexer = new PanIndexer(controlIndexation, panChoixTypeIndex);

		panContents.setLayout(cardLayout);
		panContents.add(panParamSeuil, "PARAM_SEUIL");
		panContents.add(panMenuUtilisateur, "MENU_UTILISATEUR");
		panContents.add(panRecherche, "RECHERCHE");
		panContents.add(panCreationCompteAdministrateur, "MENU_CREATION_COMPTE_ADMINISTRATEUR");
		panContents.add(panIdentificationAdministrateur, "IDENTIFICATION_ADMINISTRATEUR");
		panContents.add(panMenuAdministrateur, "MENU_ADMINISTRATEUR");
		panContents.add(panChoixTypeIndex, "CHOIX_INDEXATION");
		panContents.add(panIndexer, "INDEXER");
		panContents.add(panVisualiserResultat, "VISUALISER_RESULTAT");

		getContentPane().add(panContents);

		initialisationAcceuil();

		initialisationPanels();

		initialisationMenus();

		setVisible(true);
	}

	public void initialisationPanels() {
		panParamSeuil.initialisation(this);
		panParamSeuil.setAlignmentX(Component.CENTER_ALIGNMENT);
		panMenuUtilisateur.initialisation(this);
		panRecherche.initialisation(this);
		panCreationCompteAdministrateur.initialisation(this);
		panIdentificationAdministrateur.initialisation(this);
		panMenuAdministrateur.initialisation(this);
		panIndexer.initialisation(this);
		panChoixTypeIndex.initialisation(this);
		panVisualiserResultat.initialisation(this);
	}

	public void initialisationAcceuil() {
		panAccueil.initialisation(this);
		panContents.add(panAccueil, "ECRAN_ACCUEIL");
		cardLayout.show(panContents, "ECRAN_ACCUEIL");

	}

	public void initialisationMenus() {

		MenuItem itemAcceuil = new MenuItem("Acceuil");
		itemAcceuil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(panContents, "ECRAN_ACCUEIL");
			}
		});

		MenuItem itemParamSeuils = new MenuItem("Paramètrage recherche");
		itemParamSeuils.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(panContents, "PARAM_SEUIL");
			}
		});

		MenuItem menuUtilisateur = new MenuItem("Menu Utilisateur");
		menuUtilisateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panMenuUtilisateur.menuUtilisateur();
				cardLayout.show(panContents, "MENU_UTILISATEUR");
			}
		});

		MenuItem menuRecherche = new MenuItem("Recherche");
		menuRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panRecherche.recherche();
				cardLayout.show(panContents, "RECHERCHE");
			}
		});

		MenuItem menuIdentificationAdmin = new MenuItem("IdentificationAdmin");
		menuIdentificationAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panRecherche.recherche();
				cardLayout.show(panContents, "IDENTIFICATION_ADMINISTRATEUR");
			}
		});

		MenuItem menuAdministrateur = new MenuItem("Menu Administrateur ");
		menuAdministrateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panRecherche.recherche();
				cardLayout.show(panContents, "MENU_ADMINISTRATEUR");
			}
		});

		MenuItem menuCreationCompteAdministrateur = new MenuItem("Menu creation compte administrateur ");
		menuCreationCompteAdministrateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panRecherche.recherche();
				cardLayout.show(panContents, "MENU_CREATION_COMPTE_ADMINISTRATEUR");
			}
		});

		MenuItem menuVisualiserResultat = new MenuItem("Visualiser Résultat");
		menuVisualiserResultat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				panVisualiserResultat.visualiserResultat();
				cardLayout.show(panContents, "VISUALISER_RESULTAT");
			}
		});

	}

	public void afficherMenuAdministrateur() {
		cardLayout.show(panContents, "MENU_ADMINISTRATEUR");
	}

	public void afficherMenuUtilisateur() {
		panMenuUtilisateur.menuUtilisateur();
		cardLayout.show(panContents, "MENU_UTILISATEUR");
	}

	public void afficherIndexer() {
		panIndexer.indexer();
		cardLayout.show(panContents, "INDEXER");
	}

	public void afficherParamSeui() {
		panParamSeuil.paramSeuil();
		cardLayout.show(panContents, "PARAM_SEUIL");
	}

	public void afficherIdentificationAdministrateur() {
		cardLayout.show(panContents, "IDENTIFICATION_ADMINISTRATEUR");
	}

	public void afficherMenuPrincipal() {
		cardLayout.show(panContents, "ECRAN_ACCUEIL");
	}

	public void afficherCreationCompteAdministrateur() {
		panCreationCompteAdministrateur.creationCompteAdministrateur();
		cardLayout.show(panContents, "MENU_CREATION_COMPTE_ADMINISTRATEUR");
	}

	public void afficherMenuRecherche() {
		panRecherche.recherche();
		cardLayout.show(panContents, "RECHERCHE");
	}

	public void afficherMenuVisualiserResultat() {
		panVisualiserResultat.visualiserResultat();
		cardLayout.show(panContents, "VISUALISER_RESULTAT");
	}

	@Override
	public void pack() {
		if ((getExtendedState() & Frame.MAXIMIZED_BOTH) != Frame.MAXIMIZED_BOTH)
			super.pack();
	}

}
