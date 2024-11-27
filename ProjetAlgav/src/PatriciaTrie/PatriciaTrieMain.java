package PatriciaTrie;

import java.io.IOException;

public class PatriciaTrieMain {

	public static void main(String[] args) {
		PatriciaTrieNode trie = new PatriciaTrieNode("");
		trie.insertMotduFichier("mots.txt"); // Remplacez "mots.txt" par le nom de votre fichier
		trie.saveToFile("trie.json");
		PatriciaTrieNode trie1 = new PatriciaTrieNode("");
		PatriciaTrieNode trie2 = new PatriciaTrieNode("");

//		String text="la vie est belle";

		String text1 = "c at |cart|car|cartyy|cartyya|dog|bat";
		String text2 = "|A|quel|genial|professeur|de|dactylographie|sommes|nous|redevables|de|la|superbe|phrase|ci|dessous|,|un|modele|du|genre|,|que|toute|dactylo|connait|par|coeur|puisque|elle|fait|appel|a|chacune|des|touches|du|clavier|de|la|machine|a|ecrire|?";
		String[] parts1 = text1.split("\\|");
		String[] parts2 = text2.split("\\|");

//		 Afficher chaque élément séparé
		for (String part1 : parts1) {
			// Ignorer les espaces vides
			if (!part1.trim().isEmpty()) {
				trie1.inserer(part1);
			}
		}

		for (String part2 : parts2) {
			// Ignorer les espaces vides
			if (!part2.trim().isEmpty()) {
				trie2.inserer(part2);
			}
		}

//		System.out.println("Le nombre des mots " + FonctionAvancer.comptageMots(trie1));
//
//		System.out.println("Liste des mots dans l'ordre alphabétique : " + FonctionAvancer.listeMots(trie1));
//		System.out.println("Nombre de pointeurs vers Nil : " + FonctionAvancer.comptageNil(trie1));
//		System.out.println("La hauteur de l'arbre est " + FonctionAvancer.hauteur(trie1));
//		System.out.println("La profondeur moyenne est " + FonctionAvancer.profondeurMoyenne(trie2));
//		System.out.println("Le nombre des mots ou ca est prefixe est " + FonctionAvancer.prefixe(trie1, "car"));
//		System.out.println(FonctionAvancer.Recherche(trie1, "car")); 
//		System.out.println(FonctionAvancer.Recherche(trie1, "cart")); 
//
//		FonctionAvancer.suppression(trie1, "car");
//		System.out.println(FonctionAvancer.Recherche(trie1, "car")); 
//		System.out.println(FonctionAvancer.Recherche(trie1, "cart")); 
//
//		FonctionAvancer.suppression(trie1, "A");
//		trie1.saveToFile("triesup.json");
//
		trie1.saveToFile("trie1.json");
		trie2.saveToFile("trie2.json");
		PatriciaTrieNode trieFusionne = FonctionAvancer.fusionner(trie1, trie2);
		trieFusionne.saveToFile("trieFusionner.json");

		PatriciaTrieNode triecopy = PatriciaTrieNode.loadFromFile("trie1.json");

		triecopy.saveToFile("triecopy.json");

		PatriciaTrieNode shakspeare = new PatriciaTrieNode();
		try {
			shakspeare.insertMotsDuRepertoire("Shakespeare");
			shakspeare.saveToFile("Shakespeare.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
