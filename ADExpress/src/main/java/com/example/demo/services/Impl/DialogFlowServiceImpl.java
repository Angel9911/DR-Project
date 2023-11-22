package com.example.demo.services.Impl;

import com.example.demo.private_lib.chatbot.google.GoogleChatBot;
import com.example.demo.services.DialogFlowService;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class DialogFlowServiceImpl implements DialogFlowService {

    protected String projectId = "courier-project-380615";
    protected String credentialsFilePath = "/courier-project-380615-661db754d138.json";
    private final GoogleChatBot googleChatBot;

    public DialogFlowServiceImpl()  {

        this.googleChatBot = new GoogleChatBot(projectId,credentialsFilePath);
    }

    @Override
    public String detectIntent(String sessionId, String query) {

        String getResponseText = this.googleChatBot.sendMessage(query, sessionId);

        return getResponseText;
    }

    @Override
    public String generateSessionId() {
        return this.googleChatBot.generateRandomSessionId();
    }






    public String sendMessage(String message, String sessionId) throws IOException {

        /*String projectId = "courier-project-380615";
        SessionsClient sessionsClient = SessionsClient.create();
        String session = String.format("projects/%s/agent/sessions/%s", projectId, sessionId);
        TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode("en-US");
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        DetectIntentRequest request =
                DetectIntentRequest.newBuilder().setSession(session).setQueryInput(queryInput).build();
        return String.valueOf(sessionsClient.detectIntent(request).getQueryResult()); */
        return "";

      /*  SessionName sessionName = SessionName.of("[courier-project-380615]",sessionId);
        String languageCode = "en-US";

        // Create a text with user message
        TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode(languageCode);

        //Build request with the text input and session
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        DetectIntentRequest detectIntentRequest = DetectIntentRequest.newBuilder()
                .setSession(sessionName.toString())
                .setQueryInput(queryInput)
                .build();

        //Send the query request to the DialogFlow
        DetectIntentResponse detectIntentResponse = sessionsClient.detectIntent(detectIntentRequest);

        //Get the response from DialogFlow
        QueryResult queryResult = detectIntentResponse.getQueryResult();

        return queryResult.getFulfillmentText(); */
    }
}
