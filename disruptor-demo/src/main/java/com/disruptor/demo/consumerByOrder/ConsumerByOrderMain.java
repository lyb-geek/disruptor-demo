package com.disruptor.demo.consumerByOrder;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 按指定顺序进行消费
 * <p>
 * Title:ConsumerByOrderMain
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
public class ConsumerByOrderMain {
	public static void main(String[] args) {
		OrderEventFactory eventFactory = new OrderEventFactory();

		int ringBufferSize = 1024 * 1024;

		/**
		 * //BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现 WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy(); //SleepingWaitStrategy
		 * 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景 WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy(); //YieldingWaitStrategy
		 * 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性 WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		 */

		Disruptor<Order> disruptor = new Disruptor<Order>(eventFactory, ringBufferSize, new OrderThreadFactory(),
				ProducerType.SINGLE, new YieldingWaitStrategy());

		// handler1和Handler2并行消费，handler1和hanlder3消费完，再轮到handler3消费
		// EventHandlerGroup<Order> group = disruptor.handleEventsWith(new Handler1(), new Handler2());
		//
		// group.then(new Handler3());
		// 按handler1，handler2，handler3顺序消费
		// disruptor.handleEventsWith(new Handler1());
		// disruptor.handleEventsWith(new Handler2());
		// disruptor.handleEventsWith(new Handler3());

		// 六边形消费
		// handler1,handler2并行消费，handler1消费后，轮到handler4消费，hangler2消费完轮到handler5消费，hangler4和handler5消费完，最后轮到handler3消费
		Handler1 handler1 = new Handler1();
		Handler2 handler2 = new Handler2();
		Handler3 handler3 = new Handler3();
		Handler4 handler4 = new Handler4();
		Handler5 handler5 = new Handler5();
		disruptor.handleEventsWith(handler1, handler2);
		disruptor.after(handler1).handleEventsWith(handler4);
		disruptor.after(handler2).handleEventsWith(handler5);
		disruptor.after(handler4, handler5).handleEventsWith(handler3);
		disruptor.start();

		RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();

		OrderProducer producer = new OrderProducer(ringBuffer);
		producer.onData();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disruptor.shutdown();

	}

}
