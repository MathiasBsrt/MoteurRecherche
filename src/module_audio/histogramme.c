/**
 * @file histogramme.c
 * @author Théo TRAFNY
 * @brief L'ensemble de fichier histogramme.h et histogramme.c sont là pour donner
 * au programme principal un ensemble d'outils (structure, méthodes et macros) pour
 * pouvoir manipuler efficacement et simplement le concept d'histogramme audio.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "histogramme.h"
#include "wav_file_helper.h"

// Normalement la valeur minimale que peux prendre un short int (2 octets)
// est -32768, mais pour le bien de la conversion dans un interval [-1; 1],
// +1 a été ajouté.
#define SHORT_INT_MIN_VALUE -32767
#define SHORT_INT_MAX_VALUE 32767


HISTOGRAMME_AUDIO init_HISTOGRAMME_AUDIO(unsigned int n, unsigned int m)
{
	HISTOGRAMME_AUDIO histo;
	histo.k = (int) pow(2, n); // Nombre de fenêtre d'analyse
	histo.m = m; // Nombre d'intervalles
	histo.mat = (int *) malloc(sizeof(int) * histo.k * m);
	unsigned int y, x;
	for(y = 0; y < histo.k; y++)
		for(x = 0; x < histo.m; x++)
			set_HISTOGRAMME_AUDIO(&histo, y, x, 0);
	return histo;
}

void affiche_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO histo)
{
	unsigned int y, x;
	printf("k = %d, m = %d\n", histo.k, histo.m);
	printf("y \\ x\n");
	for(y = 0; y < histo.k; y++)
	{
		/* Retirer car à re-programmer 
		   (affichage supérieur des indices de la matrice)
		if(y == -1) 
		{ 
			for(x = 0; x < histo.m; x++) printf("%d\t", x);
			printf("\n");
			for(x = 0; x < histo.m; x++) printf("-\t", x);
			printf("\n");
		}
		else*/
		for(x = 0; x < histo.m; x++)
		{
			if(x == 0) printf("%d | \t", y);
			printf("%d\t", get_HISTOGRAMME_AUDIO(histo, y, x));
		}
		printf("\n");
	}
}

unsigned int get_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO histo, unsigned int k, unsigned int m)
{
	if(/*k < 0 ||*/ k >= histo.k /*|| m < 0*/ || m >= histo.m)
	{
		fprintf(stderr, "[HISTOGRAMME_AUDIO] Impossible de récuperer la valeur à l'index (%d,%d) OUT_OF_BOUND.\n", k, m);
		exit(1);
	}
	if(histo.mat == NULL)
	{
		fprintf(stderr, "[HISTOGRAMME_AUDIO] Impossible de récuperer la valeur à l'index (%d,%d) HISTOGRAM_NOT_INIT.\n", k, m);
		exit(1);
	}
	return *(histo.mat + (k * histo.m + m));
}

void set_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO * histo, unsigned int k, unsigned int m, unsigned int val)
{
	if(/*k < 0 ||*/ k >= histo->k /*|| m < 0*/ || m >= histo->m)
	{
		fprintf(stderr, "[HISTOGRAMME_AUDIO] Impossible de mettre la valeur à l'index (%d,%d) OUT_OF_BOUND.\n", k, m);
		exit(1);
	}
	if(histo->mat == NULL)
	{
		fprintf(stderr, "[HISTOGRAMME_AUDIO] Impossible de mettre la valeur à l'index (%d,%d) DESC_NOT_INIT.\n", k, m);
		exit(1);
	}
	*(histo->mat + k * histo->m + m) = val;
}

void inc_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO * histo, unsigned int k, unsigned int m)
{
	if(/*k < 0 ||*/ k >= histo->k /*|| m < 0*/ || m >= histo->m)
	{
		fprintf(stderr, "[HISTOGRAMME_AUDIO] Impossible d'incrementer la valeur à l'index (%d,%d) OUT_OF_BOUND.\n", k, m);
		exit(1);
	}
	if(histo->mat == NULL)
	{
		fprintf(stderr, "[HISTOGRAMME_AUDIO] Impossible d'incrementer la valeur à l'index (%d,%d) DESC_NOT_INIT.\n", k, m);
		exit(1);
	}
	set_HISTOGRAMME_AUDIO(histo, k, m, get_HISTOGRAMME_AUDIO(*histo, k, m) + 1);
}

