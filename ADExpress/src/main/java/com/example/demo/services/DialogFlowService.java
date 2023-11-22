package com.example.demo.services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DialogFlowService {
    String detectIntent(String sessionId, String query) throws IOException;
    String generateSessionId();
}
