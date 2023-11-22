package com.example.demo.private_lib.chatbot;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

import java.io.IOException;
import java.io.InputStream;

public interface ChatBotService {
    String sendMessage(String message, String sessionId);
    SessionName generateSessionName(String sessionId);
    SessionsClient generateSessionClient();
    CredentialsProvider getChatBotAccessCredentials() throws IOException;
    String generateRandomSessionId();
}
