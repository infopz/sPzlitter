package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import Exception.*;


public class CypterSplitter extends Splitter {

    private Key key;
    private Cipher cipher;
    private String buffersDimensions = "";

    public CypterSplitter(File f) {
        super(f);
    }

    @Override
    protected byte[] processBytes(byte[] b) throws CryptoException {
        try {
            // Cifra i byte e annota la dimesione in una stringa
            byte[] cipheredData = cipher.doFinal(b);
            buffersDimensions = buffersDimensions + cipheredData.length + ";";
            return cipheredData;
        } catch (Exception e){
            throw new CryptoException();
        }
    }

    @Override
    protected void beforeSplitting() throws CryptoException {
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
        }  catch (Exception e) {
            throw new CryptoException();
        }
    }

    @Override
    protected void afterSplitting() throws IOException{

        // Salva su un file la chiave di cifratura
        File keyFile = new File(Utils.getDirectory(file), file.getName() + ".key.PROTECTME");
        FileOutputStream o = new FileOutputStream(keyFile.getAbsolutePath());
        ObjectOutputStream os = new ObjectOutputStream(o);
        os.writeObject(key);
        os.close();
        o.close();
        // Apre il primo file e aggiunge in fondo i la stringa con la dimensione dei buffer
        File firstPart = new File(Utils.getDirectory(file), file.getName() + ".part0");
        String destinationPath = firstPart.getAbsolutePath();
        Files.write(Paths.get(destinationPath), buffersDimensions.getBytes(), StandardOpenOption.APPEND);
        // Apre nuovamente il file
        RandomAccessFile f = new RandomAccessFile(firstPart, "rw");
        f.seek(8);
        // Scrive il numero di byte della stringa con i buffer
        String bufferStringLength = String.format("%06d", buffersDimensions.getBytes().length);
        f.write(bufferStringLength.getBytes());
        f.close();

    }

    @Override
    protected void writeMetadata(FileOutputStream o, int currentPart) throws IOException {
        String metadata = String.format("%03d", currentPart) + String.format("%03d", nParts) + "01" + "000000";
        o.write(metadata.getBytes());
    }
}