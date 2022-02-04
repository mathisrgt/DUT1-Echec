package partie.categorieJoueur.joueur;


import java.util.ArrayList;

import entreeSortie.Couleur;
import entreeSortie.Scan;
import partie.categorieJoueur.Joueur;
import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.deplacement.DeplacementsParPiece;
import partie.plateau.pieces.Enum.CouleurPiece;
import partie.plateau.pieces.Enum.TypePiece;

/**
 * Confère la possibilité à un joueur humain de jouer un coup.
 */
public class Humain extends Joueur 
{
    /**
     * Objet permettant la saisie clavier.
     */
    private Scan scanner;

    /**
     * Récupération de la classe Scan afin de permettre la saisie par l'utilisateur.
     * @param scanner objet de saisie
     */
    public Humain (Scan scanner)
    {
        this.scanner = scanner;
    }

    // ------------------------------------------------------ DEPLACEMENT ---------------------------------------------------------------
    
    /**
    * Récupère le coup saisie par un joueur.
    * @param listeTousDeplacements liste de tout les deplacement possible.
    * @param couleurJoueur couleur du joueur qui souhaite réaliser le coup.
    * @param msgAide tout les coups possible pour chaque piece si le joueur demande de l'aide.
    * @return Un String contenant le coup du joueur après sa validation.
     */
    public String nouveauDeplacement(ArrayList<DeplacementsParPiece> listeTousDeplacements, CouleurPiece couleurJoueur, String msgAide)
    {
        String coup = scanner.saisirClavier(4);
        Coordonnees origine = new Coordonnees(coup.substring(0, 2));
        Coordonnees destination = new Coordonnees(coup.substring(2));

        while(true)
        //La boucle continue jusqu'a rencontré un return, c'est à dire jusqu'à ce que l'entrée saisie par l'utilisateur soit correcte
        {
            if (coup.compareTo("surrend") == 0)
            //Possibilité d'abandon pour le joueur
            {
                if (confirmationAbandon())
                {
                    System.out.println(affichageAbandon(couleurJoueur));
                    return coup;
                }
            }
            else if (coup.compareTo("help") == 0)
            //Demande d'aide : affichage des différents coups possibles pour le joueur
                System.out.print(msgAide);

            else if ((verifCoordonnees(origine) && verifCoordonnees(destination)) && isDeplacementPossible(origine, destination, listeTousDeplacements))
            //Verifie dans un premier temps si les coordonnées indiquées existent sur le plateau et s'il est possible
            {
                System.out.println(affichageTour(coup, couleurJoueur, "Le joueur "));
                return coup;
            } 
            else 
                System.out.println("Coup invalide");

            coup = scanner.saisirClavier(4);
            origine = new Coordonnees(coup.substring(0, 2));
            destination = new Coordonnees(coup.substring(2));
        }
    }

    /**
     * Verifie que les coordonnées existent sur le plateau.
     * @param coord coordonnées vérifiées
     * @return true si les coordonnées existent sur le plateau, false dans le cas contraire.
     */
    private boolean verifCoordonnees(Coordonnees coord)
    {
        if (!(coord.getIdxLigne() < 0 || coord.getIdxLigne() > Plateau.getTailleLigne()) &&
        !(coord.getIdxColonne() < 0 || coord.getIdxColonne() > Plateau.getTailleColonne()))
            return true;
        return false;
    }

    /**
     * Vérifie si le déplacement proposé est possible.
     * @param origine Emplacement de la pièce que l'on souhaite bouger.
     * @param destination Coordonnées de la case où l'on souhaite déplacer la pièce.
     * @return Retourne true si le déplacement est possible pour la pièce choisie.
     */
    private boolean isDeplacementPossible(Coordonnees origine, Coordonnees destination, ArrayList<DeplacementsParPiece> tabTousDeplacementsPossibles)
    {
        for (DeplacementsParPiece piece : tabTousDeplacementsPossibles)
        {
            if (piece.getCoord().equals(origine))
            {
                for (Coordonnees deplacement : piece.getListeDestination())
                {
                    if (deplacement.equals(destination))
                        return true;
                }
                return false;
            } 
        }
        return false;
    }

