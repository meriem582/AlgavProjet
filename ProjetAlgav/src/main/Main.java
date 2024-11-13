package main;

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
//		        System.out.println("Ajout en trieHybride");
		        break;
		    
		    case "suppressionPatricia":
		    	
		    	break;
		    	
		    case "suppressionHybride":
		    	
		        break;
		    case "fussionPatricia":

		        break;
		        
		    case "fussionHybride":

		        break;
		    
		    case "listeMotsPatricia":

		        break;
		        
		    case "listeMotsHybride":

		        break;
		        
		    case "profondeurMoyennePatricia":

		        break;
		        
		    case "profondeurMoyenneHybride":

		        break;
		        
		    case "prefixePatricia":

		        break;
		        
		        
		    case "prefixeHybride":

		        break;
		    
		    default:
		        System.out.println("Commande inconnue: " + command);
		}
        
    }
}
