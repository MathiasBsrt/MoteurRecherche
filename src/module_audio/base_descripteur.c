/**
 * @file base_descripteur.c
 * @author Théo TRAFNY
 * @brief L'ensemble de fichier base_descripteur.h et base_descripteur.c sont là pour donner
 * au programme principal un ensemble d'outils (structure, méthodes et macros) pour
 * pouvoir intéragir facilement avec les deux fichiers de sauvegardes
 * définis par les macros BASE_DESC_FICHIER et LISTE_BASE_FICHIER.
 * La méthode de recherche est aussi présente dans ces fichiers car le programme utilise les deux fichiers ci-dessus.
 * C'est pourquoi la spécification et l'implémentation de cette fonction ce trouve ici.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include "pile_dynamique.h"
#include "base_descripteur.h"
#include "descripteur.h"
#include "histogramme.h"

/**
 * @file base_descripteur.c
 * @author Théo TRAFNY
 * @brief Les fonctions relatives à la gestion des liens existants entre les descripteurs audio, et les élements exterieurs.
 * @version 0.1
 * @date 2021-01-19
 *
 * @copyright Copyright (c) 2020
 *
 */

void init_FICHIER_BASE_DESC()
{
	// On créé le fichier BASE_DESC_FICHIER si il n'existe pas
	FILE *file;
	FILE *fp;
    if ((file = fopen(BASE_DESC_FICHIER, "r"))){
        fclose(file);
	} else {
		fp = fopen(BASE_DESC_FICHIER, "ab+");
		int val = EOF;
		fwrite(&val, 4, 1, fp);
		fclose(fp);
	}
	

	// On créé le fichier LISTE_BASE_FICHIER si il n'existe pas
    if ((file = fopen(LISTE_BASE_FICHIER, "r"))){
        fclose(file);
	} else {
		fp = fopen(LISTE_BASE_FICHIER, "ab+");
		fprintf(fp, "-1");
		fclose(fp);
	}
}

PILE_AUDIO sauvegarder_DESC_AUDIO(PILE_AUDIO PILE_DESCRIPTEUR_AUDIO, DESC_AUDIO desc)
{
	PILE_DESCRIPTEUR_AUDIO = emPILE_AUDIO(PILE_DESCRIPTEUR_AUDIO, desc);
	return PILE_DESCRIPTEUR_AUDIO;
}


void sauvegarder_PILE_DESC_AUDIO(PILE_AUDIO PILE_DESCRIPTEUR_AUDIO)
{
	if(PILE_DESCRIPTEUR_AUDIO == NULL)
	{
		fprintf(stderr, "[SAUVEGARDER_PILE_DESC_AUDIO] Vous devez d'abord initialiser la PILE avant de pouvoir la sauvegarder.");
		exit(1);
	}
	FILE * baseDescFichier = fopen(BASE_DESC_FICHIER, "w");
	if(baseDescFichier == NULL)
	{
		fprintf(stderr, "[SAUVEGARDER_PILE_DESC_AUDIO] Impossible de charger le fichier %s en écriture.", BASE_DESC_FICHIER);
		exit(1);
	}
	DESC_AUDIO desc;
	while(!PILE_estVide_AUDIO(PILE_DESCRIPTEUR_AUDIO))
	{
		PILE_DESCRIPTEUR_AUDIO = dePILE_AUDIO(PILE_DESCRIPTEUR_AUDIO, &desc);
		//fprintf(baseDescFichier, "%d %d %d ", desc.id, desc.histo.k, desc.histo.m);
		fwrite(&(desc.id), 4, 1, baseDescFichier);
		fwrite(&(desc.histo.k), 4, 1, baseDescFichier);
		fwrite(&(desc.histo.m), 4, 1, baseDescFichier);
		fwrite(desc.histo.mat, 4, desc.histo.k * desc.histo.m, baseDescFichier);
		/*
		int y, x;
		for(y = 0; y < desc.histo.k; y++)
			for(x = 0; x < desc.histo.m; x++)
			{
				fprintf(baseDescFichier, "%d ", get_HISTOGRAMME_AUDIO(desc.histo, y, x));
			}
		fprintf(baseDescFichier, "%c", '\n');
		*/
	}
	//fprintf(baseDescFichier, "%d", EOF);
	int pEOF = EOF;
	fwrite(&pEOF, 4, 1, baseDescFichier);
	fclose(baseDescFichier);
}


