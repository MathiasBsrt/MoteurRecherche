/**
 * @file recherche.c
 * @author Mathias Bossaerts
 * @brief Fonctions de recherche du module texte
 * @version 0.1
 * @date 2021-01-10
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#include "Header.h"

int comparaison_texte(Descripteur_texte *d1, Descripteur_texte *d2, double seuil, Table_Index t, double *pourcentageS)
{
    //Similaire si seuil% des mots sont similaire
    /****Une case est similaire si :
     * - même mot
     * ET
     * - itération mot val2- seuil<= val1 <= val2 + seuil
    */
    // On ajoute cette case dans le teableau d'intersection
    int nbCaseIntersection = 0;

    Cellule_mot *parcours_d1 = d1->pile_mot;

    parcours_d1 = d1->pile_mot;

    //printf("comparaison de %d et %d\n", d1->id, d2->id);
    Index *index;
    int nbMot = 0; // nombre d mot dans d1;
    //Pour chaque mot de d1, on cherche si d2 est dans la table du mot courant
    while (parcours_d1 != NULL)
    {
        index = ExisteDansTable_Index(t, parcours_d1->elt.mot); //Mise à jour de l'index du mot courant
        nbMot++;
        //On parcours l'index
        for (int i = 0; i < index->nb_occ; i++)
        {
            if (index->idTxt_avec_occ[0][i] == d2->id)
            {
                nbCaseIntersection++;
                break;
            }
        }

        parcours_d1 = parcours_d1->suivant; // Mot suivant
    }

    //printf("\nsimilaire sur %d cases\n", nbCaseIntersection);

    double pourcentage = (double)nbCaseIntersection / nbMot * 100;
    *pourcentageS = pourcentage;
    //printf("Similaire à %f pourcents\n", pourcentage);
    //printf("seuil = %f\n", seuil);
    if (pourcentage == 100.0)
    {
        return 0;
    }
    else if (pourcentage >= seuil)
    {
        return 1;
    }

    return 2;
}

void rechercheParCritere_texte(char *mot, char **fichiersSimilaires, int *nbF)
{
    printf("recherche par critère...\n");
    PILE_descripteur_texte pile = init_PILE_desc();
    charger_PILE_Desc_mot(&pile, "sauvegarde.desc");
    Table_Index table1 = Init_Index();
    charger_Table_index(&table1, "sauvegarde.index");

    //Dans cette table on cherche l'index du mot pour obtenir la liste des fichiers qui ont ce mot

    Table_Index indexMot;
    indexMot = rechercheMot_texte(table1, mot);

    if (indexMot == NULL)
    {
        fichiersSimilaires = NULL;
    }
    else
    {
        //On compare ce fichier avec tous les autres fichiers en appliquant le seuil
        //printf("nb_max=%d\n", indexMot->nb_occ);
        *nbF = indexMot->nb_occ;
        for (int i = 0; i < indexMot->nb_occ; i++)
        {
            char chemin[MAX_STRING];
            getChemin_texte(indexMot->idTxt_avec_occ[0][i], chemin);
            printf("chemin trouvé=%s avec %d fois le mot %s\n", chemin,indexMot->idTxt_avec_occ[1][i],mot);
            strcpy(fichiersSimilaires[i], chemin);
        }
    }
}

void getChemin_texte(int id, char chemin[])
{
    FILE *liste_base_desc = fopen("sauvegardes/txt/liste_base_descripteurs", "r");
    if (liste_base_desc)
    {

        int id_texte;
        fseek(liste_base_desc, 0, SEEK_SET);
        while (fscanf(liste_base_desc, "%s %d", chemin, &id_texte) != EOF)
        {
            if (id_texte == id)
            {
                //printf("chemin trouvé : %s",chemin);
                break;
            }
        }
        fclose(liste_base_desc);
    }
}
Descripteur_texte *getDescripteur_Texte(int id, PILE_descripteur_texte *p)
{

    Descripteur_texte *parcours = NULL;
    //printf("on cherche le descripteur correspondant\n");
    parcours = *p;
    while (parcours != NULL)
    {
        //printf("parcours id :%d\n", parcours->id);

        if (parcours->id == id)
        {
            //printf(" descripteur bien trouvé\n\n");

            return parcours;
        }
        parcours = parcours->suivant;
    }
    //printf("%s\n",parcours->pile_mot->elt.mot);
    return NULL;
}

