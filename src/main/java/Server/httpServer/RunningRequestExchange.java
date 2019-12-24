package Server.httpServer;

import Server.httpServer.Email.Email;
import Server.httpServer.Email.EmailHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class RunningRequestExchange implements Runnable {
    HttpExchange exchange;

    public RunningRequestExchange(HttpExchange exchange) {
        this.exchange = exchange;
        exchange.getResponseHeaders().add("Access-control-allow-origin", "*");
    }

    @Override
    public void run() {
        try {
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                optionsExchange();
            } else if (exchange.getRequestMethod().equals("POST")) {
                postExchange();
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }


    private void postExchange() throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        System.out.println("POST z adresy: " + exchange.getRemoteAddress()+" a daty: "+query);

        EmailHandler emailHandler = new EmailHandler();
        emailHandler.parseData(query);
        emailHandler.sentEmail();


        exchange.sendResponseHeaders(204,-1);

    }

    private void optionsExchange() throws IOException {
        exchange.getResponseHeaders().add("Access-control-allow-headers", "content-type");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", " POST, OPTIONS");
        exchange.sendResponseHeaders(204, -1);
    }
}
