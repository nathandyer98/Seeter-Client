/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.client;

/**
 *  The SeetInvoker Class
 * This class invokes any command that implements the command interface (iCommand), determining that command to be executable.
 *
 * @author Nathan
 */
public class SeetInvoker {
    
    private final iCommand theCommand;

    /**
     *  The SeetInvoker Constructor
     *
     * @param newCommand parses command interface (iCommand) objects through the use of polymorphism.
     */
    public SeetInvoker(iCommand newCommand){
        
        this.theCommand = newCommand;
    }
    
    /**
     * Executes the parsed command objects execute.
     */
    public void execute(){
        
        this.theCommand.execute();
    }
}

