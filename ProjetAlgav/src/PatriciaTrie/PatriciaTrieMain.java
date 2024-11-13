package PatriciaTrie;

public class PatriciaTrieMain {

	public static void main(String[] args) {
		PatriciaTrieNode trie = new PatriciaTrieNode("");
		PatriciaTrieNode trie1 = new PatriciaTrieNode("");
		String text = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un\r\n"
				+ " modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du\r\n"
				+ " clavier de la machine a ecrire?";
//		String text="la vie est belle";
		String text1 = "cat cart car cartyy cartyya dog bat ";
		String[] parts = text.split("(?<=\\W)|(?=\\W)");
		String[] parts1 = text1.split("(?<=\\W)|(?=\\W)");
		
//		 Afficher chaque élément séparé
		for (String part : parts) {
			// Ignorer les espaces vides
			if (!part.trim().isEmpty()) {
				trie.insert(part);
			}
		}
		
		for (String part1 : parts1) {
			// Ignorer les espaces vides
			if (!part1.trim().isEmpty()) {
				trie1.insert(part1);
			}
		}
		
//		trie.insertMotduFichier("mots.txt"); // Remplacez "mots.txt" par le nom de votre fichier
//		System.out.println("Le nombre des mots " + FonctionAvancer.comptageMots(trie));
//
//		System.out.println("Liste des mots dans l'ordre alphabétique : " + FonctionAvancer.listeMots(trie));
//		System.out.println("Nombre de pointeurs vers Nil : " + FonctionAvancer.comptageNil(trie));
//		System.out.println("La hauteur de l'arbre est " + FonctionAvancer.hauteur(trie));
//		System.out.println("La profondeur moyenne est " + FonctionAvancer.profondeurMoyenne(trie));
//		System.out.println("Le nombre des mots ou ca est prefixe est " + FonctionAvancer.prefixe(trie, "ca"));
//		System.out.println(FonctionAvancer.Recherche(trie, "car")); // true
//		System.out.println(FonctionAvancer.Recherche(trie, "cart")); // true

//		FonctionAvancer.suppression(trie, "car");
//		System.out.println(FonctionAvancer.Recherche(trie, "car")); // false
//		System.out.println(FonctionAvancer.Recherche(trie, "cart")); // true

		trie.saveToFile("trie.json");
		trie1.saveToFile("trie1.json");
	    PatriciaTrieNode trieFusionne = FonctionAvancer.fusionner(trie, trie1);
	    trieFusionne.saveToFile("trieFusionne.json");

//		trie.saveToFile("trie.json");

	}
}
