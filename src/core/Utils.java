package core;

import java.io.*;
import java.nio.file.*;

public class Utils {
    public static int nPartsCalc(File f, long dim){
        int lenght = (int) f.length();

        //Calcola il numero di parti
        long partNum = lenght / dim;
        if (partNum * dim < lenght) partNum++;

        return (int) partNum;
    }

    public static long dimCalc(File f, int nParts){
        long lenght = f.length();

        // Calcola la dimensione di una parte
        long dim = lenght / nParts;
        if (dim * nParts != lenght) dim++;

        return dim;
    }

    public static String getDirectory(File f){
        Path path = Paths.get(f.getAbsolutePath());
        return path.getParent().toString();
    }

    public static String getOnlyName(File f){
        String path = f.getAbsolutePath();
        if (path.contains(".")) path = path.substring(0, path.lastIndexOf('.'));
        return path;
    }

    public static Boolean checkFileParts(File f){
        try {
            // Rimuovo l'ultimo catattere
            String partPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().length()-1);
            FileInputStream i = new FileInputStream(partPath + "0");
            byte[] metadataBytes = new byte[8];
            i.read(metadataBytes);
            String metadata = new String(metadataBytes);
            String toCheck = metadata.substring(3);
            int partNum = Integer.parseInt(metadata.substring(3,6));
            for(int j=1; j<partNum; j++){
                FileInputStream p = new FileInputStream(partPath + j);
                p.read(metadataBytes);
                String partMetadata = new String(metadataBytes);
                if (partMetadata.equals(String.format("%03d", j) + toCheck)){ }
                else {
                    throw new Exception();
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean checkKeyFile(File f){
        String name = getOnlyName(f);
        String keyFileName = name + ".key.PROTECTME";
        File key = new File(keyFileName);
        return key.exists();
    }
}
