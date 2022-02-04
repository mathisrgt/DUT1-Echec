package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;
import partie.plateau.Coordonnees;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

public class Roi extends Piece
{

    /**
     * Création d'un Roi en lui associant une couleur
     * @param couleur Correspond à la couleur de la pièce de type CouleurPiece.
     */
    public Roi(CouleurPiece couleur)
    {
        super(couleur);
    }

    // ----------------------------------------------------- GETTER ------------------------------------------------

    public char getNom()
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return 'R';
        else
            return 'r';
    }

    public String getNomComplet()
    {
        return "Le Roi";
    }

    // ------------------------------------------------ LISTE DEPLACEMENTS -----------------------------------------

    public ArrayList<Coordonnees> getMenaces (IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> listeMenaces = new ArrayList<>();

        for (int idxLigne = origine.getIdxLigne() - 1; idxLigne != origine.getIdxLigne() + 2; idxLigne++)
            for (int idxColonne = origine.getIdxColonne() - 1; idxColonne != origine.getIdxColonne() + 2; idxColonne++)
            {
                if (verifDestination(idxLigne, idxColonne) && (echiquier[idxLigne][idxColonne] == null || echiquier[idxLigne][idxColonne].getCouleur() != super.getCouleur()))
                    listeMenaces.add(new Coordonnees(idxLigne, idxColonne));
            }
        return listeMenaces;
    }

    public ArrayList<Coordonnees> getDeplacementPossible (IPiece[][] echiquier, Coordonnees origine)
    {
        return getMenaces(echiquier, origine);
    }

}
