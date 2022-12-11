package vote.Scrutateur;
import vote.Urne.RequeteScrutateur;
import vote.Urne.Sondage;
import vote.crypto.ElGamal;
import vote.crypto.KeyInfo;
import vote.crypto.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class Scrutateur extends Thread{
    private ServerSocket scrutateurSocket;
    private HashMap<Sondage,KeyInfo> sondages;
    private volatile boolean signalArret;

    public Scrutateur(int port) throws IOException {
        scrutateurSocket = new ServerSocket(port);
        scrutateurSocket.setSoTimeout(500);
        signalArret = false;
        sondages = new HashMap<>();
    }


    public KeyInfo addSondage(Sondage s,int nbBits){ //ajoute un sondage en cours,stock sa clé privé et retourne la clé publique
        if (sondages.containsKey(s)){
            System.out.println("Sondage deja connu");
            return null;
        }
        System.out.println("Ajout du sondage dans la liste");

        KeyInfo[] keys = ElGamal.keyGen(nbBits);
        sondages.put(s,keys[0]);
        return keys[1];
    }

    public HashMap<Sondage,KeyInfo> getSondages(){
        return sondages;
    }

    public void fermerScrutateur(){
        signalArret = true;
    }

    public void gererConnexion(Socket socket){
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            RequeteScrutateur input = (RequeteScrutateur) in.readObject();

            input.repondre(this,out);

        } catch (IOException e) {
            System.out.println("Error while read bytes: " + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public int essayerDechiffrer(Message m, Sondage s, int nbParticipant){
        if(!sondages.containsKey(s)){
            System.out.println("Sondage inconnu");
            return -2; //code d'erreur qui signifie que le sondage n'existe pas
        }
        int resultat = ElGamal.decrypt(m,sondages.remove(s),nbParticipant);
        System.out.println("envoie de " + resultat);
        return resultat;
    }

    @Override
    public void run(){
        System.out.println("Serveur en ecoute");
        while (!signalArret) {
            Socket socket = null;
            try {
                socket = scrutateurSocket.accept();
                System.out.println("une urne s'est connecté");
                gererConnexion(socket);
                socket.close();
            } catch (SocketTimeoutException e) { //Ca permet d'eviter qu'on demande la fermeture du serveur et que le thread reste bloqué sur le accept()
            } catch (IOException e) {
                System.out.println("Error while accepting connection :" + e);
            }
        }
    }

}
