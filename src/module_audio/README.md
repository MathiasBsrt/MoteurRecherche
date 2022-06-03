# Module Audio

## Description

Ce module audio permet de traiter les différents formats de fichier ci-dessous:

| Extension | Objectif |
|-----------|----------|
| WAV       | Permet d'écouter l'audio |
| BIN       | Permet d'effectuer des traitements sur le fichier audio |
| TXT       | Permet de vérifier la lecture du fichier audio |

Cependant ce module maintient la lecture des trois format pour pouvoir générer un descripteur audio.

## Utilisation

### Tests unitaire

Une compilation pour effectuer des tests unitaires est disponible dans le fichier ``Makefile``, en suivant les instructions suivantes vous serez capables de les éxecuter:

```bash
make test_audio
```

### Génération unique d'un histogramme

Une compilation pour pouvoir générer un executable capable de créer un histogramme à partir d'un fichier .TXT, .BIN ou .WAV est disponible:

```bash
make main_histo
./main_histo <fichier> <n> <m>
```

### Génération d'une évaluation

Une compilation pour pouvoir générer un executable capable de faire une évulation entre deux fichiers audio WAV.

```bash
make test_evaluation
./test_evaluation.out <fichier1.wav> <fichier2.wav>
```

### Génération d'une recherche

Une compilation pour pouvoir générer un executable capable de faire une recherche d'un fichier audio parmis les autres fichiers audio WAV déjà indéxés.

```bash
make test_recherche
./test_recherche.out <fichier.wav>
```

## Commentaires

Dans sa globalité, le code du module Audio est commenté.
Chaque méthode a un commentaire pour la décrire (voir format Doxygen).
En plus, pour chaque méthode, il est spécifié si elle peut être utile pour la fonctionnalité d'**indexation**, de **recherche** ou ayant une **utilité générale**.
Cela ne **prive bien sûr en rien** leurs utilisations dans un autre cas que celui qui est spécifié ! Ces notes sont présentes pour faciliter **l'implémentation** de ce module dans le **programme principale**.

## TODO

- [ ] Plus ... Toujours plus de commentaires !