Table_Index rechercheMot_texte(Table_Index a, char *mot)
{
    if (a == NULL)
    {
        //printf("mot non trouvé");
        return NULL;
    }
    else if (strcmp(a->mot, mot) == 0)
    {
        return a;
    }
    else if (strcmp(a->mot, mot) > 0)
    {
        return rechercheMot_texte(a->gauche, mot);
    }
    else
    {
        return rechercheMot_texte(a->droit, mot);
    }
}

int rechercheParDocument_texte(char *cheminVersDocument, char *fichiersSimilaires[], double seuilSimilarite)
{

    //On indexe le nouveau document
    PILE_descripteur_texte pile = init_PILE_desc();
    Table_Index table = Init_Index();

    //on charge la pile des descripteurs
    charger_Table_index(&table, "sauvegarde.index");
    charger_PILE_Desc_mot(&pile, "sauvegarde.desc");

    Descripteur_texte *desc1;
    int nbF = 0;
    int id = texte_deja_indexe(cheminVersDocument);

    if (id == 0)
    {
        id = Descripteur_texte_fichier(cheminVersDocument, &pile, &table, 1); // Bug, idée : le nouveau fichier indexé ecrase la pile ?
    }
    //printf("id : %d",id);
   /* Descripteur_texte *parcours=pile;
    while(parcours)
    {
        printf("id : %d\n",parcours->id);
        parcours=parcours->suivant;
    }*/
    
    //On récupéere le descripteur qui vient d'être crée
    desc1 = getDescripteur_Texte(id, &pile);

    //printf("id du descripteur 1 =%d\n", id);
    //On utilise un tableau pour stocker les chemins ()
    char chemin1[100];
    getChemin_texte(desc1->id, chemin1);
    // printf("chemin = %s\n", chemin1);

    //On stock les pourcentages de similarité pour chaque fichier.
    // On considère les deux tableaux (pourcentages et fichierSimilaires) triés de façon identique et correspodant.
    //C'est à dire que le pourcentage à l'indice i correspond au chemin à ce même indice
    int pourcentages[MAX_STRING];
    double pourcentagesS; // Pourcentage de similarité entre 2 descripteurs,
    if (desc1 != NULL)
    {
        //On compare le desc1 avec tous les descripteurs de la pile sauf lui même
        Descripteur_texte *desc2 = pile;
        int res; // resultat de la comparaison
        while (desc2 != NULL)
        {
            if (desc2->id != desc1->id)
            { // On exclus le texte fourni du résultat de la recherche
                char chemin2[MAX_STRING];
                //printf("\nid du descripteur 2 =%d\n", desc2->id);
                res = comparaison_texte(desc1, desc2, seuilSimilarite, table, &pourcentagesS);
                if (res < 2)
                {
                    getChemin_texte(desc2->id, chemin2);
                    pourcentages[nbF] = pourcentagesS;
                    strcpy(fichiersSimilaires[nbF], chemin2);

                    nbF++;
                }
            }

            desc2 = desc2->suivant;
        }


        //Trier les tableaux pourcenatges et fichiers similaires
        int temp;
        char tempString[MAX_STRING];

        for (int i = 0; i < nbF - 1; i++)
        {
            for (int j = 0; j < nbF - i - 1; j++)
            {
                if (pourcentages[j] < pourcentages[j + 1])
                {
                    //echange tableau pourcentage
                    temp = pourcentages[j];
                    pourcentages[j] = pourcentages[j + 1];
                    pourcentages[j + 1] = temp;

                    //echange tableau string
                    strcpy(tempString, fichiersSimilaires[j]);
                    strcpy(fichiersSimilaires[j], fichiersSimilaires[j + 1]);
                    strcpy(fichiersSimilaires[j + 1], tempString);
                }
            }
        }

        printf("après tri\n");

        for (int i = 0; i < nbF; i++)
        {
            printf("Fichier similaire : %s à %d pourcents \n", fichiersSimilaires[i], pourcentages[i]);
        }
    }

    return nbF;
}