package SolidPrinciples;
/**
 * ISP Principle:
 * Clients should not be forced to depend on methods they do not use.
 * Instead of one "Fat Interface", create multiple "Specific Interfaces".
 */

/* * THE BAD WAY (Violating ISP)
 * ----------------------------
 * interface Uber {
    * void bookRide();   // Rider action
    * void acceptRide(); // Driver action
    * void drive();      // Driver action
    * void endRide();    // Driver action
    * void payRide();    // Rider action
 * }
 * * class Rider implements Uber {
    * // PROBLEM: The Rider is forced to implement acceptRide(), drive(), and endRide()
    * // even though a Rider never drives or accepts rides. 
    * // These methods will be empty or throw exceptions, which is bad design.
 * }
 */

// THE GOOD WAY (Following ISP)
// ----------------------------

// Step 1: Split the Fat Interface into Specific Interfaces
interface RiderInterface {
    void bookRide();
    void payRide();
}

interface DriverInterface {
    void acceptRide();
    void drive();
    void endRide();
}

// Step 2: Classes now only implement what they actually NEED
class Rider implements RiderInterface {
    @Override
    public void bookRide() {
        System.out.println("Ride booked successfully!");
    }

    @Override
    public void payRide() {
        System.out.println("Payment processed.");
    }
}

class Driver implements DriverInterface {
    @Override
    public void acceptRide() {
        System.out.println("Driver accepted the request.");
    }

    @Override
    public void drive() {
        System.out.println("Car is moving...");
    }

    @Override
    public void endRide() {
        System.out.println("Ride completed.");
    }
}

public class ISP {
    public static void main(String[] args) {
        // Now, Rider objects aren't cluttered with Driver methods, 
        // and Driver objects aren't cluttered with Rider methods.
        
        Rider rider = new Rider();
        rider.bookRide();
        
        Driver driver = new Driver();
        driver.acceptRide();
    }
}