package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

/**
 * Classe représentant la pièce cavalier
 */
public class Cavalier extends Piece
{
    /**
     * Création d'un cavalier en lui associant une couleur
     * @param couleur Correspond à la couleur de la pièce de type CouleurPiece.
     */
    public Cavalier(CouleurPiece couleur)
    {
        super(couleur);
    }

    // ----------------------------------------------------- GETTER ------------------------------------------------

    @Override
    public char getNom()
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return 'C';
        else
            return 'c';
    }

    @Override
    public String getNomComplet()
    {
        return "Le cavalier";
    }

    // ------------------------------------------------ LISTE DEPLACEMENTS -----------------------------------------
    
    @Override
    public ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> listeMenaces = new ArrayList<>();

        for (int varLigne = -2; varLigne <= 2; varLigne++)
        {
            for (int varColonne = -2; varColonne <= 2; varColonne++)
            {
                if(Math.abs(varLigne) + Math.abs(varColonne) == 3)
                {
                    Coordonnees destination = new Coordonnees(origine.getIdxLigne() + varLigne, origine.getIdxColonne() + varColonne);
                    if (verifDeplacementPossible(echiquier, destination))
                        listeMenaces.add(destination);
                }
            }
        }

        return listeMenaces;
    }

    @Override
    public ArrayList<Coordonnees> getDeplacementPossible (IPiece[][] echiquier, Coordonnees origine)
    {
        return getMenaces(echiquier, origine);
    }

    
    /**
     * Vérifie que la destination ciblée est atteignable.
     * Contrôle de l'existence de la destination.
     * Puis vérification du contenu de la case : soit vide, soit couleur adverse.
     * @param echiquier
     * @param destination
     * @return
     */
    private boolean verifDeplacementPossible(IPiece[][] echiquier, Coordonnees destination)
    {
        if (verifDestination(destination))
        {
            if (echiquier[destination.getIdxLigne()][destination.getIdxColonne()] == null ||  
            echiquier[destination.getIdxLigne()][destination.getIdxColonne()].getCouleur() != super.getCouleur())
                return true;
        }
        return false;
    }
}
