package com.example.demo.private_lib.async_tasks.verification_address;

import com.example.demo.constants.AddressVerificationConstraints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class SmartyStreetVerification extends AbstractAddressVerification {

    private static final Logger logger = LoggerFactory.getLogger(SmartyStreetVerification.class);

    //@Value("adexpress.smarty.streets.auth.id")
    private final String authId;

    //@Value("adexpress.smarty.streets.auth.token")
    private final String authToken;

    private final WebClient webClient;

   // private final ModelMapper mapper;

    @Autowired
    public SmartyStreetVerification(@Value("${adexpress.smarty.streets.auth.id}")String authId, @Value("${adexpress.smarty.streets.auth.token}") String authToken, WebClient.Builder webClient) {

        super(authId,authToken);
        System.out.println("test auth id "+authId);
        System.out.println("test auth token "+authToken);
        this.authId = authId;
        this.authToken = authToken;

        this.setAddressIdUrl(AddressVerificationConstraints.ADDRESS_ID_API_URL);

        this.webClient = webClient.baseUrl(AddressVerificationConstraints.ADDRESS_VERIFICATION_API_URL).build();
        //this.mapper = mapper;
    }

    @Async
    @Override
    public CompletableFuture<Boolean> validateAddress(String city, String address) throws UnsupportedEncodingException, JsonProcessingException {

        String encodeAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());


        return this.getPostalCode(encodeAddress, city)
                .flatMap(postalCode -> this.isValidAddress(encodeAddress, postalCode))
                .onErrorResume(throwable -> {
                    System.err.println("Error occurred while processing the request: " + throwable.getMessage());
                    return Mono.just(false); // Provide a default/fallback value
                })
                .defaultIfEmpty(false)
                .toFuture();

    }


    private Mono<Boolean> isValidAddress(String address, String getPostalCode) {

        String requestUrl = String.format("?country=%s&address1=%s&postal_code=%s&auth-id=%s&auth-token=%s",AddressVerificationConstraints.COUNTRY_NAME,address,getPostalCode,this.authId,this.authToken);

        return webClient.get().uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)// TODO: use ParameterizedTypeReference instead String.class
                .flatMap(result -> {
                    List<Map<String, Object>> toListObjects = new ArrayList<>();
                    try {
                        Gson gson = new Gson();

                        Type responseJsonDataList = new TypeToken<List<Map<String, Object>>>() {}.getType();

                        toListObjects = gson.fromJson(result, responseJsonDataList);

                    }catch (JsonSyntaxException e){
                        e.printStackTrace();
                    }

                    if(!toListObjects.isEmpty()){

                        Map<String, Object> verificationObject = toListObjects.get(0);

                        if(verificationObject.containsKey("analysis")){

                            Map<String,Object> analysis = (Map<String, Object>) verificationObject.get("analysis");

                            if(analysis.containsKey("verification_status")){

                                String verificationStatus = (String) analysis.get("verification_status");
                                    if(verificationStatus.equals("Verified")){
                                        return Mono.just(true);
                                    }else{
                                        logger.error("The verification_status isn't verified");
                                        return Mono.just(false);
                                    }
                            }
                        }
                }
                    return Mono.just(false);
                })
                .onErrorResume(e -> {
                    return Mono.empty();
                });
    }
}
