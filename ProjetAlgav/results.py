import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Fonction pour tracer des histogrammes groupés
def tracer_histogramme_combine(fichiers, labels, titre, y_label):
    try:
        plt.figure(figsize=(10, 6))

        # Couleurs pour les barres
        couleurs = ['#4E79A7', '#F28E2B', '#59A14F', '#FF9DA6'] #bleu, orange, vert, rouge

        # Lire les données des fichiers
        valeurs = []
        structures = []

        for fichier in fichiers:
            data = pd.read_csv(fichier)
            valeurs.append(data.iloc[:, 1].values)  # Récupérer les valeurs numériques
            structures = data.iloc[:, 0].values     # Nom des structures (Patricia, Hybride, etc.)

        # Positions des barres
        x = np.arange(len(structures))
        largeur_barre = 0.2  # Largeur des barres

        # Tracer les histogrammes pour chaque jeu de données
        for i in range(len(labels)):
            plt.bar(x + i * largeur_barre, valeurs[i], width=largeur_barre, color=couleurs[i], label=labels[i])

        # Configuration du graphique
        plt.title(titre)
        plt.ylabel(y_label)
        plt.xticks(x + largeur_barre, structures)  # Position des ticks sur l'axe X
        plt.legend()
        plt.grid(axis='y', linestyle='--', alpha=0.7)
        plt.tight_layout()

        # Sauvegarder l'image
        nom_fichier_image = f"{titre.replace(' ', '_')}.png"
        plt.savefig(nom_fichier_image, format='png')
        print(f"Graphique sauvegardé sous : {nom_fichier_image}")

        # Afficher le graphique
        plt.show()
        plt.close()
    except FileNotFoundError as e:
        print(f"Erreur : {e}")

# Chemins des fichiers CSV
fichiers_insertion = [
    "TShakespeare_Insertion.csv",
    "1HenryIV.txt_Insertion.csv",
    "Mots_générés_aléatoirement_Insertion.csv"
]
fichiers_recherche = [
    "TShakespeareresultats_recherche.csv",
    "1HenryIV.txtresultats_recherche.csv",
    "Mots_générés_aléatoirementresultats_recherche.csv"
]
fichiers_suppression = [
    "TShakespeareresultats_suppression.csv",
    "1HenryIV.txtresultats_suppression.csv",
    "Mots_générés_aléatoirementresultats_suppression.csv"
]
fichiers_hauteur = [
    "TShakespeareresultats_hauteur.csv",
    "1HenryIV.txtresultats_hauteur.csv",
    "Mots_générés_aléatoirementresultats_hauteur.csv"
]

# Fichiers d'insertion d'un nouveau mot
fichiers_nouveau_mot = [
    "TShakespeare_InsertionNouveauMot.csv",
    "1HenryIV.txt_InsertionNouveauMot.csv",
    "Mots_générés_aléatoirement_InsertionNouveauMot.csv"
]

# Labels et Traçage
labels = ["Shakespeare", "Henry IV", "Mots Aléatoires"]


# Tracer les histogrammes groupés
tracer_histogramme_combine(fichiers_insertion, labels, "Comparaison_des_temps_insertion", "Temps (ns)")
tracer_histogramme_combine(fichiers_recherche, labels, "Comparaison_des_temps_de_recherche", "Temps (ns)")
tracer_histogramme_combine(fichiers_suppression, labels, "Comparaison_des_temps_de_suppression", "Temps (ns)")
tracer_histogramme_combine(fichiers_hauteur, labels, "Comparaison_des_hauteurs_des_arbres", "Hauteur")
tracer_histogramme_combine(fichiers_nouveau_mot, labels, "Comparaison_des_temps_insertion_nouveau_mot", "Temps (ns)")


