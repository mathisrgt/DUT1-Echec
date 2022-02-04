package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;
import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

public class Pion extends Piece
{

    /**
     * Création d'un pion en lui associant une couleur
     * @param couleur Correspond à la couleur de la pièce de type CouleurPiece.
     */
    public Pion(CouleurPiece couleur)
    {
        super(couleur);
    }

    // ----------------------------------------------------- GETTER ------------------------------------------------
    public char getNom()
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return 'P';
        else
            return 'p';
    }

    public String getNomComplet()
    {
        return "Le pion";
    }


    public ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> listeMenaces = new ArrayList<>();

        Coordonnees destination = new Coordonnees(origine.getIdxLigne() + getVarLigne(), origine.getIdxColonne() - 1);
        if (caseMenacable(echiquier, destination))
            listeMenaces.add(destination);

        destination = new Coordonnees(origine.getIdxLigne() + getVarLigne(), origine.getIdxColonne() + 1);
        if (caseMenacable(echiquier, destination))
            listeMenaces.add(destination);

        return listeMenaces;
    }

    public ArrayList<Coordonnees> getDeplacementPossible (IPiece[][] echiquier, Coordonnees origine)
    {
        ArrayList<Coordonnees> deplacementPossible = new ArrayList<>();

        Coordonnees destination;

        for (int varColonne = -1; varColonne < 2; varColonne ++)
        {
            destination = new Coordonnees(origine.getIdxLigne() + getVarLigne(), origine.getIdxColonne() + varColonne);
            if (Math.abs(varColonne) == 1)
            {
                if (verifDestination(destination) && destinationPieceAdverse(echiquier, destination))
                    deplacementPossible.add(destination);
            }
            else
            {
                if (verifDestination(destination) && destinationVide(echiquier, destination))
                    deplacementPossible.add(destination);
            }
        }

        if (emplacementOrigine(origine))
        {
            destination = new Coordonnees(origine.getIdxLigne() + (2 * getVarLigne()), origine.getIdxColonne());
            if (verifDestination(destination) && destinationVide(echiquier, destination) && echiquier[destination.getIdxLigne() - getVarLigne()][destination.getIdxColonne()] == null)
                deplacementPossible.add(destination);
        }
        
        return deplacementPossible;
    }

    // ----------------------------------- METHODES PRIVEES DETERMINANT LE DEPLACEMENT --------------------------------------------------

    /**
     * Détermine si une case ou non est menacée par le pion.
     * @param echiquier Tableau de Pièce à deux dimensions contenant l'ensemble des pièces du jeu.
     * @param destination Coordonnée de la case ciblée.
     * @return Retourne true si le pion menace la case destination
     */
    private boolean caseMenacable(IPiece[][] echiquier, Coordonnees destination)
    {
        if (verifDestination(destination) && (destinationVide(echiquier, destination) || destinationPieceAdverse(echiquier, destination)))
            return true;
        return false;
    }

    /**
     * Détermine si la case ciblée est vide
     * @param echiquier Tableau de Pièce à deux dimensions contenant l'ensemble des pièces du jeu.
     * @param destination Coordonnée de la case ciblée.
     * @return Retourne true si la case designée est vide.
     */
    private boolean destinationVide(IPiece[][] echiquier, Coordonnees destination)
    {
        if (echiquier[destination.getIdxLigne()][destination.getIdxColonne()] == null)
            return true;
        else 
            return false;
    }

    /**
     * Détermine s'il existe une pièce adverse sur la case ciblée
     * @param echiquier Tableau de Pièce à deux dimensions contenant l'ensemble des pièces du jeu.
     * @param destination Coordonnée de la case ciblée.
     * @return Retourne true si la case designée possède une pièce adverse.
     */
    private boolean destinationPieceAdverse(IPiece[][] echiquier, Coordonnees destination)
    {
        if (echiquier[destination.getIdxLigne()][destination.getIdxColonne()] != null && echiquier[destination.getIdxLigne()][destination.getIdxColonne()].getCouleur() != super.getCouleur())
            return true;
        else 
            return false;
    }

    /**
     * Détermine le sens de variation de l'index de la ligne selon la couleur du pion.
     * @return Retourne un int positif ou negatif selon la couleur.
     */
    private int getVarLigne()
    {
        if (super.getCouleur() == CouleurPiece.BLANC)
            return (-1);
        else 
            return 1;
    }

    /**
     * Vérifie qu'un pion se trouve bel et bien à son emplacement d'origine.
     * @param origine Coordonnées actuelles du pion.
     * @return Retourne true si le pion est bien sur sa case de début de partie.
     */
    private boolean emplacementOrigine(Coordonnees origine)
    {
        if (super.getCouleur() == CouleurPiece.BLANC && origine.getIdxLigne() == Plateau.getTailleLigne() - 2)
            return true;
        else if (super.getCouleur() == CouleurPiece.NOIR && origine.getIdxLigne() == Plateau.getTailleLigne() - (Plateau.getTailleLigne() - 1))
            return true;
        return false;
    }


}