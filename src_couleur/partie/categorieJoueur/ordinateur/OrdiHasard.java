package partie.categorieJoueur.ordinateur;
import java.util.ArrayList;
import java.util.Collections;

import partie.categorieJoueur.Joueur;
import partie.plateau.deplacement.DeplacementsParPiece;
import partie.plateau.pieces.Enum.CouleurPiece;
import partie.plateau.pieces.Enum.TypePiece;

/**
 * Joueur de type OrdiHasard jouant au hasard. C'est à dire que l'ensemble des actions réalisées seront aléatoires. Accès à l'ensemble des coups possibles sans possibilité d'abandon.
 */
public class OrdiHasard extends Joueur {

    // ---------------------------------------------------- DEPLACEMENT -------------------------------------------------------------

    /**
    * Récupère un coup au hasard parmi ceux possibles.
    * @param listeTousDeplacements liste de tous les deplacement possible.
    * @param couleurJoueur couleur du joueur qui souhaite réaliser le coup.
    * @param msgAide tous les coups possible pour chaque piece si le joueur demande de l'aide.
    * @return le coup sélectionné.
     */
    public String nouveauDeplacement (ArrayList<DeplacementsParPiece> listeTousDeplacements, CouleurPiece couleurJoueur, String msgAide)
    {
        String coup = coupAleatoire(listeTousDeplacements);
        
        System.out.println(affichageTour(coup, couleurJoueur, "L'ordinateur "));
        return coup;
    }

    /**
     * Selectionne un coup au hasard.
     * @param listeTousDeplacements Liste contenant l'ensemble des déplacements possibles pour ce tour.
     * @return Retourne un String contenant le coup choisi.
     */
    private String coupAleatoire(ArrayList<DeplacementsParPiece> listeTousDeplacements)
    {
        for (DeplacementsParPiece piece : listeTousDeplacements)
            Collections.shuffle(piece.getListeDestination());

        Collections.shuffle(listeTousDeplacements);

        return listeTousDeplacements.get(0).getCoord().coordonneesReverse() + listeTousDeplacements.get(0).getListeDestination().get(0).coordonneesReverse();
    }
    
    // ---------------------------------------------------- PROMOTION -------------------------------------------------------------

    /**
     * Permet de choisir la piece qu'on veux obtenir à la promotion du pion 
     * @return une piece au hasard entre la tour,le cavalier, la dame et le fou
     */    
    public TypePiece choixPromotion()
    {
        ArrayList<TypePiece> listePieces = new ArrayList<>();
        listePieces.add(TypePiece.TOUR);
        listePieces.add(TypePiece.CAVALIER);
        listePieces.add(TypePiece.DAME);
        listePieces.add(TypePiece.FOU);

        Collections.shuffle(listePieces);

        return listePieces.get(0);
    }
}
