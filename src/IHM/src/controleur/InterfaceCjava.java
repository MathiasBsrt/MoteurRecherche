package controleur;

public class InterfaceCjava {
	/* Native method declaration */
	   public native void MenuIndexation_Texte();
	   
	     /* Use static intializer */
	      static {
	         System.loadLibrary("Outils_Index_Texte");
	      }
	      /* Main function calls native method*/
	      /***
	       * @param entier : 1 =>indexer fichier, 2=> indexer 
	       */
	     public void indexation_texte() {
	    	 System.out.println("On va lancer l'indexation texte");
	    	 //Lancer la fonction MenuIndexation_Texte 
	    	 //sans la partie interaction => modifier le C
	    	 this.MenuIndexation_Texte();
	    	}
	      public static void main(String[] args) {
	        new InterfaceCjava().indexation_texte();
	      }
}
