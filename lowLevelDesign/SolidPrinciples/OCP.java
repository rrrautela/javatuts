package SolidPrinciples;
/**
 * OCP Principle: 
 * Software entities (classes, modules, functions, etc.) should be 
 * OPEN for extension, but CLOSED for modification.
 */



/* * THE BAD WAY (Violating OCP)
 * --------------------------
 * class TaxCalculator {
    * public double amountAfterTax(double amount, String region) {
        * // Every time a new country is added, we MUST modify this class.
        * // This risks breaking existing logic for India or US.
        * if (region == "INDIA") return 1.18 * amount;
        * else if (region == "US") return 1.1 * amount;
        * return amount;
    * }
 * }
 * * class InvoiceService {
    * public void calculate() {
        * TaxCalculator tc = new TaxCalculator();
        * // If we change the method signature in TaxCalculator, 
        * // we have to hunt down and fix every call like this.
        * tc.amountAfterTax(100, "INDIA"); 
    * }
 * }
 */




// THE GOOD WAY (Following OCP)
// ----------------------------

// 1. We create an Abstraction. It is "Closed for Modification" because the 
// method signature 'double amountAfterTax(double amount)' won't change.
interface TaxCalculator {
    public double amountAfterTax(double amount);
}

// 2. Extension: Adding India is just a new class.
class IndianTax implements TaxCalculator {
    public double amountAfterTax(double amount) {
        return 1.18 * amount;
    }
}

// 3. Extension: Adding US is just a new class. 
// We didn't touch IndianTax or the interface to add this!
class UsTax implements TaxCalculator {
    public double amountAfterTax(double amount) {
        return 1.10 * amount;
    }
}

// InvoiceService is now flexible. It works with the Interface, not the details.
class InvoiceService {
    public void calculate() {
        // We can swap UsTax for IndianTax here. 
        // The call to amountAfterTax(100) remains identical regardless of region.
        TaxCalculator tc = new UsTax();
        tc.amountAfterTax(100); 
    }
}

public class OCP {
    public static void main(String[] args) {
        // Execution logic would go here
    }
} 