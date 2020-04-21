package sep.client;

/**
 *
 * @author Nathan
 */
public class SeetCompose implements iCommand{
    
    SeetCommand theSeet = new SeetCommand();
    private String[] args;
    
    public SeetCompose(String[] args){
        this.args = args;
    }

    @Override
    public void execute() {
        this.theSeet.compose(args);
    }
    
}