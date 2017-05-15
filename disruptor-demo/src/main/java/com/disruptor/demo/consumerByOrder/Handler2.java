package com.disruptor.demo.consumerByOrder;

import com.lmax.disruptor.EventHandler;

public class Handler2 implements EventHandler<Order> {

	public void onEvent(Order order, long paramLong, boolean paramBoolean) throws Exception {
		System.out.println("hanlder2 set price");
		order.setPrice(200);
		Thread.sleep(1000);

	}

}
