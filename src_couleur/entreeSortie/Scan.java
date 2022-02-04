package entreeSortie;

import java.util.Scanner;

/**
 * Permet l'utilisation de l'outil scanner, autorisant l'utilisateur à saisir une entrée.
 */
public class Scan 
{
    private Scanner scanner;

    public Scan ()
    {
        scanner = new Scanner(System.in);
    }

    public void closeScanner ()
    {
        scanner.close();
    }

    public String saisirClavier(int nbrChar)
    {
        while (true)
        {
            System.out.print("\n> "); 
            String str = scanner.nextLine();
            if (str.length() == nbrChar)
                return str;
            System.out.println("Saisie invalide");
        }
    }
}
