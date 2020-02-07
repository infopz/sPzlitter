package core;

import java.io.*;
import java.util.Arrays;
import java.util.zip.Inflater;

import exception.*;


public class CompressReassembler extends Reassembler {

    public CompressReassembler(File file) throws Exception { super(file); }

    String[] bufferLength;
    int bufferLengthOffsett;
    int index = 0;

    @Override
    protected void beforeReassemble() throws IOException{

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

    }

    @Override
    protected void reassembleBuffers(File part, FileInputStream i, FileOutputStream o) throws IOException, CompressException {

        // Calcola la dimensione della parte corrente
        long partDimension = part.length() - METADATA_LENGTH;
        if (index==0) partDimension = partDimension-bufferLengthOffsett;

        // Per ogni parte, fa tanti cicli per ogni buffer da leggere
        for(long bytesRemaned = partDimension; bytesRemaned>0;){
            // Calcola la dimensione del buffer corrente
            int currentBuffer = Integer.parseInt(bufferLength[index]);
            bytesRemaned = bytesRemaned - currentBuffer;

            // Legge, processa e scrive i dati di un buffer
            byte[] compressedData = new byte[currentBuffer];
            i.read(compressedData,0, currentBuffer);
            byte[] data = processBytes(compressedData);
            o.write(data, 0, data.length);
            index++;

        }

    }

    @Override
    protected byte[] processBytes(byte[] b) throws CompressException {

            // Inizializza il decompressore
            Inflater decompresser = new Inflater();
            decompresser.setInput(b, 0, b.length);

            // Decomprime i dati
            byte[] result = new byte[BUFFER_SIZE*2];
            int resultLength;
            try {
                resultLength = decompresser.inflate(result);
            } catch (Exception e) { throw new CompressException();}
            decompresser.end();

            // Rimette a posto la dimensione dell'array
            result = Arrays.copyOfRange(result, 0, resultLength);
            return result;

    }
}
