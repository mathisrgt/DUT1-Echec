package partie.plateau.deplacement;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.gestionRisques.Danger;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.Enum.CouleurPiece;

public class Roque extends Danger{

    /**
     * Tableau à deux dimensions représentant le plateau de jeu.
     */
    private IPiece[][] echiquier;

    /**
     * Booléen autorisant ou non le petit roque blanc.
     */
    private boolean petitRoqueBlanc;

    /**
     * Booléen autorisant ou non le petit roque noir.
     */
    private boolean petitRoqueNoir;

    /**
     * Booléen autorisant ou non le grand roque blanc.
     */
    private boolean grandRoqueBlanc;

    /**
     * Booléen autorisant ou non le grand roque noir.
     */
    private boolean grandRoqueNoir;

    /**
     * Constructeur utilisé pour la création d'objet de type Roque.
     * Lors de son initialisation, autorise tous les différents roques.
     * @param couleurJoueur Couleur du joueur. 
     */
    public Roque (CouleurPiece couleurJoueur)
    {
        super(couleurJoueur);

        petitRoqueBlanc = true;
        petitRoqueNoir  = true;
        grandRoqueBlanc = true;
        grandRoqueNoir  = true;
    }

    /***
     * Permet d'ajouter les roques à la liste des coups disponibles.
     * @param deplacementsPossibles Liste de tous les déplacements possibles.
     * @param echiquier Tableau de Piece à deux dimensions représentant le plateau de jeu.
     * @param couleurJoueur Couleur du joueur.
     */
    public void ajouterRoques(ArrayList<DeplacementsParPiece> deplacementsPossibles, IPiece[][] echiquier, CouleurPiece couleurJoueur)
    {
        majVariablesAttributs(echiquier, couleurJoueur);

        int idxRoi = 0;
        Coordonnees coordRoi = getCoordRoi(echiquier);
        for (DeplacementsParPiece piece : deplacementsPossibles)
        {
            if (coordRoi.equals(piece.getCoord()))
                idxRoi = deplacementsPossibles.indexOf(piece);
        }

        if (super.getCouleurJoueur() == CouleurPiece.BLANC)
        {
            if (petitRoqueBlanc && echiquier[Plateau.getTailleLigne() - 1][Plateau.getTailleColonne() - 1] != null 
            && echiquier[Plateau.getTailleLigne() - 1][Plateau.getTailleColonne() - 1].getNom() == 'T' && isRoquePossible(coordRoi, Plateau.getTailleColonne() - 1))
                deplacementsPossibles.get(idxRoi).getListeDestination().add(new Coordonnees(Plateau.getTailleLigne() - 1, 6));

            if (grandRoqueBlanc && echiquier[Plateau.getTailleLigne() - 1][0] != null 
            && echiquier[Plateau.getTailleLigne() - 1][0].getNom() == 'T' && isRoquePossible(coordRoi, 0))
                deplacementsPossibles.get(idxRoi).getListeDestination().add(new Coordonnees(Plateau.getTailleLigne() - 1, 2));
        }
        else
        {
            if (petitRoqueNoir && echiquier[0][Plateau.getTailleColonne() - 1] != null 
            && echiquier[0][Plateau.getTailleColonne() - 1].getNom() == 't' && isRoquePossible(coordRoi, Plateau.getTailleColonne() - 1))
                deplacementsPossibles.get(idxRoi).getListeDestination().add(new Coordonnees(0, 6));
            
            if (grandRoqueNoir && echiquier[0][0] != null && echiquier[0][0].getNom() == 't' && isRoquePossible(coordRoi, 0))
                deplacementsPossibles.get(idxRoi).getListeDestination().add(new Coordonnees(0, 2));
        }
    }

    /**
     * desactive le grand roque blanc
     */
    public void desactiveGrandRoqueBlanc() {
        this.grandRoqueBlanc = false;
    }

    /**
     * desactive le grand roque noir
     */
    public void desactiveGrandRoqueNoir() {
        this.grandRoqueNoir = false;
    }

    /**
     * desactive le petit roque blanc
     */
    public void desactivePetitRoqueBlanc() {
        this.petitRoqueBlanc = false;
    }

