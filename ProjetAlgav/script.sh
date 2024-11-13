#!/bin/bash

# Vérifie le nombre d'arguments
if [ "$#" -lt 2 ]; then
  echo "Usage: $0 <command> <x> <args...>"
  exit 1
fi

command=$1
x=$2
shift 2

# Compilation si nécessaire
make

case $command in
  "inserer")
    if [ "$x" -eq 0 ]; then
      java -cp Classes main.Main insererPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp Classes main.Main insererHybride "$@"
    else
      echo "Error: x doit être 0 ou 1"
      exit 1
    fi
    ;;

  "suppression")
    if [ "$x" -eq 0 ]; then
      java -cp Classes main.Main suppressionPatricia "$@"
    elif [ "$x" -eq 1 ]; then
      java -cp Classes main.Main suppressionHybride "$@"
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
