package pairing;

import java.util.ArrayList;
/**
 * Write a description of class Student here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Student
{
    // instance variables - replace the example below with your own
    private int ID;
    private int Advisor;
    private ArrayList<Integer> Readers;
    private int numReaders = 0;
    private int seq;

    /**
     * Constructor for objects of class Faculty, ID only
     */
    public Student(int ID, int Advisor, int seq){
        // initialise instance variables
        this.ID = ID;
        this.Advisor = Advisor;
        this.seq = seq;
        Readers = new ArrayList<Integer>();
    }
    
    public int getAdvisor(){
        return Advisor;
    }
    
    /**
     * Allows other classes to access the ID of this student
     */
    public int getID(){
        return ID;
    }
    
    public ArrayList<Integer> getReaders(){
        return Readers;
    }
    /**
     * A way to add students whose paper this reader should read
     */
    public void addReader(int x){
        Readers.add(x);
        numReaders++;
    }
    
    public int getNumReaders(){
        return numReaders;
    }
    
    public int getSeq(){
        return seq;
    }
}
