package Server.httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class RegisterHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "<h1>Server na odeslání pošty běží</h1>";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
