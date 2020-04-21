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
public class SeetInvoker {
    
    private final iCommand theCommand;

    public SeetInvoker(iCommand newCommand){
        
        this.theCommand = newCommand;
    }
    
    public void execute(){
        
        this.theCommand.execute();
    }
}

