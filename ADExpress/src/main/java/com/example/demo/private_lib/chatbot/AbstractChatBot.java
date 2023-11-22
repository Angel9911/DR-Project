package com.example.demo.private_lib.chatbot;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class AbstractChatBot implements ChatBotService{

    Logger logger = LoggerFactory.getLogger(AbstractChatBot.class);

    private final String projectId;
    private final String pathToCredentials;

    protected AbstractChatBot(String projectId, String pathToCredentials) {
        this.projectId = projectId;
        this.pathToCredentials = pathToCredentials;
    }


    @Override
    public SessionName generateSessionName(String sessionId) {
        return SessionName.of(projectId, sessionId);
    }

    @Override
    public SessionsClient generateSessionClient() {

        SessionsClient sessionsClient = null;

        try {

            SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(getChatBotAccessCredentials()).build();

            sessionsClient = SessionsClient.create(sessionsSettings);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sessionsClient;
    }

    @Override
    public CredentialsProvider getChatBotAccessCredentials() throws IOException {

        FixedCredentialsProvider credentialsProvider = null;

        try (InputStream inputStream = getClass().getResourceAsStream(this.pathToCredentials)) {

            credentialsProvider = FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(inputStream));
        }catch (IOException e){

            logger.error(e.getMessage());
        }
        return credentialsProvider;
    }

    @Override
    public String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }
}
