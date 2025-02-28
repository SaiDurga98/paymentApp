package com.techie.springBootPaymentApp.service;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.StripeRequest;
import com.stripe.param.checkout.SessionCreateParams;
import com.techie.springBootPaymentApp.dto.ProductRequest;
import com.techie.springBootPaymentApp.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    // Stripe API needs productName, Amount, Quantity, Currency to connect to paymentGateway
    // return sessionId and URL

    @Value("${stripe.key}")
    private String stripeSecretKey;

    public StripeResponse checkoutProducts(ProductRequest stripeRequest) {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.LineItem.PriceData.ProductData product =
            SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(stripeRequest.getProductName()).build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(stripeRequest.getCurrency() == null ? "USD" : stripeRequest.getCurrency())
                        .setUnitAmount(stripeRequest.getAmount())
                        .setProductData(product)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(stripeRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        //Adding a line Item to Session
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:9090/success")
                .setCancelUrl("http://localhost:9090/cancel")
                .addLineItem(lineItem)
                .build();
        Session session = null;
        try{
            session = Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        return StripeResponse
                .builder()
                .status("SUCCESS")
                .message("Payment session created successfully")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
