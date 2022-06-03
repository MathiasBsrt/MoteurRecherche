/**
 * @file descripteur.c
 * @author DUVIVIER Davy et BOSSAERTS Mathias
 * @brief header des outils du descripteurs d'image
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */

#define tailleHistogramme 64
#define tailleHistogrammeNB 8
#define quantificateur 2
#include <stdlib.h>
#include <stdio.h>

typedef struct Descripteur_image_t{
    int id;
    int histogramme[tailleHistogramme];
}Descripteur_image;

void affiche_Descripteur_image(Descripteur_image e);
Descripteur_image saisir_Descripteur_image();
void affecter_Descripteur_image(Descripteur_image e1, Descripteur_image e2);
int compareDescripteur_image(Descripteur_image e1, Descripteur_image e2);
