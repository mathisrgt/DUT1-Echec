package partie.plateau.pieces;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.pieces.Enum.CouleurPiece;

public interface IPiece {

    /**
     * Obtient la couleur de la pièce
     * @return Retourne une valeur de l'énumaration CouleurPiece.
     */
    public CouleurPiece getCouleur();

    /**
     * Obtient le nom de la pièce. La casse dépend de la couleur du pion.
     * @return Retourne dans un char la lettre associée à la pièce.
     */
    public abstract char getNom();

    /**
     * Obtient le nom complet de la pièce.
     * @return Retourne un String contenant le nom complet de la pièce.
     */
    public abstract String getNomComplet();

    /**
     * Détermine les cases menacées par la pièce.
     * @param echiquier Plateau de jeu comprenant l'ensemble des pièces.
     * @param origine Coordonnées actuelles de la pièce.
     * @return Retourne un ArrayList de Coordonnées contenant toutes les cases menancées par la pièces.
     */
    public abstract ArrayList<Coordonnees> getMenaces(IPiece[][] echiquier, Coordonnees origine);

    /**
     * Détermine l'ensemble des cases où la pièce peut se déplacer.
     * @param echiquier Plateau de jeu comprenant l'ensemble des pièces.
     * @param origine Coordonnées actuelles de la pièce.
     * @return Retourne un ArrayList de Coordonnées contenant toutes les cases où la pièce peut se déplacer.
     */
    public abstract ArrayList<Coordonnees> getDeplacementPossible(IPiece[][] echiquier, Coordonnees origine);

}
