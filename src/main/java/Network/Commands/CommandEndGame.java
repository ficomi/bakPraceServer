/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Commands;

import Database.Entities.Client;
import Network.Client.GameClient;
import Network.Matchmaking.Matchmaking;
import Network.Client.RegistredClients;
import Network.Client.RunningClient;
import Network.Messange.MessageClient;
import Security.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;


/**
 * Tato třída reprezentuje co se stane když příjde příkaz EGAME. Přidelí ELO
 * podle výsledku hry a ukončí thred na kterém se klienti mezi soubou
 * domlouvaly.
 *
 * @author Miloslav Fico
 */
public class CommandEndGame implements ICommands {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String NAME = StringCommands.EGAME.toString().toUpperCase();

    @Override
    public String doCommand(RunningClient rClient, RegistredClients regClients, PrintWriter writer, BufferedReader reader, Matchmaking matchmaking, String[] values) {


        // Pridani/odebrani ela a update
        Client[] clients = new Client[2];
        for (int i = 1; i <= clients.length; i++) {
            clients[i - 1] = regClients.getClientFromRegClientsById(regClients.getIdByName(values[i]));
            if (i == 1) {
                clients[i - 1].setElo(regClients.getClientFromRegClientsById(regClients.getIdByName(values[i])).getElo() + 1);
            } else {
                clients[i - 1].setElo(regClients.getClientFromRegClientsById(regClients.getIdByName(values[i])).getElo() - 1);
            }
        }
        regClients.updateClientsAfterGame(clients);


        // Ukonceni spojeni a odeslani vysledku clientum
        GameClient[] players = new GameClient[2];
        try {


            for (int i = 0; i < players.length; i++) {
                players[i] = matchmaking.getPlayingClientByName(values[i + 1]);
                if (players[i] != null) {


                    System.out.println("Koncová zpráva pro "+values[i + 1]+" "+"UPDATE/" + regClients.getClientFromRegClientsById(regClients.getIdByName(values[i + 1])).getElo() + ";");
                    players[i].getWriter().println(Cipher.encrypt("UPDATE/" + regClients.getClientFromRegClientsById(regClients.getIdByName(values[i + 1])).getElo() + ";",matchmaking.getPlayingClientByName(values[i + 1]).getConnectionID()));
                    players[i].getWriter().flush();


                    //matchmaking.getMesClients().addToMessageClients(matchmaking.getPlayingClientByName(values[i + 1]).getConnectionID(), new MessageClient(matchmaking.getPlayingClientByName(values[i + 1]).getWriter(),matchmaking.getPlayingClientByName(values[i + 1]).getConnectionID()));
                    matchmaking.getMesClients().addToMessageClients(matchmaking.getPlayingClientByName(values[i + 1]).getConnectionID(), new MessageClient(players[i].getWriter(),players[i].getConnectionID()));
                    matchmaking.removePlayingClient(values[i + 1]);
                    logger.debug("Úspěšně ukončena hra pro :" + players[i].getName());
                } else {
                    logger.debug("Neco se stalo");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }

    @Override
    public String getName() {
        return NAME;
    }

}
