package com.disruptor.demo.workerpool;

import com.lmax.disruptor.EventFactory;

public class OrderEventFactory implements EventFactory<Order> {

	public Order newInstance() {
		// TODO Auto-generated method stub
		return new Order();
	}

}
