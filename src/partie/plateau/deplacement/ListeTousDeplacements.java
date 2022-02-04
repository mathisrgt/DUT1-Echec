package partie.plateau.deplacement;

import java.util.ArrayList;

import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.gestionRisques.Danger;
import partie.plateau.pieces.*;
import partie.plateau.pieces.Enum.CouleurPiece;
import partie.plateau.pieces.Enum.TypePiece;

public class ListeTousDeplacements extends Danger {

    /**
     * Tableau à deux dimensions représentant le plateau de jeu.
     */
    private IPiece[][] echiquier;

    /**
     * Objet de type Roque permettant la possibilité de roquer.
     */
    private Roque roque;

    /**
     * Constructeur de la classe ListeTousDeplacements.
     * Cette classe permet la création d'une liste composée de l'ensemble des déplacements possible pour le joueur.
     * @param couleurJoueur Couleur du joueur.
     * @param echiquier Tableau à deux dimensions représentant le plateau de jeu.
     * @param roque Objet permettant la possibilité de roque.
     */
    public ListeTousDeplacements(CouleurPiece couleurJoueur, IPiece[][] echiquier, Roque roque)
    {
        super(couleurJoueur);
        this.echiquier = echiquier;
        this.roque = roque;
    }

    // ---------------------------------------------------- METHODES PUBLIQUES ---------------------------------------------------------

    /**
     * Obtention du tableau contenant la liste des déplacements possibles par pièce.
     * @return Retourne un tableau de type ArrayList<DeplacementsParPiece> contenant l'ensemble de déplacements possible, organisés par pièce, pour le joueur .
     */
    public ArrayList<DeplacementsParPiece> getDeplacementsPossible()
    {
        ArrayList<DeplacementsParPiece> deplacementsPossibles = new ArrayList<>();

        //Pour chaque case de l'échiquier non nulle et dont la pièce appartient au joueur .
        for (int idxLigne = 0; idxLigne < Plateau.getTailleLigne(); idxLigne++)
        {
            for (int idxColonne = 0; idxColonne < Plateau.getTailleColonne(); idxColonne++)
            {
                if (echiquier[idxLigne][idxColonne] != null && echiquier[idxLigne][idxColonne].getCouleur() == getCouleurJoueur())
                    deplacementsPossibles.add(new DeplacementsParPiece(new Coordonnees(idxLigne, idxColonne), echiquier));   //Ajout de la structure DeplacementParPiece au tableau.
            }
        }
        
        suppressionRisques(deplacementsPossibles);
        roque.ajouterRoques(deplacementsPossibles, echiquier, getCouleurJoueur());

        return deplacementsPossibles;
    }

    // ---------------------------------------------------- METHODES PRIVEES ---------------------------------------------------------

    /**
     * Supprime tous les déplacements engendrant un risque pour le roi dans la liste de déplacements possibles.
     * @param deplacementsPossibles Liste contenant tous les déplacements possibles, y compris ceux mettant en danger le roi.
     * @return Retourne deplacementPossibles, sans les déplacements à risques.
     */
    private ArrayList<DeplacementsParPiece> suppressionRisques(ArrayList<DeplacementsParPiece> deplacementsPossibles)
    {
        ArrayList<DeplacementsParPiece> pieceSansDeplacement = new ArrayList<>();
        for (DeplacementsParPiece piece : deplacementsPossibles)
        {
            //Tous les déplacements mettant en danger le roi seront référencés dans cette ArrayList.
            ArrayList<Coordonnees> destNonSafe = new ArrayList<>();
            for (Coordonnees dest : piece.getListeDestination())
            {
                if (!isRoiSafe(piece.getCoord(), dest))
                    destNonSafe.add(dest);  //Ajout de tous les déplacements méttant le roi en danger.
            }

            //Suppression des déplacements associés à une pièce de tous ceux référencés dans destNonSafe.
            for (Coordonnees destASuppr : destNonSafe)
                piece.getListeDestination().remove(destASuppr);

            //Si la pièce n'a plus de destination possible, elle se fait référencer dans pieceSansDeplacement.
            if (piece.getListeDestination().isEmpty())
                pieceSansDeplacement.add(piece);    
        }

        //Suppression de toutes les structures référencées dans pieceSansDeplacement.
        for(DeplacementsParPiece pieceASuppr : pieceSansDeplacement)
            deplacementsPossibles.remove(pieceASuppr);

        return deplacementsPossibles;
    }

    /**
     * Verifie si le roi est en sécurité suite à un déplacement.
     * @param origine Coordonnée d'origine de la pièce.
     * @param destination Coordonnée suite au déplacement de la pièce.
     * @return Retoure true si le roi est en sécurité, false dans le cas contraire.
     */
    private boolean isRoiSafe(Coordonnees origine, Coordonnees destination)
    {
        IPiece[][] copiePlateau = initialisationCopiePlateau();
        Coordonnees coordRoiCopie = getCoordRoi(copiePlateau);
        assert(coordRoiCopie.getIdxLigne() != -1 && coordRoiCopie.getIdxColonne() != -1);

        if (origine.equals(coordRoiCopie))
            coordRoiCopie.setNouvellesCoordonnees(destination);
        
        copiePlateau[destination.getIdxLigne()][destination.getIdxColonne()] = copiePlateau[origine.getIdxLigne()][origine.getIdxColonne()];
        copiePlateau[origine.getIdxLigne()][origine.getIdxColonne()] = null;

        if (estMenacePar(coordRoiCopie, copiePlateau).isEmpty())
            return true;
        return false;    
    }

    /**
     * Création d'une copie du plateau. Cette copie sera manipulée afin de vérifier si un coup met en danger ou non le roi.
     * @return Retourne une copie de l'échiquier, c'est-à-dire un tableau de Piece à deux dimensions.
     */
    private IPiece[][] initialisationCopiePlateau()
    {
        IPiece[][] copiePlateau = new IPiece[Plateau.getTailleLigne()][Plateau.getTailleColonne()];
        FabriquePiece fp = new FabriquePiece();

        for(int idxLigne = 0; idxLigne < Plateau.getTailleLigne(); idxLigne++)
        {
            for(int idxColonne = 0; idxColonne < Plateau.getTailleColonne(); idxColonne++)
            {
                if(echiquier[idxLigne][idxColonne] != null)
                {
                    if(Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'T')
                        copiePlateau[idxLigne][idxColonne] = fp.creationPiece(echiquier[idxLigne][idxColonne].getCouleur(), TypePiece.TOUR);
                    else if(Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'C')
                        copiePlateau[idxLigne][idxColonne] = fp.creationPiece(echiquier[idxLigne][idxColonne].getCouleur(), TypePiece.CAVALIER);
                    else if(Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'F')
                        copiePlateau[idxLigne][idxColonne] = fp.creationPiece(echiquier[idxLigne][idxColonne].getCouleur(), TypePiece.FOU);
                    else if(Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'D')
                        copiePlateau[idxLigne][idxColonne] = fp.creationPiece(echiquier[idxLigne][idxColonne].getCouleur(), TypePiece.DAME);
                    else if(Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'P')
                        copiePlateau[idxLigne][idxColonne] = fp.creationPiece(echiquier[idxLigne][idxColonne].getCouleur(), TypePiece.PION);
                    else if(Character.toUpperCase(echiquier[idxLigne][idxColonne].getNom()) == 'R')
                        copiePlateau[idxLigne][idxColonne] = fp.creationPiece(echiquier[idxLigne][idxColonne].getCouleur(), TypePiece.ROI);
                }
            }
        }
        return copiePlateau;
    }
}
