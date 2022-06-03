package vuegraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.ControlAdmin;

public class PanMenuAdministrateur extends JPanel {
	// controleurs du cas + panel des cas inclus ou etendus en lien avec un acteur

	private ControlAdmin controladmin;

	// les attributs metiers (ex : numClient)

	// Les elements graphiques :

	// private PanIndexer panIndexer;
	private PanParamSeuil panParamSeuil;

	// Declaration et creation des polices d'ecritures
	private Font policeTitre = new Font("Calibri", Font.BOLD, 24);
	private Font policeParagraphe = new Font("Calibri", Font.HANGING_BASELINE, 16);

	// Declaration et creation des ComboBox
	// Declaration et creation des Button

	private JButton retour = new JButton();
	private JButton deconnexion = new JButton();

	// Declaration et creation des TextArea
	// Declaration et creation des Labels

	// Mise en page : les Box
	private Box boxMiseEnpage = Box.createVerticalBox();
	private Box boxCentre = Box.createHorizontalBox();
	private Box boxTitre = Box.createHorizontalBox();
	private Box boxRetour = Box.createHorizontalBox();

	private FrameApp frame;

	public PanMenuAdministrateur(ControlAdmin controlAdmin) {
		this.controladmin = controlAdmin;
	}

	// Methode d'initialisation du panel
	public void initialisation(FrameApp frame) {
		// mise en forme du panel (couleur, ...)
		// creation des differents elements graphiques (JLabel, Combobox, Button,
		// TextAera ...)
		this.frame = frame;
		JLabel titre = new JLabel("Menu Administrateur");
		titre.setFont(policeTitre);
		titre.setHorizontalAlignment(JLabel.CENTER);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(titre, BorderLayout.PAGE_START);
		boxTitre.add(Box.createHorizontalGlue());
		boxTitre.add(Box.createRigidArea(new Dimension(0, 200)));

		JButton boutonindexer = new JButton("Indexer");
		boutonindexer.setPreferredSize(new Dimension(300, 100));
		boutonindexer.setMaximumSize(new Dimension(300, 100));
		JButton boutonparam = new JButton("Paramétrer les seuils");
		boutonparam.setPreferredSize(new Dimension(300, 100));
		boutonparam.setMaximumSize(new Dimension(300, 100));
		boxCentre.add(boutonindexer);
		boxCentre.add(Box.createRigidArea(new Dimension(100, 0)));
		boxCentre.add(boutonparam);
		boxRetour.add(retour);
		boxRetour.add(deconnexion);
		boxMiseEnpage.add(boxTitre);
		boxMiseEnpage.add(boxCentre);
		boxMiseEnpage.add(boxRetour);
		add(boxMiseEnpage);

		boutonindexer.setText("Indexer");
		boutonindexer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.afficherIndexer();
			}
		});

		boutonparam.setText("Paramétrer les seuils");
		boutonparam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.afficherParamSeui();
			}
		});

		retour.setText("Retour");
		retour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.afficherMenuPrincipal();
			}

		});

		deconnexion.setText("Deconnexion");
		deconnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controladmin.deconnexion();
				frame.afficherMenuPrincipal();
			}

		});

	}

}