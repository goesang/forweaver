package com.forweaver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;


@Configuration
public class MongoDBConfig {

	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		ServerAddress serverAddress = new ServerAddress("127.0.0.1", 27017);
		MongoClient mongoClient = new MongoClient(serverAddress); 
		return  new SimpleMongoDbFactory(mongoClient, "forweaver");
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}
}

