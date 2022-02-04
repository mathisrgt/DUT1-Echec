package partie.plateau.gestionRisques;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.deplacement.DeplacementsParPiece;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

/**
 * Cette classe a pour vocation le contrôle de la fin d'une partie.
 * C'est-à-dire qu'elle va déterminer, à partir d'une situation donnée, si la partie est terminée.
 */
public class FinPartie extends Danger {

    /**
     * Liste composée de tous les déplacements possibles pour le joueur.
     */
    private ArrayList<DeplacementsParPiece> listeTousDeplacements;

    /**
     * Tableau à deux dimensions représentant le plateau de jeu.
     */
    private IPiece[][] echiquier;
    
    /**
     * Crée un objet de type FinPartie.
     * @param couleurJoueur couleur du joueur actuel.
     */
    public FinPartie(CouleurPiece couleurJoueur, ArrayList<DeplacementsParPiece> listeTousDeplacements, IPiece[][] echiquier)
    {
        super(couleurJoueur);
        this.listeTousDeplacements = listeTousDeplacements;
        this.echiquier = echiquier;
    }

    /**
     * Vérifie si la partie est terminée.
     * @return retourne true si la partie est terminée, false dans le cas contraire.
     */
    public Boolean isPartieTerminee()
    {
        if (checkEchecMat() || checkManqueMateriel())
            return true;

        if(listeTousDeplacements.isEmpty())
        {
            System.out.println("Pat ! Match nul\n");
            return true;
        }
        return false;
    }

    /**
     * Vérifie si le roi est actuellement en echec. Dans le cas où il se trouve en échec, il est vérifié si ce dernier est en mat.
     * @return Retourne true en cas d'échec et mat, false dans le cas contraire.
     */
    private boolean checkEchecMat()
    {
        ArrayList<Coordonnees> tableauMenacePar;

        Coordonnees coordRoi = getCoordRoi(echiquier);
        assert(coordRoi.getIdxLigne() != -1 && coordRoi.getIdxColonne() != -1);

        tableauMenacePar = estMenacePar(coordRoi, echiquier);
        if (!tableauMenacePar.isEmpty())
        {
            if (listeTousDeplacements.isEmpty())
            {
                affichageMat();
                return true;
            }

            affichageEchec(tableauMenacePar);
        }
        return false;
    }

    /**
     * Verifie si la partie est terminée en raison d'un manque de pièces restantes sur le plateau.
     * @return Retourne true s'il n'y a plus assez de pièce pour réaliser un mat, false dans le cas contraire.
     */
    private boolean checkManqueMateriel()
    {
        ArrayList<IPiece> piecesRestantes = new ArrayList<>();
        for (int idxLigne = 0; idxLigne < Plateau.getTailleLigne(); idxLigne++)
        {
            for (int idxColonne = 0; idxColonne < Plateau.getTailleColonne(); idxColonne++)
            {
                if (echiquier[idxLigne][idxColonne] != null)
                    piecesRestantes.add(echiquier[idxLigne][idxColonne]);
            }
        }

        if (piecesRestantes.size() == 3)
        {
            for (IPiece piece : piecesRestantes)
            {
                if (Character.toUpperCase(piece.getNom()) == 'F' || Character.toUpperCase(piece.getNom()) == 'C')
                {
                    System.out.println("Manque de matériels ! Match nul\n");
                    return true;
                }
            }
        }

        if ((piecesRestantes.size() == 2))
        {
            System.out.println("Manque de matériels ! Match nul\n");
            return true;
        }

        return false;
    }


    /**
     * affichage d'un message en cas de mat.
     */
    private void affichageMat()
    {
        System.out.println("Echec et mat !");
        System.out.print("Les ");
        if (getCouleurJoueur() == CouleurPiece.BLANC)
            System.out.print("noirs");
        else
            System.out.print("blancs");
        System.out.println(" ont gagné\n");
    }

    /**
     * Affichage d'un message en cas d'échec.
     * @param tableauMenacePar Liste contenant l'ensemble des pièces menançant le roi.
     */
    private void affichageEchec(ArrayList<Coordonnees> tableauMenacePar)
    {
        for (Coordonnees coordPiece : tableauMenacePar)
        {
            System.out.print("Echec ! ");
            System.out.println(echiquier[coordPiece.getIdxLigne()][coordPiece.getIdxColonne()].getNomComplet() +
            " menace le roi en " + coordPiece.coordonneesReverse() + "\n");
        } 
    }
}
