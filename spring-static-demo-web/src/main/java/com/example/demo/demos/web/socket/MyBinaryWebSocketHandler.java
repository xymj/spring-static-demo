package com.example.demo.demos.web.socket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;

public class MyBinaryWebSocketHandler extends BinaryWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Binary Connected: " + session.getId());
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        System.out.println("Received binary message of size: " + message.getPayload().array().length);

        // Echo the binary message back to the client
        ByteBuffer payload = message.getPayload();
        String s = new String(payload.array());
        System.out.println(s);
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Binary Connection closed: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Transport error: " + exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }
}