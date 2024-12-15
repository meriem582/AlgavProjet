import pandas as pd
import matplotlib.pyplot as plt

# Fonction pour tracer un histogramme
def tracer_histogramme(nom_fichier, titre, y_label, couleurs, bar_width=0.6):
    try:
        # Charger les données CSV
        data = pd.read_csv(nom_fichier, header=None, skiprows=1)  # Ignorer les en-têtes initiaux
        noms_structures = data.iloc[:, 0]  # Les noms des structures
        valeurs = data.iloc[:, 1]         # Les valeurs associées

        # Création de l'histogramme
        plt.figure(figsize=(8, 6))
        plt.bar(noms_structures, valeurs, color=couleurs, width=bar_width)
        plt.title(titre)
        plt.ylabel(y_label)
        plt.xlabel("Structures de Données")
        plt.xticks(rotation=15)
        plt.tight_layout()
        plt.grid(axis='y', linestyle='--', alpha=0.7)

        # Sauvegarder l'image
        nom_fichier_image = f"{titre.replace(' ', '_')}.png"
        plt.savefig(nom_fichier_image, format='png')
        print(f"Graphique sauvegardé sous : {nom_fichier_image}")

        plt.show()
        plt.close()
    except FileNotFoundError as e:
        print(f"Erreur : {e}")

# Chemins des fichiers CSV
fichier_insertion = "resultats_insertion.csv"
fichier_hauteur = "resultats_hauteur.csv"

# Couleurs pour les barres
couleurs_insertion = ['#4E79A7', '#F28E2B', '#59A14F']  # Bleu, Orange, Vert
couleurs_hauteur = ['#76B7B2', '#E15759', '#F1CE63']    # Vert d'eau, Rouge, Jaune clair

# Tracer les deux histogrammes
tracer_histogramme(fichier_insertion, "Insertion_equilibre", "Temps (ns)", couleurs_insertion)
tracer_histogramme(fichier_hauteur, "Hauteurs_equilibre", "Hauteur", couleurs_hauteur)
