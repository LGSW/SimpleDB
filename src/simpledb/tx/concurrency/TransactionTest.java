package simpledb.tx.concurrency;
import simpledb.server.SimpleDB;

public class TransactionTest {
	public static void main(String args[]) throws InterruptedException{
		SimpleDB.init(args[0],1);
		
		TestA t1=new TestA();new Thread(t1).start();
		TestB t2=new TestB();new Thread(t2).start();
		TestC t3=new TestC();new Thread(t3).start();
	}
}
