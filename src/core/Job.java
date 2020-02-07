package core;


import java.io.*;

public class Job {

    private Splitter splitter;
    private Reassembler reassembler;
    private String file;
    private int type;  // 0 splitter 1 compressSplitter 2 crypoSplitter 3 ressembler 4 compressReassemble 5 cryptoReassembler
    private int status = 0;

    public Job(Splitter s){
        splitter = s;
        type = 0;
        file = s.getFile();
        if (s instanceof CompressSplitter) type=1;
        else if (s instanceof CypterSplitter) type=2;
    }

    public Job(Reassembler r){
        reassembler = r;
        type = 3;
        file = r.getFile();
        if (r instanceof CompressReassembler) type=4;
        else if (r instanceof CypterReassembler) type=5;
    }

    public int getStatus(){ return status; }
    public int getType(){
        return type;
    }
    public String getFile() { return file; }

    public int updateStatus(){
        if (splitter != null) status = splitter.getStatus();
        else if (reassembler != null) status = reassembler.getStatus();
        return status;
    }

    public void execute() {
        // TODO Thread!
        try {
            if (splitter != null) splitter.split();
            else if (reassembler != null) reassembler.reassemble();
        }catch (Exception e) {e.printStackTrace();}
        //TODO ritornarla
    }

}
