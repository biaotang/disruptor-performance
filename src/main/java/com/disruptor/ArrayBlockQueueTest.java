package com.disruptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

public class ArrayBlockQueueTest {

    public static void main(String[] args) {
        final ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<>(100000000);
        final long startTime = System.currentTimeMillis();

        Executors.newSingleThreadExecutor().execute(() -> {
            for(long i=0; i < Constants.EVENT_NUM_FM; i++) {
                Data data = new Data(i, "c" + i);
                try {
                    queue.put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            for(long i=0; i < Constants.EVENT_NUM_OHM; i++) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("ArrayBlockingQueue costTime = " + (endTime - startTime) + "ms");
        });

    }

}
