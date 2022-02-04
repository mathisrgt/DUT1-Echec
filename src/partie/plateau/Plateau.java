package partie.plateau;

import java.util.ArrayList;

import partie.ModeDeJeu;
import partie.plateau.deplacement.*;
import partie.plateau.gestionRisques.FinPartie;
import partie.plateau.pieces.IPiece;
import partie.plateau.pieces.FabriquePiece;
import partie.plateau.pieces.Enum.*;

public class Plateau {

    /**
     * Nombre de lignes du plateau.
     */
    private static int tailleLigne = 8;

    /**
     * Nombre de colonnes du plateau.
     */
    private static int tailleColonne = 8;

    /**
     * Tableau de Piece à deux dimensions. Représente le plateau de jeu.
     */
    private IPiece[][] echiquier = new IPiece[tailleLigne][tailleColonne];

    /**
     * ArrayList de type DeplacementsParPiece contenant pour chaque pièce 
     * ses coordonnées ainsi qu'une liste contenant les coordonnées de toutes les cases où la pièce peut se déplacer.
     */
    private ArrayList<DeplacementsParPiece> listeTousDeplacements;

    /**
     * Couleur des pièces du joueur effectuant son tour.
     */
    private CouleurPiece couleurJoueur;

    /**
     * Objet Roque permettant la possibilité de roque. 
     */
    private Roque roque;

    //------------------------------------------------------------- CONSTRUCTEUR ------------------------------------------------------------------
    /**
     * Initialise le plateau selon le mode de jeu choisi.
     * @param modeDeJeu Correspond au mode de jeu choisi dans plateau.
     */
    public Plateau(ModeDeJeu modeDeJeu)
    {
        couleurJoueur = CouleurPiece.BLANC;

        roque = new Roque(couleurJoueur);
        switch (modeDeJeu)
        {
            case JVJ : 
            case JVO : 
            case OVO : 
                initialiserEchiquierComplet();
                break;
            case RT : 
                initialiserEchiquierRoiTour();
                break;
            default  : 
                System.out.println("ERREUR MODE DE JEU NON RECONNU");
        }
        
        listeTousDeplacements = new ListeTousDeplacements(couleurJoueur, echiquier, roque).getDeplacementsPossible();  
    }

