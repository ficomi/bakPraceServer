/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Messange;

import Network.Client.RunningClient;
import Network.Matchmaking.Matchmaking;

import java.util.HashMap;


/**
 * V této tříde jsou uloženy všichni chatující klienti.
 * @author Miloslav Fico
 */
public class MessageClients {

    private HashMap<String, MessageClient> messageClients;
    private Matchmaking matchmaking;

    public MessageClients(Matchmaking matchmaking) {
        messageClients = new HashMap<>();
        this.matchmaking = matchmaking;

    }

    public synchronized void addToMessageClients(String connectionID, MessageClient client) {

        if (!messageClients.containsKey(connectionID)) {
            messageClients.put(connectionID, client);
        }

    }

    public  synchronized MessageClient getMessegeClient(String name) {
        if (messageClients.containsKey(name)) {
            return messageClients.get(name);
        }
        return null;
    }

    public  synchronized void removeMessageClient(String ConnectionID) {
        messageClients.remove(ConnectionID);

    }

    public  synchronized void sentToAllMessageClients(String message){
        try {
            for (String name : messageClients.keySet()) {
                messageClients.get(name).sentMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
}
