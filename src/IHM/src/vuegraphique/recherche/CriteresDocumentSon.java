package vuegraphique.recherche;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.TypeDocument;

public class CriteresDocumentSon extends CriteresDocument {

	private Box boxVertical = Box.createVerticalBox();
	private Box boxTypeRecherche = Box.createHorizontalBox();
	private Box boxCriteres = Box.createHorizontalBox();
	private JLabel lblTitreRecherche = new JLabel("Type de recherche:");

	private JList<TypeRecherche> listTypeRecherche = new JList<>(new TypeRecherche[] { TypeRecherche.DOCUMENT });

	private JButton btnDocument = new JButton("...");
	private File selectedFile;

	public CriteresDocumentSon() {
		super(TypeDocument.SON);

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
						miseAJourBoxCriteres();
						boxTypeRecherche.repaint();
						boxTypeRecherche.revalidate();
					}
				}
			}
		});

		btnDocument.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers audios", "wav");
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

	private void miseAJourBoxCriteres() {
		boxCriteres.removeAll();
		boxCriteres.add(new JLabel("Sélectionner le fichier: "));
		boxCriteres.add(Box.createHorizontalGlue());
		boxCriteres.add(btnDocument);
	}

	@Override
	public Component getSelector() {
		return boxVertical;
	}

	@Override
	public List<String> getCriteres() {
		List<String> criteres = new ArrayList<>();
		if (selectedFile != null) {
			criteres.add(selectedFile.getAbsolutePath());
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
