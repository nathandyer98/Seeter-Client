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
import java.util.stream.Collectors;

/**
 *
 * @author Nathan
 */
public final class SeetController {

    private SeetModel sModel;
    private String user;

    private String input;
    private String cmd;
    private String args[] = null;

    private SeetCommand Seet;
    private seetExit exit;
    private seetFetch fetch;
    private seetCompose compose;
    private seetBody body;
    private seetSend send;

    public SeetController(String user) throws IOException {
        this.user = user;

        sModel = new SeetModel(AppState.MAIN);
    }

    public void Process(BufferedReader reader) throws IOException {
        do {
            try {
                stateHandle(sModel.getState());
                processInput(reader);
                stateSwitch(sModel.getState(), this.cmd);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Could not parse command/args");
                sModel.setState(AppState.MAIN);
            }
        } while (sModel.getState() != AppState.EXIT);
    }

    public String getInput(BufferedReader read) throws IOException {
        String rawInput = read.readLine();
        if (rawInput == null) {
            throw new IOException("Input stream closed while reading");
        }
        return rawInput;
    }

    public String getCommand(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        String command = split.remove(0);
        return command;
    }

    public String[] getArgs(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        split.remove(0);
        String[] rawargs = split.toArray(new String[split.size()]);
        return rawargs;
    }

    public void processInput(BufferedReader read) throws IOException {
        try {
            this.input = this.getInput(read);
            this.cmd = this.getCommand(input);
            this.args = this.getArgs(input);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Something Went Wrong");
        }
    }

    public boolean argsErrCheck(String string) {
        return string == null;
    }

    public void inputErrPrint() {
        System.out.println("Could not parse command/args");
    }

    public void stateHandle(AppState state) throws IOException {
        switch (state) {
            case MAIN:
                System.out.println(CLFormatter.formatMainMenuPrompt());
                break;
            case DRAFTING:
                System.out.println(CLFormatter.formatDraftingMenuPrompt(SeetCommand.draftTopic, SeetCommand.draftLines));
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }

    public void stateSwitch(AppState state, String cmd) {
        switch (state) {
            case MAIN:
                if ("compose".startsWith(cmd)) {
                    sModel.setState(AppState.DRAFTING);
                    seetInvoker doCompose = new seetInvoker(compose = new seetCompose(args));
                    doCompose.execute();
                    break;
                } else if ("fetch".startsWith(cmd)) {
                    seetInvoker doFetch = new seetInvoker(fetch = new seetFetch(args));
                    doFetch.execute();
                    break;
                } else if ("exit".startsWith(cmd)) {
                    seetInvoker doExit = new seetInvoker(exit = new seetExit());
                    sModel.setState(AppState.EXIT);
                    doExit.execute();
                    break;
                } else {
                    inputErrPrint();
                    break;
                }

            case DRAFTING:
                if ("body".startsWith(cmd)) {
                    seetInvoker doBody = new seetInvoker(body = new seetBody(this.args));
                    doBody.execute();
                    break;
                } else if ("send".startsWith(cmd)) {
                    seetInvoker doSend = new seetInvoker(send = new seetSend(user));
                    doSend.execute();
                    sModel.setState(AppState.MAIN);
                    SeetCommand.draftTopic = null;
                    break;
                } else if ("exit".startsWith(cmd)) {
                    seetInvoker doExit = new seetInvoker(exit = new seetExit());
                    sModel.setState(AppState.EXIT);
                    doExit.execute();
                    break;
                } else {
                    inputErrPrint();
                    break;
                }
            default:
                System.out.println("Could not parse command/args.");
                break;
        }
    }
    /*
    public void mainHandle(String cmd) {
        switch (cmd) {
            case "compose":
                sModel.setState(AppState.DRAFTING);
                seetInvoker doCompose = new seetInvoker(compose = new seetCompose(this.args));
                doCompose.execute();
                break;
            case "fetch":
                seetInvoker doFetch = new seetInvoker(fetch = new seetFetch(this.args));
                doFetch.execute();
                break;
            case "exit":
                seetInvoker doExit = new seetInvoker(exit = new seetExit());
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
                seetInvoker doBody = new seetInvoker(body = new seetBody(this.args));
                doBody.execute();
                break;
            case "send":
                seetInvoker doSend = new seetInvoker(send = new seetSend(user));
                doSend.execute();
                sModel.setState(AppState.MAIN);
                SeetCommand.draftTopic = null;
                break;
            case "exit":
                seetInvoker doExit = new seetInvoker(exit = new seetExit());
                doExit.execute();
                break;
            default:
                System.out.println("Could not parse command/args.");
                break;
        }
    }*/
}
