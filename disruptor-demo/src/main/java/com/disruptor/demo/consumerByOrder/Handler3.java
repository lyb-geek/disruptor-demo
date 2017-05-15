package com.disruptor.demo.consumerByOrder;

import com.lmax.disruptor.EventHandler;

public class Handler3 implements EventHandler<Order> {

	public void onEvent(Order order, long paramLong, boolean paramBoolean) throws Exception {
		System.out.println("hanlder3 getOrder :" + order);

	}

}
