#include "controle_descripteur.h"
/**
 * @file controle_descripteur.c
 * @author DUVIVIER Davy et BOSSAERTS Mathias
 * @brief Regroupe la partie d'indexation des images
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */



/**
 * @brief Cette fonction permet de créer le chemin d'accès à un fichier
 * 
 * @param chemin 
 * @param nom_dossier 
 * @param nom_fichier 
 */
void path_maker1(char *chemin, char *nom_dossier, char *nom_fichier)
{
    strcpy(chemin, nom_dossier);
    strcat(chemin, "/");
    strcat(chemin, nom_fichier);
}

/***

 */


/**
 * @brief  Cette fonction permet de créer le descripteur d'un fichier donné et le stocker dans le fichier base_descripteur
 * 
 * @param chemin 
 * @param p pile
 */
void creationDescripteur_image(char *chemin, PILE_image *p)
{
  //lire fichier pointé par le chemin
  //quantification de chaque pixel de l'image
  //Creer l'histograme
  //Creer variable descripteur et le remplir
  FILE *image;
  Descripteur_image newDesc; //Le desctipeur à ajouter

  int lignes;
  int colonnes;
  int nbComposantes;
  char *basec; //bname est le nom du fichier à indexer
  // Intialisation des variables

  basec = strdup(chemin);

  printf("basec %s \n", chemin);

  image = fopen(chemin, "r");

  //Lecture des propriétés de l'image
  fscanf(image, "%d", &lignes);
  fscanf(image, "%d", &colonnes);
  fscanf(image, "%d", &nbComposantes);
  int **matriceImageQuant = malloc(sizeof(int *) * lignes);

  //intialisation de la matrice de quantification
  for (int i = 0; i < lignes; i++)
  {
    matriceImageQuant[i] = malloc(sizeof(int) * colonnes);
  }
  //Lecture du fichier image
  if (nbComposantes == 1)
  {
    int **matriceNB = (int **)malloc(sizeof(int *) * lignes); // Contient les matrices noir et blanc
    for (int i = 0; i < lignes; i++)
    {
      matriceNB[i] = (int *)malloc(sizeof(int) * colonnes);
    }
    lire_imageNB(lignes, colonnes, matriceNB, image);
    quantificationNB(matriceNB, matriceImageQuant, lignes, colonnes);
    for (int i = 0; i < lignes; i++)
    {
      free(matriceNB[i]);
    }
    free(matriceNB);
  }
  else
  {
    RGB **matriceRGB = (RGB **)malloc(sizeof(RGB) * lignes);
    for (int i = 0; i < lignes; i++)
    {
      matriceRGB[i] = (RGB *)malloc(sizeof(RGB) * colonnes);
    }
    lire_imageRGB(lignes, colonnes, matriceRGB, image);
    quantificationRGB(matriceRGB, matriceImageQuant, lignes, colonnes);

    for (int i = 0; i < lignes; i++)
    {
      free(matriceRGB[i]);
    }
    free(matriceRGB);
  }
  fclose(image);

  // intialisation de l'histogramme
  for (int i = 0; i < tailleHistogramme; i++)
  {
    newDesc.histogramme[i] = 0;
  }
  creationHistogramme(matriceImageQuant, &newDesc, lignes, colonnes); // doit créer l'histo et remplir l'attribut histogramme du descripteur

  //Sauvegarde du nouveau descripteur
  *p = SauvegardeDescripteur_image(newDesc, *p, basec, nbComposantes);

  for (int i = 0; i < lignes; i++)
  {
    free(matriceImageQuant[i]);
  }
  free(matriceImageQuant);
}

/**
 * 
 * 
 */

/**
 * @brief Cette fonction permet de sauvegarder un descripteur donné en paramètre dans le fichier base_descripteur_image et de lier ce descripteur avec le fichier dans le fichier liste_base_image
 * 
 * @param nouveau 
 * @param p 
 * @param nom 
 * @param RGB_ou_NB 
 * @return PILE_image 
 */
