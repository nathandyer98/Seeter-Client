package sep.client;

/**
 *  The SeetCompose Class
 * This class is used the encapsulate the Compose command of the Seeter App.
 * 
 * @author Nathan
 */
public class SeetCompose implements iCommand{
    
    SeetCommand theSeet = new SeetCommand();
    private String[] args;
    
   /**
     * The SeetCompose command constructor
     * 
     * @param args parses the User's arguments to be stored inside this class, to then be used within the execute command.
     */
    public SeetCompose(String[] args){
        this.args = args;
    }

    /**
     * Executes the Compose command found inside the SeetCommand Class, whilst parsing the required parameters. 
     */
    @Override
    public void execute() {
        this.theSeet.compose(args);
    }
    
}