package com.disruptor.demo;

import java.util.concurrent.ThreadFactory;

public class LongThreadFactory implements ThreadFactory {

	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		return new Thread(r);
	}

}
