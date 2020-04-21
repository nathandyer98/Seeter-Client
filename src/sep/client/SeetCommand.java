package sep.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

public class SeetCommand {

    static String draftTopic = null;
    static List<String> draftLines = new LinkedList<>();

    public void exit() {
        System.exit(0);
    }

    public void fetch(String[] args) throws IOException, ClassNotFoundException {
        CLFormatter.chan.send(new SeetsReq(args[0]));
        SeetsReply rep = (SeetsReply) CLFormatter.chan.receive();
        System.out.print(CLFormatter.formatFetched(args[0], rep.users, rep.lines));
    }

    public void compose(String[] args) {
        draftTopic = args[0];
    }

    public void body(String[] args) {
        String line = Arrays.stream(args).collect(Collectors.joining());
        if (line.isEmpty()){}
        else{
        draftLines.add(line);}
    }

    public void send(String user) throws IOException {
        CLFormatter.chan.send(new Publish(user, draftTopic, draftLines));
        draftTopic = null;
        draftLines.clear();
    }  
}
