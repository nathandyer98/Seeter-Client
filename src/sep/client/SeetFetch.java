  package sep.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class SeetFetch implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String[] args;
    
    public SeetFetch(String[] args){
        this.args = args;
    }
    
    @Override
    public void execute() {
        try {
            this.theSeet.fetch(args);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SeetFetch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}