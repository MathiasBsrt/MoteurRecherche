/**
 * @file outils.c
 * @author Baptiste POMARELLE
 * @brief Quelques fonctions annexes nécessaire au bon fonctionnement
 * @version 0.1
 * @date 2020-12-16
 * 
 * @copyright Copyright (c) 2020
 * 
 */
#include "Header.h"

void path_maker(char *chemin, char *nom_dossier, char *nom_fichier)
{
    strcpy(chemin, nom_dossier);
    strcat(chemin, "/");
    strcat(chemin, nom_fichier);
}

void lecture_dossier_texte(FILE *f, char *nom_dossier)
{
    struct dirent *dir;
    DIR *d = opendir(nom_dossier);

    while ((dir = readdir(d)) != NULL)
    {

        if (!strcmp(dir->d_name, ".") || !strcmp(dir->d_name, ".."))
            ; //on evite de lire les "." et ".."
        else
        {
            struct stat InfosFile;
            char chemin[MAX_WORD];
            path_maker(chemin, nom_dossier, dir->d_name);
            stat(chemin, &InfosFile);            //on recupere les stat du fichier lu pour savoir si c' est un dossier
            if (S_ISREG(InfosFile.st_mode) != 0) //on vérifie si c'est un fichier
            {

                fprintf(f, "%s\n", dir->d_name);
            }
        }
    }
    closedir(d);
    rewind(f); //on remet le pointeur du fichier au début
}