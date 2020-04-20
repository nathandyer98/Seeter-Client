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
public class SeetView {

    String draftTopic = null;
    List<String> draftLines = new LinkedList<>();

    public SeetView() {

    }


    public void printMainPrompt(CLFormatter format) {
        System.out.println(format.formatMainMenuPrompt());
    }

    public void printDraftPrompt(CLFormatter format) {
        System.out.println(format.formatDraftingMenuPrompt(draftTopic, draftLines));
    }
}
