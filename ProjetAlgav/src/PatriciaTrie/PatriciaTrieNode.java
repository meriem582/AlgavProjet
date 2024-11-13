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

	public void insert(String word) {
		PatriciaTrieNode noeud = this; // l'arbre ou on va ajouter
		int index = 0; // premiere lettre

		while (index < word.length()) { // si on a pas atteint la fin du mot
			char ch = word.charAt(index); // on recupere la premiere lettre ( du mot ou partie du mot)
			if (noeud.children.containsKey(ch)) { // si on trouve la premiere lettre dans l'arbre de patricia
				PatriciaTrieNode child = noeud.children.get(ch); // on recupere le noeud ou y a la premiere lettre
				String prefixeCommun = getPrefixeCommun(word.substring(index), child.key); // on récuper prefixe commun
																							// entre le mot et l'enfant

				if (prefixeCommun.length() == child.key.length()) { // si le mot trouver et le meme que le prefixe
					noeud = child;
					index += prefixeCommun.length();
				} else {
					splitNode(noeud, child, prefixeCommun, word.substring(index + prefixeCommun.length()));
					// sinon on decortique en qlq sort le noeud en deux fils ou on ajoute le suffixe
					// du mot a ajouter et le reste de celui trouver dans l'arbre
					return;
				}

			} else { // sinon on ajoute cette lettre et son mot
				noeud.children.put(ch, new PatriciaTrieNode(word.substring(index)));
				noeud.children.get(ch).isEndOfWord = true;
				return;
			}
		}
		noeud.isEndOfWord = true; // à la fin on dit qu'on a finit d'inserer en disant que c'est la fin du mot
	}

	public void insertMotduFichier(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // on crée un bufferReader qui lit
																						// le fichier.txt
			String mot;
			while ((mot = reader.readLine()) != null) { // la on recupere les mots qui sont dans le fichier un par un en
														// les ajoutant dans l'arbre
				mot = mot.trim();// Nettoyer le mot en enlevant les espaces superflus
				if (!mot.isEmpty()) { // on insere le mot si il n'est pas vide
					insert(mot); // Insérer le mot dans l'arbre Patricia
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	// Conversion de PatriciaTrieNode en chaîne de caractère
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

	// Charge un fichier JSON et crée un PatriciaTrieNode
	public static PatriciaTrieNode loadFromFile(String filename) {
		StringBuilder jsonBuilder = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				jsonBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		// Appeler jsonToArbre pour transformer la chaîne en arbre
		return jsonToArbre(jsonBuilder.toString());
	}

	private static PatriciaTrieNode jsonToArbre(String json) {
	    json = json.trim().replaceAll("[{}\"]", ""); // Supprime les {, }, et " pour faciliter le parsing
	    String[] lines = json.split(",\n?"); // Divise chaque ligne sur des virgules, en ignorant le formatage
	    String key = null;
	    boolean isEndOfWord = false;
	    Map<Character, PatriciaTrieNode> childrenMap = new HashMap<>();

	    for (String line : lines) {
	        if (!line.contains(":")) {
	            continue; // Ignore les lignes sans ":"
	        }
	        
	        String[] entry = line.split(":");
	        if (entry.length < 2) {
	            continue; // Ignore les lignes qui ne suivent pas le format clé:valeur
	        }
	        
	        String attribute = entry[0].trim();
	        String value = entry[1].trim();

	        if (attribute.equals("label")) {
	            key = value; // Récupère la clé du nœud
	        } else if (attribute.equals("is_end_of_word")) {
	            isEndOfWord = Boolean.parseBoolean(value); // Récupère l'information sur si c'est un mot complet
	        } else if (attribute.startsWith("children")) {
	            // Vérification si le champ "children" contient bien des accolades
	            int startIdx = value.indexOf("{");
	            int endIdx = value.lastIndexOf("}");
	            
	            if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
	                String childrenJson = value.substring(startIdx + 1, endIdx); // On extrait seulement la partie entre les accolades
	                if (!childrenJson.trim().isEmpty()) {
	                    String[] childNodes = childrenJson.split(",\n?");
	                    for (String childNode : childNodes) {
	                        String[] childEntry = childNode.split(":");
	                        if (childEntry.length < 2) continue;

	                        String childKeyString = childEntry[0].trim().replaceAll("\"", "");
	                        String childJson = childEntry[1].trim();
	                        char childKey = childKeyString.charAt(0); // On suppose que childKey est un caractère

	                        PatriciaTrieNode childNodeInstance = jsonToArbre(childJson);
	                        childrenMap.put(childKey, childNodeInstance);
	                    }
	                }
	            }
	        }
	    }

	    // Création du nœud avec la clé, et ajout de l'information si c'est un mot complet
	    PatriciaTrieNode node = new PatriciaTrieNode(key);
	    node.isEndOfWord = isEndOfWord;
	    
	    // Ajout des enfants au nœud
	    for (Map.Entry<Character, PatriciaTrieNode> entry : childrenMap.entrySet()) {
	        node.children.put(entry.getKey(), entry.getValue());
	    }

	    return node;
	}


}
