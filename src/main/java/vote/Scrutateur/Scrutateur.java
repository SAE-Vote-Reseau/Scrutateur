package vote.Scrutateur;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

public class Scrutateur {
    public static void main(String[] args) {


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
                else{
                    out.println("erreur de vote");
                }

                out.close();


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void GetResults() throws IOException, ClassNotFoundException {
            Socket socket = new Socket("127.0.0.1", 5565);
            java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(socket.getOutputStream());
            out.writeUTF("getResults");
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
