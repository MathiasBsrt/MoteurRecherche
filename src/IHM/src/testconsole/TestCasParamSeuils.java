package testconsole;

import controleur.ControlParamSeuil;
import vueconsole.BoundaryParamSeuil;

public class TestCasParamSeuils {

	public static void main(String[] args) {
		ControlParamSeuil controlParamSeuil = new ControlParamSeuil();
		BoundaryParamSeuil bParamSeuil = new BoundaryParamSeuil(controlParamSeuil);

		bParamSeuil.lancerParamSeuil();

	}
}
