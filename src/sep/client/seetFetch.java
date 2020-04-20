  package sep.client;

/**
 *
 * @author Nathan
 */
public class seetFetch implements iCommand{

    SeetCommand theSeet;
    private String topic;
    
    public seetFetch(SeetCommand seeter, String topic){
        this.theSeet = seeter;
        this.topic = topic;
        
    }
    @Override
    public void execute() {
        this.theSeet.fetch(topic);
    }
    
}
