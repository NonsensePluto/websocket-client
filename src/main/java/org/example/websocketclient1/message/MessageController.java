package org.example.websocketclient1.message;

import org.example.websocketclient1.service.WebSocketClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private WebSocketClientService webSocketClientService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        try {
            webSocketClientService.sendMessage(message);
            return "Сообщение отправлено";
        } catch (IOException e) {
            return "Ошибка отправки" + e.getMessage();
        }
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Клиент подключен к серверу";
    }
}
