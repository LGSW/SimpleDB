package simpledb.server;

import simpledb.remote.*;
import java.rmi.registry.*;

public class Startup {
   public static void main(String args[]) throws Exception {
      // configure and initialize the database
	   int bufferMgr = 0;
	   
		for (String s : args) {
			System.out.println(s);
			if (s.equals("-LRU")) {
				bufferMgr = 1;
			} else if (s.equals("-FIFO")) {
				bufferMgr = 2;
			}
		}
      SimpleDB.init(args[0], bufferMgr);
      
      // create a registry specific for the server on the default port
      Registry reg = LocateRegistry.createRegistry(1099	);
      
      // and post the server entry in it
      RemoteDriver d = new RemoteDriverImpl();
      reg.rebind("simpledb", d);
      
      System.out.println("database server ready");
      
   }
}
