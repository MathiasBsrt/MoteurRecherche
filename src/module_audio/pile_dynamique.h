/**
 * @file pile_dynamique.h
 * @author Baptiste POMARELLE
 * @brief Spécification d'une pile dynamique de descripteurs.
 */

#ifndef PILE_DESC_AUDIO_H_INCLUS 

#define PILE_DESC_AUDIO_H_INCLUS

#include <stdio.h>
#include <stdlib.h>
#include "descripteur.h"

typedef struct cellule_audio_t{
  DESC_AUDIO elem;
  struct cellule_audio_t* suivant;
}CELLULE_AUDIO;
typedef CELLULE_AUDIO* PILE_AUDIO;


PILE_AUDIO init_PILE_AUDIO();
void affiche_PILE_AUDIO(PILE_AUDIO pile);
int PILE_estVide_AUDIO(PILE_AUDIO pile);
PILE_AUDIO emPILE_AUDIO(PILE_AUDIO pile, DESC_AUDIO elem);
PILE_AUDIO dePILE_AUDIO(PILE_AUDIO pile, DESC_AUDIO* elem);
//PILE saisir_PILE();

#endif