package TriesHybrides;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant des fonctions avancées pour manipuler un trie hybride.
 */
public class FonctionAvancerHybride {
	
	private static int compteurNoeud = 0;

	// Reinitaliser le compteurNoeud
	public static void resetCompteurNoeud() {
		compteurNoeud = 0;
	}

	// Récupérer le compteurNoeud
	public static int getCompteurNoeud() {
		return compteurNoeud;
	}

	// Incrémenter le compteurNoeud
	public static void incrementCompteurNoeud() {
		compteurNoeud++;
	}

    /**
     * Recherche un mot dans le trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @param mot    Le mot à rechercher.
     * @return True si le mot existe, False sinon.
     */
    public static boolean recherche(TrieHybridesNode trieHY, String mot) {
        if (trieHY == null || mot == null || mot.isEmpty()) {
            return false;
        }
        incrementCompteurNoeud();
        char c = mot.charAt(0);

        if (c < trieHY.caractere) {
            return recherche(trieHY.inferieur, mot);
        } else if (c > trieHY.caractere) {
            return recherche(trieHY.superieur, mot);
        } else {
            if (mot.length() == 1) {
                return trieHY.isEndOfWord;
            }
            return recherche(trieHY.egal, mot.substring(1));
        }
    }

    /**
     * Compte le nombre total de mots dans le trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @return Le nombre total de mots.
     */
    public static int comptageMots(TrieHybridesNode trieHY) {
    	incrementCompteurNoeud();
        if (trieHY == null) {
            return 0;
        }

        int count = trieHY.isEndOfWord ? 1 : 0;
        count += comptageMots(trieHY.inferieur);
        count += comptageMots(trieHY.egal);
        count += comptageMots(trieHY.superieur);

        return count;
    }

    /**
     * Liste tous les mots dans le trie hybride par ordre alphabétique.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @return Une liste contenant tous les mots.
     */
    public static List<String> listeMots(TrieHybridesNode trieHY) {
        List<String> mots = new ArrayList<>();
        listeMotsRec(trieHY, "", mots);
        return mots;
    }

    /**
     * Méthode récursive pour collecter les mots dans le trie.
     *
     * @param trieHY Le nœud courant.
     * @param prefixe Le préfixe actuel.
     * @param mots    La liste des mots collectés.
     */
    private static void listeMotsRec(TrieHybridesNode trieHY, String prefixe, List<String> mots) {
    	incrementCompteurNoeud();
    	if (trieHY == null) {
            return;
        }

        listeMotsRec(trieHY.inferieur, prefixe, mots);

        if (trieHY.isEndOfWord) {
            mots.add(prefixe + trieHY.caractere);
        }

        listeMotsRec(trieHY.egal, prefixe + trieHY.caractere, mots);
        listeMotsRec(trieHY.superieur, prefixe, mots);
    }

    /**
     * Compte le nombre de pointeurs `null` (Nil) dans le trie.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @return Le nombre de pointeurs vers Nil.
     */
    public static int comptageNil(TrieHybridesNode trieHY) {
    	incrementCompteurNoeud();
        if (trieHY == null) {
            return 1;
        }

        int count = 0;
        count += comptageNil(trieHY.inferieur);
        count += comptageNil(trieHY.egal);
        count += comptageNil(trieHY.superieur);

        return count;
    }

    /**
     * Calcule la hauteur du trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @return La hauteur du trie.
     */
    public static int hauteur(TrieHybridesNode trieHY) {
        if (trieHY == null) {
            return 0;
        }
        incrementCompteurNoeud();

        int hauteurInferieur = hauteur(trieHY.inferieur);
        int hauteurEgal = hauteur(trieHY.egal);
        int hauteurSuperieur = hauteur(trieHY.superieur);

        return 1 + Math.max(hauteurInferieur, Math.max(hauteurEgal, hauteurSuperieur));
    }

    /**
     * Calcule la profondeur moyenne des mots dans le trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @return La profondeur moyenne.
     */
    public static int profondeurMoyenne(TrieHybridesNode trieHY) {
        int[] profondeurTotale = new int[1];
        int[] nombreDeMots = new int[1];
        calculProfondeur(trieHY, 0, profondeurTotale, nombreDeMots);
        return (int) Math.round(nombreDeMots[0] == 0 ? 0 : (double) profondeurTotale[0] / nombreDeMots[0]);
    }

    /**
     * Méthode récursive pour calculer la profondeur totale et le nombre de mots.
     *
     * @param trieHY            Le nœud courant.
     * @param profondeurActuelle La profondeur actuelle.
     * @param profondeurTotale   Tableau pour stocker la profondeur totale.
     * @param nombreDeMots       Tableau pour stocker le nombre total de mots.
     */
    private static void calculProfondeur(TrieHybridesNode trieHY, int profondeurActuelle, int[] profondeurTotale, int[] nombreDeMots) {
    	incrementCompteurNoeud();
    	if (trieHY == null) {
            return;
        }

        if (trieHY.isEndOfWord) {
            profondeurTotale[0] += profondeurActuelle;
            nombreDeMots[0]++;
        }

        calculProfondeur(trieHY.inferieur, profondeurActuelle + 1, profondeurTotale, nombreDeMots);
        calculProfondeur(trieHY.egal, profondeurActuelle + 1, profondeurTotale, nombreDeMots);
        calculProfondeur(trieHY.superieur, profondeurActuelle + 1, profondeurTotale, nombreDeMots);
    }

