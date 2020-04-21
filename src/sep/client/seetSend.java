package sep.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class seetSend implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String user;
    
    public seetSend(String user){
        this.user = user;
    }
    
    @Override
    public void execute() {
        try {
            this.theSeet.send(user);
        } catch (IOException ex) {
            Logger.getLogger(seetSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
