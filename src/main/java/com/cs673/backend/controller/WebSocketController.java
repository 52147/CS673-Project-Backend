package com.cs673.backend.controller;

import com.cs673.backend.DTO.CheckData;
import com.cs673.backend.entity.ParkInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class WebSocketController extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("connection success");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("connection end");
        sessions.remove(session);
    }
    public void sendMessage(Object messageObject) throws Exception {
        // Convert the messageObject to a JSON string

        String messageJson = objectMapper.writeValueAsString(messageObject);
        // Loop through all active WebSocket sessions
        for (WebSocketSession session : sessions) {
            // Send the message to each session
            session.sendMessage(new TextMessage(messageJson));
        }
    }

}

