package entreeSortie;

/**
 * Utilisation de code ANSI pour ajouter de la couleur dans l'affichage console.
 */
public class Couleur {
    private static String vert = "\u001B[32m";
    private static String bleu = "\u001B[36m";
    private static String jaune = "\u001B[33m";
    private static String stopCouleur = "\u001B[0m";

    private Couleur ()
    {}
    
    public static String getBleu() {
        return bleu;
    }

    public static String getJaune() {
        return jaune;
    }

    public static String getStopCouleur() {
        return stopCouleur;
    }

    public static String getVert() {
        return vert;
    }
}
