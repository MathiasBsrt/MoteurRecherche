/**
 * @file wav_file_helper.c
 * @author Théo TRAFNY
 * @brief Petit outil permettant de récupérer des informations présentes 
 * dans l'entête du fichier au format WAV.
 * Lien vers le Wiki: https://fr.wikipedia.org/wiki/Waveform_Audio_File_Format#En-t%C3%AAte_de_fichier_WAV
 */

#include <stdio.h>
#include "wav_file_helper.h"

int wav_get_duration(char * chemin)
{
    // Ouverture du fichier
    FILE * file = fopen(chemin, "rb");
    if(file == NULL) return WAV_FILE_DOESNT_EXIST;

    // Direction la bonne partie du header du fichier WAV
    fseek(file, OFFSET_BYTE_PER_SECOND, SEEK_SET);

    // On lit 4 octets (un int) pour récupérer le nombre d'octet par seconde
    int bytePerSecond;
    fread(&bytePerSecond, 4, 1, file);
     
    // On saute 8 octets
    fseek(file, OFFSET_DATA_SIZE, SEEK_SET);

    // On lit 4 octets (un int) pour récupérer le nombre total d'octet
    int dataSize;
    fread(&dataSize, 4, 1, file);

    // On calcul le rapport
    return dataSize / bytePerSecond;
}