    // -------------------------------------------------------- PROMOTION ------------------------------------------------------------

     /**
     * Permet de choisir la piece qu'on veux obtenir à la promotion du pion.
     * @return un type de pièce selon le choix du joueur.
     */    
    public TypePiece choixPromotion()
    {
        String msgPromotion = "\nQuelle est la pièce souhaitée ?\n" + Couleur.getVert() + "T" + Couleur.getStopCouleur() +
        " -> Tour\n" + Couleur.getVert() + "C" + Couleur.getStopCouleur() + " -> Cavalier\n" +
        Couleur.getVert() + "F" + Couleur.getStopCouleur() + " -> Fou\n" + Couleur.getVert() +
        "D" + Couleur.getStopCouleur() + " -> Dame\n";
        System.out.print(msgPromotion);

        String pieceSouhaite = scanner.saisirClavier(1).toUpperCase();

        while(!checkEntree(pieceSouhaite.charAt(0)))
        //Demande un nouveau choix tant que le choix ne correspond à aucune pièce possible.
        {
            System.out.println("Pièce choisie non reconnue, saisir une nouvelle valeur.");
            System.out.print(msgPromotion);
            pieceSouhaite = scanner.saisirClavier(1).toUpperCase();
        }
        return retrouverType(pieceSouhaite.charAt(0));
    }

    /**
     * Verifie si l'entrée correspond bien à une pièce.
     * @param c Caractère désignant la pièce choisie.
     * @return Retourne vraie si le nom est correct, false dans le cas contraire.
     */
    private boolean checkEntree(char c)
    {
        if (c == 'T' || c == 'C' || c == 'F' || c == 'D')
            return true;
        return false;
    }

    private TypePiece retrouverType(char pieceSouhaite)
    {
        switch (pieceSouhaite)
        {
            case 'T' : return TypePiece.TOUR;
            case 'D' : return TypePiece.DAME;
            case 'C' : return TypePiece.CAVALIER;
            case 'F' : return TypePiece.FOU;
            default  : return null;
        }
    }
    // -------------------------------------------------------- ABANDON ------------------------------------------------------------

    /**
     * Demande confirmation de l'abandon.
     * @return true si l'utilsateur confirme vouloir abandonner, false dans le cas contraire.
     */
    private boolean confirmationAbandon()
    {
        String msgAbandon = "\nVoulez-vous vraiment abandonner ?\n" + Couleur.getVert() + "oui" + Couleur.getStopCouleur() +
        " -> Abandonner\n" + Couleur.getVert() + "non" + Couleur.getStopCouleur() + " -> Reprendre la partie";
        System.out.println(msgAbandon);
        String coup = scanner.saisirClavier(3);

        while (coup.compareTo("oui") != 0 && coup.compareTo("non") != 0)
        //Vérifie que l'entrée saisie correspond à oui ou non. Dans le cas contraire, redemande à l'utilisateur de saisir un mot. 
        {
            System.out.println("Réponse non reconnue, veuillez recommencer");
            System.out.println(msgAbandon);
            coup = scanner.saisirClavier(3);
        }

        if (coup.compareTo("oui") == 0)
            return true;
        return false;    
    }

    /**
     * Affiche le message de fin de partie correspondant à l'abandon d'un joueur.
     * @param couleurJoueur Utilisée pour le choix de la couleur lors de l'affichage.
     * @return Retourne un String contenant le message d'affichage.
     */
    private String affichageAbandon(CouleurPiece couleurJoueur)
    {
        StringBuilder str = new StringBuilder();
        str.append("\nAbandon ! Les ");
        if (couleurJoueur == CouleurPiece.BLANC)
            str.append(Couleur.getBleu() + "noirs");
        else
            str.append (Couleur.getJaune() + "blancs");
        str.append(Couleur.getStopCouleur() + " ont gagné\n");

        return str.toString();
    }


}
