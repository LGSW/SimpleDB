package simpledb.tx.concurrency;
import simpledb.file.Block;
import simpledb.tx.Transaction;

class TestY implements Runnable{
	public void run(){
		try{
			Transaction tx= new Transaction();
			Block blk1= new Block("raven",1);
			Block blk2= new Block("raven",2);
			tx.pin(blk1);
			tx.pin(blk2);
			System.out.println("Tx X: write 2 start");
			tx.setInt(blk2,0,0);
			System.out.println("Tx X: write 2 end");
			Thread.sleep(1000);
			System.out.println("Tx Y: write 1 start");
			tx.setInt(blk1,0,0);
			System.out.println("Tx Y: write 1 end");
			tx.commit();
		}
		catch(InterruptedException e){};
	}
}