PILE_image SauvegardeDescripteur_image(Descripteur_image nouveau, PILE_image p, char *nom, int RGB_ou_NB)
{
  if (!PILE_estVide_image(p))
    nouveau.id = p->elt.id + 1;
  else
  {
    nouveau.id = 1;
  }
  p = emPILE_image(p, nouveau);
  lierDescripteur_image(nouveau, nom, RGB_ou_NB);

  return p;
}

/***

 */




/**
 * @brief Cette fonction permet de sauvegarder la pile passée en paramètre dans le fichier base_descripteur_image. La pile spécifiée écrase l'ancienne
 *
 * @param p 
 * @param RGB_ou_NB 
 */
void sauvegarderPile_image(PILE_image p, int RGB_ou_NB)
{
  //On stocke sous la forme de une ligne = un element de la pile : "[id] [e1] [e2 [e3] ..." pour les 64 elements du tableau histogramme (sans les crochets)
  FILE *pileFichier;
  if (RGB_ou_NB == 1)
  {
    pileFichier = fopen("sauvegardes/img/base_descripteur_image_NB", "w+");
  }
  else
  {
    pileFichier = fopen("sauvegardes/img/base_descripteur_image_RGB", "w+");
  }
  Descripteur_image copier;
  while (!PILE_estVide_image(p))
  {
    //on ecrit l'id + espace
    //on ecrit l'histogramme, chaque valeur séparée par un espace
    //on revient à la ligne
    p = dePILE_image(p, &copier);
    // On met tout en ligne, pas beosin des indices du tableau de l'histogramme.
    // On met tout en ligne pour faciliter la lecture par le futur charger fichier/pile. Un \n represente la fin d'un descripteur
    fprintf(pileFichier, "%d ", copier.id);
    if (RGB_ou_NB == 3)
    {
      for (int i = 0; i < tailleHistogramme; i++)
      {
        fprintf(pileFichier, "%d ", copier.histogramme[i]);
      }
      fprintf(pileFichier, "%s", "\n");
    }
    else
    {
      for (int i = 0; i < tailleHistogrammeNB; i++)
      {
        fprintf(pileFichier, "%d ", copier.histogramme[i]);
      }
      fprintf(pileFichier, "%s", "\n");
    }
  }

  fclose(pileFichier);
}

/**
 * @brief Cette méthode permet de lire une image noir et blanc
 * 
 * @param lignes 
 * @param colonnes 
 * @param matriceImage 
 * @param image 
 * @return une matrice représentant l'image noir et blanc
 */
int lire_imageNB(int lignes, int colonnes, int **matriceImage, FILE *image)
{
  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      fscanf(image, "%d", &matriceImage[i][j]);
    }
  }
  return 0;
}

/**
 * @brief Quantifie, par niveau de noir et blanc, une matrice représentant l'image noir et blanc 
 * 
 * @param matriceImageNB 
 * @param matriceImageQuant 
 * @param lignes 
 * @param colonnes 
 * @return int 
 */
int quantificationNB(int **matriceImageNB, int **matriceImageQuant, int lignes, int colonnes)
{
  int niveau = tailleHistogrammeNB;
  int pixel;
  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      pixel = matriceImageNB[i][j];
      matriceImageQuant[i][j] = pixel / (256 / niveau);
    }
  }
  return 0;
}

/**
 * @brief Cette méthode permet de lire une RGB

 * 
 * @param lignes 
 * @param colonnes 
 * @param matriceImage 
 * @param image 
 * @return une matrice représentant l'image RGB
 */
int lire_imageRGB(int lignes, int colonnes, RGB **matriceImage, FILE *image)
{

  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      fscanf(image, "%d", &matriceImage[i][j].red);
    }
  }
  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      fscanf(image, "%d", &matriceImage[i][j].green);
    }
  }
  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      fscanf(image, "%d", &matriceImage[i][j].blue);
    }
  }

  return 0;
}

