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
#include <jni.h>
#include <stdio.h>
#include "../IHM/src/controleur/controleur_InterfaceCjava.h"

void waiter()
{
    fflush(stdin);
    while (getchar() != '\n')
        ;
    printf("Appuyer sur la touche Entrée pour continuer...\n");
    getchar();
}

JNIEXPORT void JNICALL Java_controleur_InterfaceCjava_MenuIndexation_1Texte
  (JNIEnv *env, jobject obj)
{
    int code;
    //Déclarations de variables
    char buffer[MAX_INPUT];
    int seuil, valeur_Limite;
    PILE_descripteur_texte pile = init_PILE_desc();
    Table_Index table = Init_Index();


    charger_PILE_Desc_mot(&pile, "sauvegarde.desc");
    charger_Table_index(&table, "sauvegarde.index");
    do
    {
        system("clear");

        //Affichage du menu
        printf("///\tMENU INDEXATION TEXTE\t///\n");
        printf("1. Indexer un fichier\n2. Indexer un dossier\n3. Sauvegarder et quitter\n");
        printf("Veuillez choisir une action :\n");
        scanf("%d", &code);
        if (code < 1 || code > 4)
        {
            printf("Veuillez choisir une action valide.\n");
            waiter();
        }

        else if (code == 1)
        {
            do
            {
                printf("Entrer un nom de dossier correct à indexer : ");
                scanf("%s", buffer);
            } while (access(buffer, F_OK));
            do
            {
                printf("Entrer maintenant un seuil qui définira les mots ignorés en fonction de leur nombre d'apparition (0 pour ignorer ce paramètre) : ");
                scanf("%d", &seuil);
            } while (seuil < 0);

            Descripteur_texte_fichier(buffer, &pile, &table, seuil);
            printf("\t=======INDEXATION FICHIER TERMINÉE=======\n");
            printf("Retour au menu Indexation texte...\n");
            waiter();
        }
        else if (code == 2)
        {
            do
            {
                printf("Entrer un nom de dossier correct à indexer : ");
                scanf("%s", buffer);
            } while (access(buffer, F_OK));
            do
            {
                printf("Entrer maintenant un seuil qui définira les mots ignorés en fonction de leur nombre d'apparition (0 pour ignorer ce paramètre) : ");
                scanf("%d", &seuil);
            } while (seuil < 0);
            Descripteur_texte_dossier(buffer, &pile, &table, seuil);
            printf("\t=======INDEXATION DOSSIER TERMINÉE=======\n");
            printf("Retour au menu Indexation texte...\n");

            waiter();
        }
        else if (code == 3) // Sauvegarde
        {
            enregistre_PILE_Desc(pile, "sauvegarde.desc");
            enregistre_Table_Index(table, "sauvegarde.index");
            printf("Sauvegarde terminée. Retour vers le menu indexation\n");
            break;
        }
    } while (1);
}

// module_texte/Textes_UTF8 