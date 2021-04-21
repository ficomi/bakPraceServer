/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Commands;

import Network.Matchmaking.Matchmaking;
import Network.Client.RegistredClients;
import Network.Client.RunningClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.logging.Level;


/**
 * Tato třída reprezentuje co se stane když příjde příkaz SSEARCH.
 * Přidá klienta do fronty klirntů čekající na hru.
 *
 * @author Miloslav Fico
 */
public class CommandStartEncryption implements ICommands {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String NAME = StringCommands.STARTENCRYPT.toString().toUpperCase();

    @Override
    public String doCommand(RunningClient rClient, RegistredClients regClients, PrintWriter writer, BufferedReader reader, Matchmaking matchmaking, String[] values) {

        if (values[1].equals("1")) {
            rClient.setEncrypted(true);
            logger.debug("Komunikce začne být šifrována");
            return "OK";
        }
        return"NOK";

    }


    @Override
    public String getName() {
        return NAME;
    }
}



