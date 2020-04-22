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
     *
     * @param user
     * @throws IOException
     */
    public SeetController(String user) throws IOException {
        this(new Locale("en", "GB"));
        this.user = user;
        sModel = new SeetModel(AppState.MAIN);
    }

    /**
     *
     * @param locale
     */
    public SeetController(Locale locale) {
        msg = ResourceBundle.getBundle(RESOURCE_PATH, locale);
    }

    /**
     *
     * @param reader
     * @throws IOException
     */
    public void process(BufferedReader reader) throws IOException {
        do {
            try {
                stateHandle(sModel.getState());
                processInput(reader);
                stateSwitch(sModel.getState(), this.cmd);
            } catch (ArrayIndexOutOfBoundsException e) {
                //System.out.println("Could not parse command/args");
                inputErrPrint();
                sModel.setState(AppState.MAIN);
            }
        } while (sModel.getState() != AppState.EXIT);
    }

    /**
     *
     * @param read
     * @return
     * @throws IOException
     */
    public String getInput(BufferedReader read) throws IOException {
        String rawInput = read.readLine();
        if (rawInput == null) {
            throw new IOException(msg.getString("msg_IOinputErr"));
        }
        return rawInput;
    }

    /**
     *
     * @param input
     * @return
     */
    public String getCommand(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        String command = split.remove(0);
        return command;
    }

    /**
     *
     * @param input
     * @return
     */
    public String[] getArgs(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        split.remove(0);
        String[] rawargs = split.toArray(new String[split.size()]);
        return rawargs;
    }
/*
    public String getText(String[] args) {
        String text = "";
        for(String txt: args){
            text = text + " ";
        }
        return text;
    }
*/
    
    /**
     *
     * @param read
     * @throws IOException
     */
    public void processInput(BufferedReader read) throws IOException {
        try {
            this.input = this.getInput(read);
            this.cmd = this.getCommand(input);
            this.args = this.getArgs(input);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(msg.getString("msg_expErr"));
        }
    }

    /**
     *
     */
    public void inputErrPrint() {
        System.out.println(msg.getString("msg_inputErr"));
        //
    }

    /**
     *
     * @param state
     * @throws IOException
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
     *
     * @param state
     * @param cmd
     */
    public void stateSwitch(AppState state, String cmd) {
        switch (state) {
            case MAIN:
                if ("compose".startsWith(cmd)) {
                    sModel.setState(AppState.DRAFTING);
                    SeetInvoker doCompose = new SeetInvoker(compose = new SeetCompose(args));
                    doCompose.execute();
                    break;
                } else if ("fetch".startsWith(cmd)) {
                    SeetInvoker doFetch = new SeetInvoker(fetch = new SeetFetch(args));
                    doFetch.execute();
                    break;
                } else if ("exit".startsWith(cmd)) {
                    SeetInvoker doExit = new SeetInvoker(exit = new SeetExit());
                    sModel.setState(AppState.EXIT);
                    doExit.execute();
                    break;
                } else {
                    inputErrPrint();
                    break;
                }

            case DRAFTING:
                if ("body".startsWith(cmd)) {
                    SeetInvoker doBody = new SeetInvoker(body = new SeetBody(this.args));
                    doBody.execute();
                    break;
                } else if ("send".startsWith(cmd)) {
                    SeetInvoker doSend = new SeetInvoker(send = new SeetSend(user));
                    doSend.execute();
                    sModel.setState(AppState.MAIN);
                    break;
                } else if ("exit".startsWith(cmd)) {
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
