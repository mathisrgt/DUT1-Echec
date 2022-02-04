package partie.plateau.gestionRisques;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

/**
 * La classe Danger est utilisée par les classes ayant besoin de savoir si une case de l'échiquier est menacée par des pièces adverses.
 */
public abstract class Danger {

    /**
     * Couleur du joueur.
     */
    private CouleurPiece couleurJoueur;

    /**
     * Constructeur de la classe Danger.
     * @param couleurJoueur Couleur du joueur
     */
    protected Danger (CouleurPiece couleurJoueur)
    {
        this.couleurJoueur = couleurJoueur;
    }

    /**
     * Getter de la variable couleurJoueur.
     * @return Couleur du joueur.
     */
    protected CouleurPiece getCouleurJoueur() {
        return couleurJoueur;
    }

    /**
     * Setter de la variable couleurJoueur.
     * @param couleurJoueur Couleur du joueur.
     */
    protected void setCouleurJoueur(CouleurPiece couleurJoueur) {
        this.couleurJoueur = couleurJoueur;
    }

    /**
     * Retourne un tableau contenant la liste des pièces menacant une case du plateau.
     * @param coord Coordonnées de la case à verifier.
     * @param plateau Echiquier contenant les différentes pièces.
     * @return
     */
    protected ArrayList<Coordonnees> estMenacePar(Coordonnees coord, IPiece[][] plateau)
    {
        ArrayList<Coordonnees> tableauMenacePar = new ArrayList<>();
        for(int idxLigne = 0; idxLigne < Plateau.getTailleLigne(); idxLigne++)
        {
            for(int idxColonne = 0; idxColonne < Plateau.getTailleColonne(); idxColonne++)
            {
                if (plateau[idxLigne][idxColonne] != null)
                {
                    Coordonnees coordPiece = new Coordonnees(idxLigne, idxColonne);
                    if (contientCoordonnees(plateau[idxLigne][idxColonne].getMenaces(plateau,coordPiece), coord))
                        tableauMenacePar.add(coordPiece);        
                }
            }
        }

        return tableauMenacePar;
    }

    /**
     * Verifie si le tableau de manaces contient les coordonnées de la case ciblée.
     * @param tabCoord Tableau des coordonnées des cases menacées par une pièce.
     * @param coord Coordonnées de la case vérifiée.
     * @return  Retourne true si le tableau de menace contient les coordonnées vérifiées, false dans le cas contraire.
     */
    private boolean contientCoordonnees(ArrayList<Coordonnees> tabCoord, Coordonnees coord)
    {
        for(Coordonnees coordTab : tabCoord)
        {
            if (coord.equals(coordTab))
                return true;
        }
        return false;
    } 

    /**
     * Permret d'obtenir les coordonnées du Roi du joueur jouant son tour.
     * @param echiquier Tableau de Piece à deux dimensions représentant le plateau de jeu.
     * @return
     */
    protected Coordonnees getCoordRoi(IPiece[][] echiquier)
    {
        for (int idxLigne = 0; idxLigne < Plateau.getTailleLigne(); idxLigne++)
        {
            for (int idxColonne = 0; idxColonne < Plateau.getTailleLigne(); idxColonne++)
            {
                if (echiquier[idxLigne][idxColonne] != null)
                {
                    if (echiquier[idxLigne][idxColonne].getCouleur() == couleurJoueur && Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'R')
                        return new Coordonnees(idxLigne, idxColonne);
                }
            }
        }
        return new Coordonnees(-1, -1);
    }

    
}
