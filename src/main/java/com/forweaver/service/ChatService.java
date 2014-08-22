package com.forweaver.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.chat.ChatMessage;
import com.forweaver.domain.chat.ChatRoom;

@Service
public class ChatService {
	
	@Autowired private CacheManager cacheManager;
	
	public ChatRoom get(String chatName){
		Cache cache = cacheManager.getCache("chat");
		Element element = cache.get(chatName);
		if (element == null || (element != null && element.getValue() == null)) {
			ChatRoom chatRoom = new ChatRoom();
			Element newElement = new Element(chatName,chatRoom);
			cache.put(newElement);
			return chatRoom;
		}else
			return (ChatRoom)element.getValue();
	}
	
	public void addChat(String chatName,ChatMessage chatMessage){
		Cache cache = cacheManager.getCache("chat");
		Element element = cache.get(chatName);
		if (element == null || (element != null && element.getValue() == null)) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoom.addChatMessage(chatMessage);
			Element newElement = new Element(chatName,chatRoom);
			cache.put(newElement);
		}else{
			ChatRoom chatRoom = (ChatRoom)element.getValue();
			chatRoom.addChatMessage(chatMessage);
		}
	}
}
