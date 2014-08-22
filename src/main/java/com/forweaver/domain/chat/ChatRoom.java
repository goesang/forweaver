package com.forweaver.domain.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom  implements Serializable{
	
	static final long serialVersionUID = 12233337L;
	
	private List<ChatMessage> chatMessages;

	public ChatRoom() {
		super();
		this.chatMessages = new ArrayList<ChatMessage>();
	}
	
	public void addChatMessage(ChatMessage chatMessage){
		this.chatMessages.add(chatMessage);
	}

	public List<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}
	
	public List<ChatMessage> getChatMessages(int index) {
		return chatMessages.subList(index, chatMessages.size()-1);
	}
	
	
	
	
}
