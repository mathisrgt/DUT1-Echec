package partie.categorieJoueur.ordinateur;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import partie.categorieJoueur.IJoueur;
import partie.plateau.deplacement.DeplacementsParPiece;
import partie.plateau.pieces.Enum.TypePiece;

/**
 * Joueur de type OrdiHasard jouant au hasard. C'est à dire que l'ensemble des actions réalisées seront aléatoires. Accès à l'ensemble des coups possibles sans possibilité d'abandon.
 */
public class OrdiHasard implements IJoueur {

    // ---------------------------------------------------- DEPLACEMENT -------------------------------------------------------------

    /**
    * Récupère un coup au hasard parmi ceux possibles.
    * @param listeTousDeplacements liste de tous les deplacement possible.
    * @param msgAide tous les coups possible pour chaque piece si le joueur demande de l'aide.
    * @return le coup sélectionné.
     */
    @Override
    public String nouveauDeplacement (ArrayList<DeplacementsParPiece> listeTousDeplacements, String msgAide)
    {
        return coupAleatoire(listeTousDeplacements);
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
    
    // ---------------------------------------------------- PROMOTION ET NULLE -------------------------------------------------------------

    /**
     * Permet de choisir la piece qu'on veux obtenir à la promotion du pion 
     * @return une piece au hasard entre la tour,le cavalier, la dame et le fou
     */
    @Override
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

    /**
     * Repond au hasard à une demande de nulle formulée par le joueur adverse.
     * @return Returne true ou false au hasard.
     */
    @Override
    public boolean confirmerNulle() {
        Random random = new Random();
        Boolean choix = random.nextBoolean();

        if (Boolean.TRUE.equals(choix))
            System.out.println("L'ordinateur accepter la proposition de nulle");
        else
            System.out.println("L'ordinateur refuse la proposition de nulle");
        return choix;
    }
}
