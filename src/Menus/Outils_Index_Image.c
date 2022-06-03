/**
 * @file Outils_Index_Image.c
 * @author Baptiste POMARELLE
 * @brief 
 * @version 0.1
 * @date 2021-01-20
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#include "header.h"



void MenuIndexation_image()
{
    int code, choix_indexation;
    char buffer[MAX_INPUT];
    do
    {
        system("clear");
        //Affichage du menu
        printf("///\tMENU INDEXATION IMAGE\t///\n");
        printf("1. Indexer un fichier\n2. Indexer un dossier\n3. Retour\n");
        printf("Veuillez choisir une action :\n");
        scanf("%d", &code);
        if (code < 1 || code > 4)
        {
            printf("Veuillez choisir une action valide.\n");
        }
        else if (code == 1)
        {
            do
            {
                printf("Entrer un nom de fichier correct à indexer : ");
                scanf("%s", buffer);
            } while (access(buffer, F_OK));
            printf("\n\n");
            do
            {
                printf("1. Indexation noir et blanc\n2. Indexation couleur\n");
                printf("Veuillez choisir une action.\n");
                scanf("%d", &choix_indexation);
                if (choix_indexation == 2)
                    choix_indexation = 3;
            } while (choix_indexation != 1 && choix_indexation != 3);
            genererDescripteur_image(buffer,choix_indexation);
            printf("\t=======INDEXATION FICHIER TERMINÉE=======\n");
            printf("Retour au menu Indexation image...\n");
            waiter();
        }
        else if (code == 2)
        {
            do
            {
                printf("Entrer un nom de dossier correct à indexer : ");
                scanf("%s", buffer);
            } while (access(buffer, F_OK));
            printf("\n\n");
            do
            {
                printf("1. Indexation noir et blanc\n2. Indexation couleur\n");
                printf("Veuillez choisir une action.\n");
                scanf("%d", &choix_indexation);
                if (choix_indexation == 2)
                    choix_indexation = 3;
            } while (choix_indexation != 1 && choix_indexation != 3);
            genererDescripteur_imageDossier(buffer, choix_indexation);
            printf("\t=======INDEXATION DOSSIER TERMINÉE=======\n");
            printf("Retour au menu Indexation image...\n");
            waiter();
        }

    } while (code != 3);
}