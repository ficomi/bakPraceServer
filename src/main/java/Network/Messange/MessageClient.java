/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network.Messange;


import Security.Cipher;

import java.io.PrintWriter;

/**
 * Tato třída reprezentuje chatujícího klienta.
 * @author Miloslav Fico
 */
public class MessageClient {
   
    PrintWriter writer;
    String ConnectionID;

    public MessageClient(PrintWriter writer, String ConnectionID) {

        this.ConnectionID = ConnectionID;
        this.writer = writer;
    }

  

    public PrintWriter getWriter() {
        return writer;
    } 
    
    public void sentMessage(String message) throws Exception{
     writer.println(Cipher.encrypt("RECMSG/"+message+";",ConnectionID));
     writer.flush();
    }
}
