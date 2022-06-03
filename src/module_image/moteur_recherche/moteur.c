#include "moteur.h"
/**
 * @file moteur.c
 * @author DUVIVIER Davy et BOSSAERTS Mathias
 * @brief Regroupe la partie recherche des images
 * @date 2020-12-16
 *
 * @copyright Copyright (c) 2020
 *
 */

/**
 * @brief compare deux image à travers un seuil de ressemblance défini par avance
 * 
 * @param d1 
 * @param d2 
 * @param seuil 
 * @param RGB_ou_NB 
 * @return int 
 */
int comparaison_image(Descripteur_image d1, Descripteur_image d2, double seuil,int RGB_ou_NB,double* pourcentage)
{
    //Méthode de comparaison : intersection des 2 histogrammes

    //Similaire si seuil% des cases sont similaire
    //Une case est similaire si l la valeur 1 et compris dans l'intervale val2-seuil; val2 +seuil
 
    if (d1.id == d2.id)
    {
        return 0;
    }

    //Construire le tableau d'intersection
    //Une case est similaire si l la valeur 1 et compris dans l'intervale val2-seuil; val2 +seuil
    // On ajoute cette case dans le teableau d'intersection
    int nbCaseIntersection = 0;
    int val1;
    int val2;
    if(RGB_ou_NB==1){
        for (int i = 0; i < tailleHistogrammeNB; i++)
         {
             val1 = d1.histogramme[i];
             val2 = d2.histogramme[i];
            if (val2 - val2 * ((100.0 - seuil) / 100.0) <= val1 && val1 <= val2 + val2 * ((100.0 - seuil) / 100.0))
        {
            nbCaseIntersection++;
        }
    }
        *pourcentage = (double)nbCaseIntersection / tailleHistogrammeNB * 100;
    }
    else{
        for (int i = 0; i < tailleHistogramme; i++)
         {
             val1 = d1.histogramme[i];
             val2 = d2.histogramme[i];
            if (val2 - val2 * ((100.0 - seuil) / 100.0) <= val1 && val1 <= val2 + val2 * ((100.0 - seuil) / 100.0))
        {
            nbCaseIntersection++;
        }
    }
        *pourcentage = (double)nbCaseIntersection / tailleHistogramme * 100;
    }


    //On obtient un tableau dont la longueur et le nb de cases similaire. On peut en tirer un pourcentage de similarité (sur les 64 cases d'un histogramme)
    //On compare la similarité au seuil

    if (*pourcentage == 100)
    {
        return 0;
    }
    else if (*pourcentage >= seuil)
    {
        return 1;
    }

    return 2;
}

/**
 * @brief Trie le tableau obtenu dans l'ordre décroissant de leur pourcentages de correspondance
 * 
 * @param chemins_fichiers 
 * @param pourcentages 
 * @param taille 
 */
void trier_tab_pourcentage_chemin(char* chemins_fichiers[255],double* pourcentages,int taille){
    int temp;
    char tempString[255];
    for(int i=0;i<taille;i++){
        for(int j=0;j<taille-i-1;j++){
            if(pourcentages[j]<pourcentages[j+1]){
                temp=pourcentages[j];
                pourcentages[j]=pourcentages[j+1];
                pourcentages[j+1]=temp;

                strcpy(tempString,chemins_fichiers[j]);
                strcpy(chemins_fichiers[j],chemins_fichiers[j+1]);
                strcpy(chemins_fichiers[j+1],tempString);
            }
        }
    }
}

/**
 * @brief Recherche par critère
 * @brief Cette fonction permet de rechercher des documents en fonction d'u critère donné
 * @param RGB couleurDominante
 * @param File[] tableau à remplir
 * @param int seuilSimilarité
 */
