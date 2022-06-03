/**
 * @file mot.h
 * @author Baptiste POMARELLE
 * @brief Les fonctions relatives aux elements de type mot
 * @version 0.1
 * @date 2020-12-16
 * 
 * @copyright Copyright (c) 2020
 * 
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_WORD 128
#define MAX_STRING 512

/**
 * @struct ELEMENT
 * @brief Element de type MOTS
 * */
typedef struct f_mots
{
    char mot[MAX_WORD];
    int nbr_occurrence;

}MOT;

/**
 * @brief Fonction permettant d'afficher un element
 * @param[in] MOT e
 * */
void affiche_MOT(MOT e);

/**
 * @brief Fonction permettant d'entrer un mot a la main
 * 
 * @param MOT *e 
 */

void saisir_MOT(MOT *e);

/**
 * @brief Fonction permettant de comparer deux mots
 * 
 * @param[in] MOT a 
 * @param[in] MOT b 
 * @return 1 si c'est egal, 0 sinon
 */
int compare_MOT(MOT a,MOT b);

/**
 * @brief permet d'affecter une valeur a un mot
 * 
 * @param[in] word 
 * @return un mot 
 */
MOT affecter_MOT(char* word);
