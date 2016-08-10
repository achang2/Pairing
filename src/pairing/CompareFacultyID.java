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
public class CompareFacultyID implements Comparator<Faculty> {

    @Override
    public int compare(Faculty t, Faculty t1) {
        return t.getID() - t1.getID();
    }
    
}
