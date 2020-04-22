/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 *  The SeetController Class
 * This class is the main controller of the Seeter App,
 * this class processes the User's input and turns them into commands and arguments,
 * it then handles the switch cases and if statements using the arguments.
 *
 * @author Nathan
 */
public class SeetController {

    private SeetModel sModel;
    private String user;

    private String input;
    private String cmd;
    private String args[] = null;

    private SeetExit exit;
    private SeetFetch fetch;
    private SeetCompose compose;
    private SeetBody body;
    private SeetSend send;

    private static final String RESOURCE_PATH = "resources/MessageBundle";
    private ResourceBundle msg;

    /**
     *  The SeetController constructor
     *
     * @param user parses the User's name to be stored as a field to be used within this classes methods.
     * @throws IOException used for handling Input/Output errors.
     */
    public SeetController(String user) throws IOException {
        this(new Locale("en", "GB"));
        this.user = user;
        sModel = new SeetModel(AppState.MAIN);
    }

    /**
     *
     * @param locale parses locale information of the resource bundle.
     */
    public SeetController(Locale locale) {
        msg = ResourceBundle.getBundle(RESOURCE_PATH, locale);
    }

    /**
     *  This is the process method
     * Which contains a do while loop, looping until the AppState is exit.
     *
     * @param reader parses the Buffered Reader, prompting the user for their input.
     * Which get processed and handled
     * @throws IOException used for handling Input/Output errors.
     */
    public void process(BufferedReader reader) throws IOException {
        do {
            try {
                stateHandle(sModel.getState());
                processUserInput(reader);
                stateSwitch(sModel.getState(), this.cmd);
            } catch (ArrayIndexOutOfBoundsException e) {
                //System.out.println("Could not parse command/args");
                inputErrPrint();
                sModel.setState(AppState.MAIN);
            }
        } while (sModel.getState() != AppState.EXIT);
    }

    /**
     *  The getInput method
     * process the user's input and returns a string
     *
     * @param read parses the Buffered Reader, prompting the user for their input.
     * @return a string interpreted as a user's input
     * @throws IOException used for handling Input/Output errors.
     */
    public String getInput(BufferedReader read) throws IOException {
        String rawInput = read.readLine();
        if (rawInput == null) {
            throw new IOException(msg.getString("msg_IOinputErr"));
        }
        return rawInput;
    }

    /**
     *  The getCommand method
     *
     * @param input parses the users input into command and arguments, and stores the command for later use.
     * @return a string that holds the users command.
     */
    public String getCommand(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        String command = split.remove(0);
        return command;
    }

    /**
     * The getArgs method
     *
     * @param input parses the users input into command and arguments, and stores the arguments for later use.
     * @return a String array that holds the users argument.
     */
    public String[] getArgs(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        split.remove(0);
        String[] rawargs = split.toArray(new String[split.size()]);
        return rawargs;
    }

    /**
     *  The processUserInput method
     * this method takes the user input as a whole and separates and stores the command and arguments. 
     *
     * @param read parses the users input into command and arguments, and stores them for later use.
     * @throws IOException used for handling Input/Output errors.
     */
    public void processUserInput(BufferedReader read) throws IOException {
        try {
            this.input = this.getInput(read);
            this.cmd = this.getCommand(input);
            this.args = this.getArgs(input);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(msg.getString("msg_expErr"));
        }
    }

    /**
     * The inputError method.
     * Prints input error message.
     */
    public void inputErrPrint() {
        System.out.println(msg.getString("msg_inputErr"));
        //
    }

    /**
     *  The StateHandle method
     * 
     *
     * @param state parses the Seeter's state and handles using a switch case.
     * @throws IOException used for handling Input/Output errors.
     */
    public void stateHandle(AppState state) throws IOException {
        switch (state) {
            case MAIN:
                System.out.println(CLFormatter.formatMainMenuPrompt());
                break;
            case DRAFTING:
                System.out.println(CLFormatter.formatDraftingMenuPrompt(SeetCommand.draftTopic, SeetCommand.draftLines));
                break;
            case EXIT:
                sModel.setState(AppState.EXIT);
                break;
            default:
                break;
        }
    }

    /**
     *  The stateSwtich method.
     *Has 2 switch cases, determining the state of the Seeter App and processing the users input
     * 
     * @param state parses the Seeter's state and handles using a switch case.
     * @param cmd parses the Clients command into a if statement, to select which if statement to execute.
     */
    public void stateSwitch(AppState state, String cmd) {
        switch (state) {
            case MAIN:
                if (msg.getString("cmd_compose").startsWith(cmd)) {
                    sModel.setState(AppState.DRAFTING);
                    SeetInvoker doCompose = new SeetInvoker(compose = new SeetCompose(args));
                    doCompose.execute();
                    break;
                } else if (msg.getString("cmd_fetch").startsWith(cmd)) {
                    SeetInvoker doFetch = new SeetInvoker(fetch = new SeetFetch(args));
                    doFetch.execute();
                    break;
                } else if (msg.getString("cmd_exit").startsWith(cmd)) {
                    SeetInvoker doExit = new SeetInvoker(exit = new SeetExit());
                    sModel.setState(AppState.EXIT);
                    doExit.execute();
                    break;
                } else {
                    inputErrPrint();
                    break;
                }

            case DRAFTING:
                if (msg.getString("cmd_body").startsWith(cmd)) {
                    SeetInvoker doBody = new SeetInvoker(body = new SeetBody(this.args));
                    doBody.execute();
                    break;
                } else if (msg.getString("cmd_send").startsWith(cmd)) {
                    SeetInvoker doSend = new SeetInvoker(send = new SeetSend(user));
                    doSend.execute();
                    sModel.setState(AppState.MAIN);
                    break;
                } else if (msg.getString("cmd_exit").startsWith(cmd)) {
                    SeetInvoker doExit = new SeetInvoker(exit = new SeetExit());
                    sModel.setState(AppState.EXIT);
                    doExit.execute();
                    break;
                } else {
                    inputErrPrint();
                    break;
                }
            default:
                inputErrPrint();
                break;
        }
    }
    /*
    public void mainHandle(String cmd) {
        switch (cmd) {
            case "compose":
                sModel.setState(AppState.DRAFTING);
                SeetInvoker doCompose = new SeetInvoker(compose = new SeetCompose(this.args));
                doCompose.execute();
                break;
            case "fetch":
                SeetInvoker doFetch = new SeetInvoker(fetch = new SeetFetch(this.args));
                doFetch.execute();
                break;
            case "exit":
                SeetInvoker doExit = new SeetInvoker(exit = new SeetExit());
                sModel.setState(AppState.EXIT);
                doExit.execute();
                break;
            default:
                System.out.println("Could not parse command/args.");
                break;
        }
    }

    public void draftHandle(String cmd) {
        switch (cmd) {
            case "body":
                SeetInvoker doBody = new SeetInvoker(body = new SeetBody(this.args));
                doBody.execute();
                break;
            case "send":
                SeetInvoker doSend = new SeetInvoker(send = new SeetSend(user));
                doSend.execute();
                sModel.setState(AppState.MAIN);
                SeetCommand.draftTopic = null;
                break;
            case "exit":
                SeetInvoker doExit = new SeetInvoker(exit = new SeetExit());
                doExit.execute();
                break;
            default:
                System.out.println("Could not parse command/args.");
                break;
        }
    }*/
}
