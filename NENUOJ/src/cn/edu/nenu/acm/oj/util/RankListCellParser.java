package cn.edu.nenu.acm.oj.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RankListCellParser {
	
	public static List<RankListCellExpression>  rankListCellExpressions = new LinkedList<RankListCellExpression>();
	/**
	 * build the recognize pattern, base on ISun's virtual-judge work.
	 * @author Winguse
	 */
	static{
		rankListCellExpressions.add(
		new RankListCellExpression(
				new long[][]{{}},
				"No submisson"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{contestLength, 0, 0};
			}
		});//1
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{}},
				"Not solved, with one wrong submission"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{contestLength, 1, 0};
			}
		});//2
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE}},
				"Solved at $1 minute with no wrong submisson"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L, 1, 1};
			}
		});//3
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE}},
				"Not solved, with $1 wrong submission(s)"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L, 2, 1};
			}
		});//4

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 hour $2 minute with no wrong submisson"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, 1, 1};
			}
		});//5

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 hour $2 minute with one wrong submission"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, 2, 1};
			}
		});//6

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $1 minute with $2 submission(s)"
		){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L, val[1], 1};
			}
		});//7
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $1 minute with $2 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L, val[1] + 1, 1};
			}
		});//8
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $2 minute with $1 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 60000L, val[0], 1};
			}
		});//9
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Solved at $2 minute with $1 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 60000L, val[0] + 1, 1};
			}
		});//10

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59}},
				"Solved at $1 hour $2 minute $3 second with no wrong submission"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, 1, 1};
			}
		});//11
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute with $3 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, val[2], 1};
			}
		});//12
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute with $3 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, val[2] + 1, 1};
			}
		});//13
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $2 hour $3 minute with $1 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L, val[0], 1};
			}
		});//14
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59}},
				"Solved at $2 hour $3 minute with $1 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L, val[0] + 1, 1};
			}
		});//15
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute $3 second with $4 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, val[3], 1};
			}
		});//16
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59},{0,Long.MAX_VALUE}},
				"Solved at $1 hour $2 minute $3 second with $4 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, val[3] + 1, 1};
			}
		});//17
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59},{0,59}},
				"Solved at $2 hour $3 minute $4 second with $1 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L + val[3] * 1000L, val[0], 1};
			}
		});//18

		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59},{0,59}},
				"Solved at $2 hour $3 minute $4 second with $1 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L + val[3] * 1000L, val[0] + 1, 1};
			}
		});//19
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Not solved, with $1 wrong submissions, the last one at $2 minute"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 60000L, val[0], 0};
			}
		});//20
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59}},
				"Not solved, with $1 wrong submissions, the last one at $2 hour $3 minute"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L, val[0], 0};
			}
		});//21
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE},{0,59},{0,59}},
				"Not solved, with $1 wrong submissions, the last one at $2 hour $3 minute $4 second"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 3600000L + val[2] * 60000L + val[3] * 1000L, val[0], 0};
			}
		});//22
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,Long.MAX_VALUE}},
				"Not solved, with $2 wrong submissions, the last one at $1 minute"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L, val[1], 0};
			}
		});//23
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,Long.MAX_VALUE}},
				"Not solved, with $3 wrong submissions, the last one at $1 hour $2 minute"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L, val[2], 0};
			}
		});//24		
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0,59},{0,Long.MAX_VALUE}},
				"Not solved, with $4 wrong submissions, the last one at $1 hour $2 minute $3 second"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 3600000L + val[1] * 60000L + val[2] * 1000L, val[3], 0};
			}
		});//25
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE}},
				"Not solved, with one wrong submission, at $1 minute"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L, 1, 0};
			}
		});//26
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 minute $2 second with no wrong submisson"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, 1, 1};
			}
		});//27
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 minute $2 second with one wrong submission"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, 2, 1};
			}
		});//28
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $1 minute $2 second with one wrong submission"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, 2, 1};
			}
		});//28
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0L,Long.MAX_VALUE}},
				"Solved at $1 minute $2 second with $3 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, val[2], 1};
			}
		});//29
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0L,Long.MAX_VALUE}},
				"Solved at $1 minute $2 second with $3 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, val[2] + 1, 1};
			}
		});//30
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $2 minute $3 second with $1 submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 60000L + val[2] * 1000L, val[0], 1};
			}
		});//31
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Solved at $2 minute $3 second with $1 wrong submission(s)"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 60000L + val[2] * 1000L, val[0] + 1, 1};
			}
		});//32
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0L,Long.MAX_VALUE},{0,59}},
				"Not solved, with $1 wrong submissions, the last one at $2 minute $3 second"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[1] * 60000L + val[2] * 1000L, val[0], 0};
			}
		});//33
		
		rankListCellExpressions.add(new RankListCellExpression(
				new long[][]{{0L,Long.MAX_VALUE},{0,59},{0L,Long.MAX_VALUE}},
				"Not solved, with $3 wrong submissions, the last one at $1 minute $2 second"
				){
			public long[] getInfo(Integer val[],long contestLength){
				return new long[]{val[0] * 60000L + val[1] * 1000L, val[2], 0};
			}
		});//34
	}
	

	private static final Pattern numberPattern = Pattern.compile("\\d+");
	
	private Map<String,Pair<String,List<RankListCellExpression>>> patterns = new HashMap<String,Pair<String,List<RankListCellExpression>>>();
	private long contestLength;
	
	/**
	 * recognize the cellString, find if exist a pattern previous, or new a pattern of it.
	 * @param cellString
	 */
	public void recognize(String cellString){
		if("".equals(cellString))return;
		String patternString = cellString.trim().replaceAll("([\\$\\^\\|\\[\\]\\{\\}\\(\\)\\.\\*\\?\\+\\\\])", "\\\\$1").replaceAll("\\d+", "(\\\\d+)");
		Matcher m = numberPattern.matcher(cellString);
		List<Integer> values = new LinkedList<Integer>();
		while(m.find()){
			values.add(Integer.parseInt(m.group()));
		}
		if(patterns.containsKey(patternString)){
			List<RankListCellExpression> matchedExpressions = patterns.get(patternString).second;
			Iterator<RankListCellExpression> itr = matchedExpressions.iterator();
			while(itr.hasNext()){
				RankListCellExpression exp = itr.next();
				if(!exp.isMatch(values.toArray(new Integer[0]),contestLength)){
					itr.remove();//remove invalid pattern
				}
			}
		}else{
			String exampleString = cellString;
			List<RankListCellExpression> matchedExpressions = new LinkedList<RankListCellExpression>();
			for(RankListCellExpression exp:rankListCellExpressions){
				if(exp.isMatch(values.toArray(new Integer[0]),contestLength)){
					matchedExpressions.add(exp);
				}
			}
			patterns.put(patternString, new Pair<String,List<RankListCellExpression>>(exampleString,matchedExpressions));
		}
	}

	public long getContestLength() {
		return contestLength;
	}

	public void setContestLength(long contestLength) {
		this.contestLength = contestLength;
	}

	public Map<String, Pair<String, List<RankListCellExpression>>> getPatterns() {
		return patterns;
	}
	
	
}
