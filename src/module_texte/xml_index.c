/**
 * @file xml_index.c
 * @author Baptiste POMARELLE
 * @brief Fonctions d'indexations et de création de piles de descripteurs
 * @version 0.1
 * @date 2020-12-16
 * 
 * @copyright Copyright (c) 2020
 * 
 */

#include "Header.h"

/**
 * @brief Permet d'enlever un mot dans une pile de mots
 * 
 * @param[in,out] p 
 * @param[in] mot 
 */
void recherche_et_destruction(pile_mot *p, MOT mot)
{
    if (!PILE_estVide_mot(*p))

    {
        Cellule_mot *marqueur;
        Cellule_mot *parcours = *p;
        while (parcours)
        {
            //on cherche le MOT dans la pile
            if (compare_MOT(mot, parcours->elt))
            {
                marqueur = parcours;
                //une fois trouvé, on le marque
                break;
            }
            
            parcours = parcours->suivant;
        }
        parcours = *p;
        while (parcours->suivant != marqueur)
            parcours = parcours->suivant;
        //on relie directement le précédent du marqueur à son suivant
        parcours->suivant = marqueur->suivant;
        free(marqueur);
    }
    else
    {
        perror("la pile est completement vide");
    }
}

/**
 * @brief Permet d'obtenir une pile contenant les mots qui apparaissent ainsi que leurs occurences dans le texte
 * 
 * @param[in] src Descripteur du fichier texte 
 * @param[in,out] p La pile à remplir
 * @param[in] seuil le seuil a respecter
 */
void descripteur_de_texte(FILE *src, pile_mot *p, int seuil)
{
    char buffer[MAX_WORD];
    //Lecture des mots présents dans le fichier
    while (fscanf(src, "%s ", buffer) != EOF)
    {
        if (!(estDanslaPile_mot(*p, buffer)))
        {
            //s'il n'est pas dans la pile, on l'empile
            *p = emPILE_mot(*p, affecter_MOT(buffer));
            //puis on incrémente
            estDanslaPile_mot(*p, buffer);
        }
    }

    Cellule_mot *parcours = *p;
    //Si le seuil est != de 0, on vient supprimer les occurences
    if (seuil)

    {
        while (parcours)
        {
            if (parcours->elt.nbr_occurrence < seuil)
            {
                recherche_et_destruction(p, parcours->elt);
            }
            parcours = parcours->suivant;
        }
    }
}

/**
 * @brief Permet de savoir si un texte a déjà été indexé
 * 
 * @param path_to_xml chemin vers le fichier
 * @return retourne l'id du texte, 0 sinon 
 */
int texte_deja_indexe(char *path_to_xml)
{
    int code_retour = 0;
    //On récupère la liste des chemins de fichiers déjà indexés
    FILE *liste_base_desc = fopen("sauvegardes/txt/liste_base_descripteurs", "r");
    if (liste_base_desc)
    {

        int id_texte;
        char buffer[MAX_STRING];
        while (fscanf(liste_base_desc, "%s %d", buffer, &id_texte) != EOF)
        {

            if (!strcmp(buffer, path_to_xml))//Si la cehmin passé en paramètre est le même que celui lu, on retourne son id
            {
                code_retour = id_texte;

                break;
            }
        }
        fclose(liste_base_desc);
    }
    return code_retour;
}

/*void conversionUTF8(char* path_to_xml)
{
    char Retour[MAX_STRING]="iconv -f ISO-8859-1 -t UTF-8 ";
    strcat(Retour,path_to_xml);
    strcat(Retour," -o temp");
    system(Retour);
}*/

/**
 * @brief Permet de concevoir un descripteur et à l'empiler
 * 
 * @param[in] path_to_xml chemin vers un fichier xml
 * @param[in,out] pile_desc Pile de descripteurs
 * @return l'id attribué au texte
 */
