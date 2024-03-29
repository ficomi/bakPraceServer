/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Commands;

import Network.Matchmaking.Matchmaking;
import Network.Client.RegistredClients;
import Network.Client.RunningClient;
import Security.Cipher;
import Security.SettingsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Base64;

/**
 * Tato třída reprezentuje co se stane když příjde příkaz STARTCOM Oveřuje
 * jestli data které server přijal odpovídají registrovanému klientovi.
 *
 * @author Miloslav Fico
 */
public class CommandStartCommunication implements ICommands {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String NAME = StringCommands.STARTCOM.toString().toUpperCase();

    @Override
    public String doCommand(RunningClient rClient, RegistredClients regClients, PrintWriter writer, BufferedReader reader, Matchmaking matchmaking, String[] values) {
        return "SETCOMMUNICATION/"+ SettingsUtil.getAsymAlgorithm() +"/"+SettingsUtil.getAsymKeySize()+"/"+SettingsUtil.getSymAlgorithm();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
