package simpledb.tx.concurrency;

import simpledb.server.SimpleDB;

public class TransactionTest2 {
	public static void main(String args[]) throws InterruptedException {
		SimpleDB.init(args[0], 1);
		TestX t1 = new TestX();
		new Thread(t1).start();
		TestY t2 = new TestY();
		new Thread(t2).start();
	}
}
