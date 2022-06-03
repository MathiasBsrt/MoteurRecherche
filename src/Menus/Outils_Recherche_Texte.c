/**
 * @file Outils_Index_Texte.c
 * @author Baptiste POMARELLE
 * @brief 
 * @version 0.1
 * @date 2021-01-16
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#include "header.h"

void MenuRecherche_texte()
{
    int code;
    //Déclarations de variables
    char buffer[MAX_WORD];
    char **fichierSimil;
    int nb_fichierSimil;
    double seuil_similarite;

    char choix;
    char commande_affichage[MAX_INPUT];

    //initialisation :
    fichierSimil = malloc(sizeof(char *) * 350);
    for (int i = 0; i < 350; i++)
        fichierSimil[i] = malloc(sizeof(char) * MAX_STRING);
    do
    {
        system("clear");

        //Affichage du menu
        printf("///\tMENU RECHERCHE TEXTE\t///\n");
        printf("Vous souhaitez lancer une recherche : \n1. Par critères\n2.Par Document\n\n3. Retour\n");
        printf("Veuillez choisir une action :\n");
        scanf("%d", &code);
        if (code < 1 || code > 3)
        {
            printf("Veuillez choisir une action valide.\n");
            waiter();
        }

        else if (code == 1)
        {
            printf("Entrer un mot a chercher : ");
            scanf("%s", buffer);
           /* do
            {
                printf("Entrer un seuil de similarité : ");
                scanf("%lf", &seuil_similarite);
            } while (seuil_similarite < 0 || seuil_similarite > 100);*/

            rechercheParCritere_texte(buffer, fichierSimil, &nb_fichierSimil);
            if (fichierSimil[0])
            {
                printf("Souhaitez vous afficher le meilleur résultat (y/n)? ");
                while ((choix = getchar()) != 'y' && choix != 'n')
                    ;
                if (choix == 'y')
                {
                    system("clear");
                    strcat(commande_affichage,"gedit ");
                    system(strcat(commande_affichage, fichierSimil[0]));
                }
            }

            waiter();
        }
        else if (code == 2)
        {
            do
            {
                printf("Entrer un nom de fichier correct a rechercher : ");
                scanf("%s", buffer);
            } while (access(buffer, F_OK));
            do
            {
                printf("Entrer un seuil de similarité : ");
                scanf("%lf", &seuil_similarite);
            } while (seuil_similarite < 0 || seuil_similarite > 100);
            rechercheParDocument_texte(buffer, fichierSimil, seuil_similarite);
            if (fichierSimil[0])
            {
                printf("Souhaitez vous afficher le meilleur résultat (y/n)? ");
                while ((choix = getchar()) != 'y' && choix != 'n')
                    ;
                if (choix == 'y')
                {
                    system("clear");
                    strcat(commande_affichage,"gedit ");
                    system(strcat(commande_affichage, fichierSimil[0]));
                }
            }
            waiter();
        }

    } while (code != 3);
}

// module_texte/Textes_UTF8