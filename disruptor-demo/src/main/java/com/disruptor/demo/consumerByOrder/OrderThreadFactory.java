package com.disruptor.demo.consumerByOrder;

import java.util.concurrent.ThreadFactory;

public class OrderThreadFactory implements ThreadFactory {

	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		return new Thread(r);
	}

}
