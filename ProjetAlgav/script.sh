#!/bin/bash

# Vérifie le nombre d'arguments pour afficher de l'aide si nécessaire
if [ "$#" -lt 1 ]; then
  echo "Usage: $0 <command> <x> <args...>"
  echo "Utilisez '$0 help' pour afficher les commandes disponibles."
  exit 1
fi

# Commande à exécuter
command=$1

# Fonction pour afficher les commandes disponibles
function display_help() {
  echo "Commandes disponibles :"
  echo "1. Inserer un mot ou un fichier de mots dans un trie :"
  echo "   bash $0 inserer <x> <fichier_mots>"
  echo "   - x = 0 : Patricia Trie"
  echo "   - x = 1 : Trie Hybride"
  echo
  echo "2. Supprimer des mots d'un trie à partir d'un fichier :"
  echo "   bash $0 suppression <x> <fichier_mots_a_supprimer>"
  echo "   - x = 0 : Patricia Trie"
  echo "   - x = 1 : Trie Hybride"
  echo
  echo "3. Fusionner deux tries :"
  echo "   bash $0 fusion <x> <trie1.json> <trie2.json>"
  echo "   - x = 0 : Patricia Trie"
  echo "   - x = 1 : Trie Hybride"
  echo
  echo "4. Lister les mots d'un trie :"
  echo "   bash $0 listeMots <x> <trie.json>"
  echo "   - x = 0 : Patricia Trie"
  echo "   - x = 1 : Trie Hybride"
  echo
  echo "5. Calculer la profondeur moyenne d'un trie :"
  echo "   bash $0 profondeurMoyenne <x> <trie.json>"
  echo "   - x = 0 : Patricia Trie"
  echo "   - x = 1 : Trie Hybride"
  echo
  echo "6. Rechercher les mots ayant un préfixe donné :"
  echo "   bash $0 prefixe <x> <trie.json> <prefixe>"
  echo "   - x = 0 : Patricia Trie"
  echo "   - x = 1 : Trie Hybride"
  echo
  echo "Pour chaque commande, 'x' indique le type de trie : 0 pour Patricia Trie et 1 pour Trie Hybride."
  exit 0
}

# Si la commande est "help", afficher l'aide
if [ "$command" == "help" ]; then
  display_help
fi

# Vérifie si un deuxième argument est fourni pour spécifier le type de trie
if [ "$#" -lt 2 ]; then
  echo "Erreur : La commande '$command' nécessite un argument <x> pour spécifier le type de trie (0 ou 1)."
  exit 1
fi

x=$2
shift 2

# Chemin vers le dossier contenant les bibliothèques externes
LIBS="libs/*"

# Création du dossier de classes compilées
mkdir -p Classes

# Compilation avec les bibliothèques Jackson
javac -d Classes -cp "src:$LIBS" src/**/*.java
if [ $? -ne 0 ]; then
  echo "Erreur lors de la compilation"
  exit 1
fi

case $command in
  "inserer")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main insererPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main insererHybride "$@"
    else
      echo "Erreur : x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "suppression")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main suppressionPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main suppressionHybride "$@"
    else
      echo "Erreur : x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "fusion")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main fussionPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main fussionHybride "$@"
    else
      echo "Erreur : x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "listeMots")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main listeMotsPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main listeMotsHybride "$@"
    else
      echo "Erreur : x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "profondeurMoyenne")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main profondeurMoyennePatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main profondeurMoyenneHybride "$@"
    else
      echo "Erreur : x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "prefixe")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main prefixePatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main prefixeHybride "$@"
    else
      echo "Erreur : x doit être 0 ou 1"
      exit 1
    fi
    ;;

  *)
    echo "Commande inconnue : '$command'. Utilisez '$0 help' pour voir les commandes disponibles."
    exit 1
    ;;
esac
