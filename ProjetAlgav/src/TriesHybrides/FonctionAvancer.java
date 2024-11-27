package TriesHybrides;

import java.util.ArrayList;
import java.util.List;

/**
    * Classe qui contient les fonctions avancées du trie hybride
    * @version 1.0
    * @author ahmed
 */

public class FonctionAvancer {

    /**
     * Recherche un mot dans le trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @param mot    Le mot à rechercher.
     * @return True si le mot existe, False sinon.
     */
    public static boolean Recherche(TrieHybridesNode trieHY, String mot) {
        // Vérifier si le noeud courant est null
        if (trieHY == null) {
            return false;
        }

        // Vérifier si le mot est vide
        if (mot == null || mot.isEmpty()) {
            return false;
        }

        char c = mot.charAt(0); // On récupère le premier caractère du mot

        // Comparer le caractère courant avec celui du nœud
        if (c < trieHY.caractere) {
            // Recherche dans le sous-arbre inférieur
            return Recherche(trieHY.inferieur, mot);
        } else if (c > trieHY.caractere) {
            // Recherche dans le sous-arbre supérieur
            return Recherche(trieHY.superieur, mot);
        } else {
            // Le caractère correspond, on avance dans le mot
            if (mot.length() == 1) {
                // Si c'est le dernier caractère, vérifier si le mot est valide
                return trieHY.valeur != trieHY.valeurVide();
            }
            // Continuer dans le sous-arbre égal
            return Recherche(trieHY.egal, mot.substring(1));
        }
    }


    /**
     * Cette methode permet de compter le nombre de mots dans le dictionnaire: ComptageMots(trieHY) → entier
     * @param trieHY
     * @return le nombre de mots dans le dictionnaire
     */
    public static int ComptageMots(TrieHybridesNode trieHY) {
        if (trieHY == null) {
            return 0;
        }
        int nbr = 0;
        if (trieHY.valeur != trieHY.valeurVide()) { //si la valeur du noeud est differente de -1 c'est à dire qu'il y a un mot
            nbr++;
        }
        nbr += ComptageMots(trieHY.inferieur);  //puis on compte les mots dans les fils infer
        nbr += ComptageMots(trieHY.egal); //on compte les mots dans les fils egal
        nbr += ComptageMots(trieHY.superieur); //on compte les mots dans les fils superieur
        return nbr;
    }

    /**
     * Cette methode permet de lister les mots du dictionnaire dans l'ordre alphabétique: ListeMots(arbre) → liste[mots]
     * @param trieHY
     * @return la liste des mots dans l'ordre alphabétique
     */
    public static List<String> ListeMots(TrieHybridesNode trieHY) {
        List<String> mots = new ArrayList<>();
        if(trieHY != null) {
            ListeMotsRec(trieHY, "", mots);
        }
        return mots;
    }

    /**
     * Cette methode permet de lister les mots du dictionnaire dans l'ordre alphabétique
     * @param trieHY
     * @param prefix
     * @param mots
     */
    private static void ListeMotsRec(TrieHybridesNode trieHY, String prefix, List<String> mots) {
        if (trieHY == null) {
            return;
        }
        ListeMotsRec(trieHY.inferieur, prefix, mots);
        if (trieHY.valeur != -1) {
            mots.add(prefix + trieHY.caractere);
        }
        ListeMotsRec(trieHY.egal, prefix + trieHY.caractere, mots);
        ListeMotsRec(trieHY.superieur, prefix, mots);

    }

    /**
     * Methode qui compte les pointeurs vers Nil : ComptageNil(arbre) → entier
     * @param trieHY
     * @return le nombre de pointeurs vers Nil
     */
    public static int ComptageNil(TrieHybridesNode trieHY) {
        if (trieHY == null) {  //si le trie est vide alors on retourne 1 car il pointe vers Nil
            return 1;
        }
        int count = 0;
        count += ComptageNil(trieHY.inferieur);
        count += ComptageNil(trieHY.egal);
        count += ComptageNil(trieHY.superieur);
        return count;
    }

    /**
     * Methode qui calcule la hauteur de l'arbre : Hauteur(arbre) → entier
     * @param trieHY
     * @return la hauteur de l'arbre
     */
    public static int Hauteur(TrieHybridesNode trieHY) {
        if (trieHY == null) {
            return 0;
        }
        int maxHeight = 0;
        int height = 0;
        height = Hauteur(trieHY.inferieur);
        if (height > maxHeight) {
            maxHeight = height;
        }
        height = Hauteur(trieHY.egal);
        if (height > maxHeight) {
            maxHeight = height;
        }
        height = Hauteur(trieHY.superieur);
        if (height > maxHeight) {
            maxHeight = height;
        }
        return maxHeight + 1;
    }

