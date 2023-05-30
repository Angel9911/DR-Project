package com.example.demo.controllers;

import com.example.demo.models.jwt.JwtResponse;
import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.services.Impl.GoogleOAuthUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/google/auth")
public class GoogleAuthController {

    private final GoogleOAuthUserServiceImpl googleOAuthUserService;

    public GoogleAuthController(GoogleOAuthUserServiceImpl googleOAuthUserService) {
        this.googleOAuthUserService = googleOAuthUserService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> authLogin(@RequestBody GoogleAuthRequest googleAuthRequest) throws GeneralSecurityException, IOException {
        //System.out.println(googleAuthRequest.getGoogleAuthToken());
        JwtResponse response = this.googleOAuthUserService.verifyToken(googleAuthRequest);
        return ResponseEntity.ok(response);
    }
}