package core;

import java.util.*;

public class JobList {

    private ArrayList<Job> jobs = new ArrayList<>();

    public void addJob(Job j){
        jobs.add(j);
    }

    public void removeJob(int index){
        jobs.remove(index);
    }

    public ArrayList<Job> getData(){
        return jobs;
    }

}