int compare_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO histo1, HISTOGRAMME_AUDIO histo2)
{
	if(histo1.k != histo2.k) return 1;
	if(histo1.m != histo2.m) return 1;
	unsigned int y, x;
	for(y = 0; y < histo1.k; y++)
		for(x = 0; x < histo1.m; x++)
			if(get_HISTOGRAMME_AUDIO(histo1, y, x) != get_HISTOGRAMME_AUDIO(histo2, y, x)) { /*printf("y=%d, x=%d\n", y, x);*/ return 1; }
	return 0;
}

int generer_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO * histo, char * chemin, int n, int m)
{
	char * ext = strrchr(chemin, '.');
	if(!ext)
	{
		fprintf(stderr, "[CREER_HISTO] Impossible de créer l'histogramme car le fichier donné n'a pas un format supporté (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	} else if(strcmp((ext + 1), "txt") == 0){
	    return creer_histogramme_TXT_DESC_AUDIO(histo, chemin, n, m);
	} else if(strcmp((ext + 1), "bin") == 0){
	    return creer_histogramme_BIN_DESC_AUDIO(histo, chemin, n, m);
	} else if(strcmp((ext + 1), "wav") == 0){
	    return creer_histogramme_WAV_DESC_AUDIO(histo, chemin, n, m);
	}
	else
	{
		fprintf(stderr, "[CREER_HISTO] Impossible de créer l'histogramme car le fichier donné n'a pas un format supporté (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	}

}

int creer_histogramme_TXT_DESC_AUDIO(HISTOGRAMME_AUDIO * histo, char * chemin, int n, int m)
{
	// Vérification de l'extension du fichier donné.
	char * ext = strrchr(chemin, '.');
	if (!ext) 
	{
	    fprintf(stderr, "[CREER_HISTO_TXT] Impossible de créer l'histogramme car le fichier donné n'est pas au bon format TXT (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	} else if(strcmp((ext + 1), "txt") != 0){
	    fprintf(stderr, "[CREER_HISTO_TXT] Impossible de créer l'histogramme car le fichier donné n'est pas au bon format TXT (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	}

	*histo = init_HISTOGRAMME_AUDIO(n, m);
	FILE * audioTXT; // Fichier audio .txt
	audioTXT = fopen(chemin, "r");
	// Si le programme n'arrive pas à lire le fichier demandé.
	if(audioTXT == NULL)
	{
		fprintf(stderr, "[CREER_HISTO_TXT] Impossible de lire le fichier %s.", chemin);
		exit(1);
	}

	int lines = 0;
	char ch;

	// Compter le nombre de ligne
	while(!feof(audioTXT))
	{
		ch = fgetc(audioTXT);
		if(ch == '\n')
		{
			lines++;
		}
	}
	audioTXT = fopen(chemin, "r");
	//printf("Data size: %d\n", lines);

	// Lecture des valeurs
	double y_ratio = lines / (double) histo->k;
	double x_ratio = 2.0 / m;

	/* DEBUG Things
	printf("Nb lines: %d\n", lines);
	printf("lines / k: %f\n", y_ratio);
	printf("2 / m: %f\n", x_ratio);
	*/

	int y, x;
	int line;
	double val;
	/* double min = 50; DEBUG VALUES
	double max = -50; */
	for(line = 0; line < lines; line++)
	{
		// Ici, l'espace avant le %lf est important
		// car dans le fichier .txt se trouve un espace avant le nombre réel.
		fscanf(audioTXT, " %lf", &val);
		/*if(val > max) max = val;
		if(val < min) min = val;*/
		y = floor(line / y_ratio);
		x = floor((val + 1) / x_ratio);
		//printf("y:%d, x:%d, val: %lf, line: %d\n", y, x, val, line);
		inc_HISTOGRAMME_AUDIO(histo, y, x);
	}
	fclose(audioTXT);
	/* printf("min: %lf, max: %lf\n", min, max); */
	return HISTOGRAMME_CREER_SUCCES;
}

int creer_histogramme_BIN_DESC_AUDIO(HISTOGRAMME_AUDIO * histo, char * chemin, int n, int m)
{
	// Vérification de l'extension du fichier donné.
	char * ext = strrchr(chemin, '.');
	if (!ext) 
	{
	    fprintf(stderr, "[CREER_HISTO_BIN] Impossible de créer l'histogramme car le fichier donné n'est pas au bon format BIN (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	} else if(strcmp((ext + 1), "bin") != 0){
	    fprintf(stderr, "[CREER_HISTO_BIN] Impossible de créer l'histogramme car le fichier donné n'est pas au bon format BIN (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	}

	*histo = init_HISTOGRAMME_AUDIO(n, m);
	FILE * audioBIN; // Fichier audio .bin
	audioBIN = fopen(chemin, "r");
	// Si le programme n'arrive pas à lire le fichier demandé.
	if(audioBIN == NULL)
	{
		fprintf(stderr, "[CREER_HISTO_BIN] Impossible de lire le fichier %s.\n", chemin);
		exit(1);
	}

	double buffer;
	long nbDoubleWord;
	for(nbDoubleWord = 0; getc(audioBIN) != EOF; nbDoubleWord++);
	nbDoubleWord = nbDoubleWord / 8;
	//printf("Data size: %d\n", nbDoubleWord);

	// Retour au début du fichier pour sa lecture
	rewind(audioBIN); 

	// Lecture des valeurs
	double y_ratio = nbDoubleWord / (double) histo->k;
	double x_ratio = 2.0 / m;

	int y, x;
	int i;
	for(i = 0; i < nbDoubleWord; i++)
	{
		fread(&buffer, 8, 1, audioBIN);
		//printf("%d %lf\n", i, buffer);
		y = floor(i / y_ratio);
		x = floor((buffer + 1) / x_ratio);
		inc_HISTOGRAMME_AUDIO(histo, y, x);
	}

	//free(buffer);
	
	return HISTOGRAMME_CREER_SUCCES;
}

int creer_histogramme_WAV_DESC_AUDIO(HISTOGRAMME_AUDIO * histo, char * chemin, int n, int m)
{
	// Vérification de l'extension du fichier donné.
	char * ext = strrchr(chemin, '.');
	if (!ext) 
	{
	    fprintf(stderr, "[CREER_HISTO_WAV] Impossible de créer l'histogramme car le fichier donné n'est pas au bon format WAV (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	} else if(strcmp((ext + 1), "wav") != 0){
	    fprintf(stderr, "[CREER_HISTO_WAV] Impossible de créer l'histogramme car le fichier donné n'est pas au bon format WAV (%s).\n", chemin);
		return HISTOGRAMME_CREER_ERREUR;
	}

	*histo = init_HISTOGRAMME_AUDIO(n, m);
	FILE * audioWAV; // Fichier audio .bin
	audioWAV = fopen(chemin, "r");
	// Si le programme n'arrive pas à lire le fichier demandé.
	if(audioWAV == NULL)
	{
		fprintf(stderr, "[CREER_HISTO_WAV] Impossible de lire le fichier %s.", chemin);
		exit(1);
	}
	/*fseek(audioWAV, 16, SEEK_SET);
	int blocSize;
	fread(&blocSize, 4, 1, audioWAV);
	printf("Bloc size: %d\n", blocSize);*/

	// Récupérer la taille du bloc DATA par lecture entière du fichier
	/*/long nbHalfWord;
	for(nbHalfWord = 0; getc(audioWAV) != EOF; nbHalfWord++);
	nbHalfWord = ((nbHalfWord - 56 ) / 2) ; // - 56 pour l'entête*/


	fseek(audioWAV, 40, SEEK_SET);

	// Récupérer la taille du bloc DATA par lecture de la propriété dans
	// l'entête du fichier WAV
	//    [... 40 octets ...]
	// 		DataSize (4 octets) : Nombre d'octets des données
	//	  DATA[] ...
	short int buffer;
	int dataSize;
	fread(&dataSize, 4, 1, audioWAV);
	dataSize = (dataSize / 2);
	//printf("Data size: %d\n", dataSize);

	fseek(audioWAV, 44, SEEK_SET);


	// Lecture des valeurs
	double y_ratio = dataSize / (double) histo->k;
	double x_ratio = 2.0 / m;

	int y, x;
	int i;

	double val;
	val = (((double) (0 - SHORT_INT_MIN_VALUE) / (-SHORT_INT_MIN_VALUE + SHORT_INT_MAX_VALUE)) * 2.0) - 1.0;
	//printf("%lf\n", val);

	for(i = 0; i < dataSize; i++)
	{
		fread(&buffer, 2, 1, audioWAV);
		val = (((double) (buffer - SHORT_INT_MIN_VALUE) / (-SHORT_INT_MIN_VALUE + SHORT_INT_MAX_VALUE)) * 2.0) - 1.0;
		//printf("%d %d %lf\n", i, buffer, val);
		y = floor(i / y_ratio);
		x = floor((val + 1) / x_ratio);
		// Il se peut que la valeur lue soit -32768
		// et c'est une valeur que le programme n'est pas capable de gérer,
		// on saute donc l'enregistrement de cette valeur.
		if(x < 0) continue;
		inc_HISTOGRAMME_AUDIO(histo, y, x);
	}

	return HISTOGRAMME_CREER_SUCCES;
}


void free_HISTOGRAMME_AUDIO(HISTOGRAMME_AUDIO histo)
{
	if(histo.mat != NULL) free(histo.mat);
}