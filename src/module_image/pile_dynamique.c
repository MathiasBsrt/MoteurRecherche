#include "pile_dynamique.h"

/**
 * @file pile_dynamique.c
 * @author DUVIVIER Davy
 * @brief Les fonctions relatives a la pile de Descripteurs (et de leurs occurences) et a la pile de descripteurs
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */

PILE_image init_pile_image()
{
    PILE_image p;
    p = NULL;
    return p;
}

int PILE_estVide_image(PILE_image p)
{
    return (p == NULL);
}

void affiche_PILE_image(PILE_image p)
{
    if (PILE_estVide_image(p))
        fprintf(stderr, "La pile est vide\n");

    else
    {
        Cellule_image *parcours = p;
        printf("\n\n\t AFFICHAGE DES DescripteurS ET LEURS OCCURENCES\n");
        do
        {
            affiche_Descripteur_image(parcours->elt);
            parcours = parcours->suivant;
        } while (parcours != NULL);
        printf("\n");
    }
}
/**
 * @brief Empile un descripteur d'image dans la pile
 * 
 * @param p 
 * @param elt 
 * @return PILE_image 
 */
PILE_image emPILE_image(PILE_image p, Descripteur_image elt)
{
    Cellule_image *cel = malloc(sizeof(Cellule_image));
    cel->suivant = NULL;
    cel->elt = elt;
    if (PILE_estVide_image(p))
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

/**
 * @brief Dépile un élément de la pile
 * 
 * @param p 
 * @param elt 
 * @return PILE_image, Descripteur_image
 */
PILE_image dePILE_image(PILE_image p, Descripteur_image *elt)
{
    if (PILE_estVide_image(p))
        fprintf(stderr, "La pile est deja vide");
    else
    {
        if (p->suivant == NULL)
        {
            *elt = p->elt;
            free(p);
            p=NULL;
        }
        else
        {
            Cellule_image* marqueur=p;
            *elt = p->elt;
            p=p->suivant;
            marqueur=NULL;
            free(marqueur);
        }
    }

    return p;
}
/**
 * @brief retourne la pile pour un affichage plus clair dans le fichier
 * 
 * @param pile 
 * @return PILE_image 
 */
PILE_image inverserPILE_image(PILE_image pile){
  PILE_image sub=init_pile_image();
  Descripteur_image elem;
  while(!PILE_estVide_image(pile)){
    pile=dePILE_image(pile,&elem);
    sub=emPILE_image(sub,elem);
  }
  return  sub;
}
/**
 * @brief Charge la pile de descripteur d'image déjà indexées
 * 
 * @param cheminFichier 
 * @param RGB_ou_NB 
 * @return PILE_image 
 */
PILE_image chargerPILE_image(char* cheminFichier,int RGB_ou_NB){
  PILE_image pile=init_pile_image();
  FILE* fich=fopen(cheminFichier,"r");
  if(fich){
    Descripteur_image desc;
    while(fscanf(fich,"%d",&desc.id)!=EOF){
      if(RGB_ou_NB==1){
        for(int i=0;i<tailleHistogrammeNB;i++){
          fscanf(fich,"%d",&desc.histogramme[i]);
        }
      }
      else{
        for(int i=0;i<tailleHistogramme;i++){
          fscanf(fich,"%d",&desc.histogramme[i]);
        }
      }
      pile=emPILE_image(pile,desc);
    }
  }

  return pile;
}
