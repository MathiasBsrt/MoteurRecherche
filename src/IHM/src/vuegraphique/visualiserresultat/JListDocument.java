package vuegraphique.visualiserresultat;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JList;

import modele.EnsembleResultat;
import modele.os.FileHelper;

public class JListDocument extends JList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7745719876651281659L;

	private File file;

	public JListDocument(EnsembleResultat ensembleResultat) {
		super(ensembleResultat.getCriteres());
		String cheminFichier = EnsembleResultat.getCheminFichierRechercheDocument(ensembleResultat);
		this.file = new File(cheminFichier);
		super.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					FileHelper.openParentDirectory(file);
				}
			}
		});
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		int index = locationToIndex(event.getPoint());
		if (index > -1) {
			String item = (String) getModel().getElementAt(index);
			return file.getAbsolutePath();
		}
		return null;
	}

	public File getFichier() {
		return file;
	}

}
