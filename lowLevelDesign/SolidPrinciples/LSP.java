package SolidPrinciples;
/**
 * LSP Principle:
 * Subclasses should be replaceable for their base classes without breaking the app.
 * It's about honoring the "Behavioral Contract" of the parent.
 */

class Notification {
    // Parent Contract: "I promise that when you call send(), a message reaches the recipient."
    public void sendNotification() {
        System.out.println("email sent");
    }

    // LSP VIOLATION: Not all notifications (like SMS) can have attachments.
    // If a child throws "NotSupportedException", it breaks the parent's promise 
    // that this method is safe to call.
    public void sendAttachment(){
        // Imagine throwing "New Exception("Not Supported")" here. 
    }
}

class TextNotification extends Notification {
    // Subclass (Email/Text): "I'll fulfill the promise using different logic (SMS servers)."
    // Different logic, same expected result = SUCCESS.
    @Override
    public void sendNotification() {
        System.out.println("text sent");
    }
}

class WtspNotification extends Notification {
    // Subclass (Broken): "I'll fulfill the promise by introducing a new method 
    // and ignoring the parent's method."
    public void sendWpNotification() {
        System.out.println("whatsapp message sent");
    }
    
    // VIOLATION: Because we didn't override sendNotification(), the child 
    // "lies" to the program. It says it's a WhatsApp Notification but acts like an Emailer.
    //  The "Promise" of sending a WhatsApp message is broken.
}

public class LSP {
    public static void main(String[] args) {
        
        // 1. Polymorphism works at the compiler level:
        Notification notif = new WtspNotification();
        
        // 2. LSP Fails at the behavioral level:
        // Expected: WhatsApp logic (since the object is WtspNotification).
        // Actual: "email sent" (because the substitution is faulty).
        notif.sendNotification(); 
        
        /* SUMMARY
         * LSP doesn't mean "don't change logic." It means "don't change the outcome."
         * If the Parent says "I can fly", the Child cannot say "I have wings but I won't fly."
         */
    }
}