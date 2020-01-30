import core.*;

import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args){
        try{
            File f = new File("/Users/infopz/Documents/IdeaProject/sPzlitter/Output/foto.jpeg");
            Splitter s = new CompressSplitter(f);
            s.setnParts(2);
            s.split();

            Reassembler r = new CompressReassembler(new File("/Users/infopz/Documents/IdeaProject/sPzlitter/Output/foto.jpeg.part0"));
            r.reassemble();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
