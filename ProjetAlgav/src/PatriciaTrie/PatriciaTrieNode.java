package PatriciaTrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PatriciaTrieNode {
	String key;
	boolean isEndOfWord;
	Map<Character, PatriciaTrieNode> children;

	public PatriciaTrieNode(String key) {
		this.key = key;
		this.isEndOfWord = false;
		this.children = new HashMap<>();
	}

//	O(k) tq k est la longeur du mots
	public void inserer(String mot) {
		PatriciaTrieNode noeud = this; // l'arbre ou on va ajouter
		int index = 0; // premiere lettre

		while (index < mot.length()) { // si on a pas atteint la fin du mot
			char ch = mot.charAt(index); // on recupere la premiere lettre ( du mot ou partie du mot)
			if (noeud.children.containsKey(ch)) { // si on trouve la premiere lettre dans l'arbre de patricia
				PatriciaTrieNode child = noeud.children.get(ch); // on recupere le noeud ou y a la premiere lettre
				String prefixeCommun = getPrefixeCommun(mot.substring(index), child.key); // on récuper prefixe commun
																							// entre le mot et l'enfant

				if (prefixeCommun.length() == child.key.length()) { // si le mot trouver et le meme que le prefixe
					noeud = child;
					index += prefixeCommun.length();
				} else {
					splitNode(noeud, child, prefixeCommun, mot.substring(index + prefixeCommun.length()));
					// sinon on decortique en qlq sort le noeud en deux fils ou on ajoute le suffixe
					// du mot a ajouter et le reste de celui trouver dans l'arbre
					return;
				}

			} else { // sinon on ajoute cette lettre et son mot
				noeud.children.put(ch, new PatriciaTrieNode(mot.substring(index)));
				noeud.children.get(ch).isEndOfWord = true;
				return;
			}
		}
		noeud.isEndOfWord = true; // à la fin on dit qu'on a finit d'inserer en disant que c'est la fin du mot
	}

//	O(n * k). tq n est le nombre de mots et k est la longueur moyenne des mots.
	public void insertMotduFichier(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // on crée un bufferReader qui lit
																						// le fichier.txt
			String mot;
			while ((mot = reader.readLine()) != null) { // la on recupere les mots qui sont dans le fichier un par un en
														// les ajoutant dans l'arbre
				mot = mot.trim();// Nettoyer le mot en enlevant les espaces superflus
				if (!mot.isEmpty()) { // on insere le mot si il n'est pas vide
					inserer(mot); // Insérer le mot dans l'arbre Patricia
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	O(k) tq k est la longeure de prefixe
	private String getPrefixeCommun(String s1, String s2) {
		int l = Math.min(s1.length(), s2.length()); // on utilise la class Math pour utiliser la fonction min pour
													// récupere la taille minimal des deux mots " car le prefixe ne
													// depassera d'un des mots
		int i = 0; // on commence par la premiere lettre
		while (i < l && s1.charAt(i) == s2.charAt(i)) { // si on a pas atteint la taille minimal et les caractere sont
														// egaux
			i++; // on ajoute un
		}
		return s1.substring(0, i); // a la fin on recupere d'un des mots le préfixe commun
	}

//	O(m + n), tq m est la longueur de prifixe commun et n est la longueur du suffixe

	private void splitNode(PatriciaTrieNode parent, PatriciaTrieNode child, String prefixeCommun, String suffix) {
		PatriciaTrieNode splitNode = new PatriciaTrieNode(prefixeCommun); // on crée un nouveau noeud qui aura le
																			// prefixe commun comme clé
		splitNode.children.put(child.key.charAt(prefixeCommun.length()), child);
		splitNode.isEndOfWord = suffix.isEmpty(); // si ya pas de suffix donc le mot est terminer

		child.key = child.key.substring(prefixeCommun.length());
		if (!suffix.isEmpty()) {
			splitNode.children.put(suffix.charAt(0), new PatriciaTrieNode(suffix));
			splitNode.children.get(suffix.charAt(0)).isEndOfWord = true;
		}

		parent.children.put(prefixeCommun.charAt(0), splitNode);
	}
//	La complexité totale de la fonction toJson est O(n * T)

	// Conversion de PatriciaTrieNode en chaîne de caractère
	// sa complexité est de O(n), tq n est le nombre de noeuds
	public String toJson() {
		String json = "{\n  \"label\": \"" + key + "\",\n  \"is_end_of_word\": " + (isEndOfWord) + ",\n";

		// Vérification si `children` est vide
		if (children.isEmpty()) {
			json += "  \"children\": {}\n";
		} else {
			json += "  \"children\": {\n";
			int nbr = 0;
			for (Map.Entry<Character, PatriciaTrieNode> entry : children.entrySet()) { // on parcours les children et on
																						// leurs applique le fonction
																						// tojsonstring
				json += "    \"" + entry.getKey() + "\": " + entry.getValue().toJson().replaceAll("(?m)^", "    ");
				if (nbr < children.size() - 1) {
					json += ",";
				}
				json += "\n";
				nbr++;
			}
			json += "  }\n";
		}
		json += "}";
		return json;
	}

	// Méthode pour sauvegarder le Patricia Trie dans un fichier au format JSON
	public void saveToFile(String filename) {
		String jsonCode = this.toJson();
		try (FileWriter file = new FileWriter(filename)) { // on crée un fichier et on mis le contenu du jsonCode dedans
			file.write(jsonCode);
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
