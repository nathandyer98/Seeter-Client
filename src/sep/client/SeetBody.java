package sep.client;

/**
 *  The SeetBody Class
 * This class is used the encapsulate the Body command of the Seeter App.
 *
 * @author Nathan
 */
public class SeetBody implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String[] args;
    
    /**
     * The SeetBody command constructor
     * 
     * @param args parses the User's arguments to be stored inside this class, to then be used within the execute command.
     */
    public SeetBody(String[] args){
        this.args = args;
    }
    
    /**
     * Executes the Body command found inside the SeetCommand Class, whilst parsing the required parameters. 
     */
    @Override
    public void execute() {
        this.theSeet.body(args);
    }
    
}