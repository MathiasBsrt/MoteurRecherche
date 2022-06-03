package testconsole;

import controleur.ControlAdmin;
import vueconsole.BoundarySidentifier;

public class TestsIdentifier {

	public static void main(String[] args) {

		ControlAdmin cAdmin = new ControlAdmin();
		BoundarySidentifier sIdentifier = new BoundarySidentifier(cAdmin);

		sIdentifier.lancerIdentification();

	}

}
