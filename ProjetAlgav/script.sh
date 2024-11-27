#!/bin/bash

# Vérifie le nombre d'arguments
if [ "$#" -lt 2 ]; then
  echo "Usage: $0 <command> <x> <args...>"
  exit 1
fi

command=$1
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
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "suppression")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main suppressionPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main suppressionHybride "$@"
    else
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;
    
    "fusion")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main fussionPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main fussionHybride "$@"
    else
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;
    
    "listeMots")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main listeMotsPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main listeMotsHybride "$@"
    else
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;
    
    "profondeurMoyenne")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main profondeurMoyennePatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main profondeurMoyenneHybride "$@"
    else
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;
    
    "prefixe")
    if [ "$x" -eq 0 ]; then
      java -cp "Classes:$LIBS" main.Main prefixePatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp "Classes:$LIBS" main.Main prefixeHybride "$@"
    else
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;
    
    

  # Ajouter les autres commandes ici
  *)

    echo "Commande inconnue: $command"
    exit 1
    ;;
esac
