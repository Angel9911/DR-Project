package com.example.demo.services.Impl;

import com.example.demo.services.DialogFlowService;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.apache.log.output.jms.TextMessageBuilder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class DialogFlowServiceImpl implements DialogFlowService {
   // private final SessionsClient sessionsClient;
    protected String credentialsFilePath = "src/main/resources/courier-project-380615-661db754d138.json";

    public DialogFlowServiceImpl() throws IOException {
        /*GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDNTIALS")));
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(googleCredentials))
                .build();

        this.sessionsClient = SessionsClient.create(sessionsSettings); */
    }

    public String detectIntent(String projectId, String sessionId, String query) throws Exception {
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(getCredentialsProvider()).build();
        SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);

        SessionName session = SessionName.of(projectId, sessionId);
        TextInput.Builder textInput = TextInput.newBuilder().setText(query).setLanguageCode("en-US");
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

        QueryResult queryResult = response.getQueryResult();
        String fulfillmentText = queryResult.getFulfillmentText();

        return fulfillmentText;
    }
    private CredentialsProvider getCredentialsProvider() throws IOException {
        // Load the credentials from a credentials.json file
        InputStream credentialsStream = getClass().getResourceAsStream("/courier-project-380615-661db754d138.json");
        return FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(credentialsStream));
    }
    @Override
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

    @Override
    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
