package com.bekjon.config;

import com.bekjon.model.MessageDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Bekjon Bakhromov
 * @created 11.04.2022-4:40 PM
 */
@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("massage received: " + message.getPayload());
        for (WebSocketSession webSocketSession : sessions) {
            MessageDto dto = new Gson().fromJson(message.getPayload(), MessageDto.class);
            webSocketSession.sendMessage(new TextMessage(dto.getName() + " said : " + dto.getMessage() + " !"));

        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connected ... " + session.getId());
        sessions.add(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("error: "+session,exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Session {} closed because of {}",session.getId(),status.getReason());
        sessions.remove(session);
    }
}
