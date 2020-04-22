package sep.client;

/**
 *  The SeetExit Class
 * This class is used the encapsulate the Exit command of the Seeter App.
 *
 * @author Nathan
 */
public class SeetExit implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    
    /**
     * Executes the Exit command found inside the SeetCommand Class. 
     */
    @Override
    public void execute() {
        theSeet.exit();
    }
    
}