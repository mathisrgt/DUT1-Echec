package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

public class Dame extends Piece
{
    /**
     * Création d'une Dame en lui associant une couleur
     * @param couleur Correspond à la couleur de la pièce de type CouleurPiece.
     */
    public Dame(CouleurPiece couleur)
    {
        super(couleur);
    }

    @Override
    public char getNom() 
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return 'D';
        else
            return 'd'; 
    }

    @Override
    public String getNomComplet()
    {
        return "La Dame";
    }

    // ------------------------------------------------ LISTE DEPLACEMENTS -----------------------------------------
    
    @Override
    public ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> listeMenaces = new ArrayList<>();

        for (Direction dir : Direction.values())
            listeMenaces.addAll(getDeplacements(echiquier, origine, dir));
        
        return listeMenaces;
    }

    @Override
    public ArrayList<Coordonnees> getDeplacementPossible (IPiece[][] echiquier, Coordonnees origine)
    {
        return getMenaces(echiquier, origine);
    }
}
