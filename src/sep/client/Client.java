package sep.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import sep.seeter.net.message.Bye;

/**
 * This class is an initial work-in-progress prototype for a command line Seeter
 * client. It has been rather hastily hacked together, as often the case with
 * early exploratory coding, and it is incomplete and buggy. However, it does
 * compile and run, and basic functionality, such as sending and fetching seets
 * to and from an instance of {@link sep.seeter.server.Server}, is working. Try
 * it!
 * <p>
 * The arguments required to run the client correspond to the
 * {@link #set(String, String, int)} method: a user name, and the host name and
 * port at which the server as listening.
 * <p>
 * You can compile and run this client using NetBeans (<i>e.g.</i>, Run
 * {@literal ->} Set Project Configuration {@literal ->} Customize...
 * {@literal ->} New...).
 * <p>
 * Assuming you have compiled using NetBeans (or another method), you can also
 * run Client from the command line.
 * <ul>
 * <li>
 * {@code C:\...\seeter>  java -cp build\classes Client userid localhost 8888}
 * </ul>
 * <p>
 * You will be significantly reworking and extending this client. Unlike the
 * base framework, you have mostly free reign to modify the client to meet the
 * specification as you see fit. (The base framework comprises the packages
 * {@link sep.seeter.server}, {@link sep.seeter.server}, {@link sep.seeter.server}
 * and {@link sep.seeter.server}, which you should not modify.) The only
 * constraints on your client are as follows.
 * <ul>
 * <li>
 * This class (<i>i.e.</i>, {@code Client}) must remain the main class for
 * running your client (<i>i.e.</i>, via the static
 * {@linkplain #main(String[]) main} method).
 * <li>
 * The {@linkplain Client#main(String[]) main} method must accept the same
 * arguments as currently, <i>i.e.</i>, user name, host name and port number.
 * <li>
 * Your client must continue to accept the specified commands on the standard
 * input stream ({@code System.in}), and output on the standard output stream
 * ({@code System.out}).
 * </ul>
 * <p>
 * You will likely find it helpful to generate the API documentation (if you're
 * not already reading this there!) for the provided classes other than this
 * class, which are stable. After importing the project into NetBeans, simply
 * right click the project in the Projects window and select Generate Javadoc.
 * By default, the output is written to the {@code dist/javadoc} directory.
 *
 * @see CLFormatter
 */
public class Client {

    private String user;
    private String host;
    private int port;

    private static final String RESOURCE_PATH = "resources/MessageBundle";
    private ResourceBundle msg;

    public Client(String user, String host, int port) {
        this(new Locale("en", "GB"));
        this.user = user;
        this.host = host;
        this.port = port;
    }

    public Client(Locale locale) {
        msg = ResourceBundle.getBundle(RESOURCE_PATH, locale);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client(args[0], args[1], Integer.parseInt(args[2]));
        client.run();
    }

// Run the client
    @SuppressFBWarnings(
            value = "DM_DEFAULT_ENCODING", justification = "When reading console, ignore default encoding warning")
    public void run() throws IOException, ClassNotFoundException {

        BufferedReader reader = null;
        CLFormatter helper = null;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            if (this.user.isEmpty() || this.host.isEmpty()) {
                throw new RuntimeException(msg.getString("msg_userhostErr"));
                //(msg.getString("msg_userhostErr"));
            }

            helper = new CLFormatter(this.host, this.port);
            System.out.print(CLFormatter.formatSplash(this.user));
            SeetController controller = new SeetController(this.user);
            controller.process(reader);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            reader.close();
            if (CLFormatter.chan.isOpen()) {
                // If the channel is open, send Bye and close
                CLFormatter.chan.send(new Bye());
                CLFormatter.chan.close();
            }
        }
    }
}