    /**
     * Pose toutes les pièces sur le plateau.
     */
    private void initialiserEchiquierComplet()
    {   
        FabriquePiece fp = new FabriquePiece();

        echiquier[0][0] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.TOUR);
        echiquier[0][7] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.TOUR);
        echiquier[0][1] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.CAVALIER);
        echiquier[0][6] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.CAVALIER);
        echiquier[0][2] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.FOU);
        echiquier[0][5] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.FOU);
        echiquier[0][3] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.DAME);
        echiquier[0][4] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.ROI);
        for (int idxColonne = 0; idxColonne < tailleColonne; idxColonne++)
            echiquier[tailleLigne - (tailleLigne - 1)][idxColonne] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.PION);

        echiquier[tailleLigne - 1][0] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.TOUR);
        echiquier[tailleLigne - 1][7] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.TOUR);
        echiquier[tailleLigne - 1][1] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.CAVALIER);
        echiquier[tailleLigne - 1][6] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.CAVALIER);
        echiquier[tailleLigne - 1][2] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.FOU);
        echiquier[tailleLigne - 1][5] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.FOU);
        echiquier[tailleLigne - 1][3] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.DAME);
        echiquier[tailleLigne - 1][4] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.ROI);
        for (int idxColonne = 0; idxColonne < tailleColonne; idxColonne++)
            echiquier[tailleLigne - 2][idxColonne] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.PION);
    }

    /**
     * Pose uniquement le roi et la tour de chaque couleur.
     */
    private void initialiserEchiquierRoiTour()
    {       
         FabriquePiece fp = new FabriquePiece();
        echiquier[0][7] = fp.creationPiece(CouleurPiece.NOIR,TypePiece.TOUR);
        echiquier[0][4] = fp.creationPiece(CouleurPiece.NOIR, TypePiece.ROI);

        echiquier[tailleLigne - 1][0] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.TOUR);
        echiquier[tailleLigne - 1][4] = fp.creationPiece(CouleurPiece.BLANC, TypePiece.ROI);
    }

    //--------------------------------------------------------------- GESTION DEPLACEMENT --------------------------------------------------

    /**
     * Application du déplacement sur le plateau.
     * @param origine Coordonnées de la pièce à déplacer.
     * @param destination Coordonnées du déplacement cible.
     */
    public void nouveauDeplacement(Coordonnees origine, Coordonnees destination)
    {
        priseFantome(origine, destination);

        echiquier[destination.getIdxLigne()][destination.getIdxColonne()] = echiquier(origine);
        echiquier[origine.getIdxLigne()][origine.getIdxColonne()] = null;

        gestionRoques(origine, destination);
        creationFantome(origine, destination);
    }

    //--------------------------------------------------------------- ROQUE --------------------------------------------------

    /**
     * Vérifie si le coup joué est un roque et si le coup joué désactive le roque.
     * @param origine Coordonnées de la pièce à déplacer.
     * @param destination Coordonnées de la destination ciblée.
     */
    public void gestionRoques(Coordonnees origine, Coordonnees destination)
    {
        desactivationRoques(origine);
        deplaceTourRoque(origine, destination);
    }

    /**
     * Déplace la tour lorsqu'un joueur joue un roque.
     * @param origine Coordonnées de la pièce à déplacer.
     * @param destination Coordonnées de la destination ciblée.
     */
    private void deplaceTourRoque (Coordonnees origine, Coordonnees destination)
    {
        if (origine.coordonneesReverse().equals("e1"))
        {
            if (destination.coordonneesReverse().equals("g1"))
            {
                echiquier[tailleLigne - 1][5] = echiquier[tailleLigne - 1][7];
                echiquier[tailleLigne - 1][7] = null;
            }
            else if (destination.coordonneesReverse().equals("c1"))
            {
                echiquier[tailleLigne - 1][3] = echiquier[7][0];
                echiquier[tailleLigne - 1][0] = null;
            }    
        }
        else if (origine.coordonneesReverse().equals("e8"))
        {
            if (destination.coordonneesReverse().equals("g8"))
            {
                echiquier[0][5] = echiquier[0][7];
                echiquier[0][7] = null;
            }
            else if (destination.coordonneesReverse().equals("c8"))
            {
                echiquier[0][3] = echiquier[0][0];
                echiquier[0][0] = null;
            }    
        }
    }

    /**
     * Desactive les roques selon le coup joué par l'utilisateur.
     * @param origine Coordonnées de la pièce à déplacer.
     */
    private void desactivationRoques(Coordonnees origine)
    {  
        if (origine.coordonneesReverse().equals("e1"))
        {
            roque.desactiveGrandRoqueBlanc();
            roque.desactivePetitRoqueBlanc();
        }
        
        if (origine.coordonneesReverse().equals("e8"))
        {
            roque.desactivePetitRoqueNoir();
            roque.desactivePetitRoqueNoir();
        }

        if (origine.coordonneesReverse().equals("a1"))
        {
           roque.desactiveGrandRoqueBlanc();
        }

        if (origine.coordonneesReverse().equals("a8"))
        {
            roque.desactiveGrandRoqueNoir();
        }

        if (origine.coordonneesReverse().equals("h1"))
        {
            roque.desactivePetitRoqueBlanc();
        }

        if (origine.coordonneesReverse().equals("h8"))
        {
            roque.desactivePetitRoqueNoir();
        }
    }

    // ----------------------------------------------------------------- LECTURE PLATEAU ---------------------------------------------------------

    /**
     * Permet de facilité l'écriture du code pour le plateau.
     * @param coord Coordonnées de la case souhaitée.
     * @return Retourne une case du plateau.
     */
    private final IPiece echiquier(Coordonnees coord)
    {
        return echiquier[coord.getIdxLigne()][coord.getIdxColonne()];
    }

    // ---------------------------------------------------------------- PRISE EN PASSANT ---------------------------------------------------------

    /**
     * Destruction du potentiel fantome existant.
     * Puis création d'un fantome si un pion avance de deux cases depuis sa position initiale. Permet la réalisation de la prise en passant.
     * @param origine Coordonnées de la case de départ.
     * @param destination Coordonnées de la case d'arrivée.
     */
    private void creationFantome(Coordonnees origine, Coordonnees destination)
    {
        Coordonnees fantome = trouverFantome();

        if (fantome != null)
            echiquier[fantome.getIdxLigne()][fantome.getIdxColonne()] = null;

        if (couleurJoueur == CouleurPiece.BLANC && echiquier(destination).getNom() == 'P' && (origine.getIdxLigne() == 6 && destination.getIdxLigne() == 4))
            echiquier[5][origine.getIdxColonne()] = new FabriquePiece().creationPiece(couleurJoueur, TypePiece.FANTOME);

        else if (couleurJoueur == CouleurPiece.NOIR && echiquier(destination).getNom() == 'p' && origine.getIdxLigne() == 1 && destination.getIdxLigne() == 3)
            echiquier[2][origine.getIdxColonne()] = new FabriquePiece().creationPiece(couleurJoueur, TypePiece.FANTOME);
    }

    /**
     * Méthode effectuant l'action de la prise en passant. C'est-à-dire que si un fantome se fait prendre par un pion adverse, le pion associé est également pris.
     * @param origine Coordonnées de la case de départ.
     * @param destination Coordonnées de la case d'arrivée.
     */
    private void priseFantome(Coordonnees origine, Coordonnees destination)
    {
        Coordonnees coordFantome = trouverFantome();

        if (destination.equals(coordFantome) && Character.toUpperCase(echiquier(origine).getNom()) == 'P')
        {
            if (couleurJoueur == CouleurPiece.BLANC)
                echiquier[coordFantome.getIdxLigne() + 1][coordFantome.getIdxColonne()] = null;
            else if (couleurJoueur == CouleurPiece.NOIR)
                echiquier[coordFantome.getIdxLigne() - 1][coordFantome.getIdxColonne()] = null;
            
        }
    }

    /**
     * Permet de retrouver les coordonnées d'une pièce de type Fantome si elle existe.
     * @return Retourne les coordonnées du Fantome s'il en existe un, null dans le cas contraire.
     */
    private Coordonnees trouverFantome()
    {
        for (int idxLigne = 0; idxLigne < tailleLigne - 1; idxLigne++)
        {
            for (int idxColonne = 0; idxColonne < tailleColonne - 1; idxColonne++)
            {
                if (echiquier[idxLigne][idxColonne] != null && echiquier[idxLigne][idxColonne].getNom() == ' ')
                    return new Coordonnees(idxLigne, idxColonne);
            }
        }
        return null;
    }

    // -------------------------------------------------------------------- GETTER ---------------------------------------------------------------
    
    /**
     * Obtenir la couleur du joueur dont le tour est en cours.
     * @return Retourne un char correspondant à la couleur.
     */
    public CouleurPiece getCouleurJoueur() 
    {
        return couleurJoueur;
    }

    /**
     * Obtenir le nom d'une pièce à des coordonnées du plateau.
     * @param coord Coordonnées de la pièce souhaitée.
     * @return  Retourne un char correspondant au nom de la pièce.
     */
    public char getPieceNom(Coordonnees coord)
    {
        if (echiquier(coord) != null)
            return echiquier(coord).getNom();
        return ' ';    
    }

    /**
     * Obtenir un tableau contenant la liste de toutes les pièces pouvant se déplacer avec la liste de leurs déplacements possibles.
     * @return Retourne un ArrayList de type <DeplacementParPiece> listant les déplacements possibles.
     */
    public ArrayList<DeplacementsParPiece> getListeTousDeplacements() {
        return listeTousDeplacements;
    }

    /**
     * Permet de connaître le nombre de lignes du plateau.
     * @return Retourne un int contenant le nombre de ligne.
     */
    public static int getTailleLigne() {
        return tailleLigne;
    }

    /**
     * Permet de connaitre le nombre de colonnes du plateau.
     * @return Retourne un int contenant le nombre de colonne.
     */
    public static int getTailleColonne() {
        return tailleColonne;
    }

    // -------------------------------------------------------------------- SETTER PROMOTION -------------------------------------------------------

    /**
     * Place une nouvelle tour aux coordonnées désignées. Utilisée lors de la promotion de pions.
     * @param destination Coordonnées correspondant à la case promue.
     * @param type Type de pièce souhaité lors de la promotion.
     */
    public void promotion(Coordonnees destination, TypePiece type)
    {
        echiquier[destination.getIdxLigne()][destination.getIdxColonne()] = new FabriquePiece().creationPiece(couleurJoueur, type);
    }

    // ------------------------------------------------------- METHODES RELATIVES AU TOUR DE JEU -------------------------------------------------

    /**
     * Inverse la couleur des joueurs et met à jour la liste des déplacements possibles.
     */
    public void inverseRole()
    {
        if (couleurJoueur == CouleurPiece.BLANC)
            couleurJoueur = CouleurPiece.NOIR;
        else
            couleurJoueur = CouleurPiece.BLANC;

        listeTousDeplacements = new ListeTousDeplacements(couleurJoueur, echiquier, roque).getDeplacementsPossible();  
    }

    // ----------------------------------------------------- METHODES APPELLEES DANS PARTIE ------------------------------------------------------

    /**
     * Permet d'obtenir la liste des déplacements possibles pour le joueur en cours.
     * @return Retourne un String contenant la liste des coups possibles.
     */
    public String getHelp()
    {
        StringBuilder listeDeplacement = new StringBuilder();
        for (DeplacementsParPiece piece : listeTousDeplacements)
        {
            listeDeplacement.append("\n" + echiquier[piece.getCoord().getIdxLigne()][piece.getCoord().getIdxColonne()].getNomComplet());

            listeDeplacement.append(" en " +  piece.getCoord().coordonneesReverse() + " peut se déplacer en : \n");
            
            for (Coordonnees dest : piece.getListeDestination())
                listeDeplacement.append(dest.coordonneesReverse() + "\n");  
        }
        return listeDeplacement.toString();
    }

    /**
     * Permet de connaitre l'état de la partie
     * @return Retourne true si la partie est terminée, false dans le cas contraire.
     */
    public boolean isPartieTerminee()
    {
        return new FinPartie(couleurJoueur, listeTousDeplacements, echiquier).isPartieTerminee();
    }

    // -------------------------------------------------- METHODES RELATIVES A L'AFFICHAGE DU PLATEAU ---------------------------------------------- 

    /**
     * Affichage de l'échiquier avec l'ensemble des pièces.
     * Est utilisé lors de l'affichage du plateau lors du tour du joueur Blanc.
     * @return Retourne un String contenant l'affichage.
     */
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append("\n"+ affichageBandeau());   //Bandeau supérieur
        
        str.append(affichageBordure());  //Bordure

        for (int nomLigne = tailleLigne; nomLigne > 0; nomLigne--)  //Ligne du tableau
        {
            str.append(nomLigne);    //Contenu de la ligne
            int idxLigne = tailleLigne - nomLigne;
            for (int idxColonne = 0; idxColonne < tailleColonne; idxColonne++) 
            {
                str.append(" | ");
                if (echiquier[idxLigne][idxColonne] != null)
                    str.append(echiquier[idxLigne][idxColonne].getNom());

                else   
                    str.append(" ");
            }
            str.append(" | " + nomLigne + "\n");

            str.append(affichageBordure());   //Bordure
        }
        str.append(affichageBandeau());   //Bandeau inférieur

        return str.toString();
    }

    /**
     * Bandeau supérieur de l'affichage. Contient les lettres des colonnes.
     * @return Retourne l'affichage sous le format String
     */
    private String affichageBandeau()
    {
        StringBuilder str = new StringBuilder();

        str.append("   ");        
        for (char c = 'a'; c <= (char)(tailleColonne + 96); c++)
            str.append(" " + c + "  ");
        str.append("   \n");

        return str.toString();
    }  

    /**
     * Bordure du tableau.
     * @return Retourne les bordures sous le format String
     */
    private String affichageBordure()
    {
        StringBuilder str = new StringBuilder();

        str.append("   ");
        for (int idxColonne = 0; idxColonne < tailleColonne; idxColonne++)
            str.append("--- ");    
        str.append("   \n");

        return str.toString();
    }

    /**
     * Bandeau supérieur de l'affichage. Contient les lettres des colonnes. L'affichage est inversé par rapport au bandeau classique.
     * @return Retourne l'affichage sous le format String
     */
    private String affichageBandeauInverse()
    {
        StringBuilder str = new StringBuilder();

        str.append("   ");        
        for (char c = (char)(tailleColonne + 96); c >= 'a'; c--)
            str.append(" " + c + "  ");
        str.append("   \n");

        return str.toString();
    }  

    /**
     * Affichage de l'échiquier avec l'ensemble des pièces. L'affichage est inversé par rapport au toString classique.
     * Est utilisé lors de l'affichage du plateau lors du tour du joueur Noir.
     * @return Retourne un String contenant l'affichage.
     */
    public String toStringInverse()
    {
        StringBuilder str = new StringBuilder();

        str.append("\n"+ affichageBandeauInverse());   //Bandeau supérieur
        
        str.append(affichageBordure());  //Bordure

        for (int nomLigne = 1; nomLigne < tailleLigne + 1; nomLigne++)  //Ligne du tableau
        {
            str.append(nomLigne);    //Contenu de la ligne
            int idxLigne = tailleLigne - nomLigne;
            for (int idxColonne = tailleColonne - 1; idxColonne >= 0; idxColonne--) 
            {
                str.append(" | ");
                if (echiquier[idxLigne][idxColonne] != null)
                    str.append(echiquier[idxLigne][idxColonne].getNom());
                else   
                    str.append(" ");
            }
            str.append(" | " + nomLigne + "\n");

            str.append(affichageBordure());   //Bordure
        }
        str.append(affichageBandeauInverse());   //Bandeau inférieur

        return str.toString();   
    }
}