package vote.Scrutateur;



import vote.Urne.RequeteGetResults;

import java.awt.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

public class Serveur {

    public static void main(String[] args) {
        MessageDebut();
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                String choix = sc.nextLine();

                if(choix.equals("getResults")) {
                    GetResults();
                }
                else if (choix.equals("exit")){
                    System.exit(0);
                }
                else if (choix.equals("keyGen")){

                }
                else{
                    out.println("erreur de vote");
                }

                out.close();


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
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
        out.println("\u001B[31mBienvenue dans le système du scrutateur !\u001B[0m");
        out.println("\"\u001B[31mgetResults\u001B[0m\" : obtenir les résultats du vote");
        out.println("\"\u001B[31mexit\u001B[0m\" : quitter le système");
    }
    public static void GetResults() throws IOException, ClassNotFoundException {
            Socket socket = new Socket("127.0.0.1", 5565);
            java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(socket.getOutputStream());
        RequeteGetResults req = new RequeteGetResults();
            out.writeObject(req);
            out.flush();
            System.out.println("Demande de résultats envoyée");

            java.io.ObjectInputStream in = new java.io.ObjectInputStream(socket.getInputStream());

            String[] Results = (String[]) in.readObject();
            System.out.println("Résultats reçus");
            PrintResults(Results);
            in.close();
            out.close();
    }

    public static void PrintResults(String[] Results){
        System.out.println("Résultats du vote : "+ "\"" +Results[0]+"\"");
        System.out.println( Results[1]+" : " + Results[3]);
        System.out.println(Results[2]+" : " + Results[4]);
    }
}
