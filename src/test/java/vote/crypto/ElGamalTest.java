package vote.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElGamalTest {

    private KeyInfo sk;
    private KeyInfo pk;

    @BeforeEach
    public void Init() {
        KeyInfo[] keys = ElGamal.keyGen(20);
        pk = keys[1];
        sk = keys[0];
    }

    @Test
    public void EncryptTestZero() throws NoSuchAlgorithmException {

        BigInteger m = new BigInteger("0");
        VerifiedMessage message = ElGamal.encrypt(m, pk);

        assertEquals(0, ElGamal.decrypt(message.getM(), sk, 1));
    }

    @Test
    public void EncryptTestUn() throws NoSuchAlgorithmException {

        BigInteger m = new BigInteger("1");
        VerifiedMessage message = ElGamal.encrypt(m, pk);

        assertEquals(1, ElGamal.decrypt(message.getM(), sk, 1));
    }

    @Test
    public void AgregationTest20() throws NoSuchAlgorithmException {
        BigInteger m = new BigInteger("1");
        VerifiedMessage message = ElGamal.encrypt(m, pk);
        Message ag = message.getM();

        for(int i = 0; i < 19; i++){
            BigInteger m2 = new BigInteger("1");
            VerifiedMessage message2 = ElGamal.encrypt(m2, pk);
            ag = ElGamal.Agreger(ag, message2.getM(), pk.getP());
        }

        assertEquals(20, ElGamal.decrypt(ag, sk, 20));
    }

    @Test
    public void AgregationTest200() throws NoSuchAlgorithmException {
        BigInteger m = new BigInteger("1");
        VerifiedMessage message = ElGamal.encrypt(m, pk);
        Message ag = message.getM();

        for(int i = 0; i < 199; i++){
            BigInteger m2 = new BigInteger("1");
            VerifiedMessage message2 = ElGamal.encrypt(m2, pk);
            ag = ElGamal.Agreger(ag, message2.getM(), pk.getP());
        }

        assertEquals(200, ElGamal.decrypt(ag, sk, 200));
    }

    @Test
    public void AgregationTest200and300Participant() throws NoSuchAlgorithmException {
        BigInteger m = new BigInteger("1");
        VerifiedMessage message = ElGamal.encrypt(m, pk);
        Message ag = message.getM();

        for(int i = 0; i < 199; i++){
            BigInteger m2 = new BigInteger("1");
            VerifiedMessage message2 = ElGamal.encrypt(m2, pk);
            ag = ElGamal.Agreger(ag, message2.getM(), pk.getP());
        }

        for(int i = 0; i < 100; i++){
            BigInteger m2 = new BigInteger("0");
            VerifiedMessage message2 = ElGamal.encrypt(m2, pk);
            ag = ElGamal.Agreger(ag, message2.getM(), pk.getP());
        }


        assertEquals(200, ElGamal.decrypt(ag, sk, 300));
    }

    @Test
    public void AgregationTestErrorMinus1() throws NoSuchAlgorithmException {
        BigInteger m = new BigInteger("1");
        VerifiedMessage message = ElGamal.encrypt(m, pk);
        Message ag = message.getM();

        for(int i = 0; i < 199; i++){
            BigInteger m2 = new BigInteger("1");
            VerifiedMessage message2 = ElGamal.encrypt(m2, pk);
            ag = ElGamal.Agreger(ag, message2.getM(), pk.getP());
        }

        for(int i = 0; i < 100; i++){
            BigInteger m2 = new BigInteger("0");
            VerifiedMessage message2 = ElGamal.encrypt(m2, pk);
            ag = ElGamal.Agreger(ag, message2.getM(), pk.getP());
        }


        assertEquals(-1, ElGamal.decrypt(ag, sk, 1));
    }

    @Test
    public void zpkOneTrue() throws NoSuchAlgorithmException {
        VerifiedMessage encrypted = ElGamal.encrypt(BigInteger.valueOf(1), pk);

        assertEquals(true, ElGamal.proofIsValid(encrypted, pk));
    }

    @Test
    public void zpkZeroTrue() throws NoSuchAlgorithmException {
        VerifiedMessage encrypted = ElGamal.encrypt(BigInteger.valueOf(0), pk);

        assertEquals(true, ElGamal.proofIsValid(encrypted, pk));
    }

    @Test
    public void zpkTenFalse() throws NoSuchAlgorithmException {
        VerifiedMessage encrypted = ElGamal.encrypt(BigInteger.valueOf(10), pk);

        assertEquals(false, ElGamal.proofIsValid(encrypted, pk));
    }

    @Test
    public void zpk100False() throws NoSuchAlgorithmException {
        VerifiedMessage encrypted = ElGamal.encrypt(BigInteger.valueOf(100), pk);

        assertEquals(false, ElGamal.proofIsValid(encrypted, pk));
    }

    @Test
    public void zpkMinus100False() throws NoSuchAlgorithmException {
        VerifiedMessage encrypted = ElGamal.encrypt(BigInteger.valueOf(-100), pk);

        assertEquals(false, ElGamal.proofIsValid(encrypted, pk));
    }
    
}
