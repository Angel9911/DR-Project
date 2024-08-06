package com.example.demo.config;

import com.example.demo.private_lib.chatbot.google.GoogleChatBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatBotConfig {

    protected String projectId = "courier-project-380615";
    protected String credentialsFilePath = "/courier-project-380615-661db754d138.json";

    @Bean
    public GoogleChatBot getChatBotInstance(){

        return new GoogleChatBot(projectId,credentialsFilePath);
    }
}
