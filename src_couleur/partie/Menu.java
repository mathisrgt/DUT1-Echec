package partie;

import entreeSortie.Couleur;
import entreeSortie.Scan;

/**
 * Menu principal du jeu.
 */
public class Menu
{
    /**
     * Objet permettant la saisie clavier par un utilisateur.
     */
    Scan scanner;
    
    /**
     * Nécessite un objet de type Scanner pour une permettre la saisie par un utilisateur.
     * @param scanner
     */
    public Menu(Scan scanner)
    {
        this.scanner = scanner;
    }

    /**
     * L'utilisateur sélectionne le mode de jeu souhaité
     * @return Retourne un caractère correspondant au mode de jeu souhaité.
     */
    public ModeDeJeu choixModeDeJeu()
    {
        System.out.println(listeModeDeJeu());
        String choixMode = scanner.saisirClavier(1);

        while (choixMode.charAt(0) != '1' && choixMode.charAt(0) != '2' && choixMode.charAt(0) != '3' && choixMode.charAt(0) != '4')
            choixMode = correctionChoixModeDeJeu();

        ModeDeJeu modeDeJeu = reconnaissanceChoix(choixMode.charAt(0));
        
        System.out.println("\nMode choisi : " + Couleur.getVert() + affichageChoix(modeDeJeu) + Couleur.getStopCouleur());

        return modeDeJeu;
    }

    /**
     * Demande une nouvelle saisie en cas d'erreur.
     * @return Retourne un String contenant le mode de jeu souhaité.
     */
    private String correctionChoixModeDeJeu()
    {
        System.out.println("Choix du mode de jeu non reconnu\n");
        System.out.println(listeModeDeJeu());
        return scanner.saisirClavier(1);
    }

    /**
     * Affichage de la liste des modes de jeu disponibles.
     * @return Retourne un String contenant l'affichage.
     */
    private String listeModeDeJeu()
    {
        StringBuilder str = new StringBuilder();
        str.append(Couleur.getVert() + "Choisissez le mode de jeu :\n\n" + Couleur.getStopCouleur()); 
        str.append(Couleur.getVert() + "1 " + Couleur.getStopCouleur() + "-> Joueur contre Joueur\n");
        str.append(Couleur.getVert() + "2 " + Couleur.getStopCouleur() + "-> Joueur contre Ordinateur (hasard)\n"); 
        str.append(Couleur.getVert() + "3 " + Couleur.getStopCouleur() + "-> Ordinateur contre Ordinateur (hasard)\n"); 
        str.append(Couleur.getVert() + "4 " + Couleur.getStopCouleur() + "-> Joueur contre Joueur (mode Roi/Tour)");   

        return str.toString();
    }

    /**
     * Affichage du message indiquant le mode de jeu choisi.
     * @param choixMode Char correspondant au mode de jeu choisi.
     * @return Retourne un String contenant le message à afficher.
     */
    private ModeDeJeu reconnaissanceChoix(char choixMode)
    {
        switch (choixMode)
        {
            case '1' : return ModeDeJeu.JVJ;
            case '2' : return ModeDeJeu.JVO;
            case '3' : return ModeDeJeu.OVO;
            case '4' : return ModeDeJeu.RT;
            default  : return null;
        }
    }

    /**
     * Affichage du message indiquant le mode de jeu choisi.
     * @param choixMode Char correspondant au mode de jeu choisi.
     * @return Retourne un String contenant le message à afficher.
     */
    private String affichageChoix(ModeDeJeu choixMode)
    {
        switch (choixMode)
        {
            case JVJ : return "Joueur contre Joueur\n";
            case JVO : return "Joueur contre Ordinateur (hasard)\n";
            case OVO : return "Ordinateur contre Ordinateur (hasard)\n";
            case RT  : return "Joueur contre Joueur (mode Roi/tour)\n";
            default  : return null;
        }
    }
}
