package sep.client;

/**
 *
 * @author Nathan
 */
public class seetCompose implements iCommand{
    
    SeetCommand theSeet = new SeetCommand();
    private String[] args;
    
    public seetCompose(String[] args){
        this.args = args;
    }

    @Override
    public void execute() {
        this.theSeet.compose(args);
    }
    
}