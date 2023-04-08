package vote.Urne.Requetes.RequeteScrutateur;

import vote.Scrutateur.Scrutateur;
import vote.Urne.metier.Sondage;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequeteLancerSondage extends RequeteScrutateur { // devrait extends Requete une fois qu'on aura un truc commun, je ne peux pas a cause de Repondre pour le moment
    private static final long serialVersionUID = 5591047666560502694L;
    private Sondage sondage;

    public RequeteLancerSondage(Sondage sondage, int nbBits){
        super("getKey");
        this.sondage = sondage;
    }

    @Override
    public void repondre(Scrutateur scrutateur, ObjectOutputStream out) throws IOException{
        out.writeObject(scrutateur.addSondage(sondage));
        out.flush();
    }
}
