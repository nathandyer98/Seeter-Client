/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.client;

/**
 *
 * @author Nathan
 */
public class SeetModel {
    
    private AppState state;
    
    /**
     *
     * @param state
     */
    public SeetModel(AppState state){
        this.state = state;
    }
    
    /**
     *
     * @return
     */
    public AppState getState(){
        return state;
    }
    
    /**
     *
     * @param state
     */
    public void setState(AppState state){
        this.state = state;
    }
}
