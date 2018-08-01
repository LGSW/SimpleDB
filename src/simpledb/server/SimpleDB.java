package simpledb.server;

import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import simpledb.file.FileMgr;
import simpledb.buffer.*;
import simpledb.tx.Transaction;
import simpledb.log.LogMgr;
import simpledb.metadata.MetadataMgr;
import simpledb.planner.*;
import simpledb.opt.HeuristicQueryPlanner;
import simpledb.index.planner.IndexUpdatePlanner;

/**
 * The class that provides system-wide static global values. These values must
 * be initialized by the method {@link #init(String) init} before use. The
 * methods {@link #initFileMgr(String) initFileMgr},
 * {@link #initFileAndLogMgr(String) initFileAndLogMgr},
 * {@link #initFileLogAndBufferMgr(String) initFileLogAndBufferMgr}, and
 * {@link #initMetadataMgr(boolean, Transaction) initMetadataMgr} provide
 * limited initialization, and are useful for debugging purposes.
 * 
 * @author Edward Sciore
 */
public class SimpleDB {
	public static int BUFFER_SIZE = 8;
	public static String LOG_FILE = "simpledb.log";

	private static FileMgr fm;
	private static BufferMgr bm;
	private static LogMgr logm;
	private static MetadataMgr mdm;

	public static String Buffer_Basic_log = "cs542_basic_log";
	public static String Buffer_LRU_log = "cs542_LRU_log";
	public static String Buffer_FIFO_log = "cs542_FIFO_log";
	public static String RecMgr_log = "cs542_RecMgr_log";
	/**
	 * Initializes the system. This method is called during system startup.
	 * 
	 * @param dirname
	 *            the name of the database directory
	 */
	private static Logger logger;
	private static Logger recLogger;

	// Logger for ouput log
	public static Logger getLogger() {
		return logger;
	}

	public static Logger getRecLogger() {
		return recLogger;
	}

	public static void init(String dirname, int bufferMgr) {
		System.out.println(dirname);
		initFileLogAndBufferMgr(dirname, bufferMgr);
		Transaction tx = new Transaction();
		boolean isnew = fm.isNew();
		if (isnew) {
			System.out.println("creating new database");
			logger.log(Level.INFO, "Creating new database");
		} else {
			System.out.println("recovering existing database");
			logger.log(Level.INFO, "Recovering existing database");
			tx.recover();
		}

		initMetadataMgr(isnew, tx);
		tx.commit();
	}

	// The following initialization methods are useful for
	// testing the lower-level components of the system
	// without having to initialize everything.

	/**
	 * Initializes only the file manager.
	 * 
	 * @param dirname
	 *            the name of the database directory
	 */
	public static void initFileMgr(String dirname) {
		fm = new FileMgr(dirname);
	}

	/**
	 * Initializes the file and log managers.
	 * 
	 * @param dirname
	 *            the name of the database directory
	 */
	public static void initFileAndLogMgr(String dirname, String log_name) {
		initFileMgr(dirname);
		logm = new LogMgr(LOG_FILE);
		try {
			Locale.setDefault(Locale.ENGLISH);
			FileHandler logFileHandler = new FileHandler(log_name);
			logFileHandler.setLevel(Level.ALL);

			SimpleFormatter simpleFormatter = new SimpleFormatter();
			logFileHandler.setFormatter(simpleFormatter);

			logger = Logger.getGlobal();
			logger.setUseParentHandlers(false);

			logger.addHandler(logFileHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Locale.setDefault(Locale.ENGLISH);
			FileHandler logFileHandler = new FileHandler(RecMgr_log);
			logFileHandler.setLevel(Level.ALL);

			SimpleFormatter simpleFormatter = new SimpleFormatter();
			logFileHandler.setFormatter(simpleFormatter);

			recLogger = Logger.getGlobal();
			recLogger.setUseParentHandlers(false);

			recLogger.addHandler(logFileHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initializes the file, log, and buffer managers.
	 * 
	 * @param dirname
	 *            the name of the database directory
	 */
	public static void initFileLogAndBufferMgr(String dirname, int bufferMgrType) {
		String log_name = Buffer_Basic_log;
		switch (bufferMgrType) {
		case 1:
			log_name = Buffer_LRU_log;
			break;
		case 2:
			log_name = Buffer_FIFO_log;
			break;
		default:
			break;
		}
		initFileAndLogMgr(dirname, log_name);
		bm = new BufferMgr(BUFFER_SIZE, bufferMgrType);

	}

	/**
	 * Initializes metadata manager.
	 * 
	 * @param isnew
	 *            an indication of whether a new database needs to be created.
	 * @param tx
	 *            the transaction performing the initialization
	 */
	public static void initMetadataMgr(boolean isnew, Transaction tx) {
		mdm = new MetadataMgr(isnew, tx);
	}

	public static FileMgr fileMgr() {
		return fm;
	}

	public static BufferMgr bufferMgr() {
		return bm;
	}

	public static LogMgr logMgr() {
		return logm;
	}

	public static MetadataMgr mdMgr() {
		return mdm;
	}

	/**
	 * Creates a planner for SQL commands. To change how the planner works,
	 * modify this method.
	 * 
	 * @return the system's planner for SQL commands
	 */
	public static Planner planner() {
		QueryPlanner qplanner = new BasicQueryPlanner();
		UpdatePlanner uplanner = new BasicUpdatePlanner();
		return new Planner(qplanner, uplanner);
	}
}