    /**
     * Methode qui calcule la profondeur moyenne de l'arbre : ProfondeurMoyenne(arbre) → réel
     * @param trieHY
     * @return la profondeur moyenne de l'arbre
     */
    public static double ProfondeurMoyenne(TrieHybridesNode trieHY) {
        int[] profondeur = new int[1];  //tableau qui contient la profondeur
        int[] somme = new int[1];  //tableau qui contient la somme des profondeurs
        ProfondeurMoyenneRec(trieHY, 0, profondeur, somme);
        return (double) somme[0] / ComptageMots(trieHY);  //la profondeur moyenne est la somme des profondeurs divisée par le nombre de mots
    }

    /**
     * Methode qui calcule la profondeur moyenne de l'arbre
     * @param trieHY
     * @param profondeurActuelle
     * @param profondeur
     * @param somme
     */
    private static void ProfondeurMoyenneRec(TrieHybridesNode trieHY, int profondeurActuelle, int[] profondeur, int[] somme) {
        if (trieHY == null) {
            return;
        }
        if (trieHY.valeur != -1) {
            somme[0] += profondeurActuelle;
            profondeur[0]++;
        }
        ProfondeurMoyenneRec(trieHY.inferieur, profondeurActuelle + 1, profondeur, somme);
        ProfondeurMoyenneRec(trieHY.egal, profondeurActuelle, profondeur, somme);
        ProfondeurMoyenneRec(trieHY.superieur, profondeurActuelle + 1, profondeur, somme);
    }

    /**
     * Methode qui compte le nombre de mots commençant par un préfixe donné : Prefixe(arbre, préfixe) → entier
     * @param trieHY
     * @param prefixe
     * @return le nombre de mots commençant par le préfixe donné
     */
    public static int Prefixe(TrieHybridesNode trieHY, String prefixe) {
        if (trieHY == null) {
            return 0;
        }
        if (prefixe.isEmpty()) {
            return ComptageMots(trieHY);
        }
        char c = prefixe.charAt(0);
        if (c < trieHY.caractere) {
            return Prefixe(trieHY.inferieur, prefixe);
        }//sinon on verifie si le caractere est egal au caractere du noeud et aussi si le prefixe est un seul caractere et que la valeur du noeud est differente de -1
        else if (c == trieHY.caractere && prefixe.length() == 1 && trieHY.valeur != -1) {
            return 1 + Prefixe(trieHY.egal, prefixe.substring(1));
        } else if (c == trieHY.caractere) {
            return Prefixe(trieHY.egal, prefixe.substring(1));
        } else {
            return Prefixe(trieHY.superieur, prefixe);
        }

    }


    /**
     * Supprime un mot du trie hybride.
     *
     * @param trieHY Le nœud racine du trie hybride.
     * @param mot    Le mot à supprimer.
     * @return Le trie modifié après suppression.
     */
    public static TrieHybridesNode Supression(TrieHybridesNode trieHY, String mot) {
        //on cherche a trouver le noeud correspondant au dernier caractere du mot
        TrieHybridesNode node = trouverNoeud(trieHY, mot);
        if (node == null || node.valeur == -1) {
            System.out.println("Mot non trouvé dans le trie.");
            return trieHY; // Rien à supprimer si le mot n'existe pas
        }

        // on marque le mot comme supprimé
        node.valeur = -1;
        System.out.println("Mot marqué comme supprimé : " + mot);

        // puis on nettoie le trie
        return nettoyerTrie(trieHY, mot, 0);
    }

    /**
     * Trouve le nœud correspondant à la fin du mot.
     *
     * @param node  Le nœud courant.
     * @param mot   Le mot à rechercher.
     * @return Le nœud correspondant au dernier caractère du mot, ou null si absent.
     */
    private static TrieHybridesNode trouverNoeud(TrieHybridesNode node, String mot) {
        //si le noeud est null ou le mot est vide on retourne null
        if (node == null || mot.isEmpty()) {
            return null;
        }

        char c = mot.charAt(0); // On récupère le premier caractère du mot

        if (c < node.caractere) {
            return trouverNoeud(node.inferieur, mot);
        } else if (c > node.caractere) {
            return trouverNoeud(node.superieur, mot);
        } else {
            //si le caractere est egal au caractere du noeud
            if (mot.length() == 1) {
                return node; // on retourne le noeud si on est au dernier caractere
            }
            return trouverNoeud(node.egal, mot.substring(1));
        }
    }

