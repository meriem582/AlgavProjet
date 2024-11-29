package PatriciaTrie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PatriciaTrieNode {
	String label;
	boolean is_end_of_word;
	Map<Character, PatriciaTrieNode> children;
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

	public PatriciaTrieNode(String label) {
		this.label = label;
		this.is_end_of_word = false;
		this.children = new HashMap<>();
	}

	public PatriciaTrieNode() {
		this.label = "";
		this.is_end_of_word = false;
		this.children = new HashMap<>();
	}

	public String getLabel() {
		return label;
	}

	public boolean isIs_end_of_word() {
		return is_end_of_word;
	}

	public void setIs_end_of_word(boolean is_end_of_word) {
		this.is_end_of_word = is_end_of_word;
	}

	public void setLabel(String key) {
		this.label = key;
	}

	public Map<Character, PatriciaTrieNode> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, PatriciaTrieNode> children) {
		this.children = children;
	}

	public void inserer(String mot) {
		PatriciaTrieNode noeud = this; // l'arbre ou on va ajouter
		int index = 0; // premiere lettre

		while (index < mot.length()) { // si on a pas atteint la fin du mot
			incrementCompteur();
			char ch = mot.charAt(index); // on recupere la premiere lettre ( du mot ou partie du mot)
			incrementCompteur();
			if (noeud.children.containsKey(ch)) { // si on trouve la premiere lettre dans l'arbre de patricia
				PatriciaTrieNode child = noeud.children.get(ch); // on recupere le noeud ou y a la premiere lettre
				String prefixeCommun = getPrefixeCommun(mot.substring(index), child.label); // on récuper prefixe commun
																							// entre le mot et l'enfant
				incrementCompteur();
				if (prefixeCommun.length() == child.label.length()) { // si le mot trouver et le meme que le prefixe
					noeud = child;
					index += prefixeCommun.length();
				} else {
					arrangement(noeud, child, prefixeCommun, mot.substring(index + prefixeCommun.length()));
					// sinon on decortique en qlq sort le noeud en deux fils ou on ajoute le suffixe
					// du mot a ajouter et le reste de celui trouver dans l'arbre
					return;
				}

			} else { // sinon on ajoute cette lettre et son mot
				noeud.children.put(ch, new PatriciaTrieNode(mot.substring(index)));
				noeud.children.get(ch).is_end_of_word = true;
				return;
			}
		}
		noeud.is_end_of_word = true; // à la fin on dit qu'on a finit d'inserer en disant que c'est la fin du mot
	}

	public void insertMotduFichier(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // on crée un bufferReader qui lit
																						// le fichier.txt
			String mot;
			while ((mot = reader.readLine()) != null) { // la on recupere les mots qui sont dans le fichier un par un en
														// les ajoutant dans l'arbre
				incrementCompteur();
				mot = mot.trim();// Nettoyer le mot en enlevant les espaces superflus
				if (!mot.isEmpty()) { // on insere le mot si il n'est pas vide
					inserer(mot); // Insérer le mot dans l'arbre Patricia
					incrementCompteur();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertMotsDuRepertoire(String repertoirePath) throws IOException {
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
				try {
					this.insertMotduFichier(fichier.getAbsolutePath());
				} catch (NullPointerException e) {
					System.err.println("Erreur interne : Vérifiez que 'children' est bien initialisé.");
					e.printStackTrace();
				}
			}
		}
	}

	public static String getPrefixeCommun(String s1, String s2) {
		int l = Math.min(s1.length(), s2.length()); // on utilise la class Math pour utiliser la fonction min pour
													// récupere la taille minimal des deux mots " car le prefixe ne
													// depassera d'un des mots
		int i = 0; // on commence par la premiere lettre
		while (i < l && s1.charAt(i) == s2.charAt(i)) { // si on a pas atteint la taille minimal et les caractere sont
			incrementCompteur();
			i++; // on ajoute un
		}
		return s1.substring(0, i); // a la fin on recupere d'un des mots le préfixe commun
	}

	private void arrangement(PatriciaTrieNode parent, PatriciaTrieNode child, String prefixeCommun, String suffix) {
		PatriciaTrieNode nvnoeud = new PatriciaTrieNode(prefixeCommun); // on crée un nouveau noeud qui aura le
																		// prefixe commun comme clé
		nvnoeud.children.put(child.label.charAt(prefixeCommun.length()), child);
		nvnoeud.is_end_of_word = suffix.isEmpty(); // si ya pas de suffix donc le mot est terminer

		child.label = child.label.substring(prefixeCommun.length());
		if (!suffix.isEmpty()) {
			incrementCompteur();
			nvnoeud.children.put(suffix.charAt(0), new PatriciaTrieNode(suffix));
			nvnoeud.children.get(suffix.charAt(0)).is_end_of_word = true;
		}

		parent.children.put(prefixeCommun.charAt(0), nvnoeud);
	}

	public String arbreToJson() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
			return "{}"; // Retourne un JSON vide en cas d'erreur
		}
	}

	// Méthode pour sauvegarder le Patricia Trie dans un fichier au format JSON
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

	public static PatriciaTrieNode jsonToArbre(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new File("Resultats", filename), PatriciaTrieNode.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // Retourne null en cas d'erreur
		}
	}

}
