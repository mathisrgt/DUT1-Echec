package partie;

import entreeSortie.Scan;
import partie.categorieJoueur.FabriqueJoueur;
import partie.categorieJoueur.IJoueur;
import partie.plateau.Coordonnees;
import partie.plateau.Plateau;
import partie.plateau.pieces.Enum.CouleurPiece;

/** 
 *  Ensemble des méthodes relatives au bon déroulenement de la partie.
 */
public class Partie {
    /**
     * Objet de type Plateau, contient la représentation de l'échiquier ainsi que toutes les méthodes liées au déplacement des pièces.
     */
    private Plateau plateau;

    /**
     * char associé à un mode de jeu. Permet de faire varier le dérouler des parties ainsi que l'organisation du plateau.
     */
    private ModeDeJeu modeChoisi;
    
    /**
     * Objet permettant la saisie clavier par un utilisateur.
     */
    private Scan scanner;

    /**
     * Compteur de tour de jeu. Initialisé à 0 en début de partie et s'incrémente au début du tour de chaque joueur.
     */
    private int cptTour;

    /** 
     *  Constructeur de la classe Partie.
     *  Initialise les différentes variables d'attributs, notamment le plateau de jeu.
     *  @param modeChoisi Mode sélectionné par le joueur. Fait varier le type de partie joué.
     *  @param scanner Classe permetant à l'utilisateur d'utiliser la fonction scanner.
     */
    public Partie(ModeDeJeu modeChoisi, Scan scanner)
    {
        plateau = new Plateau(modeChoisi);
        this.scanner = scanner;
        this.modeChoisi = modeChoisi;
        this.cptTour = 0;
    }

    // ------------------------------------------------------------ DEROULE PARTIE -------------------------------------------------------------

    /**
     * Selectionne le mode de jeu souhaité selon la variable modeChoisi.
     */
    public void jouerPartie()
    {
        FabriqueJoueur creaJoueur = new FabriqueJoueur(modeChoisi, scanner);
        IJoueur J1 = creaJoueur.creationJoueur(1);
        IJoueur J2 = creaJoueur.creationJoueur(2);

        System.out.println(affichageDebutPartie());
        boolean partieTerminee = false;
        while (!partieTerminee)
        {
            partieTerminee = nouveauTour(J1, J2);
            if (!partieTerminee)
                partieTerminee = nouveauTour(J2, J1);
        }
        System.out.println(affichageNbTourFinal());
    }

    /**
     * Organise les différentes actions réalisées au cours d'un tour de jeu pour un joueur.
     * @param joueur Joueur de type IJoueur. Correspond au joueur en train de jouer.
     * @param adversaire De type IJoueur, permet de demander confirmation au nulle.
     * @return Retourne le statut de la partie : true si partie terminée, false dans le cas contraire.
     */
    private boolean nouveauTour(IJoueur joueur, IJoueur adversaire)
    {
        affichageDebutTour();
        affichagePlateau();

        boolean partieTerminee = plateau.isPartieTerminee();
        if(!partieTerminee)
        {
            gestionCpt();

            while (true)
            {
                String coup = joueur.nouveauDeplacement(plateau.getListeTousDeplacements(), plateau.getHelp());

                //Abandon du joueur.
                if (coup.equals("surr"))
                {
                    System.out.println(affichageAbandon(plateau.getCouleurJoueur()));
                    return true;
                }

                //Demande de partie nulle.
                else if (coup.equals("null"))
                {
                    if (adversaire.confirmerNulle())
                    {
                        System.out.println("\nPartie Nulle !\n");
                        return true;    
                    }
                }
    
                //Déplacement d'une pièce.
                else
                {
                    Coordonnees origine = new Coordonnees(coup.substring(0, 2));
                    Coordonnees destination = new Coordonnees(coup.substring(2)); 
                    plateau.nouveauDeplacement(origine, destination);
                    System.out.println(affichageTour(coup));
                    promotion(joueur, destination);
    
                    plateau.inverseRole();
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Permet de réaliser la promotion.
     * @param joueur Joueur de type IJoueur. Correspond au joueur en train de jouer.
     * @param destination Coordonnée d'arrivée du déplacement.
     */
    private void promotion(IJoueur joueur, Coordonnees destination)
    {
        if (Character.toUpperCase(plateau.getPieceNom(destination)) == 'P' && (destination.getIdxLigne() == 0 || destination.getIdxLigne() == Plateau.getTailleLigne() - 1))
            plateau.promotion(destination, joueur.choixPromotion());
    }


    // ---------------------------------------------------------------- DIVERS ----------------------------------------------------------------

    /**
     * Affichage du bandeau de début de partie ainsi que les commandes spéciales disponibles.
     * @return Retourne un String contenant l'affichage.
     */
    private String affichageDebutPartie()
    {
        StringBuilder str = new StringBuilder();
        str.append("----------------------------------- DEBUT DE LA PARTIE -----------------------------------\n\n");
        str.append("Commandes spéciales :\nsurr -> pour abandonner\nnull -> demande de partie nulle\nhelp -> pour obtenir la liste des déplacements disponibles\n");

        return str.toString();
    }

    /**
     * Bandeau d'affichage de début de tour
     */
    private void affichageDebutTour()
    {
        System.out.println("--------------------------------- DEBUT TOUR JOUEUR " + plateau.getCouleurJoueur() + " ---------------------------------");
    }

    /**
     * Affichage de l'échiquier. Le sens du plateau varie selon la couleur du joueur courant.
     */
    private void affichagePlateau()
    {
        if(plateau.getCouleurJoueur() == CouleurPiece.BLANC)
            System.out.println(plateau); 
        else
            System.out.println(plateau.toStringInverse());
    }

    /**
     * Compteur de tour mis à jour à chaque fin de tour.
     */
    private void gestionCpt()
    {
        cptTour++;
        System.out.println("Tour n°" + cptTour);
    }

    /**
     * Affichage du nombre de tours joués au cours de la partie.
     * @return Retourne un String contenant le message à afficher.
     */
    private String affichageNbTourFinal()
    {
        return "Nombre de tours joués : " + cptTour;
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
            str.append("noirs");
        else
            str.append ("blancs");
        str.append(" ont gagné\n");

        return str.toString();
    }

    /**
     * Affichage du coup joué par un joueur
     * @param nouveauCoup String contenant le coup joué.
     * @return un String contenant le message à afficher
     */
    private String affichageTour(String nouveauCoup)
    {
        StringBuilder str = new StringBuilder();
        if (plateau.getCouleurJoueur() == CouleurPiece.BLANC)
            str.append("\nLes blancs");
        else
            str.append("\nLes noirs");
        str.append(" ont joué le coup : " + nouveauCoup + "\n");
        return str.toString();
    }
}
