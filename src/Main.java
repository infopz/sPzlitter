import core.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args){
        try{
            File f = new File("/Users/infopz/Documents/IdeaProject/sPzlitter/Output/4.pdf");
            Splitter s = new CypterSplitter(f);
            s.setnParts(6);
            s.split();

            Reassembler r = new CypterReassembler(new File("/Users/infopz/Documents/IdeaProject/sPzlitter/Output/4.pdf.part0"));
            r.reassemble();

            //Queue<Splitter> queue = new LinkedList<Splitter>();


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
