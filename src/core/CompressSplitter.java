package core;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.zip.*;

public class CompressSplitter extends Splitter {

    private String buffersDimensions = "";

    public CompressSplitter(File f) { super(f); }

    @Override
    protected byte[] processBytes(byte[] b) {

        // Prepara il buffer
        byte[] output = new byte[BUFFER_SIZE*2];
        // Crea l'oggetto compressore e lo imposta
        Deflater compresser = new Deflater();
        compresser.setInput(b);
        compresser.finish();
        // Comprime i byte
        int compressedDataLength = compresser.deflate(output);
        compresser.end();
        // Restringe la dimensione dell'arrya
        byte[] data = Arrays.copyOfRange(output, 0, compressedDataLength);
        // Memorizza la dimensione del buffer compresso
        buffersDimensions = buffersDimensions + compressedDataLength + ";";

        return data;
    }

    @Override
    protected void afterSplitting() throws IOException{

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
    protected void writeMetadata(FileOutputStream o, int currentPart) throws IOException{
        String metadata = String.format("%03d", currentPart) + String.format("%03d", nParts) + "10" + "000000";
        o.write(metadata.getBytes());

    }
}
