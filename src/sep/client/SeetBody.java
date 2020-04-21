package sep.client;

/**
 *
 * @author Nathan
 */
public class SeetBody implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String[] args;
    
    public SeetBody(String[] args){
        this.args = args;
    }
    
    @Override
    public void execute() {
        this.theSeet.body(args);
    }
    
}