package com.example.demo.controllers;

import com.example.demo.models.jwt.JwtResponse;
import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.payload.oauth.GoogleAuthResponse;
import com.example.demo.services.Impl.GoogleOAuthUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/google/auth")
public class GoogleAuthController {

    private final GoogleOAuthUserServiceImpl googleOAuthUserService;

    @Autowired
    public GoogleAuthController(GoogleOAuthUserServiceImpl googleOAuthUserService) {
        this.googleOAuthUserService = googleOAuthUserService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> authLogin(@RequestBody GoogleAuthRequest googleAuthRequest) throws GeneralSecurityException, IOException {
        //System.out.println(googleAuthRequest.getGoogleAuthToken());
        GoogleAuthResponse response = this.googleOAuthUserService.verifyToken(googleAuthRequest);
        if(response != null){
            return ResponseEntity.ok(response);
        } else{
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
    }
}