void rechercheParCritere_image(RGB couleurDominante, FILE *fichiersSimilaires, int seuilSimilarite)
{
    int couleur;
    float nbCouleurRecherche, nbTotal;
    Descripteur_image desc;
    char** fichiers=malloc(sizeof(char*)*25);
    for(int i=0;i<25;i++){
        fichiers[i]=malloc(sizeof(char)*255);
    }
    double pourcentage_fichier[25];
    int nbfichiers=0;
    //Calcule de la valeur du RGB
    couleur = quantifie_un_pixelRGB(couleurDominante);
    printf("couleur : %d\n",couleur);
    // Charger la pile de tous les descripteurs avec la fonction dans controle descripteur
    PILE_image pile = init_pile_image();
    pile = chargerPILE_image("sauvegardes/img/base_descripteur_image_RGB",3);
    // Pour chaque fichier faire la comparaison nb_de_données/nb_de_donnee_egal_au_RGB
    while (!PILE_estVide_image(pile))
    {
        pile = dePILE_image(pile, &desc);
        nbCouleurRecherche = desc.histogramme[couleur];
        nbTotal = 0;
        for (int i = 0; i < tailleHistogramme; i++)
        {
            nbTotal += desc.histogramme[i];
        }
        if ((nbCouleurRecherche / nbTotal) * 100 > seuilSimilarite && nbfichiers<25)
        {
                id_to_chemin_image(desc.id,3,fichiers[nbfichiers]);
                pourcentage_fichier[nbfichiers]=(nbCouleurRecherche / (float)nbTotal) * 100;
                nbfichiers++;
        }
    }
    trier_tab_pourcentage_chemin(fichiers,pourcentage_fichier,nbfichiers);
    for(int i=0;i<nbfichiers;i++){
        fprintf(fichiersSimilaires, "%f %s\n",pourcentage_fichier[i] ,fichiers[i]);
    }

    for(int i=0;i<25;i++){
        free(fichiers[i]);
    }
    free(fichiers);
}

/**
 * @brief Recherche par document
 * @brief Cette fonction permet de rechercher des documents en fonction d'un document donné
 * @param String cheminVersDocument
 * @param File[] tableau à remplir
 * @param int seuilSimilarité
 */
void rechercheParDocument_RGB(char *cheminVersDocument, FILE *fichiersSimilaires, int seuilSimilarite){
    PILE_image pile=init_pile_image();
    Descripteur_image desc1;
    Descripteur_image desc2;
    double pourcentage;
    char** fichiers=malloc(sizeof(char*)*25);
    for(int i=0;i<25;i++){
        fichiers[i]=malloc(sizeof(char)*255);
    }
    double pourcentage_fichier[25];
    int nbfichiers=0;
    int id=chemin_to_id_image(cheminVersDocument,3);
    //printf("cheminVersDocument : %s %d\n",chemin,id);
    pile=chargerPILE_image("sauvegardes/img/base_descripteur_image_RGB",3);
    while(!PILE_estVide_image(pile)){
        pile=dePILE_image(pile,&desc2);
        if(desc2.id==id){
            desc1=desc2;
        }
    }
    //charger pile
    pile=chargerPILE_image("sauvegardes/img/base_descripteur_image_RGB",3);
    while(!PILE_estVide_image(pile)){
        pile=dePILE_image(pile,&desc2);
        if(desc2.id!=id){
            if(comparaison_image(desc1, desc2, seuilSimilarite,3,&pourcentage)<2 && nbfichiers<25){
                id_to_chemin_image(desc2.id,3,fichiers[nbfichiers]);
                pourcentage_fichier[nbfichiers]=pourcentage;
                nbfichiers++;
            }
        }
    }
    trier_tab_pourcentage_chemin(fichiers,pourcentage_fichier,nbfichiers);
    for(int i=0;i<nbfichiers;i++){
        fprintf(fichiersSimilaires, "%f %s\n",pourcentage_fichier[i] ,fichiers[i]);
    }
        for(int i=0;i<25;i++){
        free(fichiers[i]);
    }
    free(fichiers);
}
/**
 * @brief Compare deux documents noir et blanc
 * 
 * @param cheminVersDocument 
 * @param fichiersSimilaires 
 * @param seuilSimilarite 
 */
void rechercheParDocument_NB(char *cheminVersDocument, FILE *fichiersSimilaires, int seuilSimilarite){
    PILE_image pile=init_pile_image();
    Descripteur_image desc1;
    Descripteur_image desc2;
    char** fichiers=malloc(sizeof(char*)*25);
    for(int i=0;i<25;i++){
        fichiers[i]=malloc(sizeof(char)*255);
    }
    double pourcentage_fichier[25];
    int nbfichiers=0;
    double pourcentage;
    int id=chemin_to_id_image(cheminVersDocument,1);
    pile=chargerPILE_image("sauvegardes/img/base_descripteur_image_NB",1);
    while(!PILE_estVide_image(pile)){
        pile=dePILE_image(pile,&desc2);
        if(desc2.id==id){
            desc1=desc2;
        }
    }
    //charger pile
    pile=chargerPILE_image("sauvegardes/img/base_descripteur_image_NB",1);
    while(!PILE_estVide_image(pile)){
        pile=dePILE_image(pile,&desc2);
        if(desc2.id!=id){
            if(comparaison_image(desc1, desc2, seuilSimilarite,1,&pourcentage)<2 && nbfichiers<25){
                id_to_chemin_image(desc2.id,1,fichiers[nbfichiers]);
                pourcentage_fichier[nbfichiers]=pourcentage;
                nbfichiers++;
            }
        }
    }
    trier_tab_pourcentage_chemin(fichiers,pourcentage_fichier,nbfichiers);
    for(int i=0;i<nbfichiers;i++){
        fprintf(fichiersSimilaires, "%f %s\n",pourcentage_fichier[i] ,fichiers[i]);
    }
        for(int i=0;i<25;i++){
        free(fichiers[i]);
    }
    free(fichiers);
}

