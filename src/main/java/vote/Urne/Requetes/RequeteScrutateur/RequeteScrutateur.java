package vote.Urne.Requetes.RequeteScrutateur;

import vote.Scrutateur.Scrutateur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class RequeteScrutateur implements Serializable {
    private static final long serialVersionUID = 1311779095806626001L;

    private String prefix;

    public RequeteScrutateur(String prefix){
        this.prefix = prefix;
    }

    public abstract void repondre(Scrutateur scrutateur, ObjectOutputStream out) throws IOException;
}
