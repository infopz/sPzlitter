package core;

import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import exception.*;


public class CypterReassembler extends Reassembler {

    private String[] bufferLength;
    private int bufferLengthOffsett;
    private int index = 0;
    private Cipher cipher;
    private File keyFile;

    //TODO: controllare file chiave

    public CypterReassembler(File f) throws Exception {
        super(f);
        keyFile = new File(Utils.getOnlyName(file)+".key.PROTECTME");
    }

    public void setKeyFile(File k){ keyFile = k; }

    @Override
    protected void beforeReassemble() throws IOException, CryptoException, ClassNotFoundException {

        // Apre la prima parte
        RandomAccessFile i = new RandomAccessFile(file, "r");
        i.seek(8);
        // Legge la lunghezza della stringa dei buffer
        byte[] bufferStringLength = new byte[8];
        i.read(bufferStringLength,0,8);
        bufferLengthOffsett = Integer.parseInt(new String(bufferStringLength));
        // Salta tutta la parte dei dati
        i.seek(file.length()-bufferLengthOffsett);
        // Legge la stringa dei buffer e la memorizza
        byte[] bufferLenghtByte = new byte[bufferLengthOffsett];
        i.read(bufferLenghtByte);
        i.close();
        bufferLength = new String(bufferLenghtByte).split(";");

        // Leggo la chiave dal file
        FileInputStream k = new FileInputStream(keyFile.getAbsolutePath());
        ObjectInputStream ks = new ObjectInputStream(k);
        Key key = (Key) ks.readObject();
        ks.close();
        k.close();

        // Imposto il decifratore
        IvParameterSpec iv = new IvParameterSpec(key.getEncoded());
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  // Transformation of the algorithm
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        } catch (Exception e) {throw new CryptoException();}


    }

    @Override
    protected void reassembleBuffers(File part, FileInputStream i, FileOutputStream o) throws IOException, CryptoException {

        // Calcola la dimensione della parte corrente
        long partDimension = part.length() - METADATA_LENGTH;
        if (index==0) partDimension = partDimension-bufferLengthOffsett;

        // Per ogni parte, fa tanti cicli per ogni buffer da leggere
        for(long bytesRemaned = partDimension; bytesRemaned>0;){
            // Calcola la dimensione del buffer corrente
            int currentBuffer = Integer.parseInt(bufferLength[index]);
            bytesRemaned = bytesRemaned - currentBuffer;
            System.out.println(bytesRemaned);
            // Legge, processa e scrive i dati di un buffer
            byte[] cipheredData = new byte[currentBuffer];
            i.read(cipheredData,0, currentBuffer);
            byte[] data = processBytes(cipheredData);
            o.write(data, 0, data.length);
            index++;
        }


    }

    @Override
    protected byte[] processBytes(byte[] b) throws CryptoException {
        try {
            return cipher.doFinal(b);
        } catch (Exception e){
            throw new CryptoException();
        }
    }
}
