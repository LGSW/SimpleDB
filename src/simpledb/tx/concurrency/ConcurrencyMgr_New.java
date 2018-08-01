package simpledb.tx.concurrency;

import simpledb.file.Block;
import java.util.*;

/**
 * The concurrency manager for the transaction. Each transaction has its own
 * concurrency manager. The concurrency manager keeps track of which locks the
 * transaction currently has, and interacts with the global lock table as
 * needed.
 * 
 * @author Edward Sciore
 */
public class ConcurrencyMgr_New {

	/**
	 * The global lock table. This variable is static because all transactions
	 * share the same table.
	 */
	private static LockTable_New locktbl = new LockTable_New();
	private Map<Block, String> locks = new HashMap<Block, String>();

	/**
	 * Obtains an SLock on the block, if necessary. The method will ask the lock
	 * table for an SLock if the transaction currently has no locks on that
	 * block.
	 * 
	 * @param blk
	 *            a reference to the disk block
	 */
	public void sLock(Block blk) {
		if (locks.get(blk) == null) {
			long timestamp = System.currentTimeMillis();
			locktbl.sLock(blk, timestamp);
			locks.put(blk, "S");
		}
	}

	/**
	 * Obtains an XLock on the block, if necessary. If the transaction does not
	 * have an XLock on that block, then the method first gets an SLock on that
	 * block (if necessary), and then upgrades it to an XLock.
	 * 
	 * @param blk
	 *            a refrence to the disk block
	 */
	public void xLock(Block blk) {
		if (!hasXLock(blk)) {
			long timestamp = System.currentTimeMillis();
			sLock(blk);
			locktbl.xLock(blk, timestamp);
			locks.put(blk, "X");
		}
	}

	/**
	 * Releases all locks by asking the lock table to unlock each one.
	 */
	public void release() {
		for (Block blk : locks.keySet())
			locktbl.unlock(blk);
		locks.clear();
	}

	private boolean hasXLock(Block blk) {
		String locktype = locks.get(blk);
		return locktype != null && locktype.equals("X");
	}
}