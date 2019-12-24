package Server.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;


public class EmailPageHandler implements HttpHandler {



    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // parse reques
       new Thread(new RunningRequestExchange(exchange)).run();
    }
}
