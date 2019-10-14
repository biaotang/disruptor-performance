package com.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

public class DisruptorTest {

    public static void main(String[] args) {

        int ringBufferSize = 65536;
        final Disruptor<Data> disruptor = new Disruptor<>(() -> new Data(),
                ringBufferSize, Executors.newSingleThreadExecutor(), ProducerType.SINGLE, new YieldingWaitStrategy());

        disruptor.handleEventsWith(new DataConsumer());
        RingBuffer<Data> ringBuffer = disruptor.start();

        Executors.newSingleThreadExecutor().execute(() -> {
            for (long i=0; i<Constants.EVENT_NUM_OHM; i++) {
                long seq = ringBuffer.next();
                Data data = ringBuffer.get(seq);
                data.setId(i);
                data.setName("c" + i);
                ringBuffer.publish(seq);
            }
        });

    }

}
