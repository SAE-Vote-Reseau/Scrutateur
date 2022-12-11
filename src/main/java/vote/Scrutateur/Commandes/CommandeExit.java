package vote.Scrutateur.Commandes;

import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Commandes.Exceptions.ParsingException;
import vote.Scrutateur.Scrutateur;

public class CommandeExit extends Commande {
    public CommandeExit(Scrutateur scrutateur){
        super(scrutateur);
    }

    @Override
    public void executer() throws ExecutionFailedException {
        getScrutateur().fermerScrutateur();
        try {
            getScrutateur().join();
        }
        catch (InterruptedException e) {
            throw new ExecutionFailedException(e.toString());
        }

        System.exit(0);
    }
}
