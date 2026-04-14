package multiThreadingClass;

public class DeadlockClass {

    public static void main(String[] args) throws InterruptedException {


        System.out.println("main is starting");
        String lock1 = "harsh";
        String lock2 = "verma";


        Thread t1 = new Thread(()->{
            synchronized (lock1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2){
                    System.out.println("locks aquired");
                }

            }
        }, "thread1");

        Thread t2 = new Thread(()->{
            synchronized (lock2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1){
                    System.out.println("locks aquired");
                }

            }
        }, "thread2");


        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("main is exiitng");
    }
}
