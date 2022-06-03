/**
 * @file xml_tokenizer.c
 * @author Baptiste POMARELLE
 * @brief Fonctions de nettoyage de fichiers .clean
 * @version 0.1
 * @date 2020-12-16
 * 
 * @copyright Copyright (c) 2020
 * 
 */

#include "Header.h"
/**
 * @brief Permet de créer une pile contenant tous les stopwords
 * 
 * @return pile_mot pile de stopwords
 */
pile_mot pile_stopwords()
{
    FILE *stopwords = fopen("sauvegardes/txt/stopwords.txt", "r");
    if (stopwords)
    {
        char buffer[MAX_WORD];
        pile_mot pile_stopwords = init_pile_mot();
        while (fscanf(stopwords, "%s", buffer) != EOF) //mise en mémoire des stopwords
            pile_stopwords = emPILE_mot(pile_stopwords, affecter_MOT(buffer));

        fclose(stopwords);
        return pile_stopwords;
    }
    else
    {
        fprintf(stderr, "Probleme d'ouverture des stopwords");
        exit(2);
    }
}

void xml_filter(FILE *src, FILE *dest)

{
    char buffer[MAX_WORD];
    pile_mot pileStopwords = pile_stopwords(); //on récupère la liste des stopwords dans une pile de mots
    while (fscanf(src, "%s ", buffer) != EOF) //lecture du fichier a filtrer
    {
        if (!estDanslaPile_mot(pileStopwords, buffer)) //si le mot lu est différent de tous les stopwords, alors c'est bon
            fprintf(dest, "%s ", buffer);
    }
}