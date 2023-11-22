package com.example.demo.controllers;

import com.example.demo.services.Impl.DialogFlowServiceImpl;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/chatbot/dialogflow")
public class DialogFlowController {
    private final DialogFlowServiceImpl dialogFlowService;

    @Autowired
    public DialogFlowController(DialogFlowServiceImpl dialogFlowService) {
        this.dialogFlowService = dialogFlowService;
    }

    @PostMapping(value = "/send/message/{sessionId}")
    public ResponseEntity<Map<String, String>> sendMessage(@PathVariable String sessionId, @RequestBody Map<String,Object> message) throws Exception {

        String getMessage = (String) message.get("message");
        System.out.println("test "+getMessage);

        String getText = this.dialogFlowService.detectIntent(sessionId,getMessage);

        Map<String, String> response = new HashMap<>();
        response.put("message", getText);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @RequestMapping(value = "/sessionId", method = RequestMethod.GET, produces = "application/text")
    public ResponseEntity<String> generateSessionId(){

        String sessionId = dialogFlowService.generateSessionId();

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }
}
