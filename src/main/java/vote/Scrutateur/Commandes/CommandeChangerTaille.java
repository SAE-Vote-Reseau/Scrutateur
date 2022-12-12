package vote.Scrutateur.Commandes;

import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Commandes.Exceptions.ParsingException;
import vote.Scrutateur.Scrutateur;
import vote.Urne.metier.Sondage;
import vote.crypto.KeyInfo;

import java.util.HashMap;

public class CommandeChangerTaille extends Commande {
    private int nbBits;
    public CommandeChangerTaille(Scrutateur scrutateur,String command) throws ParsingException {
        super(scrutateur);
        this.nbBits = parseTaille(command);
    }

    private int parseTaille(String command) throws ParsingException {
        String[] parts = command.split(" ");
        try{
            return Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e){
            throw new ParsingException("Pas un nombre");
        }

    }

    @Override
    public void executer() throws ExecutionFailedException {
        getScrutateur().setNbBitsMin(nbBits);
        System.out.println("taille changé à " + nbBits);
    }
}
