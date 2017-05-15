package com.disruptor.demo;

import java.nio.ByteBuffer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class LongEventProducerWithTranslator implements EventTranslatorOneArg<LongEvent, ByteBuffer> {

	private RingBuffer<LongEvent> ringBuffer;

	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		super();
		this.ringBuffer = ringBuffer;
	}

	// 一个translator可以看做一个事件初始化器，publicEvent方法会调用它
	// 填充Event
	public void translateTo(LongEvent paramT, long paramLong, ByteBuffer paramA) {
		// TODO Auto-generated method stub
		paramT.setValue(paramA.getLong(0));
	}

	public void onData(ByteBuffer bb) {
		ringBuffer.publishEvent(this, bb);
	}

}
