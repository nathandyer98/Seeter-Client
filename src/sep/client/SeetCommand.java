package sep.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
 *
 * @author Nathan
 */
public class SeetCommand {

    static String draftTopic = null;
    static List<String> draftLines = new LinkedList<>();

    /**
     *
     */
    public void exit() {
        System.exit(0);
    }

    /**
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void fetch(String[] args) throws IOException, ClassNotFoundException {
        CLFormatter.chan.send(new SeetsReq(args[0]));
        SeetsReply rep = (SeetsReply) CLFormatter.chan.receive();
        System.out.print(CLFormatter.formatFetched(args[0], rep.users, rep.lines));
    }

    /**
     *
     * @param args
     */
    public void compose(String[] args) {
        draftTopic = args[0];
    }

    /**
     *
     * @param args
     */
    public void body(String[] args) {
        String text = "";
        for (String txt : args) {
            text = text + txt + " " ;
        }
        if (text.isEmpty()) {
        } else {
            draftLines.add(text);
        }
    }

    /**
     *
     * @param user
     * @throws IOException
     */
    public void send(String user) throws IOException {
        if (draftLines.isEmpty()) {
        } else {
            CLFormatter.chan.send(new Publish(user, draftTopic, draftLines));
            draftTopic = null;
            draftLines.clear();
        }
    }
}

/*
    public String getDraftTopic(){
        return this.draftTopic;
    }
    
    public List<String> getDraftLines(){
        return this.getDraftLines();
    }
 */
