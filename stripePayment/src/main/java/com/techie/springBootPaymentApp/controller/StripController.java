package com.techie.springBootPaymentApp.controller;


import com.techie.springBootPaymentApp.dto.ProductRequest;
import com.techie.springBootPaymentApp.dto.StripeResponse;
import com.techie.springBootPaymentApp.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment/v1")
public class StripController {

    private StripeService stripeService;

    public StripController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping
    public ResponseEntity<StripeResponse> checkout(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }

}
