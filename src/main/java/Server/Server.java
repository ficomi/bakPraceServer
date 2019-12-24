package Server;

import Server.httpServer.HTTPServer;

public class Server {


    public Server(Boolean includeHTTPServer) {
        startServer(includeHTTPServer);
    }

    private void startServer(Boolean includeHTTPServer) {
        if (includeHTTPServer) {
            Thread httpServer = new Thread(new HTTPServer());
            httpServer.run();
        }
    }

}
