package com.forweaver.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.forweaver.domain.chat.ChatMessage;
import com.forweaver.service.ChatService;

@Controller
public class ChatController {

	@Autowired private SimpMessagingTemplate template;
	@Autowired
 
	private ChatService chatService;
	
	@MessageMapping("/chat/pub/{chatroom}")
	public void chat(String message,@DestinationVariable String chatroom,Principal prin) { 
		ChatMessage chatMessage = new ChatMessage(message, prin.getName());
		chatService.addChat(chatroom, chatMessage);
		template.convertAndSend( "/chat/sub/"+chatroom, chatMessage);
	}

}
