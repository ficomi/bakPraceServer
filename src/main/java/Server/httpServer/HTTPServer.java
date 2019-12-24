package Server.httpServer;

import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;


public class HTTPServer implements Runnable {

    private HttpServer httpServer;
    private boolean isRunning;


    public HTTPServer() {
        try {
            startHTTPServer();
        } catch (IOException e) {
           isRunning = false;
            System.out.println(e.getStackTrace());
        }


    }

    private void startHTTPServer() throws IOException {
        isRunning = true;
        httpServer = HttpServer.create(new InetSocketAddress(8081), 0);
        System.out.println("HTTP spuštěn na adrese: "+ httpServer.getAddress());
        httpServer.createContext("/", new RegisterHandler());
        httpServer.createContext("/sendEmail", new EmailPageHandler());
        httpServer.setExecutor(null);
        httpServer.start();
    }

    @Override
    public void run() {
        while (isRunning){

        }
        httpServer.stop(5);
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
