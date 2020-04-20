/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sep.client;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.seeter.net.message.Bye;
import sep.seeter.net.message.Publish;
import sep.seeter.net.message.SeetsReply;
import sep.seeter.net.message.SeetsReq;

/**
 *
 * @author Nathan
 */
public class SeetController {

    private final SeetModel model;
    private final SeetView view;
    private AppState state;
    BufferedReader reader;

    public SeetController(SeetModel model, SeetView view, AppState state, BufferedReader reader) {
        this.model = model;
        this.view = view;
        this.state = state;
        this.reader = reader;
    }

    public String getInput(BufferedReader read) throws IOException {
        String input = read.readLine();
        if (input == null) {
            throw new IOException("Input stream closed while reading");
        }
        return input;
    }

    public String getCommand(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        String command = split.remove(0);
        return command;
    }

    public String[] getArgs(String input) {
        List<String> split = Arrays.stream(input.trim().split("\\ ")).map(x -> x.trim()).collect(Collectors.toList());
        String[] args = split.toArray(new String[split.size()]);
        return args;
    }
}
