package sep.client;

/**
 *
 * @author Nathan
 */
public class seetBody implements iCommand{

    SeetCommand theSeet = new SeetCommand();
    private final String[] args;
    
    public seetBody(String[] args){
        this.args = args;
    }
    
    @Override
    public void execute() {
        this.theSeet.body(args);
    }
    
}