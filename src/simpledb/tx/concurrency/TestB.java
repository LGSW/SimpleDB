package simpledb.tx.concurrency;
import simpledb.file.Block;
import simpledb.tx.Transaction;

class TestB implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("junk",1);
			Block blk2= new Block("junk",2);
			tx.pin(blk1);
			tx.pin(blk2);
			System.out.println("Tx B: write 2 start");
			tx.setInt(blk2,0,0);
			System.out.println("Tx B: write 2 end");
			Thread.sleep(1000);
			System.out.println("Tx B: read 1 start");
			tx.getInt(blk1,0);
			System.out.println("Tx B: read 1 end");
			tx.commit();
		}
		catch(InterruptedException e){};
	}
}
