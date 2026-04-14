package multiThreadingClass;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {

    private Queue<Integer> q;
    private int capacity;

    public BlockingQueue(int cap){
        q = new LinkedList<>();
        capacity = cap;
    }

    public boolean add(int item){
        synchronized (q){
            while(q.size() == capacity){
                try{
                    q.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            q.add(item);
            q.notifyAll();
            return true;
        }

    }
    public int remove() throws InterruptedException {
        synchronized ((q)){

            while(q.size() == 0){
                try{
                    q.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }

            int ele = q.poll();
            q.notifyAll();
            return ele;

        }

    }
}
