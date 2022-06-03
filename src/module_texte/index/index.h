/**
 * @file index.h
 * @author Baptiste Pomarelle
 * @brief Fichier contenant les prototypes des fonctions relatives aux index de mots
 * @version 0.1
 * @date 2020-12-20
 * 
 * @copyright Copyright (c) 2020
 * 
 */
#include "../MOT/mot.h"
#include <unistd.h>

#define MAX_INDEX 50
/**
 * @brief Structure d'un index de mot
 * Permet de savoir dans quels textes et avec quelles occurences un mot est présent
 */

typedef struct noeud
{
    char mot[MAX_WORD];
    int nb_occ;
    int nb_max;
    int *idTxt_avec_occ[2];
    struct noeud *gauche;
    struct noeud *droit;
} Index;

typedef Index *Table_Index;


/**
 * @brief Permet d'initialiser une table d'index
 * 
 * @return Table_Index 
 */
Table_Index Init_Index();

/**
 * @brief Permet de savoir si une table d'index est vide ou non
 * 
 * @param table 
 * @return int 
 */
int Table_indexEstVide(Table_Index table);

/**
 * @brief Permet de savoir si un mot est déjà dans la table d'index
 * 
 * @param table 
 * @param mot 
 * @return Index* 
 */
Index* ExisteDansTable_Index(Table_Index table,char *mot);

/**
 * @brief Permet d'ajouter a un index de mot de nouvelles données
 * 
 * @param index 
 * @param id 
 * @param nbr_occ 
 */
void Incremente_index(Index* index,int id, int nbr_occ);


/**
 * @brief Permet d'ajouter un index dans une table d'index
 * 
 * @param table 
 * @param mot 
 * @return Index* 
 */
Index* Ajout_Dans_Table_index(Table_Index *table, char* mot);

/**
 * @brief Permet d'afficher une table d'index
 * 
 * @param a 
 */
void AFFICHE_table_index(Table_Index a);

/**
 * @brief Permet d'enregistrer une table d'index dans un fichier
 * 
 * @param table 
 * @param save_table_index chemin vers la sauvegarde 
 */
void enregistre_Table_Index(Table_Index table, char *save_table_index);

/**
 * @brief Permet de recharger une table d'index a partir d'un fichier
 * 
 * @param table 
 * @param save_table_index chemin vers la sauvegarde 
 */
void charger_Table_index(Table_Index *table, char *save_table_index);
