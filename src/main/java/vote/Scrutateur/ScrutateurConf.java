package vote.Scrutateur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ScrutateurConf {

    private static ScrutateurConf instance = null;

    private int port;

    public int getPort() {
        return port;
    }

    public int getNbBits() {
        return nbBits;
    }

    private int nbBits;

    private ScrutateurConf(){
        try{
            File file = new File("./scrutateur.props");
            if (file.exists()) {
                System.out.println("file au répertoire : " + file.getAbsolutePath());
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                props.load(fis);

                port = Integer.parseInt(props.getProperty("port"));
                nbBits = Integer.parseInt(props.getProperty("nombreBitsDefaut"));

                if( port == 0){
                    throw new RuntimeException("Poprietes invalides");
                }

                fis.close();
            } else {
                file.createNewFile();
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file);
                props.load(fis);
                props.setProperty("port","A remplir");
                props.setProperty("nombreBitsDefaut","A remplir");

                props.store(fos,"PROPERTIES");
                fis.close();
                fos.close();
                throw new RuntimeException("Veuillez remplir le fichier de configuration scrutateur");
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-2);
        }
    }
    public static ScrutateurConf getInstance(){
        if (instance == null){
            instance = new ScrutateurConf();
        }
        return instance;
    }
}
