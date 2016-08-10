package pairing;

import java.util.ArrayList;
public class Faculty
{
    // instance variables - replace the example below with your own
    private int ID;
    private ArrayList<Integer> Advisees;
    private ArrayList<Integer> Readings;
    private int numAdvisees;

    /**
     * Constructor for objects of class Faculty, ID only
     */
    public Faculty(int ID){
        // initialise instance variables
        this.ID = ID;
        Advisees = new ArrayList<Integer>();
        Readings = new ArrayList<Integer>();
    }

    /**
     * Add advisees to list of advisees
     */
    public Faculty addAdvisee(Student student)
    {
        Advisees.add(student.getID());
        numAdvisees++;
        return this;
    }
    
    /**
     *A function that returns True if faculty member is advisor/already reading the student's paper and
     *False if not
     */
    public boolean isAlreadyConnected(Student student){
        int x = student.getID();
        if(Advisees.indexOf(x) == -1 && Readings.indexOf(x)== -1){
            return false;
        }
        else{
            return true;
        }
    }
    
    /**
     * A way to add students whose paper this reader should read
     */
    public void addStudent(Student student){
        Readings.add(student.getID());
    }
    
    /**
     * A way to get the number of advisees of a particular faculty
     */
    public int getNumAdvisees(){
        return numAdvisees;
    }
    
    public ArrayList<Integer> getStudents(){
        return Readings;
    }
    
    public int getID(){
        return ID;
    }
    
    public String toString(){
        return "ID: " + ID;
    }
}
