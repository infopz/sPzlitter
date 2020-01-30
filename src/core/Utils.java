package core;

import java.io.*;
import java.nio.file.*;

public class Utils {
    public static int nPartsCalc(File f, int dim){
        int lenght = (int) f.length();

        //Calcola il numero di parti
        int partNum = lenght / dim;
        if (partNum * dim < lenght) partNum++;

        return partNum;
    }

    public static int dimCalc(File f, int nParts){
        int lenght = (int) f.length();

        // Calcola la dimensione di una parte
        double dim = (double)lenght / nParts;
        if ((int)dim * nParts != lenght) dim++;

        return (int)dim;
    }

    public static String getDirectory(File f){
        Path path = Paths.get(f.getAbsolutePath());
        return path.getParent().toString();
    }
}
