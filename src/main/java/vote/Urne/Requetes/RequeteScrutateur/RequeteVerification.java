package vote.Urne.Requetes.RequeteScrutateur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import vote.crypto.KeyInfo;

import vote.Scrutateur.Scrutateur;
import vote.crypto.ElGamal;
import vote.crypto.VerifiedMessage;

public class RequeteVerification extends RequeteScrutateur {

    private VerifiedMessage message;
    private KeyInfo publicKeyInfo;
    private static final long serialVersionUID = 1311514095806626001L;


    public RequeteVerification(VerifiedMessage message, KeyInfo publicKeyInfo) {
        super("check");

        this.message = message;
        this.publicKeyInfo = publicKeyInfo;
        
    }

    @Override
    public void repondre(Scrutateur scrutateur, ObjectOutputStream out) throws IOException {
        try{
            if(ElGamal.proofIsValid(message, publicKeyInfo)){
                out.writeObject(new String("valid"));
            }
            else {
                out.writeObject(new String("invalid"));
            }
            out.flush();
        }catch(NoSuchAlgorithmException e){
            System.out.println("Error with algorithm, aborting:" + e.getMessage());
            out.writeObject(new String("error"));
            out.flush();
        }
    }
    
}
