package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import PatriciaTrie.*;


public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Faut avoir la command et les arguments");
            System.exit(1);
        }

        String command = args[0];

        switch (command) {
		    case "insererPatricia":
		    	PatriciaTrieNode trie = new PatriciaTrieNode("");
		        // Lire les mots et les insérer
				trie.insertMotduFichier(args[1]); 
				trie.saveToFile("pat.json");
		        break;
		    
		    case "insererHybride":

		    	break;
		    
		    case "suppressionPatricia":
		    	PatriciaTrieNode pat = PatriciaTrieNode.loadFromFile("pat.json");
		    	pat=FonctionAvancer.supressionDuMotDufichier(pat, args[1]);
		    	pat.saveToFile("pat.json");
		    	break;
		    	
		    case "suppressionHybride":
		    	
		        break;
		    case "fussionPatricia":
		    	PatriciaTrieNode p1= PatriciaTrieNode.loadFromFile(args[1]);
		    	PatriciaTrieNode p2= PatriciaTrieNode.loadFromFile(args[2]);
		    	PatriciaTrieNode pf= FonctionAvancer.fusionner(p1, p2);
		    	pf.saveToFile("pat.json");
		    	
		        break;
		        
		    case "fussionHybride":

		        break;
		    
		    case "listeMotsPatricia":
		    	PatriciaTrieNode pl=PatriciaTrieNode.loadFromFile(args[1]);
		    	List<String> list=FonctionAvancer.listeMots(pl);
		    	try (FileWriter file = new FileWriter("mot.txt")) { // on crée un fichier et on mis le contenu du jsonCode dedans
					file.write(list+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		        
		    case "listeMotsHybride":

		        break;
		        
		    case "profondeurMoyennePatricia":
		    	PatriciaTrieNode pp=PatriciaTrieNode.loadFromFile(args[1]);
		    	int p=FonctionAvancer.profondeurMoyenne(pp);
		    	try (FileWriter file = new FileWriter("profondeur.txt")) { // on crée un fichier et on mis le contenu du jsonCode dedans
					file.write(p+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
		        break;
		        
		    case "profondeurMoyenneHybride":

		        break;
		        
		    case "prefixePatricia":
		    	
		    	PatriciaTrieNode ppref=PatriciaTrieNode.loadFromFile(args[1]);
		    	int pref=FonctionAvancer.prefixe(ppref, args[2]+"");
		    	try (FileWriter file = new FileWriter("prefixe.txt")) { // on crée un fichier et on mis le contenu du jsonCode dedans
					file.write(pref+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		      
		    case "prefixeHybride":

		        break;
		    
		    default:
		        System.out.println("Commande inconnue: " + command);
		}
        
    }
}
