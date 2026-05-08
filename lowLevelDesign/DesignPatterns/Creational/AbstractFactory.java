package DesignPatterns.Creational;

// Interface for the 'Product' (Payment Gateway) allowing client-implementation decoupling.
interface PaymentGateway {
    void processPayment(double amount);
}

// Concrete Product implementation for Razorpay.
class RazorpayGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via Razorpay: ₹" + amount);
    }
}

// Concrete Product implementation for PayU.
class PayUGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing INR payment via PayU: ₹" + amount);
    }
}

// Interface for the second 'Product' family (Invoice).
interface Invoice {
    void generateInvoice();
}

// Concrete Invoice implementation specifically for the Indian tax system (GST).
class GSTInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Generating GST invoice for India.");
    }
}

/**
 * IndiaFactoryOld acts as a Concrete Factory in the Abstract Factory pattern.
 * It encapsulates the logic for creating a suite of related products (Gateways
 * + Invoices)
 * specific to the Indian region.
 */
class IndiaFactoryOld {

    /**
     * Simple Factory Method: Centralizes the complex object creation logic.
     * This prevents the client (CheckoutServiceOld) from being coupled to specific
     * implementation classes like RazorpayGateway or PayUGateway.
     */
    public static PaymentGateway createPaymentGateway(String gatewayType) {
        switch (gatewayType.toLowerCase()) {
            case "razorpay":
                return new RazorpayGateway();
            case "payu":
                return new PayUGateway();
            default:
                throw new IllegalArgumentException("Unsupported payment gateway in India: " + gatewayType);
        }
    }

    /**
     * Factory Method for Invoices: Ensures the correct type of invoice is paired
     * with the region-specific gateway.
     */
    public static Invoice createInvoice() {
        return new GSTInvoice();
    }
}

// Concrete Product implementation for Stripe (US)
class StripeGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing USD payment via Stripe: $" + amount);
    }
}

// Concrete Product implementation for PayPal (US)
class PayPalGateway implements PaymentGateway {
    public void processPayment(double amount) {
        System.out.println("Processing USD payment via PayPal: $" + amount);
    }
}

// Concrete Invoice implementation for the US tax system (Sales Tax)
class SalesTaxInvoice implements Invoice {
    public void generateInvoice() {
        System.out.println("Generating Sales Tax invoice for the US.");
    }
}

/**
 * USFactoryOld acts as another Concrete Factory.
 * It provides a suite of products (Stripe/PayPal + SalesTax) specific to the US
 * region.
 */
class USFactoryOld {

    // Centralizes creation logic for US-based payment gateways
    public static PaymentGateway createPaymentGateway(String gatewayType) {
        switch (gatewayType.toLowerCase()) {
            case "stripe":
                return new StripeGateway();
            case "paypal":
                return new PayPalGateway();
            default:
                throw new IllegalArgumentException("Unsupported payment gateway in US: " + gatewayType);
        }
    }

    // Returns a US-specific Sales Tax invoice
    public static Invoice createInvoice() {
        return new SalesTaxInvoice();
    }
}

class CheckoutServiceOld {
    private String gatewayType;
    private String country; // New parameter to handle regional logic

    public CheckoutServiceOld(String gatewayType, String country) {
        this.gatewayType = gatewayType;
        this.country = country;
    }

