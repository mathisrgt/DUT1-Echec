import entreeSortie.Scan;
import partie.Menu;
import partie.Partie;

/** 
 *  Programme java reproduisant un jeu d'échec.
 */
public class Main {

    public static void main(String[] args) {
        jouer();
    }

    /**
     * Debute la session de jeu.
     * Une fois une première partie terminée, permet d'enchainer avec d'autres parties.
     */
    public static void jouer()
    {
        Scan scanner = new Scan();
        System.out.println(bandeauOuverture());
        Menu menuPricipal = new Menu(scanner);
        boolean continuerJouer = true;

        while (continuerJouer)
        {
            new Partie(menuPricipal.choixModeDeJeu(), scanner).jouerPartie();
            continuerJouer = rejouer(scanner);
        }
        
        scanner.closeScanner();
    }

    /**
     * Demande à l'utilisateur s'il veut rejouer une partie.
     * @return true si l'utilisateur veut rejouer, false dans le cas contraire.
     */
    private static boolean rejouer(Scan scanner)
    {
        String annonce = "\nJouer une nouvelle partie ?\noui -> Rejouer\nnon -> Fin du jeu\n";
        System.out.print(annonce);

        String reponse = scanner.saisirClavier(3);

        while (!verifReponse(reponse))
        {
            System.out.println("Reponse non reconnue");
            reponse = scanner.saisirClavier(3);
        }

        if (reponse.equals("oui"))
            return true;
        
        System.out.println("\nFin de la session de jeu. Merci d'avoir joué !\n");
        return false;
    }
    
    /**
     * Verifie si la réponse de l'utilisateur est bien "oui" ou "non"
     * @param reponse Réponse saisie par l'utilisateur
     * @return true si la réponse respecte la condition de forme, false dans le cas contraire.
     */
    private static boolean verifReponse(String reponse)
    {
        if (reponse.equals("oui") || reponse.equals("non"))
            return true;
        return false;    
    }

    /**
    * Affichage décoratif de début de partie.
    */
    private static String bandeauOuverture()
    {
        String str;

        str = "\n\n         ()                                                                                                              _:_    \n";
        str += "      .-:--:-.  							                                                \'-.-\'\n";
        str += "       \\____/    .----------------.  .----------------.  .----------------.  .----------------.  .----------------.    __.'.__\n";
        str += "       {====}   | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |  |_______|\n";
        str += "        )__(    | |  _________   | || |     ______   | || |  ____  ____  | || |  _________   | || |     ______   | |    )___(\n";
        str += "       /____\\   | | |_   ___  |  | || |   .' ___  |  | || | |_   ||   _| | || | |_   ___  |  | || |   .' ___  |  | |   /_____\\\n";
        str += "        |  |    | |   | |_  \\_|  | || |  / .'   \\_|  | || |   | |__| |   | || |   | |_  \\_|  | || |  / .'   \\_|  | |    |   |\n";
        str += "        |  |    | |   |  _|  _   | || |  | |         | || |   |  __  |   | || |   |  _|  _   | || |  | |         | |    |   |\n";
        str += "        |  |    | |  _| |___/ |  | || |  \\ `.___.'\\  | || |  _| |  | |_  | || |  _| |___/ |  | || |  \\ `.___.'\\  | |    |   |\n";
        str += "        |  |    | | |_________|  | || |   `._____.'  | || | |____||____| | || | |_________|  | || |   `._____.'  | |    |   |\n";
        str += "       /____\\   | |              | || |              | || |              | || |              | || |              | |   /_____\\\n";
        str += "      (======)  | \'--------------\' || \'--------------\' || \'--------------\' || \'--------------\' || \'--------------\' |  (=======)\n";
        str += "      }======{  \'------------------\'\'------------------\'\'------------------\'\'------------------\'\'------------------\'  }======={\n";
        str += "     (________)													     (_________)\n\n\n";

        return str;
    }
}
