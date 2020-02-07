import core.*;
import gui.MainFrame;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args){
        try{
            //File f = new File("/Users/infopz/Documents/IdeaProject/sPzlitter/Output/prova.txt");
            //Splitter s = new CypterSplitter(f);
            //s.setnParts(4);
            //s.split();

            //Reassembler r = new CypterReassembler(new File("/Users/infopz/Not_iCloud/StarWars_GliUltimiJedi.mkv.part0"));
            //r.reassemble();

            //Queue<Splitter> queue = new LinkedList<Splitter>();
            MainFrame fr = new MainFrame("sPzlitter");
            fr.setVisible(true);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
