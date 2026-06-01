package com.apelisser.algashop.billing.infrastructure.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("algashop.integrations.payment")
public class AlgashopPaymentProperties {

    @NotNull
    private AlgashopPaymentProvider provider;

    @NotNull
    @Valid
    private FastpayProperties fastpay;

    public enum AlgashopPaymentProvider {
        FAKE,
        FASTPAY
    }

    @Getter
    @Setter
    public static class FastpayProperties {

        @NotBlank
        private String hostname;

        @NotBlank
        private String privateToken;

        @NotBlank
        private String webhookUrl;

    }

}
