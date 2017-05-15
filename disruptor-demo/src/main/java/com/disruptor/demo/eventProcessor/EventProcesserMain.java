package com.disruptor.demo.eventProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;

public class EventProcesserMain {
	public static void main(String[] args) {
		/*
		 * createSingleProducer创建一个单生产者的RingBuffer， 第一个参数叫EventFactory，从名字上理解就是"事件工厂"，其实它的职责就是产生数据填充RingBuffer的区块。 第二个参数是RingBuffer的大小，它必须是2的指数倍 目的是为了将求模运算转为&运算提高效率
		 * 第三个参数是RingBuffer的生产都在没有可用区块的时候(可能是消费者（或者说是事件处理器） 太慢了)的等待策略
		 */
		RingBuffer<Order> ringBuffer = RingBuffer.createSingleProducer(new OrderEventFactory(), 1024 * 1024);

		Sequence[] sequencesToTrack = new Sequence[] {};
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier(sequencesToTrack);

		// 创建消息处理器
		BatchEventProcessor<Order> eventProcessor = new BatchEventProcessor<Order>(ringBuffer, sequenceBarrier,
				new OrderEventHandler());
		ExecutorService executor = Executors.newFixedThreadPool(10);

		// 这一步的目的就是把消费者的位置信息引用注入到生产者 如果只有一个消费者的情况可以省略
		ringBuffer.addGatingSequences(eventProcessor.getSequence());

		executor.submit(eventProcessor);

		OrderProducer orderProducer = new OrderProducer(ringBuffer);
		orderProducer.onData();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eventProcessor.halt();// 通知事件(或者说消息)处理器 可以结束了（并不是马上结束!!!）
		executor.shutdown();

	}

}
