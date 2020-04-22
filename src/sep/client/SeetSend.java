package sep.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  The SeetSend Class
 * This class is used the encapsulate the Send command of the Seeter App.
 *
 * @author Nathan
 */
public class SeetSend implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String user;
    
    /**
     * The SeetBody command constructor
     * 
     * @param user parses the User's name to be stored inside this class, to then be used within the execute command.
     */
    public SeetSend(String user){
        this.user = user;
    }
    
  /**
     * Executes the Send command found inside the SeetCommand Class, whilst parsing the required parameters. 
     */
    @Override
    public void execute() {
        try {
            this.theSeet.send(user);
        } catch (IOException ex) {
            Logger.getLogger(SeetSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
