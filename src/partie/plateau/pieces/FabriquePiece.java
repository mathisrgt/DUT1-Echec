package partie.plateau.pieces;

import partie.plateau.pieces.Enum.CouleurPiece;
import partie.plateau.pieces.Enum.TypePiece;
import partie.plateau.pieces.piecesParticulieres.Cavalier;
import partie.plateau.pieces.piecesParticulieres.Dame;
import partie.plateau.pieces.piecesParticulieres.Fantome;
import partie.plateau.pieces.piecesParticulieres.Fou;
import partie.plateau.pieces.piecesParticulieres.Pion;
import partie.plateau.pieces.piecesParticulieres.Roi;
import partie.plateau.pieces.piecesParticulieres.Tour;

public class FabriquePiece {
    
    /**
     * Crée une pièce selon la couleur et le type de pièce donné.
     * @param couleur Couleur de la pièce souhaitée.
     * @param type Type de pièce souhaité.
     * @return la pièce.
     */
    public IPiece creationPiece(CouleurPiece couleur, TypePiece type)
    {
        switch (type)
        {
            case PION     : return new Pion(couleur);
            case TOUR     : return new Tour(couleur);
            case CAVALIER : return new Cavalier(couleur);
            case FOU      : return new Fou(couleur);
            case DAME     : return new Dame(couleur);
            case ROI      : return new Roi(couleur);
            case FANTOME  : return new Fantome(couleur);
            default       : return null;
        }
    }
}
