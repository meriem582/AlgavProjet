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
        String text1 = "car|cat|cart|dog|bat";
        String text2 = "chat|chien|cheval|lapin|lion|l";

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
        System.out.println("Le nombre de mots dans trie 1: " + comptageMots(trie1) + "\n" + "Le nombre de mots dans trie2: " + comptageMots(trie1));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1) + "\n" + "Liste des mots dans l'ordre alphabétique dans trie2 : " + listeMots(trie2));
        System.out.println("Hauteur de l'arbre dans trie1 : " + hauteur(trie1) + "\n" + "Hauteur de l'arbre dans trie2: " + hauteur(trie2));
        System.out.println("Profondeur moyenne dans trie1 : " + profondeurMoyenne(trie1) + "\n" + "Profondeur moyenne dans trie2: " + profondeurMoyenne(trie2));
        System.out.println("Nombre de mots commençant par 'la' dans trie1 : " + prefixe(trie1, "la") + "\n" + "Nombre de mots commençant par 'la' dans trie2: " + prefixe(trie2, "la"));
        System.out.println("Recherche de 'belle' dans trie1 : " + recherche(trie1, "belle") + "\n" + "Recherche de 'belle' dans trie2: " + recherche(trie2, "belle"));
        System.out.println("Recherche de 'lion' dans trie1 : " + recherche(trie1, "lion") + "\n" + "Recherche de 'lion' dans trie2: " + recherche(trie2, "lion"));

        //on teste la suppression


        System.out.println("Avant suppression :");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie2: " + listeMots(trie2));

        // Suppression des mots
        trie1 = supression(trie1, "belle");
        trie2 = supression(trie2, "lion");

        System.out.println("Après suppression :");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1: " + listeMots(trie2));
        // Sauvegarder le trie en JSON
        trie1.saveToFile("trie1H.json");
        trie2.saveToFile("trie2H.json");

        TrieHybridesNode trieF1 = new TrieHybridesNode(' ');
        TrieHybridesNode trieF2 = new TrieHybridesNode(' ');

        // Ajouter des mots au premier trie
        trieF1 = trieF1.insert(trieF1, "chat");
        trieF1 = trieF1.insert(trieF1, "chien");

        // Ajouter des mots au deuxième trie
        trieF2 = trieF2.insert(trieF2, "chaton");
        trieF2 = trieF2.insert(trieF2, "chien");

        System.out.println("Mots dans le premier trie : " + listeMots(trieF1));
        System.out.println("Mots dans le deuxième trie : " + listeMots(trieF2));


        // Fusion des deux tries
        TrieHybridesNode trieFusionne = fusionner(trie1, trie2);
        trieFusionne.saveToFile("trieFusionnerH.json");

        System.out.println("Mots après fusion : " + listeMots(trieFusionne));

        System.out.printf("Trie fusionné : %d mots, hauteur %d, profondeur moyenne %.2f%n",
                comptageMots(trieFusionne), hauteur(trieFusionne), profondeurMoyenne(trieFusionne));

        TrieHybridesNode triecopy = TrieHybridesNode.jsonToArbre("trie1H.json");
		triecopy.saveToFile("trie1copyH.json");

        TrieHybridesNode shakeSpeare = new TrieHybridesNode();
        try {
            shakeSpeare.insertMotsDuRepertoire("Shakespeare");
            shakeSpeare.saveToFile("ShakespeareH.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}