#include "pile_dynamique.h"
#include "descripteur.h"

/**
 * @file pile_dynamique.c
 * @author Baptiste POMARELLE
 * @brief Les fonctions relatives a la pile de Descripteurs (et de leurs occurences) et a la pile de descripteurs
 * @version 0.1
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */

PILE_AUDIO init_PILE_AUDIO()
{
    PILE_AUDIO p;
    p = NULL;
    return p;
}

int PILE_estVide_AUDIO(PILE_AUDIO p)
{
    return (p == NULL);
}

void affiche_PILE_AUDIO(PILE_AUDIO p)
{
    if (PILE_estVide_AUDIO(p))
        fprintf(stderr, "La pile est vide\n");

    else
    {
        CELLULE_AUDIO *parcours = p;
        printf("\n\n\t AFFICHAGE DES DescripteurS ET LEURS OCCURENCES\n");
        do
        {
            affiche_DESC_AUDIO(parcours->elem);
            parcours = parcours->suivant;
        } while (parcours != NULL);
        printf("\n");
    }
}

PILE_AUDIO emPILE_AUDIO(PILE_AUDIO p, DESC_AUDIO elt)
{
    CELLULE_AUDIO *cel = malloc(sizeof(CELLULE_AUDIO));
    cel->suivant = NULL;
    cel->elem = elt;
    if (PILE_estVide_AUDIO(p))
    {
        p = cel;
    }
    else
    {
      cel->suivant=p;
        // Cellule *parcours = p;
        // while (parcours->suivant != NULL)
        //     parcours = parcours->suivant;
        // parcours->suivant = cel;
    }
    return cel;
}

PILE_AUDIO dePILE_AUDIO(PILE_AUDIO p, DESC_AUDIO *elt)
{
    if (PILE_estVide_AUDIO(p))
        fprintf(stderr, "La pile est deja vide");
    else
    {
        if (p->suivant == NULL)
        {
            *elt = p->elem;
            p=NULL;
        }
        else
        {
            CELLULE_AUDIO* marqueur=p;
            *elt = p->elem;
            p=p->suivant;
            marqueur=NULL;
            free(marqueur);
        }
    }

    return p;
}