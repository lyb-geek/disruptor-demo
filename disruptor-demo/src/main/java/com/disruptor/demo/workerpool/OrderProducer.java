package com.disruptor.demo.workerpool;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;

public class OrderProducer implements EventTranslator<Order> {

	private RingBuffer<Order> ringBuffer;

	public OrderProducer(RingBuffer<Order> ringBuffer) {
		super();
		this.ringBuffer = ringBuffer;
	}

	public void translateTo(Order order, long paramLong) {
		order.setId(1);
		order.setOrderName("订单");
		order.setPrice(2);

	}

	public void onData() {
		ringBuffer.publishEvent(this);
	}

}
