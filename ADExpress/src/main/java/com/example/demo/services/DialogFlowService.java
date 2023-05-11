package com.example.demo.services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DialogFlowService {
    String sendMessage(String message, String sessionId) throws IOException;
    String generateSessionId();
}
