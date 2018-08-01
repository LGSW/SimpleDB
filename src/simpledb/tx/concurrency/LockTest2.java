package simpledb.tx.concurrency;

import static org.junit.Assert.*;
import org.junit.Test;
import simpledb.file.Block;

public class LockTest2 {
	Block B1 = new Block("B1", 1);
	Block B2 = new Block("B2", 2);
	Block B3 = new Block("B3", 3);
	ConcurrencyMgr_New c1 = new ConcurrencyMgr_New();
	ConcurrencyMgr_New c2 = new ConcurrencyMgr_New();

	@Test
	public void test() throws InterruptedException {
		/*
		 * c1.sLock(B1); Thread.sleep(1000); c2.xLock(B1);
		 */
		Runnable1 r = new Runnable1();
		// r.run();并不是线程开启，而是简单的方法调用
		Thread t = new Thread(r);// 创建线程
		// t.run(); //如果该线程是使用独立的 Runnable 运行对象构造的，则调用该 Runnable 对象的 run
		// 方法；否则，该方法不执行任何操作并返回。
		t.start(); // 线程开启
		System.out.println("111");
		Thread.sleep(1000);
		c2.sLock(B1);
		// Thread.sleep(10000);
	}

	class Runnable1 implements Runnable {
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("222");
			c1.xLock(B1);
			System.out.println("333");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c1.release();
			System.out.println("444");
		}
	}
}