    /**
     * Nettoie les nœuds inutiles après la suppression d'un mot.
     *
     * @param node  Le nœud courant.
     * @param mot   Le mot à nettoyer.
     * @param index L'indice du caractère courant dans le mot.
     * @return Le nœud modifié après nettoyage.
     */
    private static TrieHybridesNode nettoyerTrie(TrieHybridesNode node, String mot, int index) {
        if (node == null) {
            return null;
        }

        // Si nous n'avons pas encore atteint le dernier caractère
        if (index < mot.length()) {
            char c = mot.charAt(index);

            if (c < node.caractere) {
                node.inferieur = nettoyerTrie(node.inferieur, mot, index);
            } else if (c > node.caractere) {
                node.superieur = nettoyerTrie(node.superieur, mot, index);
            } else {
                node.egal = nettoyerTrie(node.egal, mot, index + 1);
            }
        }

        // Si le nœud ne représente pas un mot et n'a pas de sous-arbres, le supprimer
        if (node.valeur == -1 && node.inferieur == null && node.egal == null && node.superieur == null) {
            return null;
        }

        return node; // Retourner le nœud modifié
    }


    /**
     * Rééquilibre un nœud si nécessaire, de manière récursive.
     *
     * @param node Le nœud à rééquilibrer.
     * @return Le nœud rééquilibré.
     */
    private static TrieHybridesNode reequilibrer(TrieHybridesNode node) {
        if (node == null) {
            return null;
        }

        // Rééquilibrer les sous-arbres gauche, centre et droit
        node.inferieur = reequilibrer(node.inferieur);
        node.egal = reequilibrer(node.egal);
        node.superieur = reequilibrer(node.superieur);

        // Vérifier les hauteurs des sous-arbres
        int hauteurGauche = Hauteur(node.inferieur);
        int hauteurDroit = Hauteur(node.superieur);


        // Rééquilibrer le nœud actuel
        if (hauteurGauche - hauteurDroit > 1) {
            return rotationDroite(node);
        } else if (hauteurDroit - hauteurGauche > 1) {
            return rotationGauche(node);
        }

        return node; // Retourner le nœud équilibré
    }


    /**
     * Effectue une rotation droite.
     *
     * @param node Le nœud déséquilibré.
     * @return Le nouveau nœud racine après rotation.
     */
    private static TrieHybridesNode rotationDroite(TrieHybridesNode node) {
        TrieHybridesNode nouveauRacine = node.inferieur;
        node.inferieur = nouveauRacine.superieur;
        nouveauRacine.superieur = node;
        return nouveauRacine;
    }

    /**
     * Effectue une rotation gauche.
     *
     * @param node Le nœud déséquilibré.
     * @return Le nouveau nœud racine après rotation.
     */
    private static TrieHybridesNode rotationGauche(TrieHybridesNode node) {
        TrieHybridesNode nouveauRacine = node.superieur;
        node.superieur = nouveauRacine.inferieur;
        nouveauRacine.inferieur = node;
        return nouveauRacine;

    }

    /**
     * Ajoute un mot dans le trie hybride et rééquilibre si nécessaire.
     *
     * @param node  Le nœud courant.
     * @param mot   Le mot à ajouter.
     * @param valeur La valeur associée au mot.
     * @return Le nœud modifié après ajout et rééquilibrage.
     */
    public static TrieHybridesNode ajouterEtReequilibrer(TrieHybridesNode node, String mot, int valeur) {
        // on ajoute le mot dans le trie hybride sans rééquilibrage
        node = node.insert(node, mot, valeur);

        // on rééquilibre le trie hybride si nécessaire
        return reequilibrer(node);
    }


    /**
     * Fusionne deux tries hybrides.
     *
     * @param trie1 Le premier trie hybride.
     * @param trie2 Le deuxième trie hybride.
     * @return Le trie hybride fusionné.
     */
    public static TrieHybridesNode fusionner(TrieHybridesNode trie1, TrieHybridesNode trie2) {
        // Cas de base : si l'un des tries est vide, retourner l'autre
        if (trie1 == null) {
            return trie2;
        }
        if (trie2 == null) {
            return trie1;
        }

        // Comparer les caractères des nœuds racines
        if (trie1.caractere < trie2.caractere) {
            trie1.superieur = fusionner(trie1.superieur, trie2);
            return trie1;
        } else if (trie1.caractere > trie2.caractere) {
            trie2.inferieur = fusionner(trie1, trie2.inferieur);
            return trie2;
        } else {
            // Les caractères sont identiques, fusionner les sous-arbres
            trie1.valeur = Math.max(trie1.valeur, trie2.valeur); // Combiner les valeurs si nécessaire
            trie1.inferieur = fusionner(trie1.inferieur, trie2.inferieur);
            trie1.egal = fusionner(trie1.egal, trie2.egal);
            trie1.superieur = fusionner(trie1.superieur, trie2.superieur);
            return trie1;
        }
    }










}
