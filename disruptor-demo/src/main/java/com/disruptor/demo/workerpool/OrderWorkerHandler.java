package com.disruptor.demo.workerpool;

import com.lmax.disruptor.WorkHandler;

public class OrderWorkerHandler implements WorkHandler<Order> {

	public void onEvent(Order order) throws Exception {
		System.out.println("成功接收到订单，即将进行消费：" + order);

	}

}
