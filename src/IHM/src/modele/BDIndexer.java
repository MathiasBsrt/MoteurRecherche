package modele;

import java.util.ArrayList;
import java.util.List;


public class BDIndexer {
	List<String> fichiersIndexeImage = new ArrayList<String>();
	List<String> fichiersIndexeSon = new ArrayList<String>();
	List<String> fichiersIndexeTexte = new ArrayList<String>();

	
	private static class BDIndexerHolder {
		
		public static BDIndexer INSTANCE = new BDIndexer();
		
	}
	
	public static BDIndexer getInstance() {
		
		return BDIndexerHolder.INSTANCE;
		
	}
	public List<String> getFichiersIndexe(TypeDocument typeDocument)
	{
		List<String> listeFichiers =null;
		switch(typeDocument)
		{
		case SON:
			listeFichiers=getFichiersIndexeSon();
			break;
		case IMAGE:
			listeFichiers=getFichiersIndexeImage();
			break;
		default :
			listeFichiers=getFichiersIndexeTexte();
			break;
		}
		return listeFichiers;
	}
	public List<String> getFichiersIndexeImage() {
		
		return(fichiersIndexeImage);
	}
	public List<String> getFichiersIndexeSon() {
		
		return(fichiersIndexeSon);
	}	
	public List<String> getFichiersIndexeTexte() {
		
		return(fichiersIndexeTexte);
	}
	public void ajouterFichierIndexe(TypeDocument typedocument,String chemin)
	{
		switch(typedocument)
		{
		case SON:
			fichiersIndexeSon.add(chemin);
			break;
		case IMAGE:
			fichiersIndexeImage.add(chemin);
			break;
		default :
			fichiersIndexeTexte.add(chemin);
			break;
		}
	}
}
