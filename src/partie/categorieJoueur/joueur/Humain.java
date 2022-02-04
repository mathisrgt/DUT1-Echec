package partie.categorieJoueur.joueur;


import java.util.ArrayList;

import entreeSortie.Scan;
import partie.categorieJoueur.IJoueur;
import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.deplacement.DeplacementsParPiece;
import partie.plateau.pieces.Enum.TypePiece;

/**
 * Confère la possibilité à un joueur humain de jouer un coup.
 */
public class Humain implements IJoueur
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
    @Override
    public String nouveauDeplacement(ArrayList<DeplacementsParPiece> listeTousDeplacements, String msgAide)
    {
        String coup = scanner.saisirClavier(4);
        Coordonnees origine = new Coordonnees(coup.substring(0, 2));
        Coordonnees destination = new Coordonnees(coup.substring(2));

        //La boucle continue jusqu'a rencontré un return, c'est à dire jusqu'à ce que l'entrée saisie par l'utilisateur soit correcte
        while(true)
        {
            //Possibilité d'abandon pour le joueur
            if (coup.equals("surr") && confirmationAbandon())
                return coup;

            //Possibilité de demander une partie nulle
            else if (coup.equals("null") && demanderNulle())
                return coup;

            //Demande d'aide : affichage des différents coups possibles pour le joueur
            else if (coup.equals("help"))
                System.out.print(msgAide);

            //Verifie dans un premier temps si les coordonnées indiquées existent sur le plateau et s'il est possible
            else if ((verifCoordonnees(origine) && verifCoordonnees(destination)) && isDeplacementPossible(origine, destination, listeTousDeplacements))
                return coup;

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
    @Override
    public TypePiece choixPromotion()
    {
        String msgPromotion = "\nQuelle est la pièce souhaitée ?\nT -> Tour\nC -> Cavalier\nF -> Fou\nD -> Dame\n";
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

    /**
     * Détermine le type de pièce souhaité selon le caractère saisi.
     * @param pieceSouhaite Caractère désignant le type de pièce.
     * @return Retourne une valeur de l'énum TypePiece.
     */
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
        String msgAbandon = "\nVoulez-vous vraiment abandonner ?\noui -> Abandonner\nnon -> Reprendre la partie";
        System.out.println(msgAbandon);
        String coup = scanner.saisirClavier(3);

        while (!coup.equals("oui") && !coup.equals("non"))
        //Vérifie que l'entrée saisie correspond à oui ou non. Dans le cas contraire, redemande à l'utilisateur de saisir un mot. 
        {
            System.out.println("Réponse non reconnue, veuillez recommencer");
            System.out.println(msgAbandon);
            coup = scanner.saisirClavier(3);
        }

        if (coup.equals("oui"))
            return true;
        return false;    
    }

    /**
     * Vérifie que le joueur souhaite bien demander une nulle.
     * @return true si l'utilsateur confirme vouloir abandonner, false dans le cas contraire.
     */
    private boolean demanderNulle()
    {
        String msgNulle = "\nVoulez-vous vraiment demander la nulle ?\noui -> Confirmer\nnon -> Reprendre la partie";
        System.out.println(msgNulle);
        String coup = scanner.saisirClavier(3);

        while (!coup.equals("oui") && !coup.equals("non"))
        //Vérifie que l'entrée saisie correspond à oui ou non. Dans le cas contraire, redemande à l'utilisateur de saisir un mot. 
        {
            System.out.println("Réponse non reconnue, veuillez recommencer");
            System.out.println(msgNulle);
            coup = scanner.saisirClavier(3);
        }

        if (coup.equals("oui"))
            return true;
        return false;    
    }

    /**
     * Demande confirmation au joueur adverse pour appliquer la nulle.
     * @return true si l'adversaire est d'accord.
     */
    @Override
    public boolean confirmerNulle() {
        String msgConfirmation = "\nAccepter la proposition de partie nulle ?\noui -> Accepter\nnon -> Reprendre la partie";
        System.out.println(msgConfirmation);

        String coup = scanner.saisirClavier(3);

        while (!coup.equals("oui") && !coup.equals("non"))
        //Vérifie que l'entrée saisie correspond à oui ou non. Dans le cas contraire, redemande à l'utilisateur de saisir un mot. 
        {
            System.out.println("Réponse non reconnue, veuillez recommencer");
            System.out.println(msgConfirmation);
            coup = scanner.saisirClavier(3);
        }

        if (coup.equals("oui"))
            return true;
        
        return false;
    }
}