PILE_AUDIO charger_PILE_DESC_AUDIO(int * nb_charge)
{
	PILE_AUDIO PILE_DESCRIPTEUR_AUDIO = init_PILE_AUDIO();
	FILE * baseDescFichier = fopen(BASE_DESC_FICHIER, "r");
	if(nb_charge != NULL) *nb_charge = 0;
	if(baseDescFichier == NULL)
	{
		fprintf(stderr, "[CHARGER_PILE_DESC_AUDIO] Impossible de charger le fichier %s en lecture.", BASE_DESC_FICHIER);
		exit(1);
	}
	DESC_AUDIO * desc;
	int val;
	int k, m;
	//fscanf(baseDescFichier, "%d ", &val);
	fread(&val, 4, 1, baseDescFichier);
	if(val != EOF)
		do
		{
			fread(&k, 4, 1, baseDescFichier);
			fread(&m, 4, 1, baseDescFichier);
			//fscanf(baseDescFichier, "%d %d", &k, &m);
			desc = (DESC_AUDIO *) malloc(sizeof(DESC_AUDIO));
			desc->id = val;
			desc->histo = init_HISTOGRAMME_AUDIO((int) log2(k), m);
			fread(desc->histo.mat, 4, desc->histo.k * desc->histo.m, baseDescFichier);
			/*for(y = 0; y < desc->histo.k; y++)
				for(x = 0; x < desc->histo.m; x++)
				{
					fscanf(baseDescFichier, "%d ", &val);
					set_DESC_AUDIO(desc, y, x, val);
				}
			*/
			PILE_DESCRIPTEUR_AUDIO = emPILE_AUDIO(PILE_DESCRIPTEUR_AUDIO, *desc);
			if(nb_charge != NULL) *nb_charge = *nb_charge + 1;

			//fscanf(baseDescFichier, "%d ", &val);
			fread(&val, 4, 1, baseDescFichier);
		} while(val != EOF);

	fclose(baseDescFichier);
	return PILE_DESCRIPTEUR_AUDIO;
}


DESC_AUDIO charger_byid_DESC_AUDIO(int id)
{
	FILE * baseDescFichier = fopen(BASE_DESC_FICHIER, "r");
	if(baseDescFichier == NULL)
	{
		fprintf(stderr, "[CHARGER_DESC_AUDIO] Impossible de charger le fichier %s en lecture.", BASE_DESC_FICHIER);
		exit(1);
	}
	DESC_AUDIO desc;
	desc.id = -1;
	int val;
	int k, m;
	//fscanf(baseDescFichier, "%d ", &val);
	fread(&val, 4, 1, baseDescFichier);
	do
	{
		fread(&k, 4, 1, baseDescFichier);
		fread(&m, 4, 1, baseDescFichier);
		//fscanf(baseDescFichier, "%d %d", &k, &m);
		desc.id = val;
		desc.histo = init_HISTOGRAMME_AUDIO((int) log2(k), m);
		fread(desc.histo.mat, 4, desc.histo.k * desc.histo.m, baseDescFichier);
		if(val == id) break;
		fread(&val, 4, 1, baseDescFichier);
	} while(val != EOF && desc.id != id);
	if(val == EOF) desc.id = -1;
	fclose(baseDescFichier);
	return desc;
}


DESC_AUDIO charger_byname_DESC_AUDIO(char * chemin)
{
	DESC_AUDIO desc;
	int id = get_id_byname_DESC_AUDIO(chemin);
	desc.id = id;
	if(id == ID_NOT_FOUND) return desc;
	return charger_byid_DESC_AUDIO(id);
}

PILE_AUDIO init_MULTIPLE_DESC_AUDIO(int start_id, int n, int m, char * cheminDir)
{
	struct dirent *dir;
    DIR *d = opendir(cheminDir);
    PILE_AUDIO pile = init_PILE_AUDIO();
    DESC_AUDIO * desc;
    int id = start_id;

    while ((dir = readdir(d)) != NULL)
    {

        if (!strcmp(dir->d_name, ".") || !strcmp(dir->d_name, ".."))
            ; //on evite de lire les "." et ".."
        else
        {
            struct stat InfosFile;
            char chemin[128];
            strcpy(chemin, cheminDir);
		    strcat(chemin, "/");
		    strcat(chemin, dir->d_name);
            stat(chemin, &InfosFile);            //on recupere les stat du fichier lu pour savoir si c' est un dossier
            if (S_ISREG(InfosFile.st_mode) != 0) //on vérifie si c'est un fichier
            {
            	//printf("%s\n", chemin);
				if(deja_genere_DESC_AUDIO(chemin) == ALREADY_GENERATED)
				{
					printf("Fichier '%s' déjà indexé, saut de l'indexation de ce fichier.\n", chemin);
 					continue;
				}
            	desc = (DESC_AUDIO *) malloc(sizeof(DESC_AUDIO));
            	*desc = init_DESC_AUDIO(id, n, m, chemin);
				lier_DESC_AUDIO_FICHIER(*desc, chemin);
            	pile = emPILE_AUDIO(pile, *desc);
            	id++;
            }
        }
    }
    closedir(d);
    return pile;
}


