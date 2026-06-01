package com.apelisser.algashop.billing.infrastructure.payment.fastpay.webhook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FastpayPaymentWebhookEvent {

    @NotBlank
    private String paymentId;

    @NotBlank
    private String referenceCode;

    @NotBlank
    private String status;

    @NotBlank
    private String method;

    @NotNull
    private OffsetDateTime notifiedAt;

}
