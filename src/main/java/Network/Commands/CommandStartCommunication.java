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
        try {
            String key = "";
            for (int i = 1; i < values.length; i++) {
                key += "/" + values[i];
            }
            key = key.substring(1,key.length());
            Cipher.setClientPublicKey(rClient.getConnectionID(), Base64.getDecoder().decode(key));
            System.out.println("Přijat klíč: " + key);

        } catch (Exception e) {
            logger.error("Chyba přijetí pub klíče u klienta", e);
        }


        try {

            return "SETCOMMUNICATION/"+ Cipher.getEncryptedAESKey(rClient.getConnectionID())+";";

        } catch (Exception e) {
            logger.error("Nepovedlo se encryptovat AES klíč:", e);
            return"";
        }

    }

    @Override
    public String getName() {
        return NAME;
    }

}
