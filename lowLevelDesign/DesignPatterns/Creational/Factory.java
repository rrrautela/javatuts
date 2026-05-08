package DesignPatterns.Creational;

/**
 * Product Interface: Defines the template for all concrete logistics types.
 */
interface Logistics {
    void send();
}

/**
 * Concrete Product: Implements road-based transportation.
 */
class Road implements Logistics {
    @Override
    public void send() {
        System.out.println("sending by road");
    }
}

/**
 * Concrete Product: Implements air-based transportation.
 */
class Air implements Logistics {
    @Override
    public void send() {
        System.out.println("sending by Air");
    }
}

/**
 * Concrete Product: Implements train-based transportation.
 * Demonstrates Open/Closed Principle (OCP): New types can be added without 
 * modifying existing concrete product classes.
 */
class Train implements Logistics {
    @Override
    public void send() {
        System.out.println("sending by Train");
    }
}

/**
 * Factory Class: Centralizes the object creation logic.
 * Follows Single Responsibility Principle (SRP) by decoupling the 'how to create' 
 * from the 'how to use'.
 */
class LogisticsFactory {
    public static Logistics getLogistics(String mode) {
        // Logic to determine which concrete implementation to instantiate
        if (mode == "Road") {
            return new Road();
        } 
        else if (mode == "Train") {
            return new Train();
        }
        return new Air();
    }
}

/**
 * Client-facing Service: Uses the Factory to obtain objects.
 * This class doesn't know (and doesn't care) which concrete object it receives,
 * promoting loose coupling through the Logistics interface.
 */
class LogisticsService {
    public void send(String mode) {
        // Outsourcing instantiation to the Factory
        Logistics log = LogisticsFactory.getLogistics(mode); 
        log.send();
    }
}

public class Factory {
    public static void main(String[] args) {
        // Testing the Factory Pattern
        LogisticsService service = new LogisticsService();
        service.send("Train");
        service.send("Air");
    }
}