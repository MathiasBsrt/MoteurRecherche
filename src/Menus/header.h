/**
 * @file header.h
 * @author Baptiste POMARELLE
 * @brief 
 * @version 0.1
 * @date 2021-01-16
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include "../module_texte/Header.h"
#include "../module_image/moteur_recherche/moteur.h"
#include "../module_audio/base_descripteur.h"

//1. Outils pour le menu administrateur 

//1.1 Outils partie texte:


/**
 * @brief sous menu permettant de gérer l'indexation de fichiers textes
 * 
 *  
 */
void MenuIndexation_texte();



//1.2 Outils partie image :
/**
 * @brief sous menu permettant de gérer l'indexation de fichiers images
 * 
 */
void MenuIndexation_image();


//1.3 Outils partie audio

/**
 * @brief sous menu permettant de gérer l'indexation de fichiers audios
 * 
 */
void MenuIndexation_audio(PILE_AUDIO *pile_audio);

//2 Outils pour le menu user

//1.1 Recherche texte

/**
 * @brief sous menu permettant la recherche de fichiers texte
 * 
 */
void MenuRecherche_texte();

//1.2 Recherche image

/**
 * @brief sous menu permettant la recherche de fichiers image
 * 
 */
void MenuRecherche_image();


//1.3 Recherche audio

/**
 * @brief sous menu permettant la recherche de fichiers audio
 * 
 */
void MenuRecherche_audio();
//3 outils généraux

/**
 * @brief Permet à l'utilisateur d'avoir à appuyer sur entrer pour continuer
 * 
 */
void waiter();