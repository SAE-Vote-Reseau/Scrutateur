package vote.Scrutateur.Commandes;

import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Scrutateur;
import vote.Urne.metier.Sondage;
import vote.crypto.KeyInfo;

import java.util.HashMap;

public class CommandeVoirSondage extends Commande {
    public CommandeVoirSondage(Scrutateur scrutateur){
        super(scrutateur);
    }

    @Override
    public void executer() throws ExecutionFailedException {
        HashMap<Sondage, KeyInfo> s = getScrutateur().getSondages();

        s.forEach((k,v) -> {
            System.out.println(k.toString() + " pk-data:" + v.toString());
        });
    }
}
