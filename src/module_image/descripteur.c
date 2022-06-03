/**
 * @file descripteur.c
 * @author DUVIVIER Davy et BOSSAERTS Mathias
 * @brief Outils du descripteurs d'image
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */

#include "descripteur.h"
#include <stdio.h>



/**
 * @brief Outil permettant d'afficher un descripteur d'image
 * 
 * @param e un descripteur d'image
 */
void affiche_Descripteur_image(Descripteur_image e){
    printf("identifiant : %d \n", e.id);
    printf("affiche de de l'histogramme :\n");
    for (int i = 0; i < tailleHistogramme; i++)
    {
        printf("%d : %d fois",i,e.histogramme[i]);
    }
    printf("\n");

}

