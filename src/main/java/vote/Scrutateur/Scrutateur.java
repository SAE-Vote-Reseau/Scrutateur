package vote.Scrutateur;
import vote.Urne.RequeteScrutateur;
import vote.crypto.ElGamal;
import vote.crypto.KeyInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Scrutateur extends Thread{
    private ServerSocket scrutateurSocket;
    private KeyInfo privateKeyInfo;
    private KeyInfo publicKeyInfo;
    private volatile boolean signalArret;

    public Scrutateur(int port, int nbBits) throws IOException {
        scrutateurSocket = new ServerSocket(port);
        scrutateurSocket.setSoTimeout(500);
        signalArret = false;
        regenererKey(nbBits);
    }

    public void regenererKey(int nbBits){
        System.out.println("Generation de la clé");
        KeyInfo[] keys = ElGamal.keyGen(nbBits);
        privateKeyInfo = keys[0];
        publicKeyInfo = keys[1];
        System.out.println("Clé genéré");
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


    public KeyInfo getPrivateKeyInfo() {
        return privateKeyInfo;
    }

    public KeyInfo getPublicKeyInfo() {
        return publicKeyInfo;
    }

}
