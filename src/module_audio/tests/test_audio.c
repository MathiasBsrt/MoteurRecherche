#include <stdio.h>
#include <math.h>
#include "descripteur.h"
#include "histogramme.h"
#include "base_descripteur.h"
#include "pile_dynamique.h"

int main()
{
    printf("--- DEBUT DES TESTS AUDIO --- \n");
    printf("  --- DESCRIPTEUR --- \n");
    unsigned int n = 2;
    unsigned int k = pow(2, n);
    unsigned int m = 15;
    printf("    --- Paramètres généraux proposés: --- \n");
    printf("      n = %d \n", n);
    printf("      k = 2^%d = %d \n", n, k);
    printf("      m = %d \n", m);
    printf("    --- Initialisation du descripteur --- \n");
    // Initialisation du descripteur

    DESC_AUDIO desc = init_vide_DESC_AUDIO(n, m);
    if(desc.histo.k != k) return 1;
    if(desc.histo.m != m) return 1;
    printf("      Descripteur: id=%d, k=%d, m=%d\n", desc.id, desc.histo.k, desc.histo.m);

    unsigned int y, x;

    printf("    --- SET DESC AUDIO --- \n");
    for(y = 0; y < k; y++)
        for(x = 0; x < m; x++)
            set_DESC_AUDIO(&desc, y, x, (y * desc.histo.m + x));

    printf("    --- GET DESC AUDIO --- \n");
    for(y = 0; y < k; y++)
        for(x = 0; x < m; x++)
            if(get_DESC_AUDIO(desc, y, x) != (y * desc.histo.m + x)) return 1;

    free_DESC_AUDIO(desc);
    
    printf("    --- CREER HISTOGRAMME TXT DESC AUDIO --- \n");

    HISTOGRAMME_AUDIO histoTXT;
    int codeTXT = generer_HISTOGRAMME_AUDIO(&histoTXT, "./TEST_SON/corpus_fi.txt", n, m);
    if(codeTXT != HISTOGRAMME_CREER_SUCCES) return 1;
    //affiche_HISTOGRAMME_AUDIO(histoTXT);

    printf("    --- CREER HISTOGRAMME BIN DESC AUDIO --- \n");

    HISTOGRAMME_AUDIO histoBIN;
    int codeBIN = generer_HISTOGRAMME_AUDIO(&histoBIN, "./TEST_SON/corpus_fi.bin", n, m);
    if(codeBIN != HISTOGRAMME_CREER_SUCCES) return 1;
    //affiche_HISTOGRAMME_AUDIO(histoBIN);

    printf("    --- CREER HISTOGRAMME WAV DESC AUDIO --- \n");

    HISTOGRAMME_AUDIO histoWAV;
    int codeWAV = generer_HISTOGRAMME_AUDIO(&histoWAV, "./TEST_SON/corpus_fi.wav", n, m);
    if(codeWAV != HISTOGRAMME_CREER_SUCCES) return 1;
    //affiche_HISTOGRAMME_AUDIO(histoWAV);

    printf("    --- VERIFICATION D'EGALITE --- \n");
    int compareTXTBIN = compare_HISTOGRAMME_AUDIO(histoTXT, histoBIN);
    int compareTXTWAV = compare_HISTOGRAMME_AUDIO(histoTXT, histoWAV);
    int compareBINWAV = compare_HISTOGRAMME_AUDIO(histoBIN, histoWAV);
    printf("      --- HISTO TXT == HISTO BIN --- \n");
    printf("        %s\n", (compareTXTBIN == 0 ? "Vrai" : "Faux"));
    if(compareTXTBIN != 0) return 1;
    printf("      --- HISTO TXT == HISTO WAV --- \n");
    printf("        %s\n", (compareTXTWAV == 0 ? "Vrai" : "Faux"));
    if(compareTXTWAV != 0) return 1;
    printf("      --- HISTO BIN == HISTO WAV --- \n");
    printf("        %s\n", (compareBINWAV == 0 ? "Vrai" : "Faux"));
    if(compareBINWAV != 0) return 1;

    free_HISTOGRAMME_AUDIO(histoTXT);
    free_HISTOGRAMME_AUDIO(histoBIN);
    free_HISTOGRAMME_AUDIO(histoWAV);

    printf("  --- CONTROLEUR BASE DESCRIPTEUR --- \n");
    printf("    --- Initialisation du controleur --- \n");
    PILE_AUDIO pileDescripteur = init_PILE_AUDIO();
    init_FICHIER_BASE_DESC();

    printf("    --- Création du descripteur audio de TEST_SON/corpus_fi.wav --- \n");
    DESC_AUDIO descCorpusWAV = init_DESC_AUDIO(recuperer_nouvel_id_valide_AUDIO(), n, m, "TEST_SON/corpus_fi.wav");
    
    printf("    --- Sauvegarde du descripteur --- \n");
    pileDescripteur = sauvegarder_DESC_AUDIO(pileDescripteur, descCorpusWAV);

    /*printf("    --- Sauvegarde de la pile de descripteurs --- \n");
    sauvegarder_PILE_DESC_AUDIO(pileDescripteur);*/

    printf("    --- Création du descripteur audio de TEST_SON/jingle_fi.wav --- \n");
    DESC_AUDIO descJingleWAV = init_DESC_AUDIO(recuperer_nouvel_id_valide_AUDIO(), n + 2, m, "TEST_SON/jingle_fi.wav");
    
    printf("    --- Sauvegarde du descripteur --- \n");
    pileDescripteur = sauvegarder_DESC_AUDIO(pileDescripteur, descJingleWAV);
    
    /*printf("    --- Sauvegarde de la pile de descripteurs --- \n");
    sauvegarder_PILE_DESC_AUDIO(pileDescripteur);*/

    printf("    --- Création du descripteur audio de TEST_SON/cymbale.wav --- \n");
    DESC_AUDIO descCymbaleWAV = init_DESC_AUDIO(recuperer_nouvel_id_valide_AUDIO(), n + 1, m, "TEST_SON/cymbale.wav");
    
    printf("    --- Sauvegarde du descripteur --- \n");
    pileDescripteur = sauvegarder_DESC_AUDIO(pileDescripteur, descCymbaleWAV);

    printf("    --- Sauvegarde de la pile de descripteurs --- \n");
    sauvegarder_PILE_DESC_AUDIO(pileDescripteur);

    free_DESC_AUDIO(descCorpusWAV);
    free_DESC_AUDIO(descJingleWAV);
    free_DESC_AUDIO(descCymbaleWAV);

    printf("    --- Chargement des descripteurs enregistrés --- \n");
    int nbLoaded = 0;
    PILE_AUDIO secondePile = charger_PILE_DESC_AUDIO(&nbLoaded);
    

    printf("       --- Pile est vide ? --- \n");
    printf("          %s \n", (PILE_estVide_AUDIO(secondePile) ? "Oui" : "Non"));
    if(PILE_estVide_AUDIO(secondePile) != 0) return 1;

    printf("       --- Depile premier descripteur --- \n");
    DESC_AUDIO descDepile1;
    secondePile = dePILE_AUDIO(secondePile, &descDepile1);

    printf("         --- Descripteur depile1 == celui empile ? --- \n");
    int compareDepile1Empile = compare_DESC_AUDIO(descDepile1, descCorpusWAV);
    printf("           %s\n", (compareDepile1Empile == 0 ? "Vrai" : "Faux"));
    if(compareDepile1Empile != 0) return 1;
    free_DESC_AUDIO(descDepile1);

    printf("       --- Pile est vide ? --- \n");
    printf("          %s \n", (PILE_estVide_AUDIO(secondePile) ? "Oui" : "Non"));
    if(PILE_estVide_AUDIO(secondePile) != 0) return 1;

    printf("       --- Depile second descripteur --- \n");
    DESC_AUDIO descDepile2;
    secondePile = dePILE_AUDIO(secondePile, &descDepile2);

    printf("         --- Descripteur depile2 == celui empile ? --- \n");
    int compareDepile2Empile = compare_DESC_AUDIO(descDepile2, descJingleWAV);
    printf("           %s\n", (compareDepile2Empile == 0 ? "Vrai" : "Faux"));
    if(compareDepile2Empile != 0) return 1;
    free_DESC_AUDIO(descDepile2);

    printf("       --- Pile est vide ? --- \n");
    printf("          %s \n", (PILE_estVide_AUDIO(secondePile) ? "Oui" : "Non"));
    if(PILE_estVide_AUDIO(secondePile) != 0) return 1;

    printf("       --- Depile troisième descripteur --- \n");
    DESC_AUDIO descDepile3;
    secondePile = dePILE_AUDIO(secondePile, &descDepile3);

    printf("         --- Descripteur depile3 == celui empile ? --- \n");
    int compareDepile3Empile = compare_DESC_AUDIO(descDepile3, descCymbaleWAV);
    printf("           %s\n", (compareDepile3Empile == 0 ? "Vrai" : "Faux"));
    if(compareDepile3Empile != 0) return 1;
    free_DESC_AUDIO(descDepile3);

    printf("       --- Pile est vide ? --- \n");
    printf("          %s \n", (PILE_estVide_AUDIO(secondePile) ? "Oui" : "Non"));
    if(PILE_estVide_AUDIO(secondePile) != 1) return 1;

    printf("    --- Création des descripteurs audio du dossier TEST_SON --- \n");
    PILE_AUDIO pileDescDossier = init_MULTIPLE_DESC_AUDIO(recuperer_nouvel_id_valide_AUDIO(), 2, 15, "WAV_FILES");

    
    printf("    --- Tests deja_genere_DESC_AUDIO(char *) --- \n");
    int resCorpus = deja_genere_DESC_AUDIO("WAV_FILES/corpus_fi.wav");
    if(resCorpus != ALREADY_GENERATED) { fprintf(stderr, "Le programme doit trouver un descripteur déjà généré pour le corpus.\n"); return 1; }
    int resJingle = deja_genere_DESC_AUDIO("WAV_FILES/jingle_fi.wav");
    if(resJingle != ALREADY_GENERATED) { fprintf(stderr, "Le programme doit trouver un descripteur déjà généré pour le jingle.\n"); return 1; }
    int resInconnu = deja_genere_DESC_AUDIO("WAV_FILES/jingle_fi99.wav");
    if(resInconnu == ALREADY_GENERATED) { fprintf(stderr, "Le programme ne doit pas trouver de descripteur déjà généré pour le jingle99.\n"); return 1; }

    printf("    --- Tests get_id_byname_DESC_AUDIO(char *) --- \n");
    int idCorpus = get_id_byname_DESC_AUDIO("WAV_FILES/corpus_fi.wav");
    if(idCorpus == ID_NOT_FOUND) { fprintf(stderr, "Le programme doit trouver un ID pour le corpus.\n"); return 1; }
    int idJingle = get_id_byname_DESC_AUDIO("WAV_FILES/jingle_fi.wav");
    if(idJingle == ID_NOT_FOUND) { fprintf(stderr, "Le programme doit trouver un ID pour le jingle.\n"); return 1; }
    int idInconnu = get_id_byname_DESC_AUDIO("WAV_FILES/jingle_fi99.wav");
    if(idInconnu != ID_NOT_FOUND) { fprintf(stderr, "Le programme ne doit pas trouver d'ID pour le jingle99.\n"); return 1; }


    printf("    --- Tests charger_id_DESC_AUDIO(int) --- \n");
    DESC_AUDIO descCorpus = charger_byid_DESC_AUDIO(0);
    if(descCorpus.id == ID_NOT_FOUND) { fprintf(stderr, "Le programme doit trouver un DESC_AUDIO pour l'id 0.\n"); return 1; }
    DESC_AUDIO descJingle = charger_byid_DESC_AUDIO(1);
    if(descJingle.id == ID_NOT_FOUND) { fprintf(stderr, "Le programme doit trouver un DESC_AUDIO pour l'id 1.\n"); return 1; }
    DESC_AUDIO descInconnu = charger_byid_DESC_AUDIO(50);
    if(descInconnu.id != ID_NOT_FOUND) { fprintf(stderr, "Le programme ne doit pas trouver de DESC_AUDIO pour l'id 50.\n"); return 1; }

    free_DESC_AUDIO(descCorpus);
    free_DESC_AUDIO(descJingle);
    free_DESC_AUDIO(descInconnu);

    printf("    --- Tests charger_byname_DESC_AUDIO(char *) --- \n");
    descCorpus = charger_byname_DESC_AUDIO("TEST_SON/corpus_fi.wav");
    if(descCorpus.id == ID_NOT_FOUND) { fprintf(stderr, "Le programme doit trouver un DESC_AUDIO pour le corpus.\n"); return 1; }
    descJingle = charger_byname_DESC_AUDIO("TEST_SON/jingle_fi.wav");
    if(descJingle.id == ID_NOT_FOUND) { fprintf(stderr, "Le programme doit trouver un DESC_AUDIO pour le jingle.\n"); return 1; }
    descInconnu = charger_byname_DESC_AUDIO("TEST_SON/jingle_fi99.wav");
    if(descInconnu.id != ID_NOT_FOUND) { fprintf(stderr, "Le programme ne doit pas trouver de DESC_AUDIO pour le jingle99.\n"); return 1; }

    printf("    --- Tests recuperer_nouvel_id_valide_AUDIO() --- \n");
    int nouvelId = recuperer_nouvel_id_valide_AUDIO();
    if(nouvelId != 6) { fprintf(stderr, "Le programme doit trouver un nouvel identifiant valide égale à 3. Il a trouvé %d.\n", nouvelId); return 1; } 


    DESC_AUDIO depileDesc;
    while(!PILE_estVide_AUDIO(pileDescDossier))
    {
    	pileDescDossier = dePILE_AUDIO(pileDescDossier, &depileDesc);
        char * chemin = fichier_lier_DESC_AUDIO(depileDesc);
        printf(" Fichier associé au descripteur %d: %s\n", depileDesc.id, 
            chemin != NULL ? chemin : "Introuvable");
    	if(chemin != NULL) free(chemin);
        free_DESC_AUDIO(depileDesc);
        //affiche_DESC_AUDIO(depileDesc);
    }
   
    printf("  --- RECHERCHE --- \n");
    printf("    --- Recherche WAV_FILES/jingle_fi.wav  dans WAV_FILES/corpus_fi.wav avec les 3 meilleurs résultats --- \n");

    DESC_AUDIO desc1 = charger_byname_DESC_AUDIO("TEST_SON/corpus_fi.wav");
    DESC_AUDIO desc2 = charger_byname_DESC_AUDIO("TEST_SON/jingle_fi.wav");

    //affiche_DESC_AUDIO(desc1);

    RES_EVAL_AUDIO resultat = evaluer_DESC_AUDIO(desc1, desc2, 3, EVAL_NORMAL);
    if(resultat.n == 0)
    {
        printf("Aucun résultat trouvé.\n");
    } else {
        printf("      Temps trouvés:\n");
        for(int i = 0; i < resultat.n; i++)
        {
            printf("      i = %d: %f\n", i, resultat.times[i]);
        }
    }

    free_DESC_AUDIO(desc1);
    free_DESC_AUDIO(desc2);
    free_RES_EVAL_AUDIO(resultat);

    printf("--- FIN DES TEST AUDIO --- \n");
    return 0;
}
