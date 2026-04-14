package multiThreadingClass;

public class QueueTester {

    public static void main(String[] args) {

        BlockingQueue queue = new BlockingQueue(5);

        // Producer thread
        Thread producer = new Thread(() -> {
            int value = 1;
            while (true) {
                System.out.println("Producing: " + value);
                queue.add(value++);

                try {
                    Thread.sleep(500); // slow producer
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    int val = queue.remove();
                    System.out.println(producer.getState());
                    System.out.println("Consumed: " + val);

                    Thread.sleep(1000); // slow consumer
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        System.out.println(producer.getState());



        producer.start();
        consumer.start();
    }
}