/**
 * @brief Transforme un identifiant d'image en un chemin d'accès à l'image
 * 
 * @param id 
 * @param NB_RGB 
 * @param chemin 
 * @return chemin d'accès
 */
void id_to_chemin_image(int id,int NB_RGB,char * chemin){
    FILE* fich;
    int bon_id;
    if(NB_RGB==1){
        fich=fopen("sauvegardes/img/liste_base_image_NB","r");
    }
    else{
        fich=fopen("sauvegardes/img/liste_base_image_RGB","r");
    }
    if(fich!=NULL){
        while(fscanf(fich,"%d %s",&bon_id,chemin)!=EOF){
            if(bon_id==id){
                break;
            }
        }
        fclose(fich);
    }
}

/**
 * @brief Transforme un chemin d'accès à l'image en un identifiant
 * 
 * @param chemin 
 * @param nb_RGB 
 * @return int : identifiant
 */
int chemin_to_id_image(char* chemin, int nb_RGB){
    FILE* fich;
    char bon_chemin[255];
    int id;
    if(nb_RGB==1){
        fich=fopen("sauvegardes/img/liste_base_image_NB","r");
    }
    else{
        fich=fopen("sauvegardes/img/liste_base_image_RGB","r");
    }
    if(fich!=NULL){
        while(fscanf(fich,"%d %s",&id,bon_chemin)!=EOF){
            if(strcmp(chemin,bon_chemin)==0){
                break;
            }
        }
    }

    fclose(fich);
    return id;

}

/**
 * @brief Lance la recherche par critère
 * 
 */
void lancer_recherche_critere()
{
    FILE *fich;
    fich = fopen("sauvegardes/img/Fichiers_similaires", "w+");
    RGB couleur;
    printf("Entrez un code RGB :\nValeur du rouge : ");
    scanf("%d", &couleur.red);
    printf("Valeur du vert : ");
    scanf("%d", &couleur.green);
    printf("Valeur du bleu : ");
    scanf("%d", &couleur.blue);
    rechercheParCritere_image(couleur, fich, SEUIL_DOMINANTE);
    fclose(fich);
}
/**
 * @brief Lance la recherche par comparaison de document RGB
 * 
 */
void lancer_recherche_document_RGB()
{
    FILE *fich;
    fich = fopen("sauvegardes/img/Fichiers_similaires", "w+");
    char cheminVersDocument[255];
    printf("Entrez un chemin vers un fichier : ");
    scanf("%s",cheminVersDocument);
    rechercheParDocument_RGB(cheminVersDocument, fich,SEUIL_RGB);
    fclose(fich);
}

/**
 * @brief Lance la recherche par comparaison de document noir et blanc
 * 
 */
void lancer_recherche_document_NB()
{
    FILE *fich;
    fich = fopen("sauvegardes/img/Fichiers_similaires", "w+");
    char cheminVersDocument[255];
    printf("Entrez un chemin vers un fichier : ");
    scanf("%s",cheminVersDocument);
    rechercheParDocument_NB(cheminVersDocument, fich,SEUIL_NB);
    fclose(fich);
}


void txt_to_bmp(char* txt){
    char b[7];
    int i=strlen(txt);
    txt[i-3]='b';
    txt[i-2]='m';
    txt[i-1]='p';
    while(txt[i-1]!='/'){
        i--;
    }
    strcpy(b,txt+i);
    txt[i-1]='\0';
    while(txt[i]!='/'){
        txt[i]='\0';
        i--;
    }
    strcat(txt,b);
}
void txt_to_png(char* txt){
    char b[7];
    int i=strlen(txt);
    txt[i-3]='j';
    txt[i-2]='p';
    txt[i-1]='g';
    while(txt[i-1]!='/'){
        i--;
    }
    strcpy(b,txt+i);
    txt[i-1]='\0';
    while(txt[i]!='/'){
        txt[i]='\0';
        i--;
    }
    strcat(txt,b);
}