    /**
     * desactive le petit roque noir
     */
    public void desactivePetitRoqueNoir() {
        this.petitRoqueNoir = false;
    }

    /**
     * Vérifie si le roque est possible.
     * @param coordRoi Coordonée du roi.
     * @param idxColonneTour Colonne de la tour.
     * @return true si le roque est possible sinon false.
     */
    private boolean isRoquePossible(Coordonnees coordRoi, int idxColonneTour)
    {
        if (isCasesVides(coordRoi, idxColonneTour) && isTrajetSafe(coordRoi, idxColonneTour))
            return true;
        return false;
    }

    /**
     * Mise à jours des variables d'attributs de la classe. Utilisé pour réutiliser un objet de type Roque sans avoir à en un recréer un à chaque chagement sur le plateau ou de joueur.
     * @param echiquier Tableau de Piece à deux dimensions représentant le plateau de jeu.
     * @param couleurJoueur couleur du joueur
     */
    private void majVariablesAttributs(IPiece[][] echiquier, CouleurPiece couleurJoueur)
    {
        this.echiquier = echiquier;
        super.setCouleurJoueur(couleurJoueur);
    }
    
    /**
     * Vérifie si l'ensemble des cases sur le trajet entre le roi et la tour sont vides.
     * @param coordRoi Coordonnées actuelles du roi
     * @param idxColonneTour Index de la colonne de la tour utilisée pour roquer.
     * @return Retourne true si toutes les cases sont vides, false dans le cas contraire.
     */
    private boolean isCasesVides(Coordonnees coordRoi, int idxColonneTour)
    {
        if (coordRoi.getIdxColonne() < idxColonneTour)
        {
            for (int idxColonne = coordRoi.getIdxColonne() + 1; idxColonne <= idxColonneTour - 1; idxColonne++)
            {
                if (echiquier[coordRoi.getIdxLigne()][idxColonne] != null)
                    return false;
            }
        }
        else
        {
            for (int idxColonne = coordRoi.getIdxColonne() - 1; idxColonne >= idxColonneTour + 1; idxColonne--)
            {
                if (echiquier[coordRoi.getIdxLigne()][idxColonne] != null)
                    return false;
            }
        }

        return true;
    }

    /**
     * Vérifie si aucune case du trajet n'est menacée par une pièce adverse.
     * @param coordRoi Coordonnées actuelles du roi
     * @param idxColonneTour Index de la colonne de la tour utilisée pour roquer.
     * @return Retourne true si aucune des 3 cases n'est menacée, false dans le cas contraire.
     */
    private boolean isTrajetSafe(Coordonnees coordRoi, int idxColonneTour)
    {
        if (coordRoi.getIdxColonne() < idxColonneTour)
        {
            for (int idxColonne = coordRoi.getIdxColonne(); idxColonne <= coordRoi.getIdxColonne() + 2; idxColonne++)
            {
                if (!listeMenaces(new Coordonnees(coordRoi.getIdxLigne(), idxColonne)).isEmpty())
                    return false;
            }
        }
        else
        {
            for (int idxColonne = coordRoi.getIdxColonne(); idxColonne >= coordRoi.getIdxColonne() - 2; idxColonne--)
            {
                if (!listeMenaces(new Coordonnees(coordRoi.getIdxLigne(), idxColonne)).isEmpty())
                    return false;
            }
        }
        return true;
    }

    /**
     * Permet d'obtenir la liste des pièces adverses menancant la case aux coordonnées indiquées.
     * @param coord Coordonnée de la case vérifiée
     * @return Retourne un ArrayList contenant les coordonnées des pièces menancant la case indiquée.
     */
    private ArrayList<Coordonnees> listeMenaces(Coordonnees coord)
    {
        ArrayList<Coordonnees> touteMenaces = estMenacePar(coord, echiquier);
        ArrayList<Coordonnees> menaceMemeCouleur = new ArrayList<>();

        for (Coordonnees piece : touteMenaces)
        {
            if (echiquier[piece.getIdxLigne()][piece.getIdxColonne()].getCouleur() == super.getCouleurJoueur())
                menaceMemeCouleur.add(piece);
        }

        for (Coordonnees piece : menaceMemeCouleur)
            touteMenaces.remove(piece);

        return touteMenaces;
    }
}
