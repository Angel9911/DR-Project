package com.example.demo.services;

import com.example.demo.models.jwt.JwtResponse;
import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.payload.oauth.GoogleAuthResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleOAuthUserService {
     GoogleAuthResponse verifyToken(GoogleAuthRequest token) throws GeneralSecurityException, IOException;
}
