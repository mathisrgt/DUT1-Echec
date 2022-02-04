package partie.categorieJoueur;

import java.util.ArrayList;


import partie.plateau.deplacement.DeplacementsParPiece;
import partie.plateau.pieces.Enum.CouleurPiece;
import partie.plateau.pieces.Enum.TypePiece;

public interface IJoueur {
    /**
     * Entrée d'un nouveau coup à jouer par le joueur.
     * @param echiquier objet echiquier contenant l'ensemble des méthodes relatives au plateau de jeu
     * @return retourne le statut de la partie, false si la partie n'est pas terminée, true si elle est terminée
     */
    public String nouveauDeplacement(ArrayList<DeplacementsParPiece> listeTousDeplacements, CouleurPiece couleurJoueur, String msgAide);

    /**
     * Le joueur choisi le type de pièce souhaité lors de la promotion d'un pion.
     * @return Retourne le type de pièce souhaite.
     */
    public abstract TypePiece choixPromotion();

}
