package TriesHybrides;

import PatriciaTrie.FonctionAvancer;
import PatriciaTrie.PatriciaTrieNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static TriesHybrides.FonctionAvancerHybride.*;
import static TriesHybrides.TrieHybridesNode.insertEtReequilibrer;


public class TrieHybrideMain {

    public static void main(String[] args) {
        TrieHybridesNode trie = new TrieHybridesNode();
        trie.insertMotduFichier("mots.txt"); // Remplacez "mots.txt" par le nom de votre fichier
        trie.saveToFile("trieH.json");
        TrieHybridesNode trie1 = new TrieHybridesNode();
        TrieHybridesNode trie2 = new TrieHybridesNode();

        // Exemple de texte
        String text2 = "c at |cart|car|cartyy|cartyya|dog|bat";
        String text1 = "|A|quel|genial|professeur|de|dactylographie|sommes|nous|redevables|de|la|superbe|phrase|ci|dessous|,|un|modele|du|genre|,|que|toute|dactylo|connait|par|coeur|puisque|elle|fait|appel|a|chacune|des|touches|du|clavier|de|la|machine|a|ecrire|?";

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

        TrieHybridesNode trie3 = new TrieHybridesNode();
        TrieHybridesNode trie4 = new TrieHybridesNode();


        for(String part1 : parts1) {
            trie3 = insertEtReequilibrer(trie3, part1);
        }

        for(String part2 : parts2) {
            trie4 = insertEtReequilibrer(trie4, part2);
        }

        System.out.println("Le nombre de mots dans trie 1: " + comptageMots(trie3) + "\n" + "Le nombre de mots dans trie2: " + comptageMots(trie4));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie3) + "\n" + "Liste des mots dans l'ordre alphabétique dans trie2 : " + listeMots(trie4));
        System.out.println("Hauteur de l'arbre dans trie1 : " + hauteur(trie3) + "\n" + "Hauteur de l'arbre dans trie2: " + hauteur(trie4));
        System.out.println("Profondeur moyenne dans trie1 : " + profondeurMoyenne(trie3) + "\n" + "Profondeur moyenne dans trie2: " + profondeurMoyenne(trie4));
        System.out.println("Nombre de mots commençant par 'la' dans trie1 : " + prefixe(trie3, "la") + "\n" + "Nombre de mots commençant par 'la' dans trie2: " + prefixe(trie4, "la"));
        System.out.println("Recherche de 'cart' dans trie1 : " + recherche(trie3, "cart") + "\n" + "Recherche de 'A' dans trie2: " + recherche(trie4, "A")); // true
        System.out.println("Recherche de 'ca' dans trie1 : " + recherche(trie3, "ca") + "\n" + "Recherche de 'ca' dans trie2: " + recherche(trie4, "ca")); // false


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
        trie2 = supression(trie2, "dog");
        trie2 = supression(trie2, "lion");

        System.out.println("Après suppression :");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1 : " + listeMots(trie1));
        System.out.println("Liste des mots dans l'ordre alphabétique dans trie1: " + listeMots(trie2));

        trie2.saveToFile("nvfichier.json");

        TrieHybridesNode trieF1 = new TrieHybridesNode();
        TrieHybridesNode trieF2 = new TrieHybridesNode();

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

        TrieHybridesNode trieE = new TrieHybridesNode();
        trieE = insertEtReequilibrer(trieE, "car");
        trieE = insertEtReequilibrer(trieE, "est");
        trieE = insertEtReequilibrer(trieE, "dog");

        //sauvegarder le trie
        trieE.saveToFile("trieEH.json");
        System.out.println("Liste des mots dans l'ordre alphabétique dans trieE: \n" + listeMots(trieE));
        System.out.println("Hauteur de l'arbre dans trieE : " + hauteur(trieE));
        System.out.println("Profondeur moyenne dans trieE : " + profondeurMoyenne(trieE));
        System.out.println("Nombre de mots commençant par 'ch' dans trieE : " + prefixe(trieE, "ch"));

        TrieHybridesNode trief=new TrieHybridesNode();
        trief=FonctionAvancerHybride.fusionner(trie1,trie2);
        trief.saveToFile("trieFusionnerH.json");

        System.out.println("Liste des mots dans l'ordre alphabétique dans trief apres fussion : " + listeMots(trief));



        List<String> mots = genererMotsAleatoire(2000);

        // Initialisation des arbres
        PatriciaTrieNode patriciaTrie = new PatriciaTrieNode();
        TrieHybridesNode hybrideTrie = new TrieHybridesNode();
        TrieHybridesNode hybrideTrieEquilibre = new TrieHybridesNode();

        // Insertion des mots
        System.out.println("*** Insertion des mots ***");
        for (String mot : mots) {
            patriciaTrie.inserer(mot);
            hybrideTrie.insert(hybrideTrie, mot);
            hybrideTrieEquilibre = TrieHybridesNode.insertEtReequilibrer(hybrideTrieEquilibre, mot);
        }

        // Comptage des mots
        System.out.println("\n*** Comptage des mots ***");
        System.out.println("Nombre de mots dans Patricia-Trie: " + FonctionAvancer.comptageMots(patriciaTrie));
        System.out.println("Nombre de mots dans Trie Hybride: " + FonctionAvancerHybride.comptageMots(hybrideTrie));
        System.out.println("Nombre de mots dans Trie Hybride Équilibré: " + FonctionAvancerHybride.comptageMots(hybrideTrieEquilibre));

        // Hauteur des arbres
        System.out.println("\n*** Hauteur des arbres ***");
        System.out.println("Hauteur de Patricia-Trie: " + FonctionAvancer.hauteur(patriciaTrie));
        System.out.println("Hauteur de Trie Hybride: " + FonctionAvancerHybride.hauteur(hybrideTrie));
        System.out.println("Hauteur de Trie Hybride Équilibré: " + FonctionAvancerHybride.hauteur(hybrideTrieEquilibre));

        // Recherche de mots
        System.out.println("\n*** Recherche de mots ***");
        String motRecherche = "loup";
        System.out.println("Recherche de '" + motRecherche + "' dans Patricia-Trie: " + FonctionAvancer.Recherche(patriciaTrie, motRecherche));
        System.out.println("Recherche de '" + motRecherche + "' dans Trie Hybride: " + FonctionAvancerHybride.recherche(hybrideTrie, motRecherche));
        System.out.println("Recherche de '" + motRecherche + "' dans Trie Hybride Équilibré: " + FonctionAvancerHybride.recherche(hybrideTrieEquilibre, motRecherche));

        // Liste des mots dans l'ordre alphabétique
        System.out.println("\n*** Liste des mots dans chaque arbre ***");
        System.out.println("Patricia-Trie: " + FonctionAvancer.listeMots(patriciaTrie));
        System.out.println("Trie Hybride: " + FonctionAvancerHybride.listeMots(hybrideTrie));
        System.out.println("Trie Hybride Équilibré: " + FonctionAvancerHybride.listeMots(hybrideTrieEquilibre));
    }

    private static List<String> genererMotsAleatoire(int nombreMots) {
        List<String> words = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < nombreMots; i++) {
            char[] mot = new char[random.nextInt(10) + 1];
            for (int j = 0; j < mot.length; j++) {
                mot[j] = (char) (random.nextInt(26) + 'a');
            }
            words.add(new String(mot));
        }
        return words;
    }


}
