package multiThreadingClass;

public class SampleKlass {
    public static void main(String[] args) {
        Thread thread = new Thread(() ->{
            System.out.println(Thread.currentThread());
        }, "harshit");

        thread.start();

        try {
            thread.join(); //now the child thread will eun first, blocking the main thread
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(thread.getPriority());
        thread.setPriority(9);

        System.out.println(thread.getPriority());

        System.out.println("main is executing");
    }
}