int power(int x, int puiss)
{
  int resultat = x;
  if (puiss == 0)
  {
    return 1;
  }
  for (int i = 1; i < puiss; i++)
  {
    resultat *= x;
  }
  return resultat;
}

/**
 * @brief Quantifie un seul pixel RGB en un entier
 * 
 * @param pixel 
 * @return un int représentant le pixel quantifié
 */
int quantifie_un_pixelRGB(RGB pixel)
{
  int resultat = 0;
  int etape = 1;
  int composantes[6];
  int puissance = 7;
  while (etape - quantificateur <= 0)
  {
    composantes[(3 * quantificateur) - etape] = pixel.red > power(2, puissance);
    if (pixel.red > power(2, puissance))
    {
      pixel.red /= 2;
    }
    composantes[(2 * quantificateur) - etape] = (pixel.green > (power(2, puissance)));
    if (pixel.green > power(2, puissance))
    {
      pixel.green /= 2;
    }
    composantes[(1 * quantificateur) - etape] = (pixel.blue > (power(2, puissance)));
    if (pixel.blue > power(2, puissance))
    {
      pixel.blue /= 2;
    }
    puissance--;
    etape++;
  }
  for (int i = 0; i < (3 * quantificateur); i++)
  {
    resultat += (composantes[i]) * power(2, i);
  }
  return resultat;
}


/**
 * @brief Cette méthode permet de quantifier une matrice de type RGB
 * 
 * @param matriceImageRGB 
 * @param matriceImageQuant 
 * @param lignes 
 * @param colonnes 
 * @return la matrice quantifiée et un code de retour (succes ou echec)
 */
int quantificationRGB(RGB **matriceImageRGB, int **matriceImageQuant, int lignes, int colonnes)
{
  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      matriceImageQuant[i][j] = quantifie_un_pixelRGB(matriceImageRGB[i][j]);
    }
  }
  return 0;
}

/**
 * @brief Cette méthode permet de créer un histogramme à partir de de la matrice quantifiée et de l'ajouter au nouveau descripteur
 * 
 * @param matriceImageQuant 
 * @param newDesc 
 * @param lignes 
 * @param colonnes 
 * @return le nouveau descripteur et un code de retour (succès ou echec)
 */
int creationHistogramme(int *matriceImageQuant[], Descripteur_image *newDesc, int lignes, int colonnes) // doit créer l'histo et remplir l'attribut histogramme du descripteur
{
  for (int i = 0; i < lignes; i++)
  {
    for (int j = 0; j < colonnes; j++)
    {
      newDesc->histogramme[matriceImageQuant[i][j]] += 1;
    }
  }

  return 0;
}


/**
 * @brief  * Cette fonction permet de lier un descripteur à son fichier
 * Le fichier liste_base image mémorise le nom des fichiers traités
 * et l'idendifiant unique du descripteur de ce fichier
 * 
 * @param d 
 * @param nom 
 * @param RGB_ou_NB 
 */
void lierDescripteur_image(Descripteur_image d, char *nom, int RGB_ou_NB)
{
  FILE *pileFichier;
  if (RGB_ou_NB == 1)
  {
    pileFichier = fopen("sauvegardes/img/liste_base_image_NB", "a");
  }
  else
  {
    pileFichier = fopen("sauvegardes/img/liste_base_image_RGB", "a");
  }
  //Condition si le fichier n'existe pas
  if (pileFichier == NULL)
  {
    char commande[1000];
    if (RGB_ou_NB == 1)
    {
      strcpy(commande, "touch sauvegardes/img/base_descripteur_image_NB");
    }
    else
    {
      strcpy(commande, "touch sauvegardes/img/base_descripteur_image_RGB");
    }
    system(commande);
    pileFichier = fopen("sauvegardes/img/liste_base_image", "w+");
  }

  //on ecrit l'id + le nom du fichier + retour à la la ligne
  fprintf(pileFichier, "%d %s \n", d.id, nom);

  fclose(pileFichier);
}

