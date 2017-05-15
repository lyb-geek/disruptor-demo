package com.disruptor.demo.consumerByOrder;

import com.lmax.disruptor.EventHandler;

public class Handler4 implements EventHandler<Order> {

	public void onEvent(Order order, long paramLong, boolean paramBoolean) throws Exception {
		System.out.println("handler4 get name :" + order.getOrderName());
		order.setOrderName(order.getOrderName() + "-h4");
		Thread.sleep(1000);

	}

}
