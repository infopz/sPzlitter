package core;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.util.Base64.*;
import javax.crypto.*;
import javax.crypto.spec.*;


public class CypterSplitter extends Splitter {

    private Key key;
    private Cipher cipher;
    private String buffersDimensions = "";

    public CypterSplitter(File f) {
        super(f);
    }

    @Override
    protected byte[] processBytes(byte[] b) {
        try {

            // Cifra i byte e annota la dimesione in una stringa
            byte[] cipheredData = cipher.doFinal(b);
            buffersDimensions = buffersDimensions + cipheredData.length + ";";
            return cipheredData;

        } catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void beforeSplitting() {
        try {
            // Genera la chiave
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);  // Key size
            key = keyGen.generateKey();

            // Genera il vettore di inizializzazione
            IvParameterSpec iv = new IvParameterSpec(key.getEncoded());
            // Crea l'oggetto Cripante
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  // Transformation of the algorithm
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void afterSplitting(){
        try {

            // Salva su un file la chiave di cifratura
            File keyFile = new File(Utils.getDirectory(file), file.getName() + ".key.PROTECTME");
            FileOutputStream o = new FileOutputStream(keyFile.getAbsolutePath());
            ObjectOutputStream os = new ObjectOutputStream(o);

            os.writeObject(key);
            os.writeObject(buffersDimensions);

            os.close();
            o.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void writeMetadata(FileOutputStream o, int currentPart) {
        try {
            String metadata = String.format("%03d", currentPart) + String.format("%03d", nParts) + "01" + "000000";
            o.write(metadata.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}