package com.example.demo.services;

import com.example.demo.payload.oauth.GoogleAuthRequest;
import com.example.demo.payload.oauth.GoogleAuthResponse;


import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleOAuthUserService {
     GoogleAuthResponse verifyToken(GoogleAuthRequest token) throws GeneralSecurityException, IOException;
}
