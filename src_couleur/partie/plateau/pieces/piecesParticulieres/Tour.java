package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.Enum.CouleurPiece;
import partie.plateau.pieces.IPiece;

/**
 * Type de pièce représentant la tour.
 */
public class Tour extends Piece
{
     /**
     * Création d'une tour en lui associant une couleur
     * @param couleur Correspond à la couleur de la pièce de type CouleurPiece.
     */
    public Tour(CouleurPiece couleur)
    {
        super(couleur);
    }

    // ----------------------------------------------------- GETTER ------------------------------------------------

    public char getNom()
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return 'T';
        else
            return 't';
    }

    public String getNomComplet()
    {
        return "La tour";
    }

    // ------------------------------------------------ LISTE DEPLACEMENTS -----------------------------------------

    public ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> listeMenaces = new ArrayList<>();

        for (Direction dir : Direction.values())
        {
            if (dir == Direction.NORD || dir == Direction.SUD || dir == Direction.EST || dir == Direction.OUEST)
                listeMenaces.addAll(getDeplacements(echiquier, origine, dir));
        }

        return listeMenaces;
    }

    public ArrayList<Coordonnees> getDeplacementPossible(IPiece[][] echiquier, Coordonnees origine)
    {
        return getMenaces(echiquier, origine);
    }

}
