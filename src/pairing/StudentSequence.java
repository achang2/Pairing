/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pairing;

import java.util.Comparator;

/**
 *
 * @author Arthur
 */
public class StudentSequence implements Comparator<Student>{

    @Override
    public int compare(Student t, Student t1) {
        return t.getSeq() - t1.getSeq();
    }
    
}
