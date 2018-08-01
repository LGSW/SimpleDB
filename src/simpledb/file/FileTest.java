package simpledb.file;
import static org.junit.Assert.*;
import org.junit.Test;
import simpledb.server.SimpleDB;

public class FileTest {
	@Test
	public void testBlock(){
		SimpleDB.initFileMgr("studentdb");
		FileMgr fm = SimpleDB.fileMgr();
		Block blk = new Block("junk", 6);
		Page p2 = new Page();
		p2.setString(20, "hello");
		blk = p2.append("junk");
		Page p3 = new Page();
		p3.read(blk);
		String s = p3.getString(20);
		assertEquals("hello", s);
	}

}
