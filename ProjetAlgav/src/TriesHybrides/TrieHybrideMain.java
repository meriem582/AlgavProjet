package TriesHybrides;

import static TriesHybrides.FonctionAvancerHybride.*;


public class TrieHybrideMain {

    public static void main(String[] args) {
    	TrieHybridesNode trie = new TrieHybridesNode(' ');
		trie.insertMotduFichier("mots.txt"); // Remplacez "mots.txt" par le nom de votre fichier
		trie.saveToFile("trieH.json");
        TrieHybridesNode trie1 = new TrieHybridesNode(' ');
        TrieHybridesNode trie2 = new TrieHybridesNode(' ');

        // Exemple de texte
        String text1 = "c at |cart|car|cartyy|cartyya|dog|bat";
		String text2 = "|A|quel|genial|professeur|de|dactylographie|sommes|nous|redevables|de|la|superbe|phrase|ci|dessous|,|un|modele|du|genre|,|que|toute|dactylo|connait|par|coeur|puisque|elle|fait|appel|a|chacune|des|touches|du|clavier|de|la|machine|a|ecrire|?";
		
        // on séparer les mots du texte
        String[] parts1 = text1.split("\\|");
        String[] parts2 = text2.split("\\|");

        // Insérer chaque mot dans le premier trie hybride
        for (String part1 : parts1) {
            //afficher les mots
            trie1 = trie1.insert(trie1, part1);
        }
        //afficher les mots qui sont dans le trie
        System.out.println("Liste des mots dans l'ordre alphabétique : \n" + listeMots(trie1));

        // Insérer chaque mot dans le second trie hybride
        for (String part2 : parts2) {
            trie2 = trie2.insert(trie2, part2);
        }

        // Afficher les résultats
        System.out.println("Le nombre de mots dans trie 1: " + comptageMots(trie1) + "\n" + "Le nombre de mots dans trie2: " + comptageMots(trie2));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1) + "\n" + "Liste des mots dans l'ordre alphabétique dans trie2 : " + listeMots(trie2));
        System.out.println("Hauteur de l'arbre dans trie1 : " + hauteur(trie1) + "\n" + "Hauteur de l'arbre dans trie2: " + hauteur(trie2));
        System.out.println("Profondeur moyenne dans trie1 : " + profondeurMoyenne(trie1) + "\n" + "Profondeur moyenne dans trie2: " + profondeurMoyenne(trie2));
        System.out.println("Nombre de mots commençant par 'la' dans trie1 : " + prefixe(trie1, "la") + "\n" + "Nombre de mots commençant par 'la' dans trie2: " + prefixe(trie2, "la"));
        System.out.println("Recherche de 'cart' dans trie1 : " + recherche(trie1, "cart") + "\n" + "Recherche de 'A' dans trie2: " + recherche(trie2, "A")); // true
        System.out.println("Recherche de 'ca' dans trie1 : " + recherche(trie1, "ca") + "\n" + "Recherche de 'ca' dans trie2: " + recherche(trie2, "ca")); // false

        //on teste la suppression



        // Sauvegarder le trie en JSON
        trie1.saveToFile("trie1H.json");
        trie2.saveToFile("trie2H.json");
        
      System.out.println("Avant suppression :");
      System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1));
      System.out.println("Liste des mots dans l'ordre alphabétique dans trie2: " + listeMots(trie2));

      // Suppression des mots
      trie1 = supression(trie1, "dog");
      trie2 = supression(trie2, "lion");

      System.out.println("Après suppression :");
      System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1));
      System.out.println("Liste des mots dans l'ordre alphabétique dans trie1: " + listeMots(trie2));
      
      trie1.saveToFile("nvfichier.json");

        TrieHybridesNode trieF1 = new TrieHybridesNode(' ');
        TrieHybridesNode trieF2 = new TrieHybridesNode(' ');

        // Ajouter des mots au premier trie
        trieF1 = trieF1.insert(trieF1, "chat");
        trieF1 = trieF1.insert(trieF1, "chien");
        for (String part1 : parts1) {
            //afficher les mots
            trieF1 = trieF1.insert(trieF1, part1);
        }

        // Ajouter des mots au deuxième trie
        trieF2 = trieF2.insert(trieF2, "chaton");
        trieF2 = trieF2.insert(trieF2, "chien");
        for (String part2 : parts2) {
            trieF2 = trieF2.insert(trieF2, part2);
        }

        System.out.println("Mots dans le premier trie : " + listeMots(trieF1));
        System.out.println("Mots dans le deuxième trie : " + listeMots(trieF2));





        System.out.println("Mots dans le premier trie : " + listeMots(trie1));
        System.out.println("Mots dans le deuxième trie : " + listeMots(trie2));


        TrieHybridesNode triecopy = TrieHybridesNode.jsonToArbre("trie1H.json");
		triecopy.saveToFile("trie1copyH.json");

        TrieHybridesNode trieI = new TrieHybridesNode();
        trieI = trieI.insert(trieI, "car");
        trieI = trieI.insert(trieI, "est");
        trieI = trieI.insert(trieI, "dog");

        //sauvegarder le trie
        trieI.saveToFile("trieIH.json");
        //afficher les mots
        System.out.println("Liste des mots dans l'ordre alphabétique dans trieI : \n" + listeMots(trieI));
        //calculer la hauteur
        System.out.println("Hauteur de l'arbre dans trieI : " + hauteur(trieI));
        //calculer la profondeur moyenne
        System.out.println("Profondeur moyenne dans trieI : " + profondeurMoyenne(trieI));
        //calculer le nombre de mots commençant par 'ch'
        System.out.println("Nombre de mots commençant par 'ch' dans trieI : " + prefixe(trieI, "ch"));

        //test d'insertion et equilibrage

        TrieHybridesNode trieE = new TrieHybridesNode(' ');
        trieE = trieE.insertEtReequilibrer(trieE, "car");
        trieE = trieE.insertEtReequilibrer(trieE, "est");
        trieE = trieE.insertEtReequilibrer(trieE, "dog");

        //sauvegarder le trie
        trieE.saveToFile("trieEH.json");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trieE: \n" + listeMots(trieE));
        System.out.println("Hauteur de l'arbre dans trieE : " + hauteur(trieE));
        System.out.println("Profondeur moyenne dans trieE : " + profondeurMoyenne(trieE));
        System.out.println("Nombre de mots commençant par 'ch' dans trieE : " + prefixe(trieE, "ch"));
    }
}