package modele.os;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileHelper {

	private static Desktop desktop;

	public static boolean openAudioFile(File file, double time) {
		String commande = String.format("vlc --start-time=%.1f %s", time, file.getAbsolutePath());
		System.out.println(commande);
		boolean vlcDisponnible = execProgramme(commande);
		if (!vlcDisponnible && verifierPossibleUtilisationDesktop(file)) {
			try {
				desktop.open(file);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static boolean openTexteFile(File file) {
		if (verifierPossibleUtilisationDesktop(file)) {
			try {
				desktop.edit(file);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static boolean openImageFile(File file) {
		if (verifierPossibleUtilisationDesktop(file)) {
			try {
				desktop.edit(file);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static boolean openParentDirectory(File file) {
		if (verifierPossibleUtilisationDesktop(file)) {
			File parentDirectory = file.getParentFile();
			try {
				desktop.open(parentDirectory);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	private static boolean verifierPossibleUtilisationDesktop(File file) {
		if (desktop == null) {
			if (Desktop.isDesktopSupported()) {
				desktop = Desktop.getDesktop();
			}
		}

		if (desktop != null) {
			if (desktop.isSupported(Desktop.Action.OPEN)) {
				if (file.exists()) {
					return true;
				} else
					afficherErreur("Le fichier '" + file.getAbsolutePath() + "' n'existe pas.");
			} else
				afficherErreur("Votre système n'est pas compatible avec la technologie utilisée.");

		} else
			afficherErreur("Votre système n'est pas compatible avec la technologie utilisée.");
		return false;
	}

	private static void afficherErreur(String message) {
		JOptionPane.showMessageDialog(null, "Impossible d'ouvrir le fichier !\n" + message, "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

	private static boolean programmeExiste(String nomProgramme) {
		String path = System.getenv("PATH");
		return path.contains(nomProgramme);
	}

	private static boolean execProgramme(String command) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process proc = rt.exec(command);
			proc.waitFor();
			int exitVal = proc.exitValue();
			return exitVal == 0;
		} catch (IOException e1) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

}
