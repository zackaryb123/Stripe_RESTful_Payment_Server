package com.stripe.payment_proccessing.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {

    @Autowired
    void StripeClient() {
        Stripe.apiKey = "sk_test_9pLSxXjXolREB69shvbWzGiU";
    }

    public Charge chargeMethod(String source, double amount, String currency) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", currency);
        chargeParams.put("source", source);

        return Charge.create(chargeParams);
    }

    public PaymentIntent paymentIntentMethod(double amount, String currency, String type) throws StripeException {
        Map<String, Object> paymentIntentParams = new HashMap<>();
        paymentIntentParams.put("amount", (int)(amount * 100));
        paymentIntentParams.put("currency", currency);
        List<String> payment_method_types = new ArrayList<>();
        payment_method_types.add(type);
        paymentIntentParams.put("payment_method_types", payment_method_types);

        return PaymentIntent.create(paymentIntentParams);
    }

    public Invoice invoiceMethod(double amount, String customerId, String description, String currency, int days, boolean autoAdvance) throws StripeException {
        Map<String, Object> itemParams = new HashMap<>();
        itemParams.put("customer", customerId);
        itemParams.put("amount", (int)(amount* 100));
        itemParams.put("currency", currency);
        itemParams.put("description", description);

        InvoiceItem.create(itemParams);

        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put("billing", "send_invoice");
        params.put("days_until_due", days);
        params.put("auto_advance", autoAdvance); // auto-finalize this draft after ~1 hour

        return Invoice.create(params);
    }

    public Subscription subscribeCustomerToPlan(String customerId, String planId) throws StripeException {
        Map<String, Object> item = new HashMap<>();
        item.put("plan", planId);

        Map<String, Object> items = new HashMap<>();
        items.put("0", item);

        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put("items", items);

        return Subscription.create(params);
    }

    public Product createProduct(String name) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("type", "service");

        return Product.create(params);
    }

    public Product getProduct(String id) throws StripeException {
        return Product.retrieve(id);
    }

    public Plan createPlan(String product, String nickname, String interval, String currency, double amount) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("product", product);
        params.put("nickname", nickname);
        params.put("interval", interval);
        params.put("currency", currency);
        params.put("amount", amount);

        return Plan.create(params);
    }

    public Plan getPlan(String id) throws StripeException {
        return Plan.retrieve(id);
    }

    public Customer createCustomer(String email, String source) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("source",source);

        return Customer.create(params);
    }

    public Customer getCustomer(String id) throws StripeException {
        return Customer.retrieve(id);
    }
}
