package com.example.demo.private_lib.async_tasks.verification_address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.demo.constants.AddressVerificationConstraints;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractAddressVerification implements AddressVerification {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAddressVerification.class);

    private final String authId;

    private final String authToken;

    private String addressIdUrl;

    private WebClient webClient;

    private final ObjectMapper mapper;

    public AbstractAddressVerification(String authId, String authToken) {
        this.authId = authId;
        this.authToken = authToken;

        this.mapper = new ObjectMapper();
    }

    public void setAddressIdUrl(String addressIdUrl) {
        this.addressIdUrl = addressIdUrl;
    }

    @Override
    public Mono<String> getPostalCode(String address, String city) throws JsonProcessingException, UnsupportedEncodingException {

        this.webClient = WebClient.builder().baseUrl(this.addressIdUrl).build();

        String cityAddress = URLEncoder.encode(address + " " + city, StandardCharsets.UTF_8.toString());

        String requestUrl = String.format("%s?country=%s&search=%s&auth-id=%s&auth-token=%s", this.addressIdUrl, AddressVerificationConstraints.COUNTRY_NAME, cityAddress, this.authId, this.authToken);
        System.out.println(requestUrl);

        return webClient.get().uri(requestUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<Map<String, Object>>>>() {
                })
                .flatMap(response -> {
                    List<Map<String, Object>> responseAddress = response.get("candidates");

                    if (responseAddress != null && !responseAddress.isEmpty()) {
                        Map<String, Object> getFirstAddress = responseAddress.get(0);

                        if (getFirstAddress.containsKey("address_text") && !getFirstAddress.get("address_text").toString().isEmpty()
                                && getFirstAddress.containsKey("address_id") && !getFirstAddress.get("address_id").toString().isEmpty()) {

                            String patternString = "(\\S+) (\\d+) (\\S+)";

                            Pattern pattern = Pattern.compile(patternString);

                            //TODO - sometime the external server return the number of the street that is located between postal_code and street number
                            // and in this we give number of the street instead postal code.

                            String getAddressText = getFirstAddress.get("address_text").toString();

                            Matcher matcher = pattern.matcher(getAddressText);

                            if(matcher.find()){

                                String extractPostalCode = matcher.group(2);

                                return Mono.just(extractPostalCode);
                            }
                        }
                    }
                    return Mono.empty();
                });
    }
}
