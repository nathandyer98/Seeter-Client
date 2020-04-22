package sep.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The Command Interface (iCommand)
 * This interface is used for commands to implement, ensuring that they are executable.
 * 
 * @author Nathan
 */
public interface iCommand {
    
    /**
     * An execute method for the invoker to call on the command.
     */
    public void execute();
    
}
