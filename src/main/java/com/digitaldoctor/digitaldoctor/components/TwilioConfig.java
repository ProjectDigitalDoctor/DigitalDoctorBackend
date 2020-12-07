package com.digitaldoctor.digitaldoctor.components;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.auth.account_sid}")
    private String accountSid;
    @Value("${twilio.auth.token}")
    private String authToken;

    @Bean
    void configureTwilio() {
        Twilio.init(accountSid, authToken);
    }
}
