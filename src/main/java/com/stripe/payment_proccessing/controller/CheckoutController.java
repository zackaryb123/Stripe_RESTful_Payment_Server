package com.stripe.payment_proccessing.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.payment_proccessing.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stripe")
@CrossOrigin(origins = "*")
public class CheckoutController {
    private StripeService stripeService;

    @Autowired
    CheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @RequestMapping(path = "/pull", method = RequestMethod.POST)
    public ResponseEntity<String> pullMethod(@RequestBody String token) {
        Charge charge = null;
        try {
            charge = this.stripeService.chargeMethod(
                    token,
                    1,
                    "USD"
            );
        } catch (StripeException e) {
            e.printStackTrace();
        }

        if(charge != null) {
            return new ResponseEntity<>(charge.toJson(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/payment-intent", method = RequestMethod.GET)
    public ResponseEntity<String> paymentIntentMethod() {
        PaymentIntent paymentIntent = null;
        try {
            paymentIntent = this.stripeService.paymentIntentMethod(
                    1,
                    "USD",
                    "card"
            );
        } catch (StripeException e) {
            e.printStackTrace();
        }

        if(paymentIntent != null) {
            return new ResponseEntity<>(paymentIntent.toJson(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/billing-invoice", method = RequestMethod.POST)
    public ResponseEntity<String> billingInvoiceMethod(@RequestBody String customerId) {
        Invoice invoice = null;
        try {
            invoice = this.stripeService.invoiceMethod(
                    5.99,
                    customerId,
                    "One-time setup fee",
                    "USD",
                    30,
                    true
            );
        } catch (StripeException e) {
            e.printStackTrace();
        }

        if(invoice != null) {
            return new ResponseEntity<>(invoice.toJson(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/billing-subscription", method = RequestMethod.POST)
    public ResponseEntity<String> billingSubscriptionMethod(@RequestBody String values) {
        String[] valuesArr = values.split("[-]");

        Subscription subscription = null;
        try {
            Customer customer = this.stripeService.getCustomer(valuesArr[0]);
            Plan plan = this.stripeService.getPlan(valuesArr[1]);

            System.out.println(customer);
            System.out.println(plan);

            subscription = this.stripeService.subscribeCustomerToPlan(customer.getId(), plan.getId());
        } catch (StripeException e) {
            e.printStackTrace();
        }

        if(subscription != null) {
            return new ResponseEntity<>(subscription.toJson(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/webhook", method = RequestMethod.POST)
    public ResponseEntity<Object> webHooks(@RequestBody String payload) {
        Event event = Event.GSON.fromJson(payload, Event.class);
        String[] type = event.getType().split("\\.");

        switch (type[0]){
            case "account":
                System.out.println("account Webhook " + type[1]);
                return null;
            case "balance":
                System.out.println("balance Webhook " + type[1]);
                return null;
            case "charge":
                System.out.println("charge Webhook " + type[1]);
                return null;
            case "coupon":
                System.out.println("coupon Webhook " + type[1]);
                return null;
            case "customer":
                System.out.println("customer Webhook " + type[1]);
                return null;
            case "file":
                System.out.println("file Webhook " + type[1]);
                return null;
            case "invoice":
                System.out.println("invoice Webhook " + type[1]);
                return null;
            case "invoiceitem":
                System.out.println("invoiceItem Webhook " + type[1]);
                return null;
            case "issuing_authorization":
                System.out.println("issuing_authorization Webhook " + type[1]);
                return null;
            case "issuing_card":
                System.out.println("issuing_card Webhook " + type[1]);
                return null;
            case "issuing_cardholder":
                System.out.println("issuing_cardholder Webhook " + type[1]);
                return null;
            case "issuing_dispute":
                System.out.println("issuing_dispute Webhook " + type[1]);
                return null;
            case "issuing_settlement":
                System.out.println("issuing_settlement Webhook " + type[1]);
                return null;
            case "issuing_transaction":
                System.out.println("issuing_transaction Webhook " + type[1]);
                return null;
            case "order":
                System.out.println("order Webhook " + type[1]);
                return null;
            case "payment":
                System.out.println("payment Webhook " + type[1]);
                return null;
            case "payment_intent":
                System.out.println("payment_intent Webhook " + type[1]);
                return null;
            case "payout":
                System.out.println("payout Webhook " + type[1]);
                return null;
            case "plan":
                System.out.println("plan Webhook " + type[1]);
                return null;
            case "product":
                System.out.println("product Webhook " + type[1]);
                return null;
            case "recipient":
                System.out.println("recipient Webhook " + type[1]);
                return null;
            case "reporting":
                System.out.println("reporting Webhook " + type[1]);
                return null;
            case "review":
                System.out.println("review Webhook " + type[1]);
                return null;
            case "sigma":
                System.out.println("sigma Webhook " + type[1]);
                return null;
            case "sku":
                System.out.println("sku Webhook " + type[1]);
                return null;
            case "source":
                System.out.println("source Webhook " + type[1]);
                return null;
            case "subscription_schedule":
                System.out.println("subscription_schedule Webhook " + type[1]);
                return null;
            case "topup":
                System.out.println("topup Webhook " + type[1]);
                return null;
            case "transfer":
                System.out.println("transfer Webhook " + type[1]);
                return null;
            default:
                System.out.println("Webhook not implemented" );
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
