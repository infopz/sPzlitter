package core;

import java.io.*;
import java.util.Arrays;

public class Reassembler {

    protected File file;

    final int BUFFER_SIZE = 4096;
    final int METADATA_LENGTH = 14;

    public Reassembler(File f){ file = f; }
    // TODO: Fare controlli, nel costruttore???

    public void reassemble(){
        try {

            beforeReassemble();

            // Apre il file
            FileInputStream i = new FileInputStream(file);

            // Legge i metadati
            byte[] metadataBytes = new byte[METADATA_LENGTH];
            i.read(metadataBytes, 0, METADATA_LENGTH);
            int totalParts = Integer.parseInt(new String(Arrays.copyOfRange(metadataBytes,3,6)));

            // Chiude il file
            i.close();

            String path = file.getAbsolutePath();
            String partsPath = path.substring(0, path.length() - 1);

            // TODO: Farlo ritornare al nome originale
            File out = new File(Utils.getDirectory(file), "output");
            FileOutputStream o = new FileOutputStream(out);

            for(int j=0; j<totalParts; j++){
                File part = new File(partsPath + j);
                i = new FileInputStream(part);

                // Remove metadata
                i.skip(METADATA_LENGTH);

                reassembleBuffers(part, i, o);

                i.close();
            }
            o.close();

            afterReassemble();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void reassembleBuffers(File part, FileInputStream i, FileOutputStream o){
        try {
            int partDimension = (int) part.length() - METADATA_LENGTH;
            for (int bytesRemaned = partDimension; bytesRemaned > 0; bytesRemaned = bytesRemaned - BUFFER_SIZE) {
                int currentRead = BUFFER_SIZE;
                if (bytesRemaned < BUFFER_SIZE) currentRead = bytesRemaned;

                byte[] data = new byte[currentRead];
                i.read(data, 0, currentRead);
                data = processBytes(data);
                o.write(data, 0, currentRead);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void beforeReassemble() {}
    protected byte[] processBytes(byte[] b) { return b; }
    protected void afterReassemble() {}
}
