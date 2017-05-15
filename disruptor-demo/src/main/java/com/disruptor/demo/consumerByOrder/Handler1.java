package com.disruptor.demo.consumerByOrder;

import com.lmax.disruptor.EventHandler;

public class Handler1 implements EventHandler<Order> {

	public void onEvent(Order order, long paramLong, boolean paramBoolean) throws Exception {
		System.out.println("handler1 set name");
		order.setOrderName("order-h1");
		Thread.sleep(1000);
	}

}
