package EtudesExperimentale;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import PatriciaTrie.FonctionAvancer;
import PatriciaTrie.PatriciaTrieNode;
import TriesHybrides.FonctionAvancerHybride;
import TriesHybrides.TrieHybridesNode;

public class Experimentation {
	public static void main(String[] args) {

		// Comparaison en temps de construction
		System.out.println("*****************Temps de construction*****************");
		PatriciaTrieNode shakespearePatricia = new PatriciaTrieNode();
		PatriciaTrieNode.resetCompteur(); // Réinitialiser le compteur
		TrieHybridesNode.resetCompteur();

		try {
			long startTimePatricia = System.nanoTime();
			shakespearePatricia.insertMotsDuRepertoire("Shakespeare");
			shakespearePatricia.saveToFile("ShakespeareP.json");
			long endTimePatricia = System.nanoTime();
			System.out.println("Patricia-Trie: " + (endTimePatricia - startTimePatricia) + " ns");
			System.out.println("Nombre total de comparaisons pour Patricia-Trie: " + PatriciaTrieNode.getCompteur());

		} catch (IOException e) {
			e.printStackTrace();
		}
		TrieHybridesNode shakespeareHybrid = new TrieHybridesNode();
		try {
			long startTimeHybrid = System.nanoTime();
			shakespeareHybrid.insertMotsDuRepertoire("Shakespeare");
			shakespeareHybrid.saveToFile("ShakespeareH.json");
			long endTimeHybrid = System.nanoTime();
			System.out.println("Trie Hybride: " + (endTimeHybrid - startTimeHybrid) + " ns");
			System.out.println("Nombre total de comparaisons pour Trie Hybride: " + TrieHybridesNode.getCompteur());


		} catch (Exception e) {
			e.printStackTrace();
		}
		// Comparaison en temps d'insertion de nouveau mot
		System.out.println("*****************Temps d'insertion*********************");
		PatriciaTrieNode.resetCompteur();
		TrieHybridesNode.resetCompteur();
		long startTimeAddPatricia = System.nanoTime();
		shakespearePatricia.inserer("nouveaumot");
		long endTimeAddPatricia = System.nanoTime();
		System.out.println("Patricia-Trie: " + (endTimeAddPatricia - startTimeAddPatricia) + " ns");
		System.out.println("Nombre total de comparaisons pour Patricia-Trie: " + PatriciaTrieNode.getCompteur());

		long startTimeAddHybrid = System.nanoTime();
		shakespeareHybrid.insert(shakespeareHybrid, "nouveaumot");
		long endTimeAddHybrid = System.nanoTime();
		System.out.println("Trie Hybride: " + (endTimeAddHybrid - startTimeAddHybrid) + " ns");
		System.out.println("Nombre total de comparaisons pour Trie Hybride: " + TrieHybridesNode.getCompteur());

		// Comparaison en temps de suppression d'un ensemble de mots
		System.out.println("*****************Temps de suppression*******************");

		List<String> motsASupprimer = Arrays.asList("word1", "word2", "word3");
		long startRemovePatricia = System.nanoTime();
		for (String mot : motsASupprimer) {
			FonctionAvancer.suppression(shakespearePatricia, mot);
		}
		long endRemovePatricia = System.nanoTime();
		System.out.println("Patricia-Trie: " + (endRemovePatricia - startRemovePatricia) + " ns");

		long startRemoveHybrid = System.nanoTime();
		for (String mot : motsASupprimer) {
			FonctionAvancerHybride.supression(shakespeareHybrid, mot);
		}
		long endRemoveHybrid = System.nanoTime();
		System.out.println("Trie Hybride: " + (endRemoveHybrid - startRemoveHybrid) + " ns");

		// Comparaision selon la profondeur de l'arbre
		System.out.println("*****************Profondeur****************************");

		System.out.println("Patricia-Trie: " + FonctionAvancer.profondeurMoyenne(shakespearePatricia));
		System.out.println("Hybride Trie: " + FonctionAvancerHybride.profondeurMoyenne(shakespeareHybrid));

		// Comparaison selon la hauteur de l'arbre
		System.out.println("*****************Hauteur*******************************");

		System.out.println("Patricia-Trie: " + FonctionAvancer.profondeurMoyenne(shakespearePatricia));
		System.out.println("Hybride Trie: " + FonctionAvancerHybride.profondeurMoyenne(shakespeareHybrid));

		// Comparaison en taille de fichier
		System.out.println("*****************Taille du fichier*********************");

		File patriciaFile = new File("Resultats/ShakespeareP.json");
		File hybridFile = new File("Resultats/ShakespeareH.json");

		System.out.println("Patricia-Trie: " + patriciaFile.length() + " octets");
		System.out.println("Trie Hybride: " + hybridFile.length() + " octets");

		// Comparaison en temps de recherche
		System.out.println("*****************Temps de recherche********************");
		long startSearchePatricia = System.nanoTime();
		FonctionAvancer.Recherche(shakespearePatricia, "mot");
		long endSearchePatricia = System.nanoTime();
		System.out.println("Patricia-Trie " + (endSearchePatricia - startSearchePatricia) + " ns");

		long startSearcheHybride = System.nanoTime();
		FonctionAvancerHybride.recherche(shakespeareHybrid, "mot");
		long endSearcheHybride = System.nanoTime();
		System.out.println("Trie Hybride " + (endSearcheHybride - startSearcheHybride) + " ns");

		// Comparaison en temps de de fusion
		System.out.println("*****************Temps de fusion***********************");
		long startFusionPatricia = System.nanoTime();
		FonctionAvancer.Recherche(shakespearePatricia, "mot");
		long endFusionPatricia = System.nanoTime();
		System.out.println("Patricia-Trie " + (endFusionPatricia - startFusionPatricia) + " ns");

		long startFusionHybride = System.nanoTime();
		FonctionAvancerHybride.recherche(shakespeareHybrid, "mot");
		long endFusionHybride = System.nanoTime();
		System.out.println("Trie Hybride " + (endFusionHybride - startFusionHybride) + " ns");

	}
}
