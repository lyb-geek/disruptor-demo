package com.disruptor.demo.consumerByOrder;

import com.lmax.disruptor.EventHandler;

public class Handler5 implements EventHandler<Order> {

	public void onEvent(Order order, long paramLong, boolean paramBoolean) throws Exception {
		System.out.println("handler5 get price :" + order.getPrice());
		order.setPrice(order.getPrice() + 100);
		Thread.sleep(1000);

	}

}
