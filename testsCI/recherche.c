/***
 * Ici les tests de la recherche son
 */
//#SI notre prog de test retourn 1 : echec de la pipeline

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../src/module_audio/descripteur.h"
#include "../src/module_audio/base_descripteur.h"

int rechercheImage()
{
    return 0;
}

int rechercheTexte()
{
    return 0;
}

int rechercheSon()
{
    char *filename1 = "TEST_SON/jingle_fi.wav";

    int n = 5;
    int fetch_n_best = 3;

    printf("Recherche de '%s' dans d'autres fichiers audio.\n", filename1);
    printf("Paramètres:\n \t fetch_n_best = %d\n \t threshold = %f\n", fetch_n_best, EVAL_VERYTOUGH);

    int code;
    RES_RECHERCHE_AUDIO resultat = rechercher_DESC_AUDIO(filename1, fetch_n_best, EVAL_VERYTOUGH, &code);

    if (code == RECHERCHE_ERREUR)
    {
        fprintf(stderr, "Une erreur est survenue pendant la recherche.\n");
        return code; // Retourne 1 si erreur
    }

    if (resultat.n == 0)
    {
        printf("Aucun résultat trouvé. La recherche doit trouver jingle_fi.wav dans un autre fichier audio.\n");
        return 1;
    }

    double marge = 1;

    printf("Liste des résultats (%d trouvé(s)):\n", resultat.n);
    for (int i = 0; i < resultat.n; i++)
    {
        printf("\t Résultat %d dans le fichier '%s':\n", i + 1, resultat.resultats[i].fichier);
        if (strcmp(resultat.resultats[i].fichier, "TEST_SON/corpus_fi.wav") != 0)
        {
            fprintf(stderr, "Le fichier dans lequel jingle_fi.wav a été trouvé n'est pas le bon.\n");
            return 1;
        }

        if (resultat.resultats[i].times[0] < 29 - marge || resultat.resultats[i].times[0] > 29 + marge)
        {
            fprintf(stderr, "Le résultat trouvé n'est pas bon.\n");
            fprintf(stderr, "Le programme a trouvé %f et cette valeur n'est pas dans l'interval [%f ; %f] (marge de %f)\n", resultat.resultats[i].times[0], resultat.resultats[i].times[0] - marge, resultat.resultats[i].times[0] + marge, marge);
            return 1;
        }

        for (int j = 0; j < resultat.resultats[i].n; j++)
        {
            printf("\t\t j = %d: %f\n", j, resultat.resultats[i].times[j]);
        }
    }

    free_RES_RECHERCHE_AUDIO(resultat);

    char *filename2 = "TEST_SON/cymbale.wav";

    printf("Recherche de '%s' dans d'autres fichiers audio.\n", filename2);
    printf("Paramètres:\n \t fetch_n_best = %d\n \t threshold = %f\n", fetch_n_best, EVAL_NORMAL);

    int code2;
    RES_RECHERCHE_AUDIO resultat2 = rechercher_DESC_AUDIO(filename2, fetch_n_best, EVAL_NORMAL, &code2);

    if (code2 == RECHERCHE_ERREUR)
    {
        fprintf(stderr, "Une erreur est survenue pendant la recherche.\n");
        return code2; // Retourne 1 si erreur
    }

    if (resultat2.n != 0)
    {
        printf("Des résultats ont été trouvés. La recherche ne doit pas trouver cymbale.wav dans un autre fichier audio.\n");
        printf("Liste des résultats (%d trouvé(s)):\n", resultat2.n);
        for (int i = 0; i < resultat2.n; i++)
        {
            printf("\t Résultat %d dans le fichier '%s':\n", i + 1, resultat2.resultats[i].fichier);

            for (int j = 0; j < resultat2.resultats[i].n; j++)
            {
                printf("\t\t j = %d: %f\n", j, resultat2.resultats[i].times[j]);
            }
        }
        return 1;
    }

    printf("Aucun fichier trouvé: OK\n");
    printf(" ======= TESTS MODULE_AUDIO RECHERCHE OK =======\n");

    return 0;
}

/*
int main(int argc, char const *argv[])
{
    int res;
    res = rechercheImage();
    res = rechercheSon();
    res = rechercheTexte();
    return res;
}
*/