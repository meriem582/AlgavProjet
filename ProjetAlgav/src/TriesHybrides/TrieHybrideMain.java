package TriesHybrides;

import static TriesHybrides.FonctionAvancer.*;

public class TrieHybrideMain {

    public static void main(String[] args) {

        TrieHybridesNode trie = new TrieHybridesNode(' ', -1);
        TrieHybridesNode trie1 = new TrieHybridesNode(' ', -1);

        // Exemple de texte
        String text = "la vie est belle";
        String text1 = "chat chien cheval lapin lion l";

        // on séparer les mots du texte
        String[] parts = text.split("\\s+");
        String[] parts1 = text1.split("\\s+");

        // Insérer chaque mot dans le premier trie hybride
        for (String part : parts) {
            //afficher les mots
            System.out.println(part);
            trie = trie.insert(trie, part, 1);
        }
        //afficher les mots qui sont dans le trie
        System.out.println("Liste des mots dans l'ordre alphabétique : \n" + ListeMots(trie));

        // Insérer chaque mot dans le second trie hybride
        for (String part1 : parts1) {
            trie1 = trie1.insert(trie1, part1, 1);
        }

        // Afficher les résultats
        System.out.println("Le nombre de mots dans trie: " + ComptageMots(trie) + "\n" + "Le nombre de mots dans trie1: " + ComptageMots(trie1));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie : " + ListeMots(trie) + "\n" + "Liste des mots dans l'ordre alphabétique dans trie1: " + ListeMots(trie1));
        System.out.println("Hauteur de l'arbre dans trie : " + Hauteur(trie) + "\n" + "Hauteur de l'arbre dans trie1: " + Hauteur(trie1));
        System.out.println("Profondeur moyenne dans trie : " + ProfondeurMoyenne(trie) + "\n" + "Profondeur moyenne dans trie1: " + ProfondeurMoyenne(trie1));
        System.out.println("Nombre de mots commençant par 'la' dans trie : " + Prefixe(trie, "la") + "\n" + "Nombre de mots commençant par 'la' dans trie1: " + Prefixe(trie1, "la"));
        System.out.println("Recherche de 'belle' dans trie : " + Recherche(trie, "belle") + "\n" + "Recherche de 'belle' dans trie1: " + Recherche(trie1, "belle"));
        System.out.println("Recherche de 'lion' dans trie : " + Recherche(trie, "lion") + "\n" + "Recherche de 'lion' dans trie1: " + Recherche(trie1, "lion"));

        //on teste la suppression



        System.out.println("Avant suppression :");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie : " + ListeMots(trie));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1: " + ListeMots(trie1));

// Suppression des mots
        trie = Supression(trie, "belle");
        trie1 = Supression(trie1, "lion");

        System.out.println("Après suppression :");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie : " + ListeMots(trie));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1: " + ListeMots(trie1));
        // Sauvegarder le trie en JSON
        trie.saveToFile("trieH.json");
        trie1.saveToFile("trieH1.json");

        TrieHybridesNode trieF1 = new TrieHybridesNode(' ', -1);
        TrieHybridesNode trieF2 = new TrieHybridesNode(' ', -1);

        // Ajouter des mots au premier trie
        trieF1 = trieF1.insert(trieF1, "chat", 1);
        trieF1 = trieF1.insert(trieF1, "chien", 2);

        // Ajouter des mots au deuxième trie
        trieF2 = trieF2.insert(trieF2, "chaton", 3);
        trieF2 = trieF2.insert(trieF2, "chien", 4);

        System.out.println("Mots dans le premier trie : " + ListeMots(trieF1));
        System.out.println("Mots dans le deuxième trie : " + ListeMots(trieF2));



        // Fusion des deux tries
        TrieHybridesNode trieFusionne = fusionner(trieF1, trieF2);

        System.out.println("Mots après fusion : " + ListeMots(trieFusionne));


         /*TrieHybridesNode trie = new TrieHybridesNode(' ', -1);
        TrieHybridesNode trie1 = new TrieHybridesNode(' ', -1);

        // Ajouter des mots avec rééquilibrage
        trie = ajouterEtReequilibrer(trie, "chat", 1);
        trie = ajouterEtReequilibrer(trie, "chien", 2);
        trie = ajouterEtReequilibrer(trie, "cheval", 3);

        System.out.println("Liste des mots après insertion :");
        System.out.println(ListeMots(trie));

        // Ajouter des mots sans rééquilibrage
        trie1 = trie1.insert(trie1, "chat", 1);
        trie1 = trie1.insert(trie1, "chien", 2);
        trie1 = trie1.insert(trie1, "cheval", 3);

        System.out.println("Liste des mots après insertion sans rééquilibrage :");
        System.out.println(ListeMots(trie1));

        // Sauvegarder les tries en JSON
        trie.saveToFile("trieEQ.json");
        trie1.saveToFile("trieSanEQ.json");*/

        }
}