    public void checkOut(int amount) {
        /**
         * STEP 1: INITIAL APPROACH (Hardcoded Implementations)
         * - Violated Open/Closed Principle.
         * - High Coupling with concrete classes.
         */
        // if (gatewayType == "razor") {
        // PaymentGateway pg = new RazorpayGateway();
        // pg.processPayment(amount);
        // } else {
        // PaymentGateway pg = new PayUGateway();
        // pg.processPayment(amount);
        // }

        /**
         * STEP 2: IMPROVED APPROACH (Outsourced to single Factory)
         * - Decoupled gateway logic.
         * - Problem: Limited to one region (India).
         */
        // PaymentGateway pg = IndiaFactoryOld.createPaymentGateway("payu");
        // pg.processPayment(amount);
        // Invoice inv = IndiaFactoryOld.createInvoice();
        // inv.generateInvoice();

        /**
         * STEP 3: MULTI-REGION ATTEMPT (Using India and US Factories)
         * 
         * CRITICAL LLD INSIGHT:
         * Even though we are using Factories, this class STILL violates the Single
         * Responsibility Principle (SRP).
         * Why? CheckoutServiceOld is now responsible for:
         * 1. The Business Logic of Checking out( main resposnibility)
         * 2. The Decision Logic of which Regional Factory to instantiate. (extra
         * responsibility)
         * 
         * If we add 50 countries, this 'if-else' block will become a maintenance
         * nightmare.
         */
        PaymentGateway pg = null;
        Invoice inv = null;

        if ("India".equalsIgnoreCase(country)) {
            pg = IndiaFactoryOld.createPaymentGateway(gatewayType);
            inv = IndiaFactoryOld.createInvoice();
        } else if ("US".equalsIgnoreCase(country)) {
            pg = USFactoryOld.createPaymentGateway(gatewayType);
            inv = USFactoryOld.createInvoice();
        } else {
            throw new IllegalArgumentException("Unsupported country: " + country);
        }

        // Final execution after factory decision
        if (pg != null && inv != null) {
            pg.processPayment(amount);
            inv.generateInvoice();
        }
    }
}

// ABSTRACT FACTORY: Interface for creating families of related products
// (Gateways & Invoices).
interface RegionFactory {
    PaymentGateway createPaymentGateway(String gatewayType);

    Invoice createInvoice();
}

// CONCRETE FACTORY (India): Implements creation logic for the Indian product
// suite.
class IndiaFactory implements RegionFactory {
    @Override
    public PaymentGateway createPaymentGateway(String gatewayType) {
        // Simple Factory logic encapsulated within the Abstract Factory.
        switch (gatewayType.toLowerCase()) {
            case "razorpay":
                return new RazorpayGateway();
            case "payu":
                return new PayUGateway();
            default:
                throw new IllegalArgumentException("Unsupported gateway in India: " + gatewayType);
        }
    }

    @Override
    public Invoice createInvoice() {
        return new GSTInvoice(); // Ensures GST is always paired with Indian gateways.
    }
}

// CONCRETE FACTORY (US): Implements creation logic for the US product suite.
class USFactory implements RegionFactory {
    @Override
    public PaymentGateway createPaymentGateway(String gatewayType) {
        switch (gatewayType.toLowerCase()) {
            case "stripe":
                return new StripeGateway();
            case "paypal":
                return new PayPalGateway();
            default:
                throw new IllegalArgumentException("Unsupported gateway in US: " + gatewayType);
        }
    }

    @Override
    public Invoice createInvoice() {
        return new SalesTaxInvoice(); // Ensures Sales Tax is always paired with US gateways.
    }
}

// CLIENT: Follows Dependency Inversion Principle; depends on abstraction, not concrete factories.
class CheckoutService {
    private PaymentGateway paymentGateway;
    private Invoice invoice;

    public CheckoutService(RegionFactory factory, String gatewayType) {
        // High-level module remains agnostic of region; the injected factory decides.
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void completeOrder(double amount) {
        paymentGateway.processPayment(amount);
        invoice.generateInvoice();
    }
}

// Main execution: Demonstrates the ease of swapping entire product families.
public class AbstractFactory {
    public static void main(String[] args) {
        // Client provides the specific factory; 
        // so now business logic in CheckoutService remains unchanged.
        CheckoutService indiaOrder = new CheckoutService(new IndiaFactory(), "razorpay");
        indiaOrder.completeOrder(1000.0);

        CheckoutService usOrder = new CheckoutService(new USFactory(), "stripe");
        usOrder.completeOrder(50.0);

        // in the future we can easily add a japanfactory and it would work smoothly by
        // just sening its object as parameter in checkout service
    }
}