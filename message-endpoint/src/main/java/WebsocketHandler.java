/*
WebsocketHandler class manages socket connections.
It adds and deleted connections from the list of connected clients.
 */

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WebsocketHandler {


    @OnWebSocketConnect
    public void handleConnect(Session session) throws Exception {
        Main.sessions.add(session);
        System.out.println("New Connection from " + session.getRemoteAddress());
        //session.getRemote().sendString("Connected");
        //sendNumbersToClient(session);
    }

    @OnWebSocketMessage
    public void handleMessage(Session session, String message) throws IOException {
        System.out.println("New Message Received: " + message);
        //session.getRemote().sendString(message);
        //sendNumbersToClient(session);
    }

    @OnWebSocketClose
    public void handleClose(Session session, int a, String s) {
        Main.sessions.remove(session);
        System.out.println("Connection from client " + session.getRemoteAddress() + " closed");
        //session.close();
    }

}
