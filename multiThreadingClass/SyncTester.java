package multiThreadingClass;

public class SyncTester {

    public static void main(String[] args) {
        System.out.println("main is starting");
        Stack stack = new Stack(5);
        //one thread to push
        new Thread(() -> {
            int counter = 0;
            while(counter++ < 10) {
                System.out.println("pushed: 100" );
                stack.push(100);
            }
        }, "Pusher").start();
        //other thread to popSurround
        new Thread(() -> {
            int counter = 0;
            while(counter++ < 10) {
                System.out.println("popped: " + stack.pop());
            }
        }, "Popper").start();

        System.out.println("main is exiting");


    }
}
