package cn.edu.nenu.acm.oj.util;

public abstract class RankListCellExpression {

	private long range[][];
	private String description;

	public long[][] getRange() {
		return range;
	}

	public String getDescription() {
		return description;
	}

	public RankListCellExpression(long[][] range, String description) {
		super();
		this.range = range;
		this.description = description;
	}
	
	/**
	 * @param val
	 * @param contestLength
	 * @return [0]: last valid submit time in ms; [1] total submittion times; [2]: 1 accepted, 0 not accepted 
	 */
	public abstract long[] getInfo(int[] val,long contestLength);
}
