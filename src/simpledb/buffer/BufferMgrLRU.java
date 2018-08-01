package simpledb.buffer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import simpledb.file.Block;
import simpledb.server.SimpleDB;

public class BufferMgrLRU extends AbstractBufferMgr {
	protected Map<Block, BufferLRU> buffers;
	
	protected BufferMgrLRU(int numOfBuffers) {
		super(numOfBuffers);
		buffers = new LinkedHashMap<Block, BufferLRU>();
	}

	@Override
	protected void flushAll(int txnm) {
		for (Block blk : buffers.keySet()){
			buffers.get(blk).flush();
		}
	}

	@Override
	protected synchronized BufferLRU pin(Block blk) {
		SimpleDB.getLogger().log(Level.INFO,"Checking whether requested pinned block"+ blk.toString() +" is already being stored in buffer pool");
		BufferLRU buff = findExistingBuffer(blk);
		if (buff == null){
			SimpleDB.getLogger().log(Level.INFO,"None of buffers hold the requested block, begin to choose uppinned buffer to hold it");
			buff = chooseUnpinnedBuffer();
			if (buff == null){
				SimpleDB.getLogger().log(Level.INFO,"All the buffers are currently being pinned, pin request is being saved to wait list");
				return null;
			}
			if (buff.block() == null){
				SimpleDB.getLogger().log(Level.INFO,"generate new Buffer to hold requested block");
			}else{
				SimpleDB.getLogger().log(Level.INFO,"Find the LRU unpinned buffer which holds" + buff.block().toString());
			}
			buffers.remove(buff.block());
			buff.assignToBlock(blk);	
			buffers.put(blk, buff);
		}
		SimpleDB.getLogger().log(Level.INFO,"Requested block is stored in buffer, pinned it one more time");
		if (!buff.isPinned())
	         numAvailable--;
		  //why pin several times?
	      buff.pin();
	      return buff;
	}

	@Override
	protected synchronized BufferLRU pinNew(String filename, PageFormatter fmtr) {
		BufferLRU buff = chooseUnpinnedBuffer();
	      if (buff == null)
	         return null;
	      buff.assignToNew(filename, fmtr);
	      buffers.put(buff.block(), buff);
	      numAvailable--;
	      buff.pin();
	      return buff;
	}
	
	@Override
	protected synchronized void unpin(Buffer buff) {
		buff.unpin();
	    if (!buff.isPinned()){
	    	numAvailable++;
	    }
	}
	
	protected synchronized void unpin(BufferLRU buff){
		buff.unpin();
		long time = System.currentTimeMillis();
		buff.setUnPinnedTime(time);
		if (!buff.isPinned()){
			numAvailable++;
		}
	}

	@Override
	protected int available() {
		return numAvailable;
	}

	@Override
	protected BufferLRU findExistingBuffer(Block blk) {
		if (buffers.containsKey(blk)){
			return buffers.get(blk);
		}
		return null;
	}

	@Override
	protected BufferLRU chooseUnpinnedBuffer() {
		long earlyTime = Long.MAX_VALUE;
		Block mark = null;
		if (buffers.size() < super.poolCapacity){
			BufferLRU temp = new BufferLRU();
			int left = super.poolCapacity - buffers.size() - 1;
			SimpleDB.getLogger().log(Level.INFO,"generate new Buffer to use and free buffer left" + left);
			return temp;
		}
		
		for (Block blk : buffers.keySet()){
			if (!buffers.get(blk).isPinned()) {
				if (buffers.get(blk).getUnPinnedTime() < earlyTime){
					earlyTime = buffers.get(blk).getUnPinnedTime();
					mark = blk;
				}
			}
		}
		if (buffers.containsKey(mark)){
			SimpleDB.getLogger().log(Level.INFO, "find the least recently used buffer which has block" + mark.toString());
			return buffers.get(mark);
		}else{
			return null;
		}
	}
	
	private boolean hasFreeBuffer(){
		if (this.buffers.size() < super.poolCapacity){
			return true;
		}else{
			return false;
		}
	}
}
