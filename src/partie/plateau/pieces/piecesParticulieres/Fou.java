package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

public class Fou extends Piece
{
/**
     * Création d'un fou en lui associant une couleur
     * @param couleur Correspond à la couleur de la pièce de type CouleurPiece.
     */
    public Fou(CouleurPiece couleur)
    {
        super(couleur);
    }
    
    // ----------------------------------------------------- GETTER ------------------------------------------------
    @Override
    public char getNom()
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return 'F';
        else
            return 'f';
    }

    @Override
    public String getNomComplet()
    {
        return "Le fou";
    }

    @Override
    public ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> listeMenaces = new ArrayList<>();

        for (Direction dir : Direction.values())
        {
            if (dir == Direction.NO || dir == Direction.NE || dir == Direction.SE || dir == Direction.SO)
                listeMenaces.addAll(getDeplacements(echiquier, origine, dir));
        }

        return listeMenaces;
    }

    @Override
    public ArrayList<Coordonnees> getDeplacementPossible (IPiece[][] echiquier, Coordonnees origine)
    {
        return getMenaces(echiquier, origine);
    }
}
