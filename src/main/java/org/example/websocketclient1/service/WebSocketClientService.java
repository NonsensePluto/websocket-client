package org.example.websocketclient1.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Service
public class WebSocketClientService {

    @Value("${websocket.server.url}")
    private String url;
    private final WebSocketClient webSocketClient;
    private WebSocketSession session;

    public WebSocketClientService() {
        this.webSocketClient = new StandardWebSocketClient();
    }

    @PostConstruct
    public void connectToServer() {
        try {
            System.out.println("Попытка подключиться к " + url);

            WebSocketHandler webSocketHandler = createWebSocketHandler();

            this.session = webSocketClient.execute(webSocketHandler, url).get();

            System.out.println("Подключено к " + url);

            sendMessage("Сообщение от клиента!!!");
        } catch (Exception e) {
            System.err.println("Не удалось подключиться " + e.getMessage());
        }
    }

    private WebSocketHandler createWebSocketHandler() {
        return new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("Id сессии: " + session.getId());

            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                System.out.println("Cессия закрыта: " + session.getId());
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                String messageFromServer = message.getPayload();
                System.out.println("Получено сообщение от сервера: " + messageFromServer);
            }
        };
    }

    public void sendMessage(String message) throws IOException {
        if(session.isOpen()) {
            String jsonMessage = "{\"Client\":\"" + message + "\"}";

            session.sendMessage(new TextMessage(jsonMessage));

            System.out.println("Отправлено сообщение серверу");
        }
        else {
            System.err.println("Сообщение не отправлено");
        }
    }

}
