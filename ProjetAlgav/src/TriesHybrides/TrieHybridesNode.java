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
    
	private static int compteurComparaisons = 0; // Compteur statique pour mesurer les comparaisons
	
	public static void resetCompteur() {
		compteurComparaisons = 0; // Réinitialiser le compteur
	}

	public static int getCompteur() {
		return compteurComparaisons; // Récupérer le nombre de comparaisons
	}

	public static void incrementCompteur() {
		compteurComparaisons++; // Incrémenter le compteur
	}

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
        	incrementCompteur();
            return trieH;
        }

        if (trieH == null) {
        	incrementCompteur();
            trieH = new TrieHybridesNode(mot.charAt(0));
        }

        char premierCaractere = mot.charAt(0);

        if (premierCaractere < trieH.caractere) {
        	incrementCompteur();
            trieH.inferieur = insert(trieH.inferieur, mot);
        } else if (premierCaractere > trieH.caractere) {
            trieH.superieur = insert(trieH.superieur, mot);
        } else {
        	incrementCompteur();
            if (mot.length() == 1) {
            	incrementCompteur();
                trieH.isEndOfWord = true;
            } else {
            	incrementCompteur();
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
            	incrementCompteur();
                mot = mot.trim();
                if (!mot.isEmpty()) {
                	incrementCompteur();
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
        	incrementCompteur();
            System.err.println("Erreur : Le chemin spécifié n'est pas un répertoire valide.");
            return;
        }

        File[] fichiers = repertoire.listFiles();
        if (fichiers == null) {
        	incrementCompteur();
            System.err.println("Erreur : Impossible de lire le contenu du répertoire.");
            return;
        }

        for (File fichier : fichiers) {
            if (fichier.isFile() && fichier.getName().endsWith(".txt")) {
            	incrementCompteur();
                insertMotduFichier(fichier.getAbsolutePath());
            }
        }
    }

    // Conversion en JSON
    public String arbreToJson() {
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
	    String jsonCode = this.arbreToJson();
	    // Définir le répertoire où le fichier sera enregistré
	    String directoryPath = "Resultats";
	    File directory = new File(directoryPath);

	    // Vérifier si le répertoire existe, sinon le créer
	    if (!directory.exists()) {
	    	incrementCompteur();
	        if (!directory.mkdirs()) {
	        	incrementCompteur();
	            System.err.println("Échec de la création du répertoire : " + directoryPath);
	            return; // On sort si le répertoire ne peut pas être créé
	        }
	    }

	    // Construire le chemin complet du fichier
	    File filePath = new File(directory, filename);

	    // Écrire le contenu dans le fichier
	    try (FileWriter fileWriter = new FileWriter(filePath)) {
	        fileWriter.write(jsonCode);
	        fileWriter.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    // Charger un trie hybride depuis un fichier JSON
    public static TrieHybridesNode jsonToArbre(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("Resultats",filename), TrieHybridesNode.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retourne null en cas d'erreur
        }
    }

    // Fonction utilitaire pour tester le trie (exemple)
    public static void printTrie(TrieHybridesNode node, String prefix) {
        if (node == null) {
        	incrementCompteur();
        	return;
        }

        if (node.isEndOfWord) {
        	incrementCompteur();
            System.out.println(prefix + node.caractere);
        }

        printTrie(node.inferieur, prefix);
        printTrie(node.egal, prefix + node.caractere);
        printTrie(node.superieur, prefix);
    }
}
