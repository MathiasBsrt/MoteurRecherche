/**
 * @file main_histo.c
 * @author Théo TRAFNY
 * @brief Programme de test pour pouvoir générer un histogramme.
 */

/*#include <stdio.h>
#include <stdlib.h>
#include "histogramme.h"

int main(int argc, char * argv[])
{
	if(argc != 4)
	{
		fprintf(stderr, "Pour que le programme puisse fonctionner, des arguments sont attendus.\n");
		fprintf(stderr, "Usage: %s <fichier> <n> <m>\n", argv[0]);
		return 1;
	}

	int n, m;

	sscanf(argv[2],"%d",&n);
	sscanf(argv[3],"%d",&m);

	HISTOGRAMME_AUDIO histo;
	int code = generer_HISTOGRAMME_AUDIO(&histo, argv[1], n, m);
	if(code != HISTOGRAMME_CREER_SUCCES) exit(code);
	affiche_HISTOGRAMME_AUDIO(histo);
}*/