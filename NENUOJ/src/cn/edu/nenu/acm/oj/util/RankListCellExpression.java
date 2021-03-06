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
	
	
	public boolean isMatch(Integer val[],long contestLength){
		int rLength = range.length;
		if(range.length == 1 && range[0].length == 0){
			rLength = 0;
		}
		if(val.length!=rLength)return false;
		for(int i = 0;i<rLength;i++)
			if(val[i]<range[i][0]||range[i][1]<val[i])
				return false;
		if(getInfo(val,contestLength)[0]>contestLength)return false;
		return true;
	}
	
	/**
	 * @param val
	 * @param contestLength
	 * @return [0]: last valid submit time in ms; [1] total submittion times; [2]: 1 accepted, 0 not accepted 
	 */
	public abstract long[] getInfo(Integer val[],long contestLength);
}
