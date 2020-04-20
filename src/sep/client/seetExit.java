package sep.client;

/**
 *
 * @author Nathan
 */
public class seetExit implements iCommand{

    SeetCommand app;
    
    public seetExit(SeetCommand newApp){
        
        this.app = newApp;
    }
    @Override
    public void execute() {
        app.exit();
    }
    
}