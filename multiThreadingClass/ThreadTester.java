package multiThreadingClass;

public class ThreadTester {


    public static void main(String[] args) {
        System.out.println("inside main thread");

        System.out.println();
        Thread thread1 = new Thread1("rahul");
        thread1.start(); //to start the new thread in parallel
//        thread1.setDaemon(true); shodlnt be done after thread starts, will result in exception in current htread and will stop its esxecution, but other non daemon threads would keep running
//        thread1.run(); //dont do this this will make synchronous behavuour, no concurrenxcy


        Thread thread2 = new Thread(new Thread2(), "nitin"); //threads made using runnable interface are created this way
        thread2.start();


        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("inside " + Thread.currentThread().getName()+  " value => " + i);
            }
        }, "karan");
        thread3.start();

        System.out.println("outside main thread");





    }
}
