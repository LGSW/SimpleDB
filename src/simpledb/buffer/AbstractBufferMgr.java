package simpledb.buffer;

import simpledb.file.Block;
/**
 * Abstract class to help implement different algorithm
 * @author Yupeng
 *
 */
public abstract class AbstractBufferMgr {
	protected int numAvailable;
	protected int poolCapacity;
	
	protected AbstractBufferMgr(int numOfBuffers){
		numAvailable = numOfBuffers;
		poolCapacity = numOfBuffers;
	}
	
	protected abstract void flushAll(int txnm);
	protected abstract Buffer pin(Block blk);
	protected abstract Buffer pinNew(String filename, PageFormatter fmtr);
	protected abstract void unpin(Buffer buff);
	protected abstract int available();
	protected abstract Buffer findExistingBuffer(Block blk);
	protected abstract Buffer chooseUnpinnedBuffer();
	
}
