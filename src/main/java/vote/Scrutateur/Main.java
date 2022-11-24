package vote.Scrutateur;



import vote.Scrutateur.Commandes.Commande;
import vote.Scrutateur.Commandes.CommandeExit;
import vote.Scrutateur.Commandes.CommandeRegenerer;
import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Commandes.Exceptions.ParsingException;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static Scrutateur scrutateur;

    public static Commande parse(String commandeBrut) throws ParsingException, ExecutionFailedException {
        int firstSpace = commandeBrut.indexOf(" ");
        String firstWord = commandeBrut;
        if (firstSpace != -1) {
            firstWord = commandeBrut.substring(0, firstSpace);
        }
        if(firstWord.length() != 0) {
            switch (firstWord) {
                case "exit":
                    return new CommandeExit(scrutateur);
                case "regenerateKeys":
                    return new CommandeRegenerer(scrutateur, commandeBrut);
            }
        }
        return null;
    }


    public static void main(String[] args) {
        MessageDebut();
        try {
            scrutateur = new Scrutateur(6656, 100); //Provisoire
            scrutateur.start();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        try {

            while (true) {
                Scanner sc = new Scanner(System.in);
                Commande cmd = parse(sc.nextLine());
                if(cmd != null){
                    cmd.executer();
                }
                else {
                    System.out.println("Commande inconnu");
                }
            }
        }
        catch (Exception e){
            System.out.println("Erreur: " + e.toString());
        }
    }

    public static void MessageDebut(){
        System.out.println("\u001B[36m  ____     _   _      _       ____      _  __    ____    U  ___ u   _       _      \n" +
                " / __\"| u |'| |'| U  /\"\\  uU |  _\"\\ u  |\"|/ /  U|  _\"\\ u  \\/\"_ \\/  |\"|     |\"|     \n" +
                "<\\___ \\/ /| |_| |\\ \\/ _ \\/  \\| |_) |/  | ' /   \\| |_) |/  | | | |U | | u U | | u   \n" +
                " u___) | U|  _  |u / ___ \\   |  _ <  U/| . \\\\u  |  __/.-,_| |_| | \\| |/__ \\| |/__  \n" +
                " |____/>> |_| |_| /_/   \\_\\  |_| \\_\\   |_|\\_\\   |_|    \\_)-\\___/   |_____| |_____| \n" +
                "  )(  (__)//   \\\\  \\\\    >>  //   \\\\_,-,>> \\\\,-.||>>_       \\\\     //  \\\\  //  \\\\  \n" +
                " (__)    (_\") (\"_)(__)  (__)(__)  (__)\\.)   (_/(__)__)     (__)   (_\")(\"_)(_\")(\"_) \u001B[0m");
        System.out.println("\u001B[31mBienvenue dans le système du scrutateur !\u001B[0m");
        System.out.println("\"\u001B[31mregenerateKeys [nbBits]\u001B[0m\" : regenerer la clé");
        System.out.println("\"\u001B[31mexit\u001B[0m\" : quitter le système");
    }

}
