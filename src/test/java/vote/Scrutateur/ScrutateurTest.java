package vote.Scrutateur;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;

import vote.Urne.Requetes.RequeteScrutateur.RequeteScrutateur;
import vote.Urne.metier.Sondage;
import vote.crypto.ElGamal;
import vote.crypto.KeyInfo;
import vote.crypto.Message;

public class ScrutateurTest {

    private Scrutateur scru;

    @BeforeEach
    void init() throws IOException{
        scru = new Scrutateur(6566,200);
    }

    @Test
    void essayerDechiffrerErreur(){
        Sondage s = new Sondage("cc", "c", "c", new KeyInfo(null, null, null, null));
        Message me = new Message(null, null);

        assertEquals(-2, scru.essayerDechiffrer(me, s, 0)); 
    }

    @Test
    void essayerDechiffrer() throws NoSuchAlgorithmException{
        Sondage s = new Sondage("cc", "c", "c", null);
        KeyInfo pk = scru.addSondage(s);

        Message m = ElGamal.encrypt(BigInteger.valueOf(1), pk).getM();

        assertEquals(1, scru.essayerDechiffrer(m, s, 1)); 
    }
}
