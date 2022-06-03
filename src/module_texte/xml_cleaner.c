/**
 * @file xml_cleaner.c
 * @author Baptiste POMARELLE
 * @brief Fonctions permettant de nettoyer un fichier xml et d'obtenir un .xml
 * @version 0.1
 * @date 2020-12-16
 * 
 * @copyright Copyright (c) 2020
 * 
 */

#include "Header.h"


/**
 * @brief Permet d'ajouter un caractère à une chaine
 * 
 * @param[out] buffer chaine de retour
 * @param[in] c 
 * @param[in] cpt 
 */
void add_char(char *buffer, char c, int *cpt)
{
    c = tolower(c);
    buffer[*cpt] = c;
    *cpt = *cpt + 1;
}

/**
 * @brief Permet de déterminer si le caractère donné en paramètre est une ponctuation
 * 
 * @param[in] c 
 * @return 1 si c'est de la ponctuation, 0 sinon
 */
int estPonctuation(char c)
{
    return (c == '.' || c == ',' || c == '?' || c == ';' || c == '!' || c == '\'' || c == ':' || c == '"' || c == '&' || c == '(' || c == ')' || c == '-' || c == '_' || c == '%' || !strcmp("$",&c) || !strcmp("€",&c) || !strcmp("£",&c) || !strcmp("«",&c) || !strcmp("»",&c) || !strcmp("ةtة",&c));
}


void xml_cleaner(FILE *src, FILE *dest)
{
    char parcours;
    char buffer[MAX_STRING];
    int nbr_char = 0;
    parcours = fgetc(src);
    do
    {
        if (parcours == '<')
        //On enleve les balises 
            while ((parcours = fgetc(src)) != '>')
                ;
        else if (nbr_char < MAX_STRING - 1)
        {
            if (estPonctuation(parcours))
            //On enleve la ponctuation
                add_char(buffer, ' ', &nbr_char);
            else
            //on récupère le mot lettre a lettre dans la variable buffer
                add_char(buffer, parcours, &nbr_char);
        }
        else
        {
            //si le buffer est plein, on le sauvegarde dans le fichier dest
            fprintf(dest, "%s", buffer);
            strcpy(buffer, "");
            nbr_char = 0;
            if (estPonctuation(parcours))
                add_char(buffer, ' ', &nbr_char);
            add_char(buffer, parcours, &nbr_char);
        }

    } while ((parcours = fgetc(src)) != EOF);
    if (nbr_char != 0)
    {
        buffer[nbr_char] = '\0';
        fprintf(dest, "%s", buffer);
    }
}
