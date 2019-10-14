package com.disruptor;

import com.lmax.disruptor.EventHandler;

public class DataConsumer implements EventHandler<Data> {

    private long startTime;
    private int i;

    DataConsumer() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onEvent(Data data, long seq, boolean endOfBatch) throws Exception {
        if (i++ == Constants.EVENT_NUM_FM) {
            long endTime = System.currentTimeMillis();
            System.out.println("Disruptor costTime = " + (endTime - startTime) + "ms");
        }
    }
}
