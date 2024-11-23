package PatriciaTrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FonctionAvancer {

//	 complexite est de O(m*p) tq : m est la longueur du mot à rechercher
//	, p est la longueur du préfixe de chaque nœud dans le Patricia Trie (qui peut varier).
	public static boolean Recherche(PatriciaTrieNode p, String mot) { // fonction qui retourne True si le mot est
																		// retrouver dans l'arbre de patricia
		PatriciaTrieNode pc = p; // on recupere l'arbre dans pc
		int index = 0;

		while (index < mot.length()) { // on boucle tant que tout le mot n'est pas retrouver
			char ch = mot.charAt(index); // on recuper la premiere lettre (du mot ou partie du mot qu'on est en cours de
											// rechercher)
			if (!pc.children.containsKey(ch)) { // si on trouve pas une lettre c'est directement false le mot n'est pas
												// dans l'arbre
				return false;
			}
			PatriciaTrieNode child = pc.children.get(ch);
			String prefix = child.key;
			if (!mot.startsWith(prefix, index)) {
				return false;
			}
			index += prefix.length();
			pc = child;
		}
		return pc.isEndOfWord; // si à la fin on trouve que le mot figure dans l'arbre alors on retourne true
								// sinon false
	}
//	la  complexité est de O(n) " n par rapport au nombre de nœuds dans l'arbre" 
	public static int comptageMots(PatriciaTrieNode node) { // on compte les fois où isEndOfWord est true
		int nbr = 0;
		if (node.isEndOfWord) {
			nbr++;
		}
		for (PatriciaTrieNode child : node.children.values()) {
			nbr += comptageMots(child);
		}
		return nbr;
	}
//	est la complexité de listeMotsRec
	public static List<String> listeMots(PatriciaTrieNode node) { //
		List<String> mots = new ArrayList<>();
		listeMotsRec(node, "", mots);
		return mots;
	}
	
//	la complexité est de O(n⋅k), tq : n est le nombre de nœuds dans l'arbre.
//  k est la longueur moyenne des mots (ou des clés des nœuds dans le Patricia Trie).
	private static void listeMotsRec(PatriciaTrieNode node, String prefix, List<String> mots) {
		if (node.isEndOfWord) {
			mots.add(prefix);
		}
		// Utilisation de TreeMap pour garantir un ordre alphabétique des enfants
		Map<Character, PatriciaTrieNode> Children0 = new TreeMap<>(node.children);
		for (Map.Entry<Character, PatriciaTrieNode> entry : Children0.entrySet()) {
			listeMotsRec(entry.getValue(), prefix + entry.getValue().key, mots);
		}
	}
//	la même avec comptageNilRecursif
	public static int comptageNil(PatriciaTrieNode node) {
		return comptageNilRecursif(node);
	}
//	la complexité est de O(n) "n est le nombre total des noeuds"
	
	private static int comptageNilRecursif(PatriciaTrieNode node) {
		int count = 0;
		// Si le noeud a des enfants
		if (node.children.isEmpty()) {
			// Si le noeud n'a pas d'enfants, on considère que c'est un "Nil"
			count++;
		} else {
			// Sinon, on parcourt récursivement les enfants
			for (PatriciaTrieNode child : node.children.values()) {
				count += comptageNilRecursif(child);
			}
		}

		return count;
	}
//	O(n)
	public static int hauteur(PatriciaTrieNode node) {
		if (node.children.isEmpty()) {
			return 0;
		}
		int maxHeight = 0;
		for (PatriciaTrieNode child : node.children.values()) {
			maxHeight = Math.max(maxHeight, hauteur(child));
		}
		return maxHeight + 1;
	}
//	O(n)
	public static int profondeurMoyenne(PatriciaTrieNode node) {
		int[] result = new int[2]; // result[0] : somme des profondeurs, result[1] : nombre de feuilles
		calculerProfondeurMoyenne(node, 0, result);
		return (int) (result[1] == 0 ? 0 : (double) result[0] / result[1]);
	}
//	O(n)
	private static void calculerProfondeurMoyenne(PatriciaTrieNode node, int profondeurActuelle, int[] result) {
		if (node.children.isEmpty()) { // Si le nœud est une feuille
			result[0] += profondeurActuelle;
			result[1]++;
		} else {
			for (PatriciaTrieNode child : node.children.values()) {
				calculerProfondeurMoyenne(child, profondeurActuelle + 1, result); // +1 car y a un fils
			}
		}
	}
//	la complexité est O(n+m) tq: m est la longueur du préfixe donné en paramètre.
//	n est le nombre de nœuds descendants à partir du nœud correspondant au préfixe (inclus).
	
	public static int prefixe(PatriciaTrieNode p, String prefix) {
		PatriciaTrieNode pc = p;
		int index = 0;

		// Parcourir l'arbre jusqu'à trouver le nœud correspondant au préfixe
		while (index < prefix.length()) {
			char ch = prefix.charAt(index);
			if (!pc.children.containsKey(ch)) {
				return 0; // Si le préfixe n'est pas présent dans l'arbre
			}

			PatriciaTrieNode child = pc.children.get(ch);
			String childKey = child.key;

			if (!prefix.startsWith(childKey, index)) {
				return 0; // Le préfixe ne correspond pas à un mot dans l'arbre
			}

			index += childKey.length();
			pc = child;
		}

		// Une fois le préfixe trouvé, compter les mots descendants de ce nœud
		return compterMotsDescendants(pc);
	}
	
//	complexite O(n)

	private static int compterMotsDescendants(PatriciaTrieNode node) {
		int count = node.isEndOfWord ? 1 : 0; // Compter le nœud actuel s'il représente un mot
		for (PatriciaTrieNode child : node.children.values()) {
			count += compterMotsDescendants(child); // Ajouter les mots dans les sous-arbres
		}
		return count;
	}
//	 le même avec suppression parceque c'est juste un appel à la fonction supression.
	public static PatriciaTrieNode suppression(PatriciaTrieNode node, String mot) {
		suppression(node, mot, 0);
		return node;
	}
//	O(n⋅m) tq : n est le nombre de mots dans le fichier. m est la longueur moyenne des mots dans le fichier.
	public static PatriciaTrieNode supressionDuMotDufichier(PatriciaTrieNode node, String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // on crée un bufferReader qui lit
																						// le fichier.txt
			String mot;
			while ((mot = reader.readLine()) != null) { // la on recupere les mots qui sont dans le fichier un par un en
														// les ajoutant dans l'arbre
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
//	La complexité au pire cas de la fonction suppression est O(n), où n est la longueur du mot à supprimer.
	private static boolean suppression(PatriciaTrieNode p, String mot, int index) {
		if (index == mot.length()) {
			// Nous avons atteint la fin du mot dans l'arbre
			if (!p.isEndOfWord) {
				return false; // Le mot n'existe pas
			}
			p.isEndOfWord = false; // Marquer ce nœud comme n'étant plus la fin d'un mot

			// Retourner vrai si le nœud courant n'a pas d'enfants, indiquant qu'il peut
			// être supprimé
			return p.children.isEmpty();
		}

		char ch = mot.charAt(index);
		PatriciaTrieNode child = p.children.get(ch);
		if (child == null) {
			return false; // Le mot n'existe pas
		}

		// Récursion pour supprimer dans les enfants
		boolean suppimer = suppression(child, mot, index + child.key.length());

		// Supprimer le nœud enfant si nécessaire
		if (suppimer) {
			p.children.remove(ch);

			// Si le nœud actuel n'est plus la fin d'un mot et n'a pas d'autres enfants, il
			// peut aussi être supprimé
			return p.children.isEmpty() && !p.isEndOfWord;
		}
		return false;
	}
//	O(min(m,n)) tq: m est la longueur de la chaîne s1. n est la longueur de la chaîne s2.
	private static String getPrefixeCommun(String s1, String s2) {
		int l = Math.min(s1.length(), s2.length());
		int i = 0;
		while (i < l && s1.charAt(i) == s2.charAt(i)) {
			i++;
		}
		return s1.substring(0, i);
	}
//	O((n+m)⋅k)
	public static PatriciaTrieNode fusionner(PatriciaTrieNode trie1, PatriciaTrieNode trie2) {
		PatriciaTrieNode resultat = new PatriciaTrieNode("");
		fusionnerNoeuds(resultat, trie1);
		fusionnerNoeuds(resultat, trie2);
		return resultat;
	}

//	La complexité au pire cas de la fonction fusionnerNoeuds est O(n⋅k):
//		n est le nombre d'enfants dans le nœud source.
//		k est la longueur moyenne des clés des nœuds (en termes de préfixe).
	private static void fusionnerNoeuds(PatriciaTrieNode cible, PatriciaTrieNode source) {
		for (Map.Entry<Character, PatriciaTrieNode> entry : source.children.entrySet()) {
			char charCle = entry.getKey();
			PatriciaTrieNode noeudSource = entry.getValue();

			if (cible.children.containsKey(charCle)) {
				// Le nœud existe déjà, fusionner les enfants
				PatriciaTrieNode noeudCible = cible.children.get(charCle);
				String prefixCommun = getPrefixeCommun(noeudCible.key, noeudSource.key);

				if (prefixCommun.equals(noeudSource.key)) {
					// Le préfixe source est identique au préfixe cible
					fusionnerNoeuds(noeudCible, noeudSource);
				} else {
					// Diviser le nœud et fusionner
					PatriciaTrieNode splitNode = new PatriciaTrieNode(prefixCommun);
					cible.children.put(charCle, splitNode);

					// Ajuster les enfants pour le nœud source et le nœud cible
					noeudSource.key = noeudSource.key.substring(prefixCommun.length());
					noeudCible.key = noeudCible.key.substring(prefixCommun.length());

					// Vérifier que les clés ne sont pas vides avant d'accéder à charAt(0)
					if (!noeudSource.key.isEmpty()) {
						splitNode.children.put(noeudSource.key.charAt(0), noeudSource);
					}
					if (!noeudCible.key.isEmpty()) {
						splitNode.children.put(noeudCible.key.charAt(0), noeudCible);
					}
				}
			} else {
				// Ajouter le nœud source dans l'arbre cible
				cible.children.put(charCle, noeudSource);
			}
		}
	}

}