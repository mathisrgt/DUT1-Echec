package partie.categorieJoueur;

import entreeSortie.Scan;
import partie.ModeDeJeu;
import partie.categorieJoueur.joueur.Humain;
import partie.categorieJoueur.ordinateur.OrdiHasard;
import partie.plateau.pieces.Enum.CouleurPiece;

public class FabriqueJoueur {
    /**
     * Mode de jeu choisi par l'utilisateur, parmi l'énumération ModeDeJeu.
     */
    private ModeDeJeu modeChoisi;

    /**
     * Outil scanner permettant la saisie clavier.
     */
    private Scan scanner;

    /**
     * Couleur choisie par l'utilisateur parmi l'unumartion CouleurPiece
     */
    private CouleurPiece couleurChoisie;

    /**
     * Classe utilisée pour la création d'objet de type IJoueur. 
     * @param modeChoisi Mode de jeu choisi par l'utilisateur.
     * @param scanner Permet la saisie clavier.
     */
    public FabriqueJoueur(ModeDeJeu modeChoisi, Scan scanner)
    {
        this.modeChoisi = modeChoisi;
        this.scanner = scanner;
        if (modeChoisi == ModeDeJeu.JVO)
            couleurChoisie = choixCouleur();
    }

    /**
     * Permet de créer un joueur selon le type de partie sélectionné.
     * @param numJoueur Utilisé pour déterminer le type de joueur à créer dans les parties Humain contre OrdiHasard
     * @return Retourne un joueur de type IJoueur.
     */
    public IJoueur creationJoueur(int numJoueur)
    {
        switch (modeChoisi)
        {
            case JVJ :
            case RT  : return new Humain(scanner);
            case OVO : return new OrdiHasard();
            case JVO : 
                if ((numJoueur == 1 && couleurChoisie == CouleurPiece.BLANC) || (numJoueur == 2 && couleurChoisie == CouleurPiece.NOIR))
                    return new Humain(scanner);
                else
                    return new OrdiHasard();
            default  : return null;
        }
    }

    /**
     * Choix de la couleur lors d'une partie Humain contre OrdiHasard
     * @return Retourne une couleur de pièce.
     */
    private CouleurPiece choixCouleur()
    {
        System.out.println("Quelle couleur voulez-vous jouer ?\nb -> jouer les blancs\nn -> jouer les noirs");

        String choixCouleur = scanner.saisirClavier(1);

        while (true)
        //Continue tant que l'utilisateur n'a pas choisie une couleur correcte.
        {
            switch (choixCouleur.charAt(0))
            {
                case 'b' :
                    System.out.println("Vous jouez les  blancs\n");
                    return CouleurPiece.BLANC;
                case 'n' :
                    System.out.println("Vous jouez les  noirs\n");
                    return CouleurPiece.NOIR;
                default :
                    System.out.println("Couleur invalide");
                    choixCouleur = scanner.saisirClavier(1);
            }
        }
    }
}
