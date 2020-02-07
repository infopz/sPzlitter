package core;

import java.io.*;
import java.util.Arrays;

import exception.*;


public class Reassembler {

    protected File file;

    final int BUFFER_SIZE = 4096;
    final int METADATA_LENGTH = 16;

    public Reassembler(File f) throws FileMetadataError {
        file = f;
        if (!Utils.checkFileParts(f)) {
            throw new FileMetadataError();
        }
    }

    public int getStatus() { return 0; }
    public String getFile() { return file.getAbsolutePath(); }

    public void reassemble() throws Exception{

        beforeReassemble();

        // Apre il file
        FileInputStream i = new FileInputStream(file);

        // Legge i metadati
        byte[] metadataBytes = new byte[METADATA_LENGTH];
        i.read(metadataBytes, 0, METADATA_LENGTH);
        int totalParts = Integer.parseInt(new String(Arrays.copyOfRange(metadataBytes,3,6)));

        // Chiude il file
        i.close();

        // Calcola la stringa fino a file.part
        String path = file.getAbsolutePath();
        String partsPath = path.substring(0, path.length() - 1);

        // Apre il nuovo file
        File out = new File(Utils.getOnlyName(file));
        FileOutputStream o = new FileOutputStream(out);

        // Per ogni parte, la apre, legge i dati, li rimette a posto e la chiude
        for(int j=0; j<totalParts; j++){
            File part = new File(partsPath + j);
            i = new FileInputStream(part);

            // Remove metadata
            i.skip(METADATA_LENGTH);

            reassembleBuffers(part, i, o);

            i.close();
        }
        o.close();
    }

    protected void reassembleBuffers(File part, FileInputStream i, FileOutputStream o) throws Exception{

        // Calcola la lunghezza dei byte da leggere
        long partDimension =  part.length() - METADATA_LENGTH;

        // Prende i byte a blocchi di BUFFER_SIZE alla volta e li scrive sulla destinazione
        for (long bytesRemaned = partDimension; bytesRemaned > 0; bytesRemaned = bytesRemaned - BUFFER_SIZE) {
            int currentRead = BUFFER_SIZE;
            if (bytesRemaned < BUFFER_SIZE) currentRead = (int) bytesRemaned;

            byte[] data = new byte[currentRead];
            i.read(data, 0, currentRead);
            data = processBytes(data);
            o.write(data, 0, currentRead);
        }

    }

    protected void beforeReassemble() throws Exception {}
    protected byte[] processBytes(byte[] b) throws Exception{ return b; }
}