    /**
     * Compte le nombre de mots dans le trie où un préfixe donné est présent.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @param prefix Le préfixe à rechercher.
     * @return Le nombre de mots correspondant au préfixe.
     */
    public static int prefixe(TrieHybridesNode trieHY, String prefix) {
        TrieHybridesNode noeud = trouverNoeudPrefixe(trieHY, prefix);
        if (noeud == null) {
            return 0;
        }
        return comptageMots(noeud);
    }

    /**
     * Trouve le nœud correspondant au dernier caractère d'un préfixe.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @param prefix Le préfixe à rechercher.
     * @return Le nœud correspondant au dernier caractère du préfixe.
     */
    private static TrieHybridesNode trouverNoeudPrefixe(TrieHybridesNode trieHY, String prefix) {
    	incrementCompteurNoeud();
        if (trieHY == null || prefix.isEmpty()) {
            return null;
        }

        char c = prefix.charAt(0);

        if (c < trieHY.caractere) {
            return trouverNoeudPrefixe(trieHY.inferieur, prefix);
        } else if (c > trieHY.caractere) {
            return trouverNoeudPrefixe(trieHY.superieur, prefix);
        } else {
            if (prefix.length() == 1) {
                return trieHY;
            }
            return trouverNoeudPrefixe(trieHY.egal, prefix.substring(1));
        }
    }


    /**
     * Supprime un mot du trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @param mot    Le mot à supprimer.
     * @return Le trie modifié après suppression.
     */
    public static TrieHybridesNode supression(TrieHybridesNode trieHY, String mot) {
        if (trieHY == null || mot == null || mot.isEmpty()) {
            System.out.println("Mot non trouvé ou invalide.");
            return trieHY;
        }

        // Trouver et supprimer le mot
        trieHY = supprimer(trieHY, mot, 0);

        return trieHY;
    }

    /**
     * Supprime un mot du trie hybride de manière récursive.
     *
     * @param node  Le nœud courant.
     * @param mot   Le mot à supprimer.
     * @param index L'indice du caractère courant dans le mot.
     * @return Le nœud modifié après suppression.
     */
    private static TrieHybridesNode supprimer(TrieHybridesNode node, String mot, int index) {
    	incrementCompteurNoeud();
    	if (node == null) {
            return null;
        }

        char c = mot.charAt(index);

        if (c < node.caractere) {
            node.inferieur = supprimer(node.inferieur, mot, index);
        } else if (c > node.caractere) {
            node.superieur = supprimer(node.superieur, mot, index);
        } else {
            // Le caractère correspond
            if (index == mot.length() - 1) {
                // Dernier caractère du mot
                node.isEndOfWord = false; // Marquer le mot comme supprimé
            } else {
                // Continuer à supprimer dans le sous-arbre égal
                node.egal = supprimer(node.egal, mot, index + 1);
            }

            // Nettoyer le nœud si nécessaire
            if (!node.isEndOfWord && node.inferieur == null && node.egal == null && node.superieur == null) {
                return null; // Supprimer le nœud s'il est vide
            }
        }

        return node;
    }

    /**
     * Supprime un ensemble de mots issus d'un fichier du trie hybride.
     * @param node le nœud racine du trie hybride.
     * @param filename le chemin du fichier contenant les mots à supprimer.
     * @return le nœud racine après suppression des mots.
     */
    public static TrieHybridesNode supressionMotDuFichier(TrieHybridesNode node, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String mot;
            while ((mot = reader.readLine()) != null) {
                mot = mot.trim(); // nettoie le mot en supprimant les espaces superflus
                if (!mot.isEmpty()) {
                    node = supression(node, mot); // on supprimer chaque mot du fichier
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    /**
     * Fusionne deux tries hybrides.
     *
     * @param trie1 Le premier trie hybride.
     * @param trie2 Le deuxième trie hybride.
     * @return Le trie hybride fusionné.
     */
    public static TrieHybridesNode fusionner(TrieHybridesNode arbre1,TrieHybridesNode arbre2) {
    	TrieHybridesNode arbre=new TrieHybridesNode();
		if(arbre1==null) {
			return arbre2;
		}else if(arbre2==null) {
			return arbre1;
		}else {
			List<String> mots1=listeMots(arbre1);
			int mots_arbre1=mots1.size();
			List<String> mots2=listeMots(arbre2);
			int mots_arbre2=mots2.size();
			if(mots_arbre1<=mots_arbre2) {
				for (String part1 : mots1) {
					incrementCompteurNoeud();
		            //afficher les mots
					arbre=arbre2.insert(arbre2, part1);
		        }
				
			}else {
				for (String part1 : mots2) {
					incrementCompteurNoeud();
		            //afficher les mots
					arbre=arbre2.insert(arbre1, part1);
		        }
			}
			
			return arbre;
		}
		
	}

}
