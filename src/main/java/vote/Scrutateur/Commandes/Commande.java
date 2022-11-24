package vote.Scrutateur.Commandes;


import vote.Scrutateur.Commandes.Exceptions.ExecutionFailedException;
import vote.Scrutateur.Commandes.Exceptions.ParsingException;
import vote.Scrutateur.Scrutateur;

public abstract class Commande {
        private Scrutateur scrutateur;

        public Commande(Scrutateur scrutateur){
                this.scrutateur = scrutateur;
        }

        public Scrutateur getScrutateur() {
                return scrutateur;
        }

        public abstract void executer() throws ExecutionFailedException;


}
