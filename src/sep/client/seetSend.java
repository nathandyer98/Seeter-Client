package sep.client;

/**
 *
 * @author Nathan
 */
public class seetSend implements iCommand{

    SeetCommand theSeet;
    
    public seetSend(SeetCommand seeter){
        this.theSeet = seeter;
        
    }
    @Override
    public void execute() {
        this.theSeet.send();
    }
    
}
