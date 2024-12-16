package EtudesExperimentale;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import PatriciaTrie.FonctionAvancer;
import PatriciaTrie.PatriciaTrieNode;
import TriesHybrides.FonctionAvancerHybride;
import TriesHybrides.TrieHybridesNode;

import static TriesHybrides.FonctionAvancerHybride.*;
import static TriesHybrides.TrieHybridesNode.insertEtReequilibrer;

public class Experimentation {

	public static void main(String[] args) throws IOException {
		effectuerExperimentationRepertoire("Shakespeare", "TShakespeare");
		effectuerExperimentationFichier("Shakespeare/1henryiv.txt", "1HenryIV.txt");
		List<String> motsAleatoires = genererMotsAleatoire(1000);
		effectuerExperimentation(motsAleatoires, "Mots_générés_aléatoirement");
		resultatExperimentationHybrideEquiliber();
	}

	private static void effectuerExperimentationFichier(String cheminFichier, String description) {
		System.out.println("\n**** Expérimentation: " + description + " ****\n");

		PatriciaTrieNode patriciaTrie = new PatriciaTrieNode();
		TrieHybridesNode hybrideTrie = new TrieHybridesNode();
		//temps d'insertion dans patricia
		long debutPatricia = System.nanoTime();
		patriciaTrie.insertMotduFichier(cheminFichier);
		long finPatricia = System.nanoTime();
		System.out.println("Patricia-Trie: " + (finPatricia - debutPatricia) + " ns");
		patriciaTrie.saveToFile(description + "_Patricia.json");

		//temps d'insertion dans hybride
		long debutHybride = System.nanoTime();
		hybrideTrie.insertMotduFichier(cheminFichier);
		long finHybride = System.nanoTime();
		System.out.println("Trie Hybride: " + (finHybride - debutHybride) + " ns");
		hybrideTrie.saveToFile(description + "_Hybride.json");

		enregistrerDonneesCSV(description + "_Insertion.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{finPatricia - debutPatricia, finHybride - debutHybride});
		//temps de recherche et suppression
		long debutRecherchePatricia = System.nanoTime();
		boolean trouvePatricia = FonctionAvancer.Recherche(patriciaTrie, "king");
		long finRecherchePatricia = System.nanoTime();
		long tempsRecherchePatricia = finRecherchePatricia - debutRecherchePatricia;
		System.out.println("Temps de recherche dans Patricia-Trie: " + tempsRecherchePatricia + " ns (Trouvé: " + trouvePatricia + ")");
		long debutRechercheHybride = System.nanoTime();
		boolean trouveHybride = FonctionAvancerHybride.recherche(hybrideTrie, "king");
		long finRechercheHybride = System.nanoTime();
		long tempsRechercheHybride = finRechercheHybride - debutRechercheHybride;
		System.out.println("Temps de recherche dans Trie Hybride: " + tempsRechercheHybride + " ns (Trouvé: " + trouveHybride + ")");
		//sauvegarder les resultats de recherche dans un fichier csv
		enregistrerDonneesCSV(description+"resultats_recherche.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{tempsRecherchePatricia, tempsRechercheHybride});

		//temps de suppression
		long debutSuppressionPatricia = System.nanoTime();
		FonctionAvancer.suppression(patriciaTrie, "king");
		long finSuppressionPatricia = System.nanoTime();
		long tempsSuppressionPatricia = finSuppressionPatricia - debutSuppressionPatricia;
		System.out.println("Temps de suppression dans Patricia-Trie: " + tempsSuppressionPatricia + " ns");
		long debutSuppressionHybride = System.nanoTime();
		FonctionAvancerHybride.supression(hybrideTrie, "king");
		long finSuppressionHybride = System.nanoTime();
		long tempsSuppressionHybride = finSuppressionHybride - debutSuppressionHybride;
		System.out.println("Temps de suppression dans Trie Hybride: " + tempsSuppressionHybride + " ns");
		//sauvegarder les resultats de suppression dans un fichier csv
		enregistrerDonneesCSV(description+"resultats_suppression.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{tempsSuppressionPatricia, tempsSuppressionHybride});

		//les hauteur des arbres
		int hauteurPatricia = FonctionAvancer.hauteur(patriciaTrie);
		int hauteurHybride = FonctionAvancerHybride.hauteur(hybrideTrie);
		int pronfondeurPatricia = FonctionAvancer.profondeurMoyenne(patriciaTrie);
		int profondeurHybride = FonctionAvancerHybride.profondeurMoyenne(hybrideTrie);
		System.out.println("Hauteur de Patricia-Trie: " + hauteurPatricia);
		System.out.println("Hauteur de Trie Hybride: " + hauteurHybride);
		System.out.println("Pronfondeur de Patricia-Trie: " + pronfondeurPatricia);
		System.out.println("Profondeur de Trie Hybride: " + profondeurHybride);
		//sauvegarder les hauteurs dans un fichier csv
		enregistrerDonneesCSV(description+"resultats_hauteur.csv", new String[]{"Hauteur Patricia-Trie", "Hauteur Trie Hybride"}, new long[]{hauteurPatricia, hauteurHybride});

		//temps d'insertion d'un nouveau mot dans patriacia
		long debutInsertionPatricia = System.nanoTime();
		patriciaTrie.inserer("mot");
		long finInsertionPatricia = System.nanoTime();
		System.out.println("Temps d'insertion d'un nouveau mot dans Patricia-Trie: " + (finInsertionPatricia - debutInsertionPatricia) + " ns");
		//temps d'insertion d'un nouveau mot dans hybride
		long debutInsertionHybride = System.nanoTime();
		hybrideTrie.insert(hybrideTrie, "mot");
		long finInsertionHybride = System.nanoTime();
		System.out.println("Temps d'insertion d'un nouveau mot dans Trie Hybride: " + (finInsertionHybride - debutInsertionHybride) + " ns");

		enregistrerDonneesCSV(description + "_InsertionNouveauMot.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{finInsertionPatricia - debutInsertionPatricia , finInsertionHybride - debutInsertionHybride});
	}

	private static void effectuerExperimentationRepertoire(String cheminFichier, String description) throws IOException {
		System.out.println("\n**** Expérimentation: " + description + " ****\n");

		PatriciaTrieNode patriciaTrie = new PatriciaTrieNode();
		TrieHybridesNode hybrideTrie = new TrieHybridesNode();

		long debutPatricia = System.nanoTime();
		patriciaTrie.insertMotsDuRepertoire(cheminFichier);
		long finPatricia = System.nanoTime();
		System.out.println("Patricia-Trie: " + (finPatricia - debutPatricia) + " ns");
		patriciaTrie.saveToFile(description + "_Patricia.json");

		long debutHybride = System.nanoTime();
		hybrideTrie.insertMotsDuRepertoire(cheminFichier);
		long finHybride = System.nanoTime();
		System.out.println("Trie Hybride: " + (finHybride - debutHybride) + " ns");
		hybrideTrie.saveToFile(description + "_Hybride.json");

		enregistrerDonneesCSV(description + "_Insertion.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{finPatricia - debutPatricia, finHybride - debutHybride});

		//temps de recherche
		long debutRecherchePatricia = System.nanoTime();
		boolean trouvePatricia = FonctionAvancer.Recherche(patriciaTrie, "king");
		long finRecherchePatricia = System.nanoTime();
		long tempsRecherchePatricia = finRecherchePatricia - debutRecherchePatricia;
		System.out.println("Temps de recherche dans Patricia-Trie: " + tempsRecherchePatricia + " ns (Trouvé: " + trouvePatricia + ")");

		long debutRechercheHybride = System.nanoTime();
		boolean trouveHybride = FonctionAvancerHybride.recherche(hybrideTrie, "king");
		long finRechercheHybride = System.nanoTime();
		long tempsRechercheHybride = finRechercheHybride - debutRechercheHybride;
		System.out.println("Temps de recherche dans Trie Hybride: " + tempsRechercheHybride + " ns (Trouvé: " + trouveHybride + ")");

		enregistrerDonneesCSV(description+"resultats_recherche.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{tempsRecherchePatricia, tempsRechercheHybride});

		//temps de suppression
		long debutSuppressionPatricia = System.nanoTime();
		FonctionAvancer.suppression(patriciaTrie, "king");
		long finSuppressionPatricia = System.nanoTime();
		long tempsSuppressionPatricia = finSuppressionPatricia - debutSuppressionPatricia;
		System.out.println("Temps de suppression dans Patricia-Trie: " + tempsSuppressionPatricia + " ns");

		long debutSuppressionHybride = System.nanoTime();
		FonctionAvancerHybride.supression(hybrideTrie, "king");
		long finSuppressionHybride = System.nanoTime();
		long tempsSuppressionHybride = finSuppressionHybride - debutSuppressionHybride;
		System.out.println("Temps de suppression dans Trie Hybride: " + tempsSuppressionHybride + " ns");

		enregistrerDonneesCSV(description+"resultats_suppression.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{tempsSuppressionPatricia, tempsSuppressionHybride});

		//hauteur des arbres
		int hauteurPatricia = FonctionAvancer.hauteur(patriciaTrie);
		int hauteurHybride = FonctionAvancerHybride.hauteur(hybrideTrie);
		int pronfondeurPatricia = FonctionAvancer.profondeurMoyenne(patriciaTrie);
		int profondeurHybride = FonctionAvancerHybride.profondeurMoyenne(hybrideTrie);
		System.out.println("Hauteur de Patricia-Trie: " + hauteurPatricia);
		System.out.println("Hauteur de Trie Hybride: " + hauteurHybride);
		System.out.println("Pronfondeur de Patricia-Trie: " + pronfondeurPatricia);
		System.out.println("Profondeur de Trie Hybride: " + profondeurHybride);

		enregistrerDonneesCSV(description+"resultats_hauteur.csv", new String[]{"Hauteur Patricia-Trie", "Hauteur Trie Hybride"}, new long[]{hauteurPatricia, hauteurHybride});

		//temps d'insertion d'un nouveau mot dans patriacia
		long debutInsertionPatricia = System.nanoTime();
		patriciaTrie.inserer("mot");
		long finInsertionPatricia = System.nanoTime();
		System.out.println("Temps d'insertion d'un nouveau mot dans Patricia-Trie: " + (finInsertionPatricia - debutInsertionPatricia) + " ns");
		//temps d'insertion d'un nouveau mot dans hybride
		long debutInsertionHybride = System.nanoTime();
		hybrideTrie.insert(hybrideTrie, "mot");
		long finInsertionHybride = System.nanoTime();
		System.out.println("Temps d'insertion d'un nouveau mot dans Trie Hybride: " + (finInsertionHybride - debutInsertionHybride) + " ns");

		enregistrerDonneesCSV(description + "_InsertionNouveauMot.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{finInsertionPatricia - debutInsertionPatricia , finInsertionHybride - debutInsertionHybride});
	}

	private static void effectuerExperimentation(List<String> mots, String description) {
		System.out.println("\n**** Expérimentation: " + description + " ****\n");

		PatriciaTrieNode patriciaTrie = new PatriciaTrieNode();
		TrieHybridesNode hybrideTrie = new TrieHybridesNode();

		long debutPatricia = System.nanoTime();
		for (String mot : mots) {
			patriciaTrie.inserer(mot);
		}
		long finPatricia = System.nanoTime();
		System.out.println("Patricia-Trie: " + (finPatricia - debutPatricia) + " ns");
		patriciaTrie.saveToFile(description + "_Patricia.json");

		long debutHybride = System.nanoTime();
		for (String mot : mots) {
			hybrideTrie.insert(hybrideTrie, mot);
		}
		long finHybride = System.nanoTime();
		System.out.println("Trie Hybride: " + (finHybride - debutHybride) + " ns");
		hybrideTrie.saveToFile(description + "_Hybride.json");

		enregistrerDonneesCSV(description + "_Insertion.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{finPatricia - debutPatricia, finHybride - debutHybride});

		//temps de recherche
		long debutRecherchePatricia = System.nanoTime();
		boolean trouvePatricia = FonctionAvancer.Recherche(patriciaTrie, "king");
		long finRecherchePatricia = System.nanoTime();
		long tempsRecherchePatricia = finRecherchePatricia - debutRecherchePatricia;
		System.out.println("Temps de recherche dans Patricia-Trie: " + tempsRecherchePatricia + " ns (Trouvé: " + trouvePatricia + ")");

		long debutRechercheHybride = System.nanoTime();
		boolean trouveHybride = FonctionAvancerHybride.recherche(hybrideTrie, "king");
		long finRechercheHybride = System.nanoTime();
		long tempsRechercheHybride = finRechercheHybride - debutRechercheHybride;
		System.out.println("Temps de recherche dans Trie Hybride: " + tempsRechercheHybride + " ns (Trouvé: " + trouveHybride + ")");

		enregistrerDonneesCSV(description+"resultats_recherche.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{tempsRecherchePatricia, tempsRechercheHybride});

		//temps de suppression
		long debutSuppressionPatricia = System.nanoTime();
		FonctionAvancer.suppression(patriciaTrie, "king");
		long finSuppressionPatricia = System.nanoTime();
		long tempsSuppressionPatricia = finSuppressionPatricia - debutSuppressionPatricia;
		System.out.println("Temps de suppression dans Patricia-Trie: " + tempsSuppressionPatricia + " ns");

		long debutSuppressionHybride = System.nanoTime();
		FonctionAvancerHybride.supression(hybrideTrie, "king");
		long finSuppressionHybride = System.nanoTime();
		long tempsSuppressionHybride = finSuppressionHybride - debutSuppressionHybride;
		System.out.println("Temps de suppression dans Trie Hybride: " + tempsSuppressionHybride + " ns");

		enregistrerDonneesCSV(description+"resultats_suppression.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{tempsSuppressionPatricia, tempsSuppressionHybride});

		//hauteur des arbres
		int hauteurPatricia = FonctionAvancer.hauteur(patriciaTrie);
		int hauteurHybride = FonctionAvancerHybride.hauteur(hybrideTrie);
		int pronfondeurPatricia = FonctionAvancer.profondeurMoyenne(patriciaTrie);
		int profondeurHybride = FonctionAvancerHybride.profondeurMoyenne(hybrideTrie);
		System.out.println("Hauteur de Patricia-Trie: " + hauteurPatricia);
		System.out.println("Hauteur de Trie Hybride: " + hauteurHybride);
		System.out.println("Pronfondeur de Patricia-Trie: " + pronfondeurPatricia);
		System.out.println("Profondeur de Trie Hybride: " + profondeurHybride);

		enregistrerDonneesCSV(description+"resultats_hauteur.csv", new String[]{"Hauteur Patricia-Trie", "Hauteur Trie Hybride"}, new long[]{hauteurPatricia, hauteurHybride});

		//temps d'insertion d'un nouveau mot dans patriacia
		long debutInsertionPatricia = System.nanoTime();
		patriciaTrie.inserer("mot");
		long finInsertionPatricia = System.nanoTime();
		System.out.println("Temps d'insertion d'un nouveau mot dans Patricia-Trie: " + (finInsertionPatricia - debutInsertionPatricia) + " ns");
		//temps d'insertion d'un nouveau mot dans hybride
		long debutInsertionHybride = System.nanoTime();
		hybrideTrie.insert(hybrideTrie, "mot");
		long finInsertionHybride = System.nanoTime();
		System.out.println("Temps d'insertion d'un nouveau mot dans Trie Hybride: " + (finInsertionHybride - debutInsertionHybride) + " ns");

		enregistrerDonneesCSV(description + "_InsertionNouveauMot.csv", new String[]{"Patricia-Trie", "Trie Hybride"}, new long[]{finInsertionPatricia - debutInsertionPatricia , finInsertionHybride - debutInsertionHybride});
	}


	private static void resultatExperimentationHybrideEquiliber() {
		//pour patricia
		PatriciaTrieNode patriciaTrie = new PatriciaTrieNode();
		long debut = System.nanoTime();
		patriciaTrie.inserer("apple");
		patriciaTrie.inserer("ball");
		patriciaTrie.inserer("car");
		patriciaTrie.inserer("dog");
		patriciaTrie.inserer("est");
		patriciaTrie.inserer("fan");
		patriciaTrie.inserer("go");
		patriciaTrie.inserer("hat");
		patriciaTrie.inserer("ink");
		patriciaTrie.inserer("joke");
		patriciaTrie.inserer("kite");
		patriciaTrie.inserer("love");
		patriciaTrie.inserer("milk");
		patriciaTrie.inserer("nice");
		patriciaTrie.inserer("open");
		patriciaTrie.inserer("play");
		patriciaTrie.inserer("quit");
		patriciaTrie.inserer("run");
		patriciaTrie.inserer("sun");
		patriciaTrie.inserer("top");
		patriciaTrie.inserer("use");
		patriciaTrie.inserer("van");
		patriciaTrie.inserer("win");
		patriciaTrie.inserer("xray");
		patriciaTrie.inserer("yak");
		patriciaTrie.inserer("zoo");
		long fin = System.nanoTime();
		long tempsPatricia = fin - debut;
		int hauteurPatricia = FonctionAvancer.hauteur(patriciaTrie);
		patriciaTrie.saveToFile("triePH.json");
		System.out.println("Hauteur Patricia: " + hauteurPatricia);

		//pour hybride
		TrieHybridesNode trieI = new TrieHybridesNode();
		long debutI = System.nanoTime();
		trieI = trieI.insert(trieI, "apple");
		trieI = trieI.insert(trieI, "ball");
		trieI = trieI.insert(trieI, "car");
		trieI = trieI.insert(trieI, "dog");
		trieI = trieI.insert(trieI, "est");
		trieI = trieI.insert(trieI, "fan");
		trieI = trieI.insert(trieI, "go");
		trieI = trieI.insert(trieI, "hat");
		trieI = trieI.insert(trieI, "ink");
		trieI = trieI.insert(trieI, "joke");
		trieI = trieI.insert(trieI, "kite");
		trieI = trieI.insert(trieI, "love");
		trieI = trieI.insert(trieI, "milk");
		trieI = trieI.insert(trieI, "nice");
		trieI = trieI.insert(trieI, "open");
		trieI = trieI.insert(trieI, "play");
		trieI = trieI.insert(trieI, "quit");
		trieI = trieI.insert(trieI, "run");
		trieI = trieI.insert(trieI, "sun");
		trieI = trieI.insert(trieI, "top");
		trieI = trieI.insert(trieI, "use");
		trieI = trieI.insert(trieI, "van");
		trieI = trieI.insert(trieI, "win");
		trieI = trieI.insert(trieI, "xray");
		trieI = trieI.insert(trieI, "yak");
		trieI = trieI.insert(trieI, "zoo");
		long finI = System.nanoTime();
		long tempsHybride = finI - debutI;
		int hauteurHybride = hauteur(trieI);
		trieI.saveToFile("trieIH.json");
		System.out.println("Hauteur Hybride: " + hauteurHybride);

		//pour hybride équilibré
		TrieHybridesNode trieE = new TrieHybridesNode();
		long debutE = System.nanoTime();
		trieE = insertEtReequilibrer(trieE, "apple");
		trieE = insertEtReequilibrer(trieE, "ball");
		trieE = insertEtReequilibrer(trieE, "car");
		trieE = insertEtReequilibrer(trieE, "dog");
		trieE = insertEtReequilibrer(trieE, "est");
		trieE = insertEtReequilibrer(trieE, "fan");
		trieE = insertEtReequilibrer(trieE, "go");
		trieE = insertEtReequilibrer(trieE, "hat");
		trieE = insertEtReequilibrer(trieE, "ink");
		trieE = insertEtReequilibrer(trieE, "joke");
		trieE = insertEtReequilibrer(trieE, "kite");
		trieE = insertEtReequilibrer(trieE, "love");
		trieE = insertEtReequilibrer(trieE, "milk");
		trieE = insertEtReequilibrer(trieE, "nice");
		trieE = insertEtReequilibrer(trieE, "open");
		trieE = insertEtReequilibrer(trieE, "play");
		trieE = insertEtReequilibrer(trieE, "quit");
		trieE = insertEtReequilibrer(trieE, "run");
		trieE = insertEtReequilibrer(trieE, "sun");
		trieE = insertEtReequilibrer(trieE, "top");
		trieE = insertEtReequilibrer(trieE, "use");
		trieE = insertEtReequilibrer(trieE, "van");
		trieE = insertEtReequilibrer(trieE, "win");
		trieE = insertEtReequilibrer(trieE, "xray");
		trieE = insertEtReequilibrer(trieE, "yak");
		trieE = insertEtReequilibrer(trieE, "zoo");
		long finE = System.nanoTime();
		long tempsHybrideEquilibre = finE - debutE;
		int hauteurHybrideEquilibre = hauteur(trieE);
		trieE.saveToFile("trieEH.json");
		System.out.println("Hauteur Hybride Équilibré: " + hauteurHybrideEquilibre);

		// Sauvegarder dans un CSV
		enregistrerDonneesCSV("resultats_insertion.csv", new String[]{"Patricia-Trie", "Trie Hybride", "Trie Hybride Équilibré"}, new long[]{tempsPatricia, tempsHybride, tempsHybrideEquilibre});
		enregistrerDonneesCSV("resultats_hauteur.csv", new String[]{"Hauteur Patricia-Trie", "Hauteur Trie Hybride", "Hauteur Trie Hybride Équilibré"}, new long[]{hauteurPatricia, hauteurHybride, hauteurHybrideEquilibre});

	}

	private static void enregistrerDonneesCSV(String nomFichier, String[] enTetes, long[] donnees) {
		try (FileWriter writer = new FileWriter(nomFichier)) {
			writer.append(String.join(",", enTetes)).append("\n");
			for (int i = 0; i < donnees.length; i++) {
				writer.append(enTetes[i]).append(",").append(String.valueOf(donnees[i])).append("\n");
			}
			System.out.println("Données enregistrées dans " + nomFichier);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<String> genererMotsAleatoire(int nombreMots) {
		List<String> mots = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < nombreMots; i++) {
			char[] mot = new char[random.nextInt(10) + 1];
			for (int j = 0; j < mot.length; j++) {
				mot[j] = (char) (random.nextInt(26) + 'a');
			}
			mots.add(new String(mot));
		}
		return mots;
	}
}
