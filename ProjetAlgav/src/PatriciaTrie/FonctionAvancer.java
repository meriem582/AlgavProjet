package PatriciaTrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FonctionAvancer {
	
	private static int compteurNoeud = 0;
  
	// Réinitialiser le compteur de nœuds
	public static void resetCompteurNoeud() {
		compteurNoeud = 0;
	}

	// Obtenir le compteur de nœuds
	public static int getCompteurNoeud() {
		return compteurNoeud;
	}

	// Incrémenter le compteur de nœuds
	public static void incrementCompteurNoeud() {
		compteurNoeud++;
	}

	public static boolean Recherche(PatriciaTrieNode p, String mot) { // fonction qui retourne True si le mot est
																		// retrouver dans l'arbre de patricia
		PatriciaTrieNode pc = p; // on recupere l'arbre dans pc
		int index = 0;

		while (index < mot.length()) { // on boucle tant que tout le mot n'est pas retrouver
			incrementCompteurNoeud();
			char ch = mot.charAt(index); // on recuper la premiere lettre (du mot ou partie du mot qu'on est en cours de
											// rechercher)
			if (!pc.children.containsKey(ch)) { // si on trouve pas une lettre c'est directement false le mot n'est pas
												// dans l'arbre
				return false;
			}
			PatriciaTrieNode child = pc.children.get(ch);
			String prefix = child.label;
			if (!mot.startsWith(prefix, index)) {
				return false;
			}
			index += prefix.length();
			pc = child;
		}
		return pc.is_end_of_word; // si à la fin on trouve que le mot figure dans l'arbre alors on retourne true
									// sinon false
	}

	public static int comptageMots(PatriciaTrieNode node) { // on compte les fois où isEndOfWord est true
		int nbr = 0;
		if (node.is_end_of_word) {
			nbr++;
		}
		for (PatriciaTrieNode child : node.children.values()) {
			incrementCompteurNoeud();
			nbr += comptageMots(child);
		}
		return nbr;
	}

	public static List<String> listeMots(PatriciaTrieNode node) { //
		List<String> mots = new ArrayList<>();
		listeMotsRec(node, "", mots);
		return mots;
	}

	private static void listeMotsRec(PatriciaTrieNode node, String prefix, List<String> mots) {
		if (node.is_end_of_word) {
			mots.add(prefix);
		}
		// Utilisation de TreeMap pour garantir un ordre alphabétique des enfants
		Map<Character, PatriciaTrieNode> Children0 = new TreeMap<>(node.children);
		for (Map.Entry<Character, PatriciaTrieNode> entry : Children0.entrySet()) {
			incrementCompteurNoeud();
			listeMotsRec(entry.getValue(), prefix + entry.getValue().label, mots);
		}
	}

	public static int comptageNil(PatriciaTrieNode node) {
		return comptageNilRecursif(node);
	}

	private static int comptageNilRecursif(PatriciaTrieNode node) {
		int count = 0;
		// Si le noeud a des enfants
		if (node.children.isEmpty()) {
			// Si le noeud n'a pas d'enfants, on considère que c'est un "Nil"
			incrementCompteurNoeud();
			count++;
		} else {
			// Sinon, on parcourt récursivement les enfants
			for (PatriciaTrieNode child : node.children.values()) {
				incrementCompteurNoeud();
				count += comptageNilRecursif(child);
			}
		}

		return count;
	}

	public static int hauteur(PatriciaTrieNode node) {
		if (node.children.isEmpty()) {
			return 0;
		}
		int maxHeight = 0;
		for (PatriciaTrieNode child : node.children.values()) {
			incrementCompteurNoeud();
			maxHeight = Math.max(maxHeight, hauteur(child));
		}
		return maxHeight + 1;
	}

	public static int profondeurMoyenne(PatriciaTrieNode node) {
		int[] result = new int[2]; // result[0] : somme des profondeurs, result[1] : nombre de feuilles
		calculerProfondeurMoyenne(node, 0, result);
		return (int) Math.round(result[1] == 0 ? 0 : (double) result[0] / result[1]);
	}

	private static void calculerProfondeurMoyenne(PatriciaTrieNode node, int profondeurActuelle, int[] result) {
		if (node.children.isEmpty()) { // Si le nœud est une feuille
			incrementCompteurNoeud();
			result[0] += profondeurActuelle;
			result[1]++;
		} else {
			for (PatriciaTrieNode child : node.children.values()) {
				incrementCompteurNoeud();
				calculerProfondeurMoyenne(child, profondeurActuelle + 1, result); // +1 car y a un fils
			}
		}
	}

	public static int prefixe(PatriciaTrieNode p, String prefix) {
		PatriciaTrieNode pc = p;
		int index = 0;
		// Parcourir l'arbre jusqu'à trouver le nœud correspondant au préfixe
		while (index < prefix.length()) {
			incrementCompteurNoeud();
			char ch = prefix.charAt(index);
			if (!pc.children.containsKey(ch)) {
				return 0; // Si le préfixe n'est pas présent dans l'arbre
			}

			PatriciaTrieNode child = pc.children.get(ch);
			String childKey = child.label;

			if (!prefix.startsWith(childKey, index)) {
				return 0; // Le préfixe ne correspond pas à un mot dans l'arbre
			}

			index += childKey.length();
			pc = child;
		}

		// Une fois le préfixe trouvé, compter les mots descendants de ce nœud
		return compterMotsDescendants(pc);
	}

	private static int compterMotsDescendants(PatriciaTrieNode node) {
		int count = node.is_end_of_word ? 1 : 0; // Compter le nœud actuel s'il représente un mot
		for (PatriciaTrieNode child : node.children.values()) {
			incrementCompteurNoeud();
			count += compterMotsDescendants(child); // Ajouter les mots dans les sous-arbres
		}
		return count;
	}

	public static PatriciaTrieNode suppression(PatriciaTrieNode node, String mot) {
		suppression(node, mot, 0);
		return node;
	}

	public static PatriciaTrieNode supressionDuMotDufichier(PatriciaTrieNode node, String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // on crée un bufferReader qui lit
																						// le fichier.txt
			String mot;
			while ((mot = reader.readLine()) != null) { // la on recupere les mots qui sont dans le fichier un par un en
														// les ajoutant dans l'arbre
				incrementCompteurNoeud();
				mot = mot.trim();// Nettoyer le mot en enlevant les espaces superflus
				if (!mot.isEmpty()) { // on insere le mot si il n'est pas vide
					suppression(node, mot); // Insérer le mot dans l'arbre Patricia
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return node;
	}

	private static boolean suppression(PatriciaTrieNode p, String mot, int index) {
	    incrementCompteurNoeud();

	    // Vérification de la fin du mot
	    if (index == mot.length()) {
	        if (!p.is_end_of_word) {
	            return false; // Le mot n'existe pas
	        }
	        p.is_end_of_word = false; // Marquer ce nœud comme n'étant plus la fin d'un mot

	        // Retourner vrai si ce nœud n'a pas d'enfants
	        return p.children.isEmpty();
	    }

	    // Vérification de l'index
	    if (index >= mot.length()) {
	        return false; // Index invalide
	    }

	    char ch = mot.charAt(index);
	    PatriciaTrieNode child = p.children.get(ch);
	    if (child == null) {
	        return false; // Le mot n'existe pas
	    }

	    // Calculer le nouvel index et vérifier
	    int nextIndex = index + child.label.length();
	    if (nextIndex > mot.length()) {
	        return false; // Index invalide
	    }

	    // Récursion pour supprimer dans les enfants
	    boolean supprimer = suppression(child, mot, nextIndex);

	    // Supprimer le nœud enfant si nécessaire
	    if (supprimer) {
	        p.children.remove(ch);

	        // Supprimer ce nœud si plus d'enfants et pas la fin d'un mot
	        return p.children.isEmpty() && !p.is_end_of_word;
	    }

	    return false;
	}


	public static PatriciaTrieNode fusionner(PatriciaTrieNode trie1, PatriciaTrieNode trie2) {
		PatriciaTrieNode resultat = new PatriciaTrieNode("");
		fusionnerNoeuds(resultat, trie1);
		fusionnerNoeuds(resultat, trie2);
		return resultat;
	}

	private static void fusionnerNoeuds(PatriciaTrieNode cible, PatriciaTrieNode source) {
		for (Map.Entry<Character, PatriciaTrieNode> entry : source.children.entrySet()) {
			incrementCompteurNoeud();
			char charCle = entry.getKey();
			PatriciaTrieNode noeudSource = entry.getValue();

			if (cible.children.containsKey(charCle)) {
				// Le nœud existe déjà, fusionner les enfants
				PatriciaTrieNode noeudCible = cible.children.get(charCle);
				String prefixCommun = PatriciaTrieNode.getPrefixeCommun(noeudCible.label, noeudSource.label);

				if (prefixCommun.equals(noeudSource.label)) {
					// Le préfixe source est identique au préfixe cible
					fusionnerNoeuds(noeudCible, noeudSource);
				} else {
					// Diviser le nœud et fusionner
					PatriciaTrieNode splitNode = new PatriciaTrieNode(prefixCommun);
					cible.children.put(charCle, splitNode);

					// Ajuster les enfants pour le nœud source et le nœud cible
					noeudSource.label = noeudSource.label.substring(prefixCommun.length());
					noeudCible.label = noeudCible.label.substring(prefixCommun.length());

					// Vérifier que les clés ne sont pas vides avant d'accéder à charAt(0)
					if (!noeudSource.label.isEmpty()) {
						splitNode.children.put(noeudSource.label.charAt(0), noeudSource);
					}
					if (!noeudCible.label.isEmpty()) {
						splitNode.children.put(noeudCible.label.charAt(0), noeudCible);
					}
				}
			} else {
				// Ajouter le nœud source dans l'arbre cible
				cible.children.put(charCle, noeudSource);
			}
		}
	}

}