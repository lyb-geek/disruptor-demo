package com.disruptor.demo.eventProcessor;

import com.lmax.disruptor.EventHandler;

public class OrderEventHandler implements EventHandler<Order> {

	public void onEvent(Order order, long paramLong, boolean paramBoolean) throws Exception {
		System.out.println("成功接收到订单，即将进行消费：" + order);

	}

}
