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
 * The SeetCommand class, is a model of the Seeter App, which holds the main
 * method bodies of all the command, to be later called through the usage of
 * command pattern
 *
 * @author Nathan
 */
public class SeetCommand {

    static String draftTopic = null;
    static List<String> draftLines = new LinkedList<>();

    /**
     * The exit method
     *
     * Terminates the application when called.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * The fetch method
     *
     * @param args parses the user's arguments which gets handled by CLFormatter
     * and prints the output.
     * @throws IOException used for handling Input/Output errors.
     * @throws ClassNotFoundException used for handling class not found error.
     */
    public void fetch(String[] args) throws IOException, ClassNotFoundException {
        CLFormatter.chan.send(new SeetsReq(args[0]));
        SeetsReply rep = (SeetsReply) CLFormatter.chan.receive();
        System.out.print(CLFormatter.formatFetched(args[0], rep.users, rep.lines));
    }

    /**
     * The compose method
     *
     * @param args parses the user's arguments that assigns draftTopic.
     */
    public void compose(String[] args) {
        draftTopic = args[0];
    }

    /**
     * The body method
     *
     * @param args parses the user's argument as an Array, which is then split
     * into single words to be tied up into a string. The command also checks if
     * the string is empty before assigning draftLines, to prevent any issues at
     * runtime.
     */
    public void body(String[] args) {
        String text = "";
        for (String txt : args) {
            text = text + txt + " ";
        }
        if (text.isEmpty()) {
        } else {
            draftLines.add(text);
        }
    }

    /**
     * The send method
     *
     * @param user parses the user's name to be used by CLFormatter
     * @throws IOException used for handling Input/Output errors. The command
     * also checks whether daftLines is empty before executing the method body,
     * which also clears draftLines and sets draftTopic to null if ran
     * successfully.
     */
    public void send(String user) throws IOException {
        if (draftLines.isEmpty()) {
            //System.out.print("Nothing to send");
        } else {
            CLFormatter.chan.send(new Publish(user, draftTopic, draftLines));
            //System.out.print("Sendig....");
            draftLines.clear();
            draftTopic = null;

        }
    }
}