int fabrique_a_descripteur(char *path_to_xml, PILE_descripteur_texte *pile_desc, Table_Index *table_index, int seuil)
{
    int id = 0;
    FILE *tmp = fopen("tmp", "w+");   //fichier de destination du xml_cleaner / source de xml_tokenizer
    FILE *tmp1 = fopen("tmp1", "w+"); // fichier de destination du xml_tokenizer
    FILE *src = fopen(path_to_xml, "r");

    pile_mot p = init_pile_mot();

    if (!src)
    {
        printf("erreur src %s\n", path_to_xml);
    }
    if (tmp && tmp1 && src)
    {
        if (!texte_deja_indexe(path_to_xml))
        {
            xml_cleaner(src, tmp); //première étape de nettoyage
            rewind(tmp); //on remet le curseur au début du fichier tmp
            xml_filter(tmp, tmp1); // deuxième étape de nettoyage
            rewind(tmp1); //on remet le curseur au début du fichier tmp 1

            descripteur_de_texte(tmp1, &p, seuil);

            id = EMPILE_desc_from_pile(p, pile_desc, path_to_xml, table_index);
        }
        else
        {
            perror("Texte déjà rentré !\n");
        }
        fclose(tmp);
        fclose(tmp1);
        fclose(src);
        remove("temp");
    }
    else
    {
        fprintf(stderr,"Erreur dans l'ouverture de %s",path_to_xml);
    }
    return id;
}

int Descripteur_texte_dossier(char *nom_dossier, PILE_descripteur_texte *pile_desc, Table_Index *table_index, int seuil)
{
    FILE *f = fopen("nom_fichiers.txt", "w+");
    if (f)
    {
        printf("--> Dossier en cours d'indexation\n");
        lecture_dossier_texte(f, nom_dossier); //On récupère tous les noms de fichiers présents dans la racine du dossier afin de les traiter
        char path_to_xml[MAX_WORD];
        char nom_fichier[MAX_WORD / 2];
        while (fscanf(f, "%s", nom_fichier) != EOF)
        {
            path_maker(path_to_xml, nom_dossier, nom_fichier); //permet de recuperer le chemin vers un fichier xml dans path_to_xml
            fabrique_a_descripteur(path_to_xml, pile_desc, table_index, seuil); //création des descripteurs 1 à 1
        }
        fclose(f);
        remove("tmp");
        remove("tmp1");
        remove("nom_fichiers.txt");
        return 1;
    }

    return 0;
}

int Descripteur_texte_fichier(char *nom_fichier, PILE_descripteur_texte *pile_desc, Table_Index *table_index, int seuil)
{
    int id;
    printf("--> Fichier en cours d'indexation\n");
    id = fabrique_a_descripteur(nom_fichier, pile_desc, table_index, seuil); //création du descripteur
    remove("tmp");
    remove("tmp1");
    return id;
}

/*
int main(void)
{
    PILE_descripteur_texte pile = init_PILE_desc();
    PILE_descripteur_texte pile1 = init_PILE_desc();
    Table_Index table = Init_Index();
    Table_Index table1 = Init_Index();

    Descripteur_texte_dossier("Textes_UTF8", &pile, &table, 1);
    //Descripteur_texte_fichier("Textes_UTF8/03-Mimer_un_signal_nerveux_pour_utf8.xml", &pile, &table, 1);
    //affiche_PILE_mots(pile1->pile_mot);

    //Enregistrement Index
<<<<<<< HEAD
    //enregistre_Table_Index(table, "sauvegarde.index");
    charger_Table_index(&table1, "sauvegarde.index");

    //Enregistrement Pile
    //enregistre_PILE_Desc(pile, "sauvegarde.desc");
    charger_PILE_Desc_mot(&pile1, "sauvegarde.desc");

    //Affichage de quelques resultats
    //affiche_PILE_mots(pile1->pile_mot);
    //printf("nombre total de mots : %d\tnombre de mots differents : %d\n", pile1->nombre_mots_total, pile1->nbr_mots_retenus);
    printf("nombre total de mots : %d\tnombre de mots differents : %d\n", pile->nombre_mots_total, pile->nbr_mots_retenus);
    AFFICHE_table_index(table1);
    remove("liste_base_descripteurs");
}*/