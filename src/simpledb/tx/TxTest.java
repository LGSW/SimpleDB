package simpledb.tx;

import static org.junit.Assert.*;

import org.junit.Test;

import simpledb.file.Block;
import simpledb.server.SimpleDB;

public class TxTest {

	@Test
	public void testTransaction() {
		SimpleDB.init("studentdbTest", 1);
		// to initate the database one more transaction must be setted so the
		// number start from 2
		Transaction tx2 = new Transaction();
		Block blk2 = new Block("test2.tbl", 0);
		tx2.pin(blk2);

		Transaction tx3 = new Transaction();
		Block blk3 = new Block("test3.tbl", 0);
		tx3.pin(blk3);

		Transaction tx4 = new Transaction();
		Block blk4 = new Block("test4.tbl", 0);
		tx4.pin(blk4);

		Transaction tx5 = new Transaction();
		Block blk5 = new Block("test5.tbl", 0);
		tx5.pin(blk5);

		tx3.commit();
		tx4.commit();
		tx5.commit();
	}

}
