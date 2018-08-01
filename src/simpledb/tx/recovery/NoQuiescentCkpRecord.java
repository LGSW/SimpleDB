package simpledb.tx.recovery;

public class NoQuiescentCkpRecord implements LogRecord {
	private int[] txnums;
	private int size = 0;
	private int next = 0;

	public NoQuiescentCkpRecord(int[] UnCommitedTxs) {
		for (int i = 0; i < UnCommitedTxs.length; i++) {
			txnums[i] = UnCommitedTxs[i];
		}
		size = UnCommitedTxs.length;
	}

	public int writeToLog() {
		Object[] rec = new Object[] { NQCKPT, txnums };
		return logMgr.append(rec);
	}

	public int op() {
		return NQCKPT;
	}

	public int txNumber() {
		if (next < size) {
			return txnums[next++];
		}
		return -1;
	}

	public void undo(int txnum) {
	}

	public String toString() {
		String record = "< NQCKPT";
		for (int i = 0; i < txnums.length; i++) {
			record = record + " " + Integer.toString(txnums[i]);
		}
		record = record + " >";
		return record;
	}
}
