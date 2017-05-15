package com.disruptor.demo;

import com.lmax.disruptor.EventHandler;

// 我们还需要一个事件消费者，也就是一个事件处理器。这个事件处理器简单地把事件中存储的数据打印到终端：
public class LongEventHandle implements EventHandler<LongEvent> {

	public void onEvent(LongEvent arg0, long arg1, boolean arg2) throws Exception {
		System.out.println("接收到数据：" + arg0.getValue() + ",并成功消费");

	}

}
