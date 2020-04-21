package sep.client;

/**
 *
 * @author Nathan
 */
public class SeetExit implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    
    @Override
    public void execute() {
        theSeet.exit();
    }
    
}