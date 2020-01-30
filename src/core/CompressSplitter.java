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
        // Li restituisce
        byte[] data = Arrays.copyOfRange(output, 0, compressedDataLength);

        buffersDimensions = buffersDimensions + compressedDataLength + ";";

        //String metadataString = compressedDataLength + "pz";
        //byte[] metadata = metadataString.getBytes();
        //byte[] toWrite = new byte[data.length + metadata.length];
        //System.arraycopy(metadata, 0 , toWrite, 0, metadata.length);
        //System.arraycopy(data, 0, toWrite, metadata.length, data.length);
        return data;
    }

    @Override
    protected void afterSplitting(){
        try {
            File firstPart = new File(Utils.getDirectory(file), file.getName() + ".part0");
            String destinationPath = firstPart.getAbsolutePath();
            Files.write(Paths.get(destinationPath), buffersDimensions.getBytes(), StandardOpenOption.APPEND);

            RandomAccessFile f = new RandomAccessFile(firstPart, "rw");
            f.seek(8);

            String bufferStringLength = String.format("%06d", buffersDimensions.getBytes().length);
            f.write(bufferStringLength.getBytes());
            f.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void writeMetadata(FileOutputStream o, int currentPart) {
        try {
            String metadata = String.format("%03d", currentPart) + String.format("%03d", nParts) + "10" + "000000";
            o.write(metadata.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
