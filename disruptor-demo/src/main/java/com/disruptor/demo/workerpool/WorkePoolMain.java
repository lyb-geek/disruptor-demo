package com.disruptor.demo.workerpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;

public class WorkePoolMain {
	public static void main(String[] args) {
		RingBuffer<Order> ringBuffer = RingBuffer.createSingleProducer(new OrderEventFactory(), 1024 * 1024);

		Sequence[] sequencesToTrack = new Sequence[] {};
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier(sequencesToTrack);

		WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(),
				new OrderWorkerHandler());
		ExecutorService executor = Executors.newFixedThreadPool(10);

		ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
		workerPool.start(executor);

		OrderProducer orderProducer = new OrderProducer(ringBuffer);
		orderProducer.onData();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		workerPool.halt();
		executor.shutdown();

	}

}
