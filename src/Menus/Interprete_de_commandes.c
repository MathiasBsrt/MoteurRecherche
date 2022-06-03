/**
 * @file Interprete_de_commandes.c
 * @author Baptiste POMARELLE
 * @brief 
 * @version 0.1
 * @date 2021-01-16
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include "header.h"

void menus_admin(PILE_AUDIO *pile_audio)
{
    int code;
    do
    {
        system("clear");
        //Affichage du menu
        printf("///\tMENU INDEXATION\t///\n");
        printf("1. Indexation audio\n2. Indexation Image\n3. Indexation Texte\n4. Retour\n");
        printf("Veuillez choisir une action :\n");
        scanf("%d", &code);
        if (code < 1 || code > 4)
        {
            printf("Veuillez choisir une action valide.\n");
        }

        else if (code == 1)
        {
            MenuIndexation_audio(pile_audio);
        }
        else if (code == 2)
        {
            MenuIndexation_image();
        }
        else if (code == 3)
        {

            MenuIndexation_texte();
        }
    } while (code != 4);
}

void menus_user()
{
    int code;
    do
    {
        system("clear");
        //Affichage du menu
        printf("///\tMENU RECHERCHE\t///\n");
        printf("1. Recherche Audio\n2. Recherche Image\n3. Recherche Texte\n4. Retour\n");
        printf("Veuillez choisir une action :\n");
        scanf("%d", &code);
        if (code < 1 || code > 4)
        {
            printf("Veuillez choisir une action valide.\n");
        }

        else if (code == 1)
        {
            MenuRecherche_audio();
        }
        else if (code == 2)
        {
            MenuRecherche_image();
        }
        else if (code == 3)
        {
            MenuRecherche_texte();
        }
    } while (code != 4);
}

void modif_mot_de_passe(int code,char* mdp)
{
    FILE *f = fopen("sauvegardes/mdp", "w+");
    char tmp[MAX_WORD], tmp2[MAX_WORD];
    do
    {
        system("clear");
        printf("///\t%s Mot de passe\t///\n", (code == 1) ? "Création" : "Réinitialisation");
        printf(" saisir un mot de passe (max 128): ");
        scanf("%s", tmp);
        printf("Veuillez a nouveau entrer le mot de passe : ");
        scanf("%s", tmp2);

    } while (strcmp(tmp, tmp2));
    printf("le mot de passe est alors : %s\n", tmp);
    fwrite(tmp, sizeof(char), strlen(tmp), f);
    strcpy(mdp,tmp);
    printf("Mot de passe enregistré avec succès...\n");
    waiter();
    fclose(f);
}
void menu()
{
    char motdepasse[MAX_WORD];

    //recupération mot de passe
    FILE *mdp = fopen("sauvegardes/mdp", "r");
    if (mdp)
    {
        fread(motdepasse, sizeof(char) * MAX_WORD, 1, mdp);
        fclose(mdp);
    }
    else
        modif_mot_de_passe(1, motdepasse);

    //PILE POUR L'AUDIO
    PILE_AUDIO pile_audio = init_PILE_AUDIO();
    int code;
    do
    {
        system("clear");
        //Affichage du menu
        printf("///\tMENU\t///\n");
        printf("1. Indexation\n2. Recherche\n3. Réinitialiser le mot de passe\n4. Retour\n");
        printf("Veuillez choisir une action :\n");
        scanf("%d", &code);
        if (code < 1 || code > 4)
        {
            printf("Veuillez choisir une action valide.\n");
            waiter();
        }

        else if (code == 1)
        {
            char test[MAX_WORD];
            printf("Veuillez entrer le mot de passe admin : ");
            scanf("%s", test);
            if (!strcmp(test, motdepasse))
            {
                menus_admin(&pile_audio);
            }
            else
            {
                printf("Erreur de le mot de passe, veuillez réessayer (ou reinitialiser le mot de passe)\n");
                waiter();
            }
        }
        else if (code == 2)
        {
            menus_user();
        }
        else if (code == 3)
            modif_mot_de_passe(0, motdepasse);

    } while (code != 4);
}


int main(void)
{
    //PILES ET TABLE POUR L'INDEXATION TEXTE

    menu();

    system("clear");
    return 0;
}
