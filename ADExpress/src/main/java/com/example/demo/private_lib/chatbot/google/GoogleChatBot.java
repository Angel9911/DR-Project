package com.example.demo.private_lib.chatbot.google;

import com.example.demo.private_lib.chatbot.AbstractChatBot;
import com.google.cloud.dialogflow.v2.*;

public class GoogleChatBot extends AbstractChatBot {

    private final SessionsClient sessionsClient;

    public GoogleChatBot(String projectId, String pathToCredentials) {

        super(projectId, pathToCredentials);

        sessionsClient = this.generateSessionClient();
    }

    @Override
    public String sendMessage(String message, String sessionId) {

        TextInput textInput = TextInput.newBuilder().setText(message).setLanguageCode("en-US").build();
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        SessionName sessionName = this.generateSessionName(sessionId);

        DetectIntentResponse response = sessionsClient.detectIntent(sessionName, queryInput);

        QueryResult queryResponse = response.getQueryResult();
        String responseToText = queryResponse.getFulfillmentText();

        return responseToText;
    }
}
