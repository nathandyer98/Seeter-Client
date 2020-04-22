/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.client;

/**
 *  The SeetModel Class
 * This class is used to store the Seeter's state throughout runtime, allowing to be accessed and mutated.
 *
 * @author Nathan
 */
public class SeetModel {
    
    private AppState state;
    
    /**
     *  The SeetModel Constructor
     *
     * @param state parses the Enumeration AppState to be stored as a state inside this class.
     */
    public SeetModel(AppState state){
        this.state = state;
    }
    
    /**
     *  The accessor method for the SeetModel Class
     *
     * @return the AppState of the SeetModel object
     */
    public AppState getState(){
        return state;
    }
    
    /**
     * The mutator method for the SeetModel Class
     * 
     * @param state parses the AppState, setting the state for the SeetModel object.
     */
    public void setState(AppState state){
        this.state = state;
    }
}
