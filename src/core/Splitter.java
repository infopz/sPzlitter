package core;

import java.io.*;

public class Splitter {

    protected File file;
    protected int dim;
    protected int nParts;

    final int BUFFER_SIZE = 4096;

    public Splitter(File f) {
        file = f;
    }

    public void setDim(int dim){
        this.dim = dim;
        nParts = Utils.nPartsCalc(file, dim);
    }

    public void setnParts(int nParts){
        this.nParts = nParts;
        dim = Utils.dimCalc(file, nParts);
    }

    public void split() throws Exception{

            beforeSplitting();

            // Apre il file sorgente
            String sourcePath = file.getAbsolutePath();
            FileInputStream i = new FileInputStream(sourcePath);

            // Ne calcola la dimensione e prepara il buffer
            int totalRemaning = (int) file.length();
            byte[] bytes;

            // Ogni giro scrive una parte diversa
            for (int j = 0; j < nParts; j++) {

                // Apre il file destinazione
                String destinationPath = new File(Utils.getDirectory(file), file.getName() + ".part" + j).getAbsolutePath();
                FileOutputStream o = new FileOutputStream(destinationPath);

                writeMetadata(o, j);

                // Calcola la dimensione di quella parte
                int destinationSize = dim;
                if (totalRemaning < dim) destinationSize = totalRemaning;

                System.out.println("\nPART SIZE: " + destinationSize);

                // A letture della lunghezza di BUFFER_SIZE, legge dal sorgente e scrive sulla destinazione
                for(int bytesRemaning = destinationSize; bytesRemaning>0; bytesRemaning = bytesRemaning - BUFFER_SIZE){
                    int readSize = BUFFER_SIZE;
                    if (bytesRemaning<BUFFER_SIZE) readSize = bytesRemaning;

                    System.out.println("BLOCK SIZE: " + bytesRemaning + " " + readSize);
                    bytes = new byte[readSize];
                    i.read(bytes, 0, readSize);
                    // Fra l'input e l'output chiama la funzione processBytes per zippare e/o cifrare
                    bytes = processBytes(bytes);
                    o.write(bytes, 0, bytes.length);
                }
                o.close();
                totalRemaning = totalRemaning - dim;
            }

            afterSplitting();

    }

    protected void beforeSplitting() throws Exception {}

    protected byte[] processBytes(byte[] b) throws Exception{ return b; }

    protected void afterSplitting() throws Exception {}

    protected void writeMetadata(FileOutputStream o, int currentPart) throws IOException {
            String metadata = String.format("%03d", currentPart) + String.format("%03d", nParts) + "00" + "000000";
            o.write(metadata.getBytes());
    }
}