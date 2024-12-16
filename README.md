# AlgavProjet

## Description
Ce projet consiste à représenter un dictionnaire de mots. Dans cette optique, on a utiliser deux structures de tries concurrentes Patricia-Trie et Hybride-Trie.

## Prérequis 
Pour exécuter ce projet, vous aurez besoin de :
- Java (Version : 17.0.10 recommander).
- Eclipse IDE : Pour le développement et l’exécution du projet.
Environnement d'Exécution et Compilateur: 
- Environnement d'exécution : Java(TM) SE Runtime Environment (build 17.0.10+11-LTS-240).
- Compilateur : javac version 17.0.10.

## Les Commandes

### Accéder au projet:
 cd chemin_vers_le_projet

### Supprime les fichiers générés précédemment (comme les fichiers objets .o, exécutables, etc.) pour nettoyer l'environnement de travail on tappe :
 make clean

### Compile et génère les fichiers spécifiés dans le Makefile:
 make

### La commande dos2unix script.sh convertit le fichier script.sh de format Windows (avec des retours à la ligne \r\n) en format Unix (avec des retours à la ligne \n).
 dos2unix script.sh

### Commande pour insérer:
 bash script.sh inserer 0 mots.txt
 bash script.sh inserer 1 mots.txt

### Commande pour supprimer une liste de mots:
 bash script.sh suppression 0 motsAsupprimer.txt
 bash script.sh suppression 1 motsAsupprimer.txt

### Commande pour fusionner:
 bash script.sh fusion 0 trie1P.json trie2P.json
 bash script.sh fusion 1 trie1H.json trie2H.json

### Commande pour lister les mots:
 bash script.sh listeMots 0 pat.json
 bash script.sh listeMots 1 trie.json

### Commande pour trouver la profondeur:
 bash script.sh profondeurMoyenne 0 pat.json
 bash script.sh profondeurMoyenne 1 trie.json

### Commande pour donner le nombre des préfixe d'un mot exemple car:
 bash script.sh prefixe 0 pat.json car
 bash script.sh prefixe 1 trie.json car
