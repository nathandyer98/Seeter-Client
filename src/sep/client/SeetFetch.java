  package sep.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  The SeetFetch Class
 * This class is used the encapsulate the Fetch command of the Seeter App.
 *
 * @author Nathan
 */
public class SeetFetch implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String[] args;
    
    /**
     * The SeetFetch command constructor
     * 
     * @param args parses the User's arguments to be stored inside this class, to then be used within the execute command.
     */
    public SeetFetch(String[] args){
        this.args = args;
    }
    
 /**
     * Executes the Fetch command found inside the SeetCommand Class, whilst parsing the required parameters. 
     */
    @Override
    public void execute() {
        try {
            this.theSeet.fetch(args);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SeetFetch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
