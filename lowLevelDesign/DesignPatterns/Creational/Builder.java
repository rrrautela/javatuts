package DesignPatterns.Creational;

import java.util.*;



/**
 * The Builder Pattern is used to construct complex objects step-by-step.
 * It prevents "Telescoping Constructors" where you'd have to pass many nulls 
 * for optional parameters.
 */
class BurgerMeal {
    // Required parameters are marked 'final' to ensure immutability
    private final String bunType;
    private final String patty;

    // Optional parameters
    private final boolean hasCheese;
    private final List<String> toppings;
    private final String side;
    private final String drink;

    /**
     * Private constructor ensures that a BurgerMeal can ONLY be created via the Builder.
     * It takes the Builder instance and copies the gathered values into the final fields.
     */
    private BurgerMeal(BurgerBuilder builder) {
        this.bunType = builder.bunType;
        this.patty = builder.patty;
        this.hasCheese = builder.hasCheese;
        this.toppings = builder.toppings;
        this.side = builder.side;
        this.drink = builder.drink;
    }

   /**
     * Why STATIC Inner Class?
     * 1. Independence: 'static' means this class is associated with the outer class name,
     *    not a specific instance. We can create a Builder BEFORE a BurgerMeal exists.
     * 2. Instance Support: Even though the CLASS is static (the blueprint), we can create 
     *    multiple unique INSTANCES of it (new BurgerBuilder) to store different configurations.
     * 3. No Memory Leak: A non-static inner class holds a "hidden" (implicit) reference to the 
     *    outer class instance that created it, preventing the outer object from being garbage 
     *    collected. By making it static, we cut this link, ensuring the Builder doesn't 
     *    accidentally keep a heavy BurgerMeal object stuck in memory.
     * 4. Private Access: In Java, the outer class has full access to the private members 
     *    of its inner class (and vice versa). This allows the BurgerMeal constructor to 
     *    cleanly pull data from the builder's private fields without needing public getters.
     */

    public static class BurgerBuilder {

        // Builder holds temporary state during the "assembly" process
        
        // Required
        private final String bunType;
        private final String patty;

        // Optional
        private boolean hasCheese;
        private List<String> toppings;
        private String side;
        private String drink;

        /**
         * The Builder constructor takes only the MANDATORY parameters.
         * This ensures a burger cannot be built without the essentials (bun and patty).
         */
        public BurgerBuilder(String bunType, String patty) {
            this.bunType = bunType;
            this.patty = patty;
        }

        // Fluent Interface / Method Chaining: 
        // Each setter returns 'this' (the current builder instance)
        // allowing us to chain calls like .withCheese().withDrink().

        public BurgerBuilder withCheese(boolean hasCheese) {
            this.hasCheese = hasCheese;
            return this;
        }

        public BurgerBuilder withToppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public BurgerBuilder withSide(String side) {
            this.side = side;
            return this;
        }

        public BurgerBuilder withDrink(String drink) {
            this.drink = drink;
            return this;
        }

        /**
         * The final assembly step. 
         * It passes the current state of the builder to the BurgerMeal constructor.
         */
        public BurgerMeal build() {
            return new BurgerMeal(this);
        }
    }

    @Override
    public String toString() {
        return "BurgerMeal{" +
                "bunType='" + bunType + '\'' +
                ", patty='" + patty + '\'' +
                ", hasCheese=" + hasCheese +
                ", toppings=" + toppings +
                ", side='" + side + '\'' +
                ", drink='" + drink + '\'' +
                '}';
    }
}

public class Builder {
    public static void main(String[] args) {

        // OLD WAY: Highly confusing and requires passing 'null' for things we don't want.
        //because everything happened at constructor level
        // BurgerMeal bmOld = new BurgerMeal("wheat", "veg", null, null, null);

        // BUILDER WAY: Clean and readable.
        // 1. Static access to the Builder class to start construction.
        BurgerMeal bm = new BurgerMeal.BurgerBuilder("wheat", "veg").build();
        
        // 2. Chained methods to add only what is needed.
        BurgerMeal bm2 = new BurgerMeal.BurgerBuilder("wheat", "veg")
                .withCheese(true)
                .withDrink("coke")
                .build();
        
        System.out.println(bm2);
    }
}





// Telescoping Constructors Antipatter example


// class BurgerMeal {
//     private final String bunType;
//     private final String patty;
//     private final boolean hasCheese;
//     private final List<String> toppings;
//     private final String side;
//     private final String drink;

//     // 1. Level One: Only the absolute mandatory requirements
//     public BurgerMeal(String bunType, String patty) {
//         this(bunType, patty, false); // Chains to Level Two
//     }

//     // 2. Level Two: Adds Cheese
//     public BurgerMeal(String bunType, String patty, boolean hasCheese) {
//         this(bunType, patty, hasCheese, Collections.emptyList()); // Chains to Level Three
//     }

//     // 3. Level Three: Adds Toppings
//     public BurgerMeal(String bunType, String patty, boolean hasCheese, List<String> toppings) {
//         this(bunType, patty, hasCheese, toppings, "No Side"); // Chains to Level Four
//     }

//     // 4. Level Four: Adds Side
//     public BurgerMeal(String bunType, String patty, boolean hasCheese, List<String> toppings, String side) {
//         this(bunType, patty, hasCheese, toppings, side, "No Drink"); // Chains to Master
//     }

//     // 5. MASTER CONSTRUCTOR: The "End of the Telescope"
//     // All other constructors eventually point here to actually set the fields.
//     public BurgerMeal(String bunType, String patty, boolean hasCheese, List<String> toppings, String side, String drink) {
//         this.bunType = bunType;
//         this.patty = patty;
//         this.hasCheese = hasCheese;
//         this.toppings = toppings;
//         this.side = side;
//         this.drink = drink;
//     }

//     @Override
//     public String toString() {
//         return "BurgerMeal{" +
//                 "bunType='" + bunType + '\'' +
//                 ", patty='" + patty + '\'' +
//                 ", hasCheese=" + hasCheese +
//                 ", toppings=" + toppings +
//                 ", side='" + side + '\'' +
//                 ", drink='" + drink + '\'' +
//                 '}';
//     }
// }