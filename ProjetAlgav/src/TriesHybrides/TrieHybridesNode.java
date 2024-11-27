package TriesHybrides;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Cette classe represente un noeud du trie hybride
 * @version 1.0
 * @author ahmed
 */

public class TrieHybridesNode {

    char caractere; // le caractere du noeud
    int valeur; // la valeur du noeud
    TrieHybridesNode inferieur; // le fils inferieur
    TrieHybridesNode egal; // le fils egal
    TrieHybridesNode superieur; // le fils superieur


    public TrieHybridesNode(char caractere, int valeur) {
        this.caractere = caractere;
        this.valeur = valeur;
        this.inferieur = null;
        this.egal = null;
        this.superieur = null;
    }

    //constructeur par defaut
    public TrieHybridesNode(char caractere, int valeur, TrieHybridesNode inferieur, TrieHybridesNode egal, TrieHybridesNode superieur) {
        this.caractere = caractere;
        this.valeur = valeur;
        this.inferieur = inferieur;
        this.egal = egal;
        this.superieur = superieur;
    }

    /**
     * methode qui renvoir une valeur vide
     * @return -1
     */
    public int valeurVide() {
        return -1;
    }

    /**
     * methode qui construit par ajout successifs le trie hybride
     *
     * @param trieH
     * @param mot
     * @param valeur
     *
     * @return
     */
    /**
     * Insère un mot dans le trie hybride.
     *
     * @param trieH  Le nœud racine du trie hybride.
     * @param mot    Le mot à insérer.
     * @param valeur La valeur associée au mot.
     * @return Le nœud mis à jour après insertion.
     */
    public TrieHybridesNode insert(TrieHybridesNode trieH, String mot, int valeur) {
        // Si le mot est vide, on retourne le trie sans modification
        if (mot.isEmpty()) {
            return trieH;
        }

        // Si le trie est vide, on crée un nouveau nœud avec le premier caractère
        if (trieH == null) {
            trieH = new TrieHybridesNode(mot.charAt(0), -1);
        }

        char premierCaractere = mot.charAt(0);

        // Comparaison du caractère actuel du mot avec celui du nœud courant
        if (premierCaractere < trieH.caractere) {
            // Cas où le caractère est inférieur : insertion dans le sous-arbre inférieur
            trieH.inferieur = insert(trieH.inferieur, mot, valeur);
        } else if (premierCaractere > trieH.caractere) {
            // Cas où le caractère est supérieur : insertion dans le sous-arbre supérieur
            trieH.superieur = insert(trieH.superieur, mot, valeur);
        } else {
            // Cas où le caractère est égal
            if (mot.length() == 1) {
                // Si on est au dernier caractère du mot, on affecte la valeur
                trieH.valeur = valeur;
            } else {
                // Sinon, on continue l'insertion dans le sous-arbre égal
                trieH.egal = insert(trieH.egal, mot.substring(1), valeur);
            }
        }

        return trieH;
    }

    /**
     * Méthode qui permet de convertir un trie hybride en format JSON.
     *
     * @return Une chaîne JSON représentant le trie hybride.
     */
    public String toJson() {
        StringBuilder json = new StringBuilder();

        json.append("{\n");
        json.append("  \"caractere\": \"").append(caractere).append("\",\n");
        json.append("  \"valeur\": ").append(valeur).append(",\n");

        // Ajouter le sous-arbre inférieur
        json.append("  \"inferieur\": ");
        if (inferieur == null) {
            json.append("{}");
        } else {
            json.append(inferieur.toJson().replaceAll("(?m)^", "    ")); // Indenter le JSON du sous-arbre
        }
        json.append(",\n");

        // Ajouter le sous-arbre égal
        json.append("  \"egal\": ");
        if (egal == null) {
            json.append("{}");
        } else {
            json.append(egal.toJson().replaceAll("(?m)^", "    "));
        }
        json.append(",\n");

        // Ajouter le sous-arbre supérieur
        json.append("  \"superieur\": ");
        if (superieur == null) {
            json.append("{}");
        } else {
            json.append(superieur.toJson().replaceAll("(?m)^", "    "));
        }
        json.append("\n");

        json.append("}");
        return json.toString();
    }


    /**
     * methode qui permet de sauvegarder un trie hybride dans un fichier au format json
     * @param filename
     */
    public void saveToFile(String filename) {
        //on cree un fichier json
        String json = toJson();
        //on sauvegarde le trie hybride dans le fichier
        try {
            FileWriter file = new FileWriter(filename);
            file.write(json);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }





}
