
/**
 * @file pile_dynamique.c
 * @author DUVIVIER Davy
 * @brief header des fonctions relatives a la pile de Descripteurs (et de leurs occurences) et a la pile de descripteurs
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */


#include <stdio.h>
#include <stdlib.h>
#include "descripteur.h"


typedef struct s_Cellule_image
{
    Descripteur_image elt;
    struct s_Cellule_image *suivant;
}Cellule_image;


typedef Cellule_image* PILE_image;

//FONCTIONS POUR PILES DE MOTS


PILE_image dePILE_image(PILE_image p, Descripteur_image *elt);


PILE_image emPILE_image(PILE_image p, Descripteur_image elt);

PILE_image init_pile_image();


void affiche_PILE_image(PILE_image p);


int PILE_estVide_image(PILE_image p);


int estDanslaPile_image(PILE_image p,char *buffer);

PILE_image chargerPILE_image(char* cheminFichier,int RGB_ou_NB);
PILE_image inverserPILE_image(PILE_image pile);



