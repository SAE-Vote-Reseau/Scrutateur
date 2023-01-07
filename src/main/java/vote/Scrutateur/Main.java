package vote.Scrutateur;



import vote.Scrutateur.Commandes.Commande;
import vote.Scrutateur.Commandes.CommandeChangerTaille;
import vote.Scrutateur.Commandes.CommandeExit;
import vote.Scrutateur.Commandes.CommandeVoirSondage;
import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Commandes.Exceptions.ParsingException;

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
                case "voir_sondage":
                    return new CommandeVoirSondage(scrutateur);
                case "changer_taille":
                    return new CommandeChangerTaille(scrutateur,commandeBrut);
            }
        }
        return null;
    }


    public static void main(String[] args) {
        MessageDebut();
        try {
            int port = ScrutateurConf.getInstance().getPort();
            int nbBits = ScrutateurConf.getInstance().getNbBits();

            scrutateur = new Scrutateur(port,nbBits); //Provisoire
            System.out.println("Taille minimum par defaut: " + nbBits + "\nConfiguré avec le port: " + port);
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
        System.out.println("\"\u001B[31mvoir_sondage\u001B[0m\" : le nom parle de lui meme");
        System.out.println("\"\u001B[31mchanger_taille [nbBits]\u001B[0m\" : change la taille des clés");
        System.out.println("\"\u001B[31mexit\u001B[0m\" : quitter le système");
    }

}
