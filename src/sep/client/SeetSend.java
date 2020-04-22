package sep.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class SeetSend implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String user;
    
    /**
     *
     * @param user
     */
    public SeetSend(String user){
        this.user = user;
    }
    
    /**
     *
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
