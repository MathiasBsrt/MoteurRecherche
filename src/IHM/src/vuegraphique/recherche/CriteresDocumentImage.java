package vuegraphique.recherche;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.TypeDocument;

public class CriteresDocumentImage extends CriteresDocument {

	private Box boxVertical = Box.createVerticalBox();
	private Box boxTypeRecherche = Box.createHorizontalBox();
	private Box boxCriteres = Box.createHorizontalBox();
	private JLabel lblTitreRecherche = new JLabel("Type de recherche:");

	private JList<TypeRecherche> listTypeRecherche = new JList<>(
			new TypeRecherche[] { TypeRecherche.DOCUMENT, TypeRecherche.CRITERES });

	private JButton btnDocument = new JButton("...");
	private JButton btnCouleur = new JButton("...");
	private File selectedFile;
	private Color color;

	public CriteresDocumentImage() {
		super(TypeDocument.IMAGE);

		boxVertical.add(boxTypeRecherche);
		boxVertical.add(boxCriteres);

		boxTypeRecherche.add(lblTitreRecherche);
		boxTypeRecherche.add(Box.createHorizontalStrut(25));
		boxTypeRecherche.add(Box.createHorizontalGlue());

		listTypeRecherche.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(listTypeRecherche);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		boxTypeRecherche.add(scrollPane);

		// Listener pour la JList demandant
		// le type de recherche (par document ou par critères)
		listTypeRecherche.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting() == false) {
					int selectedIndex = listTypeRecherche.getSelectedIndex();
					if (selectedIndex != -1) {
						btnDocument.setLabel("...");
						selectedFile = null;
						TypeRecherche typeRecherche = TypeRecherche.values()[selectedIndex];
						miseAJourBoxCriteres(typeRecherche);
						boxTypeRecherche.repaint();
						boxTypeRecherche.revalidate();
					}
				}
			}
		});

		btnCouleur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(null, "Choisir une couleur", Color.WHITE);
				if (color != null)
					btnCouleur.setBackground(color);
				btnCouleur.setBorderPainted(false);
			}
		});

		btnDocument.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte d'images", "txt");
				chooser.setFileFilter(filter);
				chooser.setMultiSelectionEnabled(false);
				int returnVal = chooser.showOpenDialog(boxTypeRecherche);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					selectedFile = chooser.getSelectedFile();
					btnDocument.setLabel(chooser.getSelectedFile().getName());
				}
			}
		});
	}

	private void miseAJourBoxCriteres(TypeRecherche typeRecherche) {
		boxCriteres.removeAll();
		switch (typeRecherche) {
		case DOCUMENT: {
			boxCriteres.add(new JLabel("Sélectionner le fichier: "));
			boxCriteres.add(Box.createHorizontalGlue());
			boxCriteres.add(btnDocument);
			break;
		}
		case CRITERES: {
			boxCriteres.add(new JLabel("Sélectionner les couleurs: "));
			boxCriteres.add(Box.createHorizontalGlue());
			boxCriteres.add(btnCouleur);
		}
		}
		boxCriteres.repaint();
		boxCriteres.revalidate();
	}

	@Override
	public Component getSelector() {
		return boxVertical;
	}

	@Override
	public List<String> getCriteres() {
		int selectedIndex = listTypeRecherche.getSelectedIndex();
		TypeRecherche typeRecherche = TypeRecherche.values()[selectedIndex];
		List<String> criteres = new ArrayList<>();
		switch (typeRecherche) {
		case DOCUMENT: {
			if (selectedFile != null) {
				criteres.add(selectedFile.getAbsolutePath());
			}
			break;
		}
		case CRITERES: {
			if (color != null) {
				StringJoiner sj = new StringJoiner(":");
				sj.add(String.valueOf(color.getRed()));
				sj.add(String.valueOf(color.getGreen()));
				sj.add(String.valueOf(color.getBlue()));
				criteres.add(sj.toString());
			}
			break;
		}
		}

		return criteres;
	}

	@Override
	public boolean isTypeRechercheSelectionne() {
		int selectedIndex = listTypeRecherche.getSelectedIndex();
		return selectedIndex != -1;
	}

	@Override
	public TypeRecherche getTypeRecherche() {
		int selectedIndex = listTypeRecherche.getSelectedIndex();
		if (selectedIndex != -1)
			return listTypeRecherche.getSelectedValue();
		return null;
	}

}
