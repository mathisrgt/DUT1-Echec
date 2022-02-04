package partie.plateau;

/**
 * Coordonnées utilisée pour mémoriser l'emplacement des pièces sur l'échiquier.
 */
public class Coordonnees {
    private int idxLigne;
    private int idxColonne;

    // ----------------------------------------------- CONSTRUCTEURS ------------------------------------------------

    /**
     * Initialise les coordonnées à partir d'un index de ligne et de colonne.
     * @param idxLigne Index de la ligne.
     * @param idxColonne Index de la colonne.
     */
    public Coordonnees (int idxLigne, int idxColonne)
    {
        this.idxLigne = idxLigne;
        this.idxColonne = idxColonne;
    }

    /**
     * Initialise les coordonnées à partir d'une autre coordonnée sous le format String.
     * @param position Coordonnée source au format String.
     */
    public Coordonnees(String position)
    {
        this.idxLigne = convertIdxLigne(position.charAt(1));
        this.idxColonne = convertIdxColonne(position.charAt(0));
    }
    
    // ----------------------------------------------- GETTER ------------------------------------------------

    public int getIdxColonne() {
        return idxColonne;
    }

    public int getIdxLigne() {
        return idxLigne;
    }
    // ----------------------------------------------- SETTER ------------------------------------------------

    /**
     * Mise à jour des coordonnées à partir d'un autre coordonnée.
     * @param coord Coordonnée utilisée pour la mise à jour.
     */
    public void setNouvellesCoordonnees(Coordonnees coord)
    {
        this.idxLigne = coord.idxLigne;
        this.idxColonne = coord.idxColonne;
    }

    // ------------------------------------------ METHODES PUBLIQUES -----------------------------------------

    /**
     * Permet d'obtenir un String contenant les coordonnées selon le format utilisé dans un échiquier.
     * @return Retour un String contenant les coordonnées sous le nouveau format.
     */
    public String coordonneesReverse()
    {
        return (idxColonneReverse(idxColonne) + idxLigneReverse(idxLigne));
    }

    /**
     * Vérifie une égalité de valeur entre deux coordonnées.
     * @param obj Objet que l'on souhaite comparer.
     */
    @Override
    public boolean equals(Object obj) 
    {
        // test sur les références
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        // test sur les classes
        if (this.getClass() != obj.getClass ())
            return false;
        // test sur les données
        Coordonnees other = (Coordonnees) obj;
        return (this.idxLigne == other.idxLigne && this.idxColonne == other.idxColonne);
    }

    // -------------------------------------------- CONVERSION -------------------------------------------

    /**
     * Permet d'obtenir l'index d'une ligne à partir d'un char.
     * @param numLigne Numéro de ligne affiché sur le plateau de jeu.
     * @return Retourne un int contenant l'index de la ligne.
     */
    private int convertIdxLigne(char numLigne)
    {
        return Plateau.getTailleLigne() - Character.getNumericValue(numLigne);
    }

    /**
     * Permet d'obtenir l'index d'une colonne à partir d'un char.
     * @param lettreColonne Lettre associée à une colonne visible sur plateau de jeu.
     * @return Retourne un int contenant l'index de la colonne.
     */
    private int convertIdxColonne(char lettreColonne)
    {
        return (int)lettreColonne - 97;
    }

    /**
     * Permet d'obtenir la lettre associée à une colonne telle qu'elle est affichée sur le plateau de jeu à partir de l'index de la colonne.
     * @param idxColonne Index de la colonne en int
     * @return Retourne un String contenant la lettre associée à la colonne. 
     */
    private String idxColonneReverse(int idxColonne)
    {
        return Character.toString((char)(idxColonne + 97)) ;
    }

    /**
     * Permet d'obtenir le numéro de ligne telle qu'il est affiché sur le plateau de jeu à partir de l'index de la ligne.
     * @param idxColonne Index de la ligne en int
     * @return Retourne un String contenant la lettre associée à la ligne. 
     */
    private String idxLigneReverse(int idxLigne)
    {
        int numLigne = Plateau.getTailleLigne() - idxLigne;
        return Integer.toString(numLigne);
    }


}
