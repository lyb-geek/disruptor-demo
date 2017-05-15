package com.disruptor.demo;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 在Disruptor中，我们想实现hello world 需要如下几步骤： 第一：建立一个Event类 第二：建立一个工厂Event类，用于创建Event类实例对象 第三：需要有一个监听事件类，用于处理数据（Event类） 第四：我们需要进行测试代码编写。实例化Disruptor实例，配置一系列参数。然后我们对Disruptor实例绑定监听事件类，接受并处理数据。
 * 第五：在Disruptor中，真正存储数据的核心叫做RingBuffer，我们通过Disruptor实例拿到它，然后把数据生产出来，把数据加入到RingBuffer的实例对象中即可。
 * 
 * <p>
 * Title:LongDisruptorMain
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author linyb
 * @date 2017年4月15日
 */
public class LongDisruptorMain {
	public static void main(String[] args) {

		LongEventFactory eventFactory = new LongEventFactory();

		ExecutorService executor = Executors.newCachedThreadPool();

		int ringBufferSize = 1024 * 1024;

		/**
		 * //BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现 WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy(); //SleepingWaitStrategy
		 * 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景 WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy(); //YieldingWaitStrategy
		 * 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性 WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		 */

		// Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());

		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory, ringBufferSize, new LongThreadFactory(),
				ProducerType.SINGLE, new YieldingWaitStrategy());
		// 连接消费事件方法
		disruptor.handleEventsWith(new LongEventHandle());

		// 启动
		disruptor.start();

		// Disruptor 的事件发布过程是一个两阶段提交的过程：
		// 发布事件
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

		// LongEventProducer producer = new LongEventProducer(ringBuffer);

		LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

		ByteBuffer buffer = ByteBuffer.allocate(8);

		for (int i = 0; i < 10; i++) {
			buffer.putLong(0, Long.valueOf(i + ""));
			producer.onData(buffer);
		}

		disruptor.shutdown();

	}

}