/**
 * @brief lecture du dossier à indexer
 * 
 * @param f 
 * @param nom_dossier 
 */
void lecture_dossier_image(FILE *f, char *nom_dossier)
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
      char chemin[100];
      path_maker1(chemin, nom_dossier, dir->d_name);
      stat(chemin, &InfosFile);            //on recupere les stat du fichier lu pour savoir si c' est un dossier
      if (S_ISREG(InfosFile.st_mode) != 0) //on vérifie si c'est un fichier
      {
        fprintf(f, "%s ", chemin);
      }
    }
  }
  closedir(d);
  rewind(f); //on remet le pointeur du fichier au début
}
/**
 * @brief vérifie si une image a déjà été indexé
 * 
 * @param path_to_xml 
 * @param liste_base_image 
 * @return int 
 */
int image_deja_indexe(char *path_to_xml, char *liste_base_image)
{
  int code_retour = 0;

  FILE *liste_base_desc = fopen(liste_base_image, "r");
  if (liste_base_desc)
  {
    int id_texte;
    char buffer[255];
    while (fscanf(liste_base_desc, "%d %s", &id_texte, buffer) != EOF)
    {
      if (!strcmp(buffer, path_to_xml))
      {
        code_retour = id_texte;
        break;
      }
    }
  }
  return code_retour;
}

/**
 * @brief Permet d'indexer un dossier entier
 * 
 * @param cheminDossier 
 * @param RGB_ou_NB 
 */
void genererDescripteur_imageDossier(char *cheminDossier, int RGB_ou_NB)
{
  PILE_image pile;
  FILE *fich;
  fich = fopen("sauvegardes/img/nom_fichiers.txt", "w+");
  char chemin[255];
  lecture_dossier_image(fich, cheminDossier);

  if (RGB_ou_NB == 1)
  {
    pile = chargerPILE_image("sauvegardes/img/base_descripteur_image_NB",1);
  }
  else
  {
    pile = chargerPILE_image("sauvegardes/img/base_descripteur_image_RGB",3);
  }
  if (!PILE_estVide_image(pile) && pile->elt.id == 1)
  {
    pile = inverserPILE_image(pile);

  }
  while (fscanf(fich, "%s", chemin) != EOF)
  {
    if (RGB_ou_NB == 1)
    {
      if (!image_deja_indexe(chemin, "sauvegardes/img/liste_base_image_NB"))
      {
        creationDescripteur_image(chemin, &pile);
      }
    }
    else
    {
      if (!image_deja_indexe(chemin, "sauvegardes/img/liste_base_image_RGB"))
      {
        creationDescripteur_image(chemin, &pile);
      }
    }
  }
  sauvegarderPile_image(pile, RGB_ou_NB);
  fclose(fich);
}

/**
 * @brief Permet d'indexer une image à la fois
 * 
 * @param cheminImage 
 * @param RGB_ou_NB 
 */
void genererDescripteur_image(char *cheminImage, int RGB_ou_NB)
{
  PILE_image pile;

  if (RGB_ou_NB == 1)
  {
    pile = chargerPILE_image("sauvegardes/img/base_descripteur_image_NB",1);
  }
  else
  {
    pile = chargerPILE_image("sauvegardes/img/base_descripteur_image_RGB",3);
  }
  if (!PILE_estVide_image(pile) && pile->elt.id == 1)
  {
    pile = inverserPILE_image(pile);

  }
  if (RGB_ou_NB == 1)
  {
    if (!image_deja_indexe(cheminImage, "sauvegardes/img/liste_base_image_NB"))
    {
      creationDescripteur_image(cheminImage, &pile);
    }
  }
  else
  {
    if (!image_deja_indexe(cheminImage, "sauvegardes/img/liste_base_image_RGB"))
    {
      creationDescripteur_image(cheminImage, &pile);
    }
  }
  sauvegarderPile_image(pile, RGB_ou_NB);
}
