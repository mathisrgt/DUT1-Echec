package partie.plateau.pieces.piecesParticulieres;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

/**
 * Classe abstraite utilisée comme base pour toutes les pièces du jeu d'échec.
 */
public abstract class Piece implements IPiece
{
    /**
     * Couleur de la pièce.
     */
    private CouleurPiece couleur;

    protected Piece (CouleurPiece couleur)
    {
        this.couleur = couleur;
    }

    /**
     * Utile pour connaitre la couleur d'une pièce.
     */
    public CouleurPiece getCouleur()
    {
        return couleur;
    }

    // ----------------------------------------------------- METHODES PROTECTED POUR DEPLACEMENT  -----------------------------------------------
    /**
     * Enumération permettant de gérer les variation d'index lors des déplacements.
     */
    protected enum Direction {NORD, SUD, EST, OUEST, NO, NE, SE, SO}

    /**
     * Méthode utilisée lors de la détermination des déplacements possible d'une pièce. Vérifie que les coordonnées de déstination existe sur le plateau.
     * @param idxLigne Index de la ligne dans le tableau.
     * @param idxColonne Index de la colonne dans le tableau.
     * @return Retourne true si les coordonnées existent, false dans le cas contraire.
     */
    protected boolean verifDestination(int idxLigne, int idxColonne)
    {
        if((idxLigne < 0 || idxLigne > Plateau.getTailleLigne() - 1) || (idxColonne < 0 || idxColonne > Plateau.getTailleColonne() - 1))
                return false;
        return true;
    }

    /**
     * Méthode utilisée lors de la détermination des déplacements possible d'une pièce. Vérifie que les coordonnées de déstination existe sur le plateau.
     * @param destination Coordonnées de la case ciblée.
     * @return
     */
    protected boolean verifDestination(Coordonnees destination)
    {
        if((destination.getIdxLigne() >= 0 && destination.getIdxLigne() < Plateau.getTailleLigne()) && (destination.getIdxColonne() >= 0 &&
        destination.getIdxColonne() < Plateau.getTailleColonne()))
            return true; 
        return false;
    }

    /**
     * Permet de déterminer tous les déplacements possible de la pièce, selon la direction choisie.
     * @param echiquier Tableau de Pièce à deux dimensions représentant le plateau de jeu.
     * @param origine Coordonnées de départ de la pièce avant déplacement de type Coordonnées.
     * @param dir Enumération de type Direction. Détermine la direction dans laquelle on souhaite obtenir les déplacements possibles. 
     * @return
     */
    protected ArrayList<Coordonnees> getDeplacements(IPiece[][] echiquier, Coordonnees origine, Direction dir)
    {
        ArrayList<Coordonnees> deplacementsPossibles = new ArrayList<>();
        int varLigne = origine.getIdxLigne();
        int varColonne = origine.getIdxColonne();

        varLigne = variationLigne(varLigne, dir);
        varColonne = variationColonne(varColonne, dir);

        Coordonnees destination = new Coordonnees(varLigne, varColonne);
        while (verifDestination(destination) && echiquier[varLigne][varColonne] == null)
        {
            deplacementsPossibles.add(destination);

            varLigne = variationLigne(varLigne, dir);
            varColonne = variationColonne(varColonne, dir);
            destination = new Coordonnees(varLigne, varColonne);
        }

        if (verifDestination(destination) && echiquier[varLigne][varColonne].getCouleur() != this.couleur)
            deplacementsPossibles.add(destination);

        return deplacementsPossibles;
    }

    // ----------------------------------------------------- METHODES PRIVEE POUR DEPLACEMENT  -----------------------------------------------
    
    /**
     * Permet d'incrémenter ou de décrémenter l'index de la ligne selon la direction en paramètre.
     * @param varLigne Index de la ligne de type char.
     * @param dir Direction du déplacement souhaité de type Direction.
     * @return Retour l'index de la ligne après sa modification si nécessaire.
     */
    private int variationLigne (int varLigne, Direction dir)
    {
        switch (dir)
        {
            case NORD : 
            case NO   :
            case NE   : return --varLigne;
            case SUD  : 
            case SO   : 
            case SE   : return ++varLigne;
            default   : return varLigne;
        }
    }

    /**
     * Permet d'incrémenter ou de décrémenter l'index de la Colonne selon la direction en paramètre.
     * @param varColonne Index de la Colonne de type char.
     * @param dir Direction du déplacement souhaité de type Direction.
     * @return Retour l'index de la Colonne après sa modification si nécessaire.
     */
    private int variationColonne (int varColonne, Direction dir)
    {
        switch (dir)
        {
            case OUEST : 
            case SO    :
            case NO    : return --varColonne;
            case EST   : 
            case SE    : 
            case NE    : return ++varColonne;
            default    : return varColonne;
        }
    }
}