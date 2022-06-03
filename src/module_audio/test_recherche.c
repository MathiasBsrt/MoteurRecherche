/**
 * @file test_recherche.c
 * @author Théo TRAFNY
 * @brief Programme de test pour pouvoir générer une recherche d'un fichier.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "descripteur.h"
#include "base_descripteur.h"

/*
int main(int argc, char * argv[])
{
    if(argc != 2)
    {
        fprintf(stderr, "Usage: %s <file1>\n", argv[0]);
        fprintf(stderr, "\tPour rechercher file1 dans les fichiers déjà indexés.\n");
        return 1;
    }

    char * filename1 = argv[1];

    int n = 5;
    int fetch_n_best = 3;
    
    printf("Recherche de '%s' dans d'autres fichiers audio.\n", filename1);

    int code;
    RES_RECHERCHE_AUDIO resultat = rechercher_DESC_AUDIO(filename1, fetch_n_best, EVAL_VERYTOUGH, &code);

    if(code == RECHERCHE_ERREUR)
    {
        fprintf(stderr, "Une erreur est survenue pendant la recherche.\n");
        return code;
    }

    if(resultat.n == 0)
    {
        printf("Aucun résultat trouvé.\n");
        return 0;
    }

    printf("Liste des résultats (%d trouvé(s)):\n", resultat.n);
    for(int i = 0; i < resultat.n; i++)
    {
        printf("\t Résultat %d dans le fichier '%s':\n", i + 1, resultat.resultats[i].fichier);
        for(int j = 0; j < resultat.resultats[i].n; j++)
        {
        printf("\t\t j = %d: %f\n", j, resultat.resultats[i].times[j]);
        }
    }

    
    free_RES_RECHERCHE_AUDIO(resultat);

}*/