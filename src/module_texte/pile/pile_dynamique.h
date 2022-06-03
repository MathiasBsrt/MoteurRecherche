/**
 * @file pile_dynamique.h
 * @author Baptiste POMARELLE
 * @brief Les fonctions relatives a la pile de mots (et de leurs occurences) et a la pile de descripteurs
 * @version 0.1
 * @date 2020-12-16
 * 
 * @copyright Copyright (c) 2020
 * 
 */

#include "../index/index.h"

/**
 * @brief Structure d'une Cellule_mot
 * 
 */
typedef struct s_Cellule_mot
{
    MOT elt;
    struct s_Cellule_mot *suivant;
}Cellule_mot;

/**
 * @brief Structure d'une pile de mots avec occurences
 * 
 */
typedef Cellule_mot* pile_mot;

/**
 * @brief Strucutre d'un descripteur de texte
 * 
 */
typedef struct s_Descripteur
{
    int id;
    pile_mot pile_mot;
    int nbr_mots_retenus;
    int nombre_mots_total;
    struct s_Descripteur* suivant;
}Descripteur_texte;

/**
 * @brief Structure d'une pile de Descripteurs de textes
 * 
 */
typedef Descripteur_texte* PILE_descripteur_texte;




//FONCTIONS POUR PILES DE MOTS

/**
 * @brief Permet de dépiler un élément d'une pile de mots
 * 
 * @param[in] p 
 * @param[in,out] elt 
 * @return La pile sans le dernier element 
 */
pile_mot dePILE_mot(pile_mot p, MOT *elt);

/**
 * @brief Permet d'empiler un élément d'une pile de mots
 * 
 * @param[in] p 
 * @param[in] elt 
 * @return La pile avec elt
 */
pile_mot emPILE_mot(pile_mot p, MOT elt);

/**
 * @brief Permet d'initialiser une pile de mots
 * 
 * @return une pile vide
 */
pile_mot init_pile_mot();

/**
 * @brief Permet d'afficher une pile de mots
 * 
 * @param[in] p 
 */
void affiche_PILE_mots(pile_mot p);

/**
 * @brief Permet de dire si une pile est vide ou non
 * 
 * @param[in] p 
 * @return 1 si vrai, 0 sinon
 */
int PILE_estVide_mot(pile_mot p);

/**
 * @brief Permet de dire si un mot est dans une pile de mots
 * 
 * @param[in] p 
 * @param[in] buffer 
 * @return 1 si vrai, 0 sinon
 */
int estDanslaPile_mot(pile_mot p, char *buffer);



// FONCTIONS POUR PILES DE DESCRIPTEURS

/**
 * @brief Permet d'initialiser une pile de descripteurs
 * 
 * @return une pile de descripteurs vide
 */
PILE_descripteur_texte init_PILE_desc();

/**
 * @brief Permet de dire si une pile de descripteurs est vide
 * 
 * @param[in] d 
 * @return 1 si vrai, 0 sinon
 */
int PILE_desc_estVide(PILE_descripteur_texte d);

/**
 * @brief Permet d'empiler un descripteur dans une pile de descripteurs a partir d'une pile
 * 
 * @param[in] p 
 * @param[in,out] d 
 * @param[in] path_to_xml Chemin vers le fichier xml
 * @param[in] table 
 * @return l'id du texte indexé
 */
int EMPILE_desc_from_pile(pile_mot p, PILE_descripteur_texte *d, char *path_to_xml,Table_Index *table);

/**
 * @brief Permet d'empiler un descripteur dans une pile de descripteurs
 * 
 * @param d 
 * @param descripteur 
 */
void EMPILE_desc(PILE_descripteur_texte *d, Descripteur_texte descripteur, FILE* stream);

/**
 * @brief Permet de depiler un le dernier descripteur dans la pile de descripteurs
 * 
 * @param[in] p
 * @return Le descripteur dépilé 
 */
Descripteur_texte* DEPILE_desc(PILE_descripteur_texte *p);


/**
 * @brief Permet d'enregistrer une pile de descripteurs
 * 
 * @param[in] p 
 * @param[in] save_descripteurs_textes 
 */
void enregistre_PILE_Desc(PILE_descripteur_texte p, char *save_descripteurs_textes);

/**
 * @brief Permet de charger une pile de descripteurs a partir d'un fichier
 * 
 * @param[in,out] p 
 * @param[in] save_descripteurs_textes 
 */
void charger_PILE_Desc_mot(PILE_descripteur_texte *p, char *save_descripteurs_textes);