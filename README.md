# Projet fil rouge

Projet commun à plusieurs cours de la première année SRI.

Nous avons élaboré un moteur de recherche (image, son, audio).

Veuillez lire le cahier des charges ainsi que le dossier de spécification se trouvant dans le dossier "documentation/"

Ce projet utilise la méthode d'intégration continue. Vous pouvez visualiser la pipeline ici : https://gitlab.com/MathiasBsrt/pfr/-/pipelines

## Lancement

### Version 1
Une version rudimentaire et codée en C est disponible en suivant ce processus  :

Pour compiler et lancer le moteur de recherche, vous devez vous placer dans le dossier pfr (à la racine) et lancer la commande suivante :
```
cd src/Menus && make && cd .. && ./moteur_recherche
```

### Version 2
La version 2 ajoute une interface graphique, développée en java. :

``java -jar SeekBit.jar``

Vous pouvez retrouver le code dans ``src/IHM``

## Ressources

Les guide d'utilisations des outils utiles pour le projet se trouvent dans le dossier ``guide/``.

Pour accèder à la documentation du code, ouvrez le fichier ``doxygen/html/index.html``
La liste des fonctions et des structures sont disponibles à partir des menus.

