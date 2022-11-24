package vote.Scrutateur.Commandes;

import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Commandes.Exceptions.ParsingException;
import vote.Scrutateur.Scrutateur;

public class CommandeRegenerer extends Commande{
    private int nbBits;

    public CommandeRegenerer(Scrutateur scrutateur, String rawCommand) throws ParsingException{
        super(scrutateur);
        this.nbBits = parse(rawCommand);
    }

    private int parse(String rawCommand) throws ParsingException {
        String[] parts = rawCommand.split(" ");
        if (parts.length < 2){
            throw new ParsingException("Pas assez d'arguments");
        }
        return Integer.parseInt(parts[1]);
    }

    @Override
    public void executer() throws ExecutionFailedException {
        getScrutateur().regenererKey(nbBits);
    }
}
