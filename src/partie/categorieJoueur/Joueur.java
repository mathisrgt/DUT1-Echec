package partie.categorieJoueur;

import partie.plateau.pieces.Enum.CouleurPiece;

/**
 * Classe abstraite contenant les méthodes communes entre les différents types de joueurs.
 */
public abstract class Joueur implements IJoueur{

    /**
     * Affichage du coup joué par un joueur
     * @param nouveauCoup String contenant le coup joué.
     * @param couleurJoueurCourant Couleur du joueur, utilisé pour faire varier la couleur de l'affichage
     * @return
     */
    protected String affichageTour(String nouveauCoup, CouleurPiece couleurJoueur, String typeJoueur)
    {
        StringBuilder str = new StringBuilder();
        str.append(typeJoueur);  
        if (couleurJoueur == CouleurPiece.BLANC)
            str.append("blanc");
        else
            str.append("noir");
        str.append(" joue le coup : " + nouveauCoup + "\n");
        return str.toString();
    }
}
