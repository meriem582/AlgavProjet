package TriesHybrides;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

public class TrieHybridesNode {

    @JsonProperty("char")
    char caractere; // Le caractère du nœud
    @JsonProperty("is_end_of_word")
    boolean isEndOfWord; // Indique si le nœud marque la fin d'un mot
    @JsonProperty("left")
    TrieHybridesNode inferieur; // Sous-arbre gauche
    @JsonProperty("middle")
    TrieHybridesNode egal; // Sous-arbre central
    @JsonProperty("right")
    TrieHybridesNode superieur; // Sous-arbre droit

    // Constructeur principal pour désérialisation avec Jackson
    @JsonCreator
    public TrieHybridesNode(
            @JsonProperty("char") char caractere,
            @JsonProperty("is_end_of_word") boolean isEndOfWord,
            @JsonProperty("left") TrieHybridesNode inferieur,
            @JsonProperty("middle") TrieHybridesNode egal,
            @JsonProperty("right") TrieHybridesNode superieur) {
        this.caractere = caractere;
        this.isEndOfWord = isEndOfWord;
        this.inferieur = inferieur;
        this.egal = egal;
        this.superieur = superieur;
    }

    // Constructeur
    public TrieHybridesNode(@JsonProperty("char") char caractere) {
        this.caractere = caractere;
        this.isEndOfWord = false;
        this.inferieur = null;
        this.egal = null;
        this.superieur = null;
    }

    //constructeur par defaut
    public TrieHybridesNode() {
        this.caractere = '\0';
        this.isEndOfWord = false;
        this.inferieur = null;
        this.egal = null;
        this.superieur = null;
    }


    public char getCaractere() {
        return caractere;
    }

    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }

    public boolean isIs_end_of_word() {
        return isEndOfWord;
    }

    public void setisIs_end_of_word(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public TrieHybridesNode getInferieur() {
        return inferieur;
    }

    public void setInferieur(TrieHybridesNode inferieur) {
        this.inferieur = inferieur;
    }

    public TrieHybridesNode getEgal() {
        return egal;
    }

    public void setEgal(TrieHybridesNode egal) {
        this.egal = egal;
    }

    public TrieHybridesNode getSuperieur() {
        return superieur;
    }

    public void setSuperieur(TrieHybridesNode superieur) {
        this.superieur = superieur;
    }

    // Insérer un mot dans le trie hybride
    public TrieHybridesNode insert(TrieHybridesNode trieH, String mot) {
        if (mot == null || mot.isEmpty()) {
            return trieH;
        }

        if (trieH == null) {
            trieH = new TrieHybridesNode(mot.charAt(0));
        }

        char premierCaractere = mot.charAt(0);

        if (premierCaractere < trieH.caractere) {
            trieH.inferieur = insert(trieH.inferieur, mot);
        } else if (premierCaractere > trieH.caractere) {
            trieH.superieur = insert(trieH.superieur, mot);
        } else {
            if (mot.length() == 1) {
                trieH.isEndOfWord = true;
            } else {
                trieH.egal = insert(trieH.egal, mot.substring(1));
            }
        }

        return trieH;
    }

    // Insérer des mots à partir d'un fichier texte
    public void insertMotduFichier(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String mot;
            while ((mot = reader.readLine()) != null) {
                mot = mot.trim();
                if (!mot.isEmpty()) {
                    insert(this, mot);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Insérer des mots à partir de plusieurs fichiers dans un répertoire
    public void insertMotsDuRepertoire(String repertoirePath) {
        File repertoire = new File(repertoirePath);

        if (!repertoire.exists() || !repertoire.isDirectory()) {
            System.err.println("Erreur : Le chemin spécifié n'est pas un répertoire valide.");
            return;
        }

        File[] fichiers = repertoire.listFiles();
        if (fichiers == null) {
            System.err.println("Erreur : Impossible de lire le contenu du répertoire.");
            return;
        }

        for (File fichier : fichiers) {
            if (fichier.isFile() && fichier.getName().endsWith(".txt")) {
                insertMotduFichier(fichier.getAbsolutePath());
            }
        }
    }

    // Conversion en JSON
    public String arbretoJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}"; // Retourne un JSON vide en cas d'erreur
        }
    }

    // Sauvegarder le trie dans un fichier au format JSON
    public void saveToFile(String filename) {
        String json = arbretoJson();
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Charger un trie hybride depuis un fichier JSON
    public static TrieHybridesNode jsonToArbre(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filename), TrieHybridesNode.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retourne null en cas d'erreur
        }
    }

    // Fonction utilitaire pour tester le trie (exemple)
    public static void printTrie(TrieHybridesNode node, String prefix) {
        if (node == null) return;

        if (node.isEndOfWord) {
            System.out.println(prefix + node.caractere);
        }

        printTrie(node.inferieur, prefix);
        printTrie(node.egal, prefix + node.caractere);
        printTrie(node.superieur, prefix);
    }
}
