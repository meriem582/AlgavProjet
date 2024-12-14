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
			long endTimePatricia = System.nanoTime();
			System.out.println("Patricia-Trie: " + (endTimePatricia - startTimePatricia) + " ns");
			System.out.println("Nombre total de comparaisons pour Patricia-Trie: " + PatriciaTrieNode.getCompteur());
			shakespearePatricia.saveToFile("ShakespeareP.json");

		} catch (IOException e) {
			e.printStackTrace();
		}
		TrieHybridesNode shakespeareHybrid = new TrieHybridesNode();
		try {
			long startTimeHybrid = System.nanoTime();
			shakespeareHybrid.insertMotsDuRepertoire("Shakespeare");
			long endTimeHybrid = System.nanoTime();
			System.out.println("Trie Hybride: " + (endTimeHybrid - startTimeHybrid) + " ns");
			System.out.println("Nombre total de comparaisons pour Trie Hybride: " + TrieHybridesNode.getCompteur());
			shakespeareHybrid.saveToFile("ShakespeareH.json");

		} catch (Exception e) {
			e.printStackTrace();
		}

		//construction avec equilibrage
		System.out.println("*****************Temps de construction avec equilibrage*****************");
		TrieHybridesNode shakespeareHybridEquilibre = new TrieHybridesNode();
		try {
			long startTimeHybridEquilibre = System.nanoTime();
			shakespeareHybridEquilibre.insertEtReequilibrerMotsDuRepertoire("Shakespeare");
			long endTimeHybridEquilibre = System.nanoTime();
			System.out.println("Trie Hybride avec equilibrage: " + (endTimeHybridEquilibre - startTimeHybridEquilibre) + " ns");
			System.out.println("Nombre total de comparaisons pour Trie Hybride avec equilibrage: " + TrieHybridesNode.getCompteur());
			shakespeareHybridEquilibre.saveToFile("ShakespeareHE.json");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Comparaison en temps d'insertion d'un fichier:
		System.out.println("*****************Temps d'insertion d'un fichier********");
		PatriciaTrieNode henryPatricia= new PatriciaTrieNode();
		long startTimePatriciahenry=System.nanoTime();
		henryPatricia.insertMotduFichier("Shakespeare/1henryiv.txt");
		long endTimePatriciahenry=System.nanoTime();
		System.out.println("Patricia-Trie: " + (endTimePatriciahenry - startTimePatriciahenry) + " ns");
		
		TrieHybridesNode henryHybride= new TrieHybridesNode();
		long startTimeHybridehenry=System.nanoTime();
		henryHybride.insertMotduFichier("Shakespeare/1henryiv.txt");
		long endTimeHybridehenry=System.nanoTime();
		System.out.println("Trie Hybride: " + (endTimeHybridehenry - startTimeHybridehenry) + " ns");

		//construction avec equilibrage
		TrieHybridesNode henryHybrideEquilibre= new TrieHybridesNode();
		long startTimeHybrideEquilibrehenry=System.nanoTime();
		henryHybrideEquilibre.insertEtReequilibrerMotsDuFichier("Shakespeare/1henryiv.txt");
		long endTimeHybrideEquilibrehenry=System.nanoTime();
		System.out.println("Trie Hybride avec equilibrage: " + (endTimeHybrideEquilibrehenry - startTimeHybrideEquilibrehenry) + " ns");

		
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

		//comparaison avec equilibrage
		long startTimeAddHybridEquilibre = System.nanoTime();
		shakespeareHybridEquilibre.insertEtReequilibrer(shakespeareHybridEquilibre, "nouveaumot");
		long endTimeAddHybridEquilibre = System.nanoTime();
		System.out.println("Trie Hybride avec equilibrage: " + (endTimeAddHybridEquilibre - startTimeAddHybridEquilibre) + " ns");
		System.out.println("Nombre total de comparaisons pour Trie Hybride avec equilibrage: " + TrieHybridesNode.getCompteur());



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

		//comparaison avec equilibrage
		long startRemoveHybridEquilibre = System.nanoTime();
		for (String mot : motsASupprimer) {
			FonctionAvancerHybride.supression(shakespeareHybridEquilibre, mot);
		}
		long endRemoveHybridEquilibre = System.nanoTime();
		System.out.println("Trie Hybride avec equilibrage: " + (endRemoveHybridEquilibre - startRemoveHybridEquilibre) + " ns");


		// Comparaision selon la profondeur de l'arbre
		System.out.println("*****************Profondeur****************************");

		System.out.println("Patricia-Trie: " + FonctionAvancer.profondeurMoyenne(shakespearePatricia));
		System.out.println("Hybride Trie: " + FonctionAvancerHybride.profondeurMoyenne(shakespeareHybrid));
		System.out.println("Hybride Trie avec equilibrage: " + FonctionAvancerHybride.profondeurMoyenne(shakespeareHybridEquilibre));

		// Comparaison selon la hauteur de l'arbre
		System.out.println("*****************Hauteur*******************************");

		System.out.println("Patricia-Trie: " + FonctionAvancer.hauteur(shakespearePatricia));
		System.out.println("Hybride Trie: " + FonctionAvancerHybride.hauteur(shakespeareHybrid));
		System.out.println("Hybride Trie avec equilibrage: " + FonctionAvancerHybride.hauteur(shakespeareHybridEquilibre));
		//nombre de mots dans l'arbre hybride avec equilibrage
		System.out.println("Nombre de mots dans l'arbre hybride avec equilibrage: " + FonctionAvancerHybride.comptageMots(shakespeareHybridEquilibre));
		System.out.println("Nombre de mots dans l'arbre hybride: " + FonctionAvancerHybride.comptageMots(shakespeareHybrid));

		// Comparaison en taille de fichier
		System.out.println("*****************Taille du fichier*********************");

		File patriciaFile = new File("Resultats/ShakespeareP.json");
		File hybridFile = new File("Resultats/ShakespeareH.json");
		File hybridFileEquilibre = new File("Resultats/ShakespeareHE.json");

		System.out.println("Patricia-Trie: " + patriciaFile.length() + " octets");
		System.out.println("Trie Hybride: " + hybridFile.length() + " octets");
		System.out.println("Trie Hybride avec equilibrage: " + hybridFileEquilibre.length() + " octets");

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

		//comparaison avec equilibrage
		long startSearcheHybrideEquilibre = System.nanoTime();
		FonctionAvancerHybride.recherche(shakespeareHybridEquilibre, "mot");
		long endSearcheHybrideEquilibre = System.nanoTime();
		System.out.println("Trie Hybride avec equilibrage " + (endSearcheHybrideEquilibre - startSearcheHybrideEquilibre) + " ns");

		// Comparaison en temps de de fusion
		System.out.println("*****************Temps de suppression***********************");
		long startFusionPatricia = System.nanoTime();
		FonctionAvancer.suppression(shakespearePatricia, "mot");
		long endFusionPatricia = System.nanoTime();
		System.out.println("Patricia-Trie " + (endFusionPatricia - startFusionPatricia) + " ns");

		long startFusionHybride = System.nanoTime();
		FonctionAvancerHybride.supression(shakespeareHybrid, "mot");
		long endFusionHybride = System.nanoTime();
		System.out.println("Trie Hybride " + (endFusionHybride - startFusionHybride) + " ns");

		//comparaison avec equilibrage
		long startFusionHybrideEquilibre = System.nanoTime();
		FonctionAvancerHybride.supression(shakespeareHybridEquilibre, "mot");
		long endFusionHybrideEquilibre = System.nanoTime();
		System.out.println("Trie Hybride avec equilibrage " + (endFusionHybrideEquilibre - startFusionHybrideEquilibre) + " ns");

		
	}
}
