package pairing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
/**
 * Write a description of class Pair here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pairing
{
    public static void main(String[] args) throws IOException, BiffException, WriteException{
        //Sets up file for reading
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the full path of the file containing the list of students and faculty readers");
        String path = scanner.nextLine();
        System.out.println("Please enter the path of the folder you would like the output to be saved to");
        String output = scanner.nextLine() + "/output.xls";
        File file = new File(output);
        File testFile = new File(path); 
        Workbook w;
        //Creates two lists, one for all the faculty and one for all the students
        ArrayList<Faculty> All_Readers = new ArrayList<Faculty>();
        ArrayList<Student> Students = new ArrayList<Student>();
        ArrayList<Student> permanentStudents = new ArrayList<Student>();
        try {
           //Read in workbook
           w = Workbook.getWorkbook(testFile);
           //Get first sheet
           Sheet sheet1 = w.getSheet(0);
           //Get second sheet
           Sheet sheet2 = w.getSheet(1);
           All_Readers = addFaculty(sheet2);
           Students = addStudents(sheet1);
        }
        catch(BiffException e){
        }
        //Sort all faculty by ID
        Collections.sort(All_Readers, new CompareFacultyID());
        //Add all advisees each faculty member has
        for(Student student: Students){
            int index = findAdvisor(All_Readers, student.getAdvisor());
            if(index == -1){
            }
            else{               
                Faculty faculty = All_Readers.get(index);
                faculty = faculty.addAdvisee(student);
                All_Readers.set(index, faculty);
            }
        }
        //calculate the number of papers each faculty neads to read
        int numReads = (2*Students.size())/All_Readers.size() + 1;
        //sorts faculty based on most to least advisees
        Collections.sort(All_Readers, new Compare());
        Student potentialStudent;
        //loops through n times, where n is the most number of papers a faculty member would have to read
        for(int i = 0; i<numReads; i++){
            for(Faculty faculty : All_Readers){
                //checks to see if there are any more students left
                if(Students.isEmpty()){
                    break;
                }
                //finds a random student and checks to see if the faculty is the advisor or already reading this student's paper
                //If yes, find a new random student
                //If no, keep trying to find a new student
                potentialStudent = Students.get((int)(Math.random()*Students.size()));
                while(faculty.isAlreadyConnected(potentialStudent) == true){
                    potentialStudent = Students.get((int)(Math.random()*Students.size()));
                }
                faculty.addStudent(potentialStudent);
                potentialStudent.addReader(faculty.getID());
                if(potentialStudent.getNumReaders() == 2){
                    permanentStudents.add(Students.remove(Students.indexOf(potentialStudent)));
                }
            }
        }
        //Create excel file to print out resulting pairs
        WritableWorkbook pairs = Workbook.createWorkbook(file);
        WritableSheet pair = pairs.createSheet("pair", 0);
        //Create headers
        WritableCell facultyID = new Label (1,0,"Reader 1 ID");
        WritableCell studentID = new Label (0,0, "Student ID");
        try {
            pair.addCell(facultyID);
            pair.addCell(studentID);
            facultyID = new Label (2,0,"Reader 2 ID");
            pair.addCell(facultyID);
        } catch (WriteException ex) {
            Logger.getLogger(Pairing.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(permanentStudents, new StudentSequence());
        int numStudents = 1;
        //Print out the results, both from console and to Excel file
        for(Student student: permanentStudents){
            int numFaculty = 1;
            for(Integer integer : student.getReaders()){
                studentID = new Label(0, numStudents, Integer.toString(student.getID()));
                facultyID = new Label (numFaculty, numStudents, integer.toString());
                //System.out.println(.toString(false) + ": " + integer.toString());
                try {
                    pair.addCell(facultyID);
                    pair.addCell(studentID);
                } catch (WriteException ex) {
                    Logger.getLogger(Pairing.class.getName()).log(Level.SEVERE, null, ex);
                }
                numFaculty++;
            }
            numStudents++;
        }
        pairs.write();
        pairs.close();
    }
    
    //Finds the index of the advisor of a particular student
    //Takes as paramaters a sorted ArrayList of Faculty and the faculty's ID #
    public static int findAdvisor(ArrayList<Faculty> faculty, int facultyID){
        int lower_bound = 0;
        int upper_bound = faculty.size();
        int index;
        int tempFacultyID;
        while(upper_bound - lower_bound != 1){
            index = (lower_bound + upper_bound)/2;
            tempFacultyID = faculty.get(index).getID();
            if(tempFacultyID==facultyID){
                return index;
            }
            else if(tempFacultyID < facultyID){
                lower_bound = index;
            }
            else{
                upper_bound = index;
            }
        }
        return -1;
    }
    
    
    //Adds all Faculty to an ArrayList
    //needs an excel sheet (with faculty IDs in the first column, no header) and the number of faculty readers
    public static ArrayList<Faculty> addFaculty(Sheet sheet){
        ArrayList<Faculty> Faculty = new ArrayList<>();
        int k = sheet.getRows();
        for(int i = 1; i < k; i++){
            String tempID = sheet.getCell(0,i).getContents();
            if(tempID.equals("")){
                
            }
            else{
                int ID = Integer.parseInt(tempID);
                Faculty faculty = new Faculty(ID);
                Faculty.add(faculty);
            }
        }
        return Faculty;
    }
    
    //Adds all students to an arraylist
    //needs an excel sheet with a header and all students in the second column as well as the number of students
    public static ArrayList<Student> addStudents(Sheet sheet){
        ArrayList<Student> students = new ArrayList<>();
        int k = sheet.getRows();
        for(int i = 1; i < k; i++){
            String tempID = sheet.getCell(3,i).getContents();
            if(tempID.equals("")){
                
            }
            else{
                int ID = Integer.parseInt(tempID);
                int Advisor = Integer.parseInt(sheet.getCell(7, i).getContents());
                Student student = new Student(ID, Advisor, i);
                students.add(student);
            }
        }
        return students;
    }
}
