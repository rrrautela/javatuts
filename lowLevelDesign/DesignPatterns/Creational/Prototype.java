package DesignPatterns.Creational;

import java.util.*;

/**
 * =========================================================================
 * PHASE 1: THE "BEFORE" STATE (Manual 'new' keyword)
 * =========================================================================
 * REVISION NOTE: This is the naive approach.
 * Why it's bad:
 * 1. Constructor Overhead: If WelcomeEmail() did heavy work (like loading 
 *    10MB of data), calling 'new' 100 times would be incredibly slow.
 * 2. Boilerplate: If you had 20 fields to set, you'd have to write 20 
 *    setters for every single new object.
 */

// Interface defining the bare essentials for an email.
// interface EmailTemplate {
//     void setContent(String content);
//     void send(String to);
// }

/**
 * Concrete implementation. 
 * Notice that every time we call 'new', we are hard-coding 
 * the initial state (subject and content) inside the constructor.
 */
// class WelcomeEmail implements EmailTemplate {
//     private String subject;
//     private String content;

//     public WelcomeEmail() {
//         // ISSUE 1: Hardcoded Initial State
//         // Every instance starts with these exact strings. 
//         // If you need 100 emails, you're allocating these strings 100 times.
//         this.subject = "welcome to tuf ultra!";
//         this.content = "hi there thanks for joining us !";
//     }

//     @Override
//     public void setContent(String content) {
//         this.content = content;
//     }

//     @Override
//     public void send(String to) {
//         System.out.println("sending to " + to + ": [" + subject + "] " + content);
//     }
// }


/**
 * =========================================================================
 * PHASE 2: THE "AFTER" STATE (Cloneable & Registry)
 * =========================================================================
 * We extend Cloneable to use the JVM's optimized memory-copying mechanism.
 */

interface EmailTemplate extends Cloneable {
    // We add clone() to the contract so we can duplicate without 'new'
    EmailTemplate clone(); 
    void setContent(String content);
    void send(String to);
    String getContent();
}

class WelcomeEmail implements EmailTemplate {
    private String subject;
    private String content;

    public WelcomeEmail() {
        // Imagine this constructor is super "expensive" (takes 2 seconds to run)
        this.subject = "welcome to tuf ultra!";
        this.content = "hi there thanks for joining us !";
    }

    @Override
    public WelcomeEmail clone() {
        try {
            /**
             * REVISION NOTE: super.clone() does a "Shallow Copy".
             * It copies the bit-pattern of the object. Since 'subject' and 'content'
             * are Strings (immutable), a shallow copy is perfectly safe here.
             */
            return (WelcomeEmail) super.clone();
        }
        catch(CloneNotSupportedException e) {
            // This happens if you forget to implement 'Cloneable'
            throw new RuntimeException("clone failed", e);
        }
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void send(String to) {
        System.out.println("sending to " + to + ": [" + subject + "] " + content);
    }
    
    @Override
    public String getContent() {
        return content;
    }
}

/**
 * THE REGISTRY (The "Factory/Store" for Prototypes)
 * This solves the Singleton problem. We store ONE instance in a Map,
 * but we never give out that instance. We only give out COPIES of it.
 */
class EmailTemplateRegistry {
    private static final Map<String, EmailTemplate> templates = new HashMap<>();

    static {
        // We create the "Master" object ONCE. The heavy constructor runs ONLY ONCE.
        templates.put("welcome", new WelcomeEmail());
        // Add more templates like "discount", "feature-update" etc.
    }

    public static EmailTemplate geTemplate(String type) {
        // IMPORTANT: .clone() ensures the requester gets their own private copy.
        // If we didn't clone, this would be a Singleton and everyone would
        // overwrite each other's email content!
        return templates.get(type).clone(); 
    }
}

public class Prototype {
    public static void main(String[] args) {
        /**
         * -----------------------------------------------------------
         * LEGACY CODE (PHASE 1) - Why we moved away from this:
         * -----------------------------------------------------------
         */
        // ISSUE 2: Repetitive Initialization
        // We are manually calling 'new' for every single recipient.
        // WelcomeEmail w1 = new WelcomeEmail();
        // WelcomeEmail w2 = new WelcomeEmail();
        // WelcomeEmail w3 = new WelcomeEmail();
        // WelcomeEmail w4 = new WelcomeEmail();

        // ISSUE 3: Manual State Management
        // If w1 needs a custom 'TUF' message and w2 needs 'TUF+', 
        // you have to manually call setters for every single object.
        // w1.setContent("Welcome to TUF!");
        // w2.setContent("Welcome to TUF+!");
        
        /**
         * -----------------------------------------------------------
         * MODERN CODE (PHASE 2) - Registry + Cloning
         * -----------------------------------------------------------
         */

        // Get first independent clone
        EmailTemplate we = EmailTemplateRegistry.geTemplate("welcome");
        we.setContent("type 1");
        System.out.println("Object 1 content: " + we.getContent());

        // Get second independent clone
        EmailTemplate we2 = EmailTemplateRegistry.geTemplate("welcome");
        we2.setContent("type 2");

        // VERIFICATION FOR FUTURE YOU:
        // If cloning works, printing 'we' now should STILL show "type 1".
        // If it shows "type 2", then the Registry is accidentally 
        // acting like a Singleton and sharing the same memory!
        System.out.println("Object 1 (Checking for pollution): " + we.getContent());
        System.out.println("Object 2 content: " + we2.getContent());
    }
}