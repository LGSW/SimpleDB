package simpledb.buffer;

import java.util.Comparator;

/**
 * An individual buffer support the implementation of LRU algorithm.
 * 
 * 
 */
public class BufferLRU extends Buffer{
	private long timeUnPinned;
	private long timePinned;

	public BufferLRU(){
		super();
		timeUnPinned = -1;
		timePinned = 0;
	}
	
	long getUnPinnedTime(){
		return timeUnPinned;
	}
	
	long getPinnedTime(){
		return timePinned;
	}
	
	void setPinnedTime(long time){
		timePinned = time;
	}
	
	void setUnPinnedTime(long time){
		timeUnPinned = time;
	}
//  this code can implement lru by using priority queue.
//	public static Comparator<BufferLRU> UnPinnedTimeComparator = new Comparator<BufferLRU>(){
//		public int compare(BufferLRU buff1, BufferLRU buff2){
//			long time1 = buff1.getUnPinnedTime();
//			long time2 = buff2.getUnPinnedTime();
//			
//			if (time1 > time2){
//				return -1;
//			}else{
//				return 1;
//			}	
//		}
//	};
}
