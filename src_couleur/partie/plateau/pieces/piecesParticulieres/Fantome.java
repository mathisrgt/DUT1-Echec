package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

/**
 * Classe utilisée pour permettre la prise en passant. Un fantôme est une trace d'un pion lorsque ce dernier avance de 2 cases depuis sa position initiale.
 */
public class Fantome extends Piece {
 
    /**
     * Création d'un fantôme. 
     * @param couleur Couleur de la pièce associée.
     */
    public Fantome (CouleurPiece couleur)
    {
        super(couleur);
    }

    public char getNom()
    {
        return ' ';
    }

    public String getNomComplet()
    {
        return " ";
    }

    /**
     * Retourne un tableau vide.
     */
    public ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine)
    {
        return new ArrayList<>();
    }

    /**
     * Retourne un tableau vide.
     */
    public ArrayList<Coordonnees> getDeplacementPossible(IPiece[][] echiquier, Coordonnees origine)
    {
        return new ArrayList<>();
    }

}
