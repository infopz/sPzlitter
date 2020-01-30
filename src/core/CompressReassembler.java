package core;

import java.io.*;
import java.util.Arrays;
import java.util.zip.Inflater;

public class CompressReassembler extends Reassembler {

    public CompressReassembler(File file) { super(file); }

    String[] bufferLength;
    int bufferLengthOffsett;
    int index = 0;

    @Override
    protected void beforeReassemble() {
        try {

            RandomAccessFile i = new RandomAccessFile(file, "r");
            i.seek(8);
            byte[] bufferStringLength = new byte[6];
            i.read(bufferStringLength,0,6);
            bufferLengthOffsett = Integer.parseInt(new String(bufferStringLength));

            i.seek(file.length()-bufferLengthOffsett);
            byte[] bufferLenghtByte = new byte[bufferLengthOffsett];
            i.read(bufferLenghtByte);
            i.close();
            bufferLength = new String(bufferLenghtByte).split(";");

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void reassembleBuffers(File part, FileInputStream i, FileOutputStream o) {
        try{
            int partDimension = (int) part.length() - METADATA_LENGTH;
            if (index==0) partDimension = partDimension-bufferLengthOffsett;

            System.out.println("\nPARTDIMENSION: "+partDimension);

            int currentBuffer = Integer.parseInt(bufferLength[index]);
            for(int bytesRemaned = partDimension; bytesRemaned>0;){
                bytesRemaned = bytesRemaned - currentBuffer;
                System.out.println("REMANED: " + bytesRemaned);
                byte[] compressedData = new byte[currentBuffer];
                i.read(compressedData,0, currentBuffer);
                byte[] data = processBytes(compressedData);
                o.write(data, 0, data.length);

                index++;
                if (index!=bufferLength.length) currentBuffer = Integer.parseInt(bufferLength[index]);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected byte[] processBytes(byte[] b) {
        try {

            Inflater decompresser = new Inflater();
            decompresser.setInput(b, 0, b.length);
            //FIXME: cambiare la dimensione
            byte[] result = new byte[BUFFER_SIZE*2];
            int resultLength = decompresser.inflate(result);
            decompresser.end();

            result = Arrays.copyOfRange(result, 0, resultLength);
            return result;
        } catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