int lier_DESC_AUDIO_FICHIER(DESC_AUDIO desc, char * chemin)
{
	if(deja_genere_DESC_AUDIO(chemin) == ALREADY_GENERATED) return 0;
	FILE * listeBaseFichier = fopen(LISTE_BASE_FICHIER, "rw");
	char tmpFichier[100];
	strcpy(tmpFichier, LISTE_BASE_FICHIER);
	strcat(tmpFichier, "_tmp");
	FILE * tmp = fopen(tmpFichier, "ab+");
	int returnCode = 0;
	int id;
	char fichier[100];
	fscanf(listeBaseFichier, "%d %s", &id, fichier);
	while(id != EOF)
	{
		if(id == desc.id) // Le descripteur est déjà lié.
		{
			fprintf(tmp, "%d %s\n", desc.id, chemin);
			returnCode = 1;
		} else
		{
			fprintf(tmp, "%d %s\n", id, fichier);
		}
		fscanf(listeBaseFichier, "%d %s", &id, fichier);	
	}
	if(returnCode == 0) fprintf(tmp, "%d %s\n", desc.id, chemin);
	fprintf(tmp, "%d", EOF);

	fclose(listeBaseFichier);
	fclose(tmp);

	char command[100];
	strcpy(command, "mv ");
	strcat(command, tmpFichier);
	strcat(command, " ");
	strcat(command, LISTE_BASE_FICHIER);
	system(command);

	return returnCode;
}


int get_id_byname_DESC_AUDIO(char * chemin)
{
	FILE * listeBaseFichier = fopen(LISTE_BASE_FICHIER, "r");
	int id;
	char fichier[100];
	fscanf(listeBaseFichier, "%d %s", &id, fichier);
	while(id != EOF)
	{
		if(strcmp(fichier, chemin) == 0) // On a trouvé le descripteur
		{
			return id;
		}
		fscanf(listeBaseFichier, "%d %s", &id, fichier);	
	}
	return ID_NOT_FOUND;
}


int deja_genere_DESC_AUDIO(char * chemin)
{
	FILE * listeBaseFichier = fopen(LISTE_BASE_FICHIER, "r");
	int id;
	char fichier[100];
	fscanf(listeBaseFichier, "%d %s", &id, fichier);
	while(id != EOF)
	{
		if(strcmp(fichier, chemin) == 0) // On a trouvé le descripteur
		{
			return ALREADY_GENERATED;
		}
		fscanf(listeBaseFichier, "%d %s", &id, fichier);	
	}
	return 0;
}


char * fichier_lier_DESC_AUDIO(DESC_AUDIO desc)
{
	char * filename = (char *) malloc(sizeof(char) * 100);
	FILE * listeBaseFichier = fopen(LISTE_BASE_FICHIER, "rw");
	if(listeBaseFichier == NULL) return NULL;

	int id;
	do
	{
		fscanf(listeBaseFichier, "%d", &id);
		if(id == EOF) break;
		fscanf(listeBaseFichier, "%s", filename);
	} while(id != desc.id);
	if(id == EOF) return NULL;
	return filename;
}


RES_RECHERCHE_AUDIO rechercher_DESC_AUDIO(char * source, unsigned int n, double threshold, int * code)
{
	RES_RECHERCHE_AUDIO resultat;
	resultat.n = 0;

	DESC_AUDIO desc_source = charger_byname_DESC_AUDIO(source);
	if(desc_source.histo.mat == NULL) { *code = RECHERCHE_ERREUR; return resultat; }

	int nb_charges;
	PILE_AUDIO descripteurs = charger_PILE_DESC_AUDIO(&nb_charges);
	int nb_retenus = 0;
	RES_EVAL_AUDIO * resultats_evals = (RES_EVAL_AUDIO *) malloc(sizeof(RES_EVAL_AUDIO) * nb_charges);

	DESC_AUDIO desc;

	while(!PILE_estVide_AUDIO(descripteurs))
	{
		descripteurs = dePILE_AUDIO(descripteurs, &desc);
		if(desc.id == desc_source.id) { free_DESC_AUDIO(desc); continue; } // On saute le descripteur source

		RES_EVAL_AUDIO resultat_eval = evaluer_DESC_AUDIO(desc, desc_source, n, threshold);
		if(resultat_eval.n != 0)
		{
			resultats_evals[nb_retenus] = resultat_eval;
			nb_retenus++;
		}
		free_DESC_AUDIO(desc);
	}

	resultat.n = nb_retenus;
	resultat.resultats = (RES_EVAL_AUDIO *) malloc(sizeof(RES_EVAL_AUDIO) * nb_retenus);
	for(int i = 0; i < nb_retenus; i++)
	{
		RES_EVAL_AUDIO res;
		res.fichier = resultats_evals[i].fichier;
		res.n = resultats_evals[i].n;
		res.times = resultats_evals[i].times;
		resultat.resultats[i] = res;
	}

	free(resultats_evals);
	return resultat;
}

void free_RES_RECHERCHE_AUDIO(RES_RECHERCHE_AUDIO resultat)
{
	for(int i = 0; i < resultat.n; i++)
	{
		free_RES_EVAL_AUDIO(resultat.resultats[i]);
	}
	free(resultat.resultats);
}


int recuperer_nouvel_id_valide_AUDIO()
{
	FILE * listeBaseFichier = fopen(LISTE_BASE_FICHIER, "r");
	int id;
	int lastId = -1;
	char fichier[100];
	fscanf(listeBaseFichier, "%d %s", &id, fichier);
	while(id != EOF)
	{
		lastId = id;
		fscanf(listeBaseFichier, "%d %s", &id, fichier);	
	}
	return lastId + 1;
}