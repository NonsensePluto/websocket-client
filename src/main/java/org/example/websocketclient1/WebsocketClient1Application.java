package org.example.websocketclient1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketClient1Application {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketClient1Application.class, args);
        System.out.println("Websocket клиент запущен на localhost:8081");
    }

}
