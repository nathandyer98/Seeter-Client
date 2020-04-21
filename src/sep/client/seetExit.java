package sep.client;

/**
 *
 * @author Nathan
 */
public class seetExit implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    
    @Override
    public void execute() {
        theSeet.exit();
    }
    
}