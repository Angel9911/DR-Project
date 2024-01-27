package com.example.demo.private_lib.async_tasks.verification_address;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface AddressVerification {
    Mono<String> getPostalCode(String address, String city) throws JsonProcessingException, UnsupportedEncodingException;
    Future<Boolean> validateAddress(String city, String address) throws ExecutionException, InterruptedException, UnsupportedEncodingException, JsonProcessingException;
}
