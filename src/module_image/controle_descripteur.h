/**
 * @file controle_descripteur.h
 * @author DUVIVIER Davy et BOSSAERTS Mathias
 * @brief header de la partie d'indexation des images
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <libgen.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

#include "pile_dynamique.h"
//STRUCTURES
/**
  *Structure pour une matrice rgb
  *.red .green .blue accès à la couleur voulu
*/
typedef struct RGB_t{
  int red;
  int green;
  int blue;
}RGB;


//FONCTIONS

void lancer_indexation_image();


int quantifie_un_pixelRGB(RGB pixel);
int quantificationRGB(RGB **matriceImageRGB,int *matriceImageQuant[],int lignes,int colonnes);

int creationHistogramme(int *matriceImageQuant[],Descripteur_image *newDesc,int lignes,int colonnes); // doit créer l'histo et remplir l'attribut histogramme du Descripteur_image
void creationDescripteur_image(char *chemin,PILE_image * p);

PILE_image SauvegardeDescripteur_image(Descripteur_image nouveau, PILE_image p, char *nom,int RGB_ou_NB);

void sauvegarderPile_image(PILE_image p,int RGB_ou_NB);

int lire_imageNB(int lignes, int colonnes, int* matriceImage[], FILE *image);
int lire_imageRGB(int lignes, int colonnes, RGB** matriceImage, FILE *image);
int quantificationNB(int **matriceImageNB,int** matriceImageQuant,int lignes,int colonnes);

void lierDescripteur_image(Descripteur_image d, char *nom, int RGB_ou_NB);

void genererDescripteur_image(char *cheminImage, int RGB_ou_NB);
void genererDescripteur_imageDossier(char *cheminDossier,int RGB_ou_NB);
void genererDescripteur_image(char *cheminImage, int RGB_ou_NB);