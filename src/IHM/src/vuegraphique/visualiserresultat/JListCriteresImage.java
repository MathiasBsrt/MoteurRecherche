package vuegraphique.visualiserresultat;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;

import modele.EnsembleResultat;

public class JListCriteresImage extends JList<Color> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7745719876651281659L;

	private static final ImageListCellRenderer cellRender = new ImageListCellRenderer();

	private String[] criteres;

	public JListCriteresImage(EnsembleResultat ensembleResultat) {
		super(criteresToColor(ensembleResultat.getCriteres()));
		this.criteres = ensembleResultat.getCriteres();
		super.setCellRenderer(cellRender);
		super.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int index = locationToIndex(evt.getPoint());
					Color color = getSelectedValue();
					JColorChooser.showDialog(null, "Couleur", color);
				}
			}
		});
	}

	public String[] getCriteres() {
		return criteres;
	}

	private static Color[] criteresToColor(String[] criteres) {
		Color[] colors = new Color[criteres.length];
		for (int i = 0; i < criteres.length; i++) {
			String[] colorValues = criteres[i].split(":");
			int red = Integer.valueOf(colorValues[0]);
			int green = Integer.valueOf(colorValues[1]);
			int blue = Integer.valueOf(colorValues[2]);
			colors[i] = new Color(red, green, blue);
		}

		return colors;
	}

	private static class ImageListCellRenderer extends DefaultListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8095739520448468529L;

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			Color color = (Color) value;
			String content = String.format("(R=%d, G=%d, B=%d)", color.getRed(), color.getGreen(), color.getBlue());
			label.setText(content);

			if (isSelected) {
				label.setBackground(color);
				label.setForeground(couleurLisible(color));
			}

			return label;

		}

		/**
		 * Permet de calculer la couleur d'un texte qui est sur un fond de couleur
		 * couleurDeFond.
		 * 
		 * @param couleurDeFond La couleur de fond
		 * @return La couleur applicable au texte qui est sur la couleur de fond. <br />
		 * 
		 *         Voir aussi: <br />
		 * 
		 *         {@link https://stackoverflow.com/questions/3116260/given-a-background-color-how-to-
		 *         get-a-foreground-color-that-makes-it-readable-o/3118280}
		 */
		private Color couleurLisible(Color couleurDeFond) {
			// 2.2 représentant le gamma.

			double red = Math.pow((couleurDeFond.getRed() / 255.0D), 2.2);
			double green = Math.pow((couleurDeFond.getGreen() / 255.0D), 2.2);
			double blue = Math.pow((couleurDeFond.getBlue() / 255.0D), 2.2);

			double threshold = 0.2126 * red + 0.7152 * green + 0.722 * blue;

			if (threshold > 0.5)
				return Color.BLACK;
			return Color.WHITE;
		}
	}

}
