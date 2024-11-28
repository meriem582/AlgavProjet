package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import PatriciaTrie.*;
import TriesHybrides.FonctionAvancerHybride;
import TriesHybrides.TrieHybridesNode;


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
				trie.saveToFile("patP.json");
		        break;
		    
		    case "insererHybride":
		    	TrieHybridesNode trieH = new TrieHybridesNode(' ');
				trieH.insertMotduFichier(args[1]);
				trieH.saveToFile("patH.json");
		    	break;
		    
		    case "suppressionPatricia":
		    	PatriciaTrieNode pat = PatriciaTrieNode.jsonToArbre("patP.json");
		    	pat=FonctionAvancer.supressionDuMotDufichier(pat, args[1]);
		    	pat.saveToFile("patP.json");
		    	break;
		    	
		    case "suppressionHybride":
		    	TrieHybridesNode trieH1 = TrieHybridesNode.jsonToArbre("patH.json");
				trieH1=FonctionAvancerHybride.supressionMotDuFichier(trieH1, args[1]);
				trieH1.saveToFile("patH.json");
		    	
		        break;
		    case "fussionPatricia":
		    	PatriciaTrieNode p1= PatriciaTrieNode.jsonToArbre(args[1]);
		    	PatriciaTrieNode p2= PatriciaTrieNode.jsonToArbre(args[2]);
		    	PatriciaTrieNode pf= FonctionAvancer.fusionner(p1, p2);
		    	pf.saveToFile("patP.json");
		    	
		        break;
		        
		    case "fussionHybride":
		    	TrieHybridesNode trieH2 = TrieHybridesNode.jsonToArbre(args[1]);
				TrieHybridesNode trieH3 = TrieHybridesNode.jsonToArbre(args[2]);
				TrieHybridesNode trieH4 = FonctionAvancerHybride.fusionner(trieH2, trieH3);
				trieH4.saveToFile("patH.json");

		        break;
		    
		    case "listeMotsPatricia":
		    	PatriciaTrieNode pl=PatriciaTrieNode.jsonToArbre(args[1]);
		    	List<String> list=FonctionAvancer.listeMots(pl);
		    	try (FileWriter file = new FileWriter("motsP.txt")) { // on crée un fichier et on mis le contenu du jsonCode dedans
					file.write(list+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		        
		    case "listeMotsHybride":
		    	TrieHybridesNode trieH5 = TrieHybridesNode.jsonToArbre(args[1]);
				List<String> list1 = FonctionAvancerHybride.listeMots(trieH5);
				try (FileWriter file = new FileWriter("motsH.txt")) {
					file.write(list1+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		        
		    case "profondeurMoyennePatricia":
		    	PatriciaTrieNode pp=PatriciaTrieNode.jsonToArbre(args[1]);
		    	int p=FonctionAvancer.profondeurMoyenne(pp);
		    	try (FileWriter file = new FileWriter("profondeurP.txt")) {
					file.write(p+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
		        break;
		        
		    case "profondeurMoyenneHybride":
		    	TrieHybridesNode trieH6 = TrieHybridesNode.jsonToArbre(args[1]);
				int ph1 = FonctionAvancerHybride.profondeurMoyenne(trieH6);
				try (FileWriter file = new FileWriter("profondeurH.txt")) {
					file.write(ph1+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		        
		    case "prefixePatricia":
		    	
		    	PatriciaTrieNode ppref=PatriciaTrieNode.jsonToArbre(args[1]);
		    	int pref=FonctionAvancer.prefixe(ppref, args[2]+"");
		    	try (FileWriter file = new FileWriter("prefixeP.txt")) { 
					file.write(pref+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		      
		    case "prefixeHybride":
		    	TrieHybridesNode trieH7 = TrieHybridesNode.jsonToArbre(args[1]);
				int ph2 = FonctionAvancerHybride.prefixe(trieH7, args[2]+"");
				try (FileWriter file = new FileWriter("prefixeH.txt")) {
					file.write(ph2+"");
					file.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        break;
		    
		    default:
		        System.out.println("Commande inconnue: " + command);
		}
        
    }
}
