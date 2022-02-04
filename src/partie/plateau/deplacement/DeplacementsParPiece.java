package partie.plateau.deplacement;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.IPiece;

/**
 * Classe utilisée comme structure. 
 * Mémorise les coordonées d'une pièce ainsi qu'un ArrayList contenant l'ensemble des coordonnées où la pièce peut se déplacer.
 */
public class DeplacementsParPiece
{
    /**
     * Coordonnées de la pièce étudiée.
     */
    private Coordonnees coord;

    /**
     * Liste comprenant l'ensemble des coordonnées où la pièce étudiée peut se déplacée.
     */
    private ArrayList<Coordonnees> listeDestination;

    /**
     * Initialise les deux variables de classe
     * -> coord coorespond aux coordonnées de la pièce.
     * -> listeDestintion correspond au tableau contenant tous les déplacements possibles.
     * @param origine Coordonnées actuelles de la pièce
     * @param echiquier Tableau représentant l'échiquier contenant les différentes pièces.
     */
    public DeplacementsParPiece(Coordonnees origine, IPiece[][] echiquier)
    {
        this.coord = origine;
        listeDestination = echiquier[origine.getIdxLigne()][origine.getIdxColonne()].getDeplacementPossible(echiquier, origine);
    }

    /**
     * Permet d'obtenir la position sur le plateau de la pièce
     * @return Retourne les coordonnées de la pièce.
     */
    public Coordonnees getCoord() {
        return coord;
    }

    /**
     * Permet d'obtenir un tableau contenant tous les déplacements possibles pour la pièce.
     * @return Retourne un tableau de type ArrayList<Coordonnees> contenant tous les déplacements.
     */
    public ArrayList<Coordonnees> getListeDestination() {
        return listeDestination;
    }

}