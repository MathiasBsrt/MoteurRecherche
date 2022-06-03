/**
 * @file wav_file_helper.h
 * @author Théo TRAFNY
 * @brief Petit outil permettant de récupérer des informations présentes 
 * dans l'entête du fichier au format WAV.
 * Lien vers le Wiki: https://fr.wikipedia.org/wiki/Waveform_Audio_File_Format#En-t%C3%AAte_de_fichier_WAV
 */

#ifndef WAV_FILE_HELPER_H_INCLUS 

#define WAV_FILE_HELPER_H_INCLUS

#define WAV_FILE_DOESNT_EXIST -1

#define OFFSET_FILE_SIZE 4
#define OFFSET_NBR_CHANNEL 22
#define OFFSET_FREQUENCY 24
#define OFFSET_BYTE_PER_SECOND 28
#define OFFSET_BYTE_PER_BLOCK 32
#define OFFSET_BITS_PER_SAMPLE 34
#define OFFSET_DATA_SIZE 40

/** Récupérer la durée d'un fichier wav.
* Retourne WAV_FILE_DOESNT_EXIST si le fichier n'existe pas.
* @param char * chemin vers le fichier wav
-
* @return int durée exprimée en seconde
*/
int wav_get_duration(char *);

#endif