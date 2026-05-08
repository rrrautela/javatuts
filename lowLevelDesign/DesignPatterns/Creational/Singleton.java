package DesignPatterns.Creational;

/**
 * This class represents the problem: state is fragmented across multiple instances.
 * In a real judge system (like LeetCode), we need a single source of truth 
 * for global metrics across the entire application.
 */
class JudgeAnalytics {
    private int run = 0;
    private int submit = 0;

    public void countRun() {
        run++;
    }

    public void countSubmit() {
        submit++;
    }

    public int getRunCount() {
        return run;
    }

    public int getSubmitCount() {
        return submit;
    }
}

class Main {
    public static void main(String[] args) {

        // Instance 1: Tracks its own independent memory space
        JudgeAnalytics j1 = new JudgeAnalytics();
        j1.countRun();
        j1.countSubmit();

        // Instance 2: A completely new object with its own 'run' and 'submit' counters initialized to 0
        JudgeAnalytics j2 = new JudgeAnalytics();
        j2.countRun();

        // System.out.println(j2.getRunCount()); 
        // PROBLEM: We want the total runs across the whole system to be 2.
        // However, because these are separate objects, j2 doesn't know about j1's actions.
        
        /* 
         * LOGIC CHECK: 
         * It returns 1 and not 2 because 'j1' and 'j2' reside at different memory addresses.
         * To fix this, we need a SINGLETON pattern to ensure that every part of the 
         * program interacts with the same instance, maintaining a global state.
         */
    }
}


/**
 * EAGER INITIALIZATION:
 * The instance is created at the time of class loading.
 * Pros: Thread-safe without synchronized blocks, very fast access.
 * Cons: Instance is created even if the application never uses it.
 */
class EagerJudge {
    // Initialized immediately; JVM guarantees thread safety during class loading.
    private static final EagerJudge j = new EagerJudge();

    private int run = 0;
    private int submit = 0;

    private EagerJudge() {}

    //static function coz we need to call this without making an object
    public static EagerJudge getInstance() {
        return j;
    }

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

/**
 * LAZY INITIALIZATION:
 * The instance is created only when 'getInstance()' is called for the first time.
 * Pros: Saves memory if the object is never needed.
 * Cons: This specific implementation is NOT thread-safe.
 */
class LazyJudge {
    // Not final, as it must be assigned inside the getter.
    private static LazyJudge j;

    private int run = 0;
    private int submit = 0;

    private LazyJudge() {}

    public static LazyJudge getInstance() {
        // Created only on demand.
        // NOTE: In a multi-threaded environment, two threads could pass the 'null' 
        // check simultaneously and create two different instances.
        if (j == null) {
            j = new LazyJudge();
        }
        return j;
    }

    public void countRun() { run++; }
    public void countSubmit() { submit++; }
    public int getRunCount() { return run; }
    public int getSubmitCount() { return submit; }
}

public class Singleton {
    public static void main(String[] args) {

        // --- EAGER LOADING TEST ---
        
        // EagerJudge e0 = new EagerJudge(); // ERROR: Private constructor.
        
        EagerJudge e1 = EagerJudge.getInstance();
        EagerJudge e2 = EagerJudge.getInstance();

        e1.countRun(); 
        e2.countRun(); 

        System.out.println("Eager - Total Runs: " + e2.getRunCount()); // Output: 2
        System.out.println("Eager - Same Instance: " + (e1 == e2));    // Output: true


        // --- LAZY LOADING TEST ---

        // The instance j is null until this next line is executed.
        LazyJudge l1 = LazyJudge.getInstance();
        LazyJudge l2 = LazyJudge.getInstance();

        l1.countSubmit();
        l2.countSubmit();

        System.out.println("Lazy - Total Submits: " + l2.getSubmitCount()); // Output: 2
        System.out.println("Lazy - Same Instance: " + (l1 == l2));      // Output: true